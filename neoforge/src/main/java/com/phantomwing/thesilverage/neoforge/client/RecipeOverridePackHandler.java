package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.neoforge.Configuration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// Built-in resource pack for the silver-themed brewing-stand/comparator/repeater
// textures, gated on the override_vanilla_recipes setting (server value when synced,
// else local config). The config gate lives in the source lambda (not the event
// handler) because AddPackFindersEvent fires once but the source re-runs every reload.
@EventBusSubscriber(modid = TheSilverAge.MOD_ID, value = Dist.CLIENT)
public final class RecipeOverridePackHandler {
    private static final String PACK_ID = "builtin/" + TheSilverAge.MOD_ID + "/recipe_overrides";
    private static final String PACK_RESOURCE_ROOT = "resourcepacks/silver_recipe_overrides";

    private static volatile boolean appliedOverride = false;
    private static volatile boolean syncPending = false;

    private RecipeOverridePackHandler() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        Path packRoot = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile()
                .getContents().getContentRoots().stream()
                .map(root -> root.resolve(PACK_RESOURCE_ROOT))
                .filter(Files::exists)
                .findFirst()
                .orElse(null);
        if (packRoot == null || !Files.exists(packRoot)) {
            TheSilverAge.LOGGER.warn("Recipe-override pack root '{}' not found in the mod file; skipping pack registration.", PACK_RESOURCE_ROOT);
            return;
        }

        event.addRepositorySource(consumer -> {
            boolean enabled = desiredOverride();
            appliedOverride = enabled;
            if (!enabled) return;

            PackLocationInfo location = new PackLocationInfo(
                    PACK_ID,
                    Component.literal("The Silver Age: Recipe-Override Textures"),
                    PackSource.BUILT_IN,
                    Optional.empty());
            // required=true force-selects the pack at the initial load (no reload for default-on); TOP overrides vanilla.
            PackSelectionConfig selection = new PackSelectionConfig(true, Pack.Position.TOP, true);

            Pack pack = Pack.readMetaAndCreate(
                    location,
                    new PathPackResources.PathResourcesSupplier(packRoot),
                    PackType.CLIENT_RESOURCES,
                    selection);
            if (pack != null) {
                consumer.accept(pack);
            } else {
                TheSilverAge.LOGGER.warn("Recipe-override pack at '{}' has no readable metadata; skipping.", PACK_RESOURCE_ROOT);
            }
        });
    }

    @SubscribeEvent
    public static void onConfigReloaded(@NotNull ModConfigEvent.Reloading event) {
        if (!event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) return;
        syncFromState();
    }

    // Server value wins when synced; else local config. Defensive read in case the spec hasn't loaded.
    private static boolean desiredOverride() {
        boolean local;
        try {
            local = Configuration.OVERRIDE_VANILLA_RECIPES.get();
        } catch (Exception ignored) {
            local = true;
        }
        return ServerOverrideState.effective(local);
    }

    // Request a pack-state sync; the reload is deferred to clientTick() (reloading during world-join freezes the client).
    public static void syncFromState() {
        syncPending = true;
    }

    // Reloads only when the desired state differs from what is applied, and only once it is safe.
    public static void clientTick() {
        if (!syncPending) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        if (mc.getOverlay() != null) return;                   // a resource reload is already in progress
        if (mc.screen instanceof LevelLoadingScreen) return; // still joining a world — reloading now hangs
        syncPending = false;
        if (appliedOverride != desiredOverride()) {
            mc.reloadResourcePacks();
        }
    }
}
