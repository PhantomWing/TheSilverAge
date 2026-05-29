package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.neoforge.Configuration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
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
import java.util.ArrayList;
import java.util.Optional;

/**
 * Built-in resource pack housing the silver-themed brewing-stand / comparator /
 * repeater textures + models. Whether it applies tracks the
 * {@code override_vanilla_recipes} setting — the server's value when connected
 * to one that synced it ({@link ServerOverrideState}), else the local config —
 * so the textures stay consistent with the (server-driven) recipe overrides.
 *
 * <p><b>Why selection toggling, not conditional registration.</b>
 * {@link AddPackFindersEvent} fires only ONCE, when the {@link PackRepository}
 * is constructed; {@code reloadResourcePacks()} re-runs the existing repository
 * sources but does NOT re-fire the event. So a source added conditionally at
 * startup is permanent — gating registration on the config and reloading would
 * never re-evaluate it (verified: silver textures persisted on a server with
 * the override off). Instead we register the pack <em>unconditionally</em> and
 * toggle its <em>selection</em> via {@link PackRepository#setSelected} +
 * {@code reloadResourcePacks()}. This mirrors the Fabric handler exactly.</p>
 *
 * <p>The pack is {@code required=false} so its selection can be toggled. Initial
 * activation comes from the join sync (single-player included, via the
 * integrated server) / config-save listener calling {@link #syncFromState()};
 * the selection is persisted to {@code options.txt} thereafter, so a launch
 * that should show the textures has them pre-selected with no reload flash.</p>
 */
@EventBusSubscriber(modid = TheSilverAge.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RecipeOverridePackHandler {
    private static final String PACK_ID = "builtin/" + TheSilverAge.MOD_ID + "/recipe_overrides";
    private static final String PACK_RESOURCE_ROOT = "resourcepacks/silver_recipe_overrides";

    private RecipeOverridePackHandler() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        Path packRoot = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile()
                .findResource(PACK_RESOURCE_ROOT);
        if (packRoot == null || !Files.exists(packRoot)) {
            // In a dev environment common's resources may not be on the NeoForge
            // mod file's resource path (see the processResources copy in
            // neoforge/build.gradle); in a broken build the pack dir could be
            // missing entirely. Either way, skip rather than feed a null pack
            // into the repository, which NPEs during pack discovery.
            TheSilverAge.LOGGER.warn("Recipe-override pack root '{}' not found in the mod file; skipping pack registration.", PACK_RESOURCE_ROOT);
            return;
        }

        PackLocationInfo location = new PackLocationInfo(
                PACK_ID,
                Component.literal("The Silver Age: Recipe-Override Textures"),
                PackSource.BUILT_IN,
                Optional.empty());
        // required=false so the selection is toggleable; fixedPosition=true keeps
        // it at TOP priority (overriding vanilla) whenever it IS selected.
        PackSelectionConfig selection = new PackSelectionConfig(false, Pack.Position.TOP, true);

        Pack pack = Pack.readMetaAndCreate(
                location,
                new PathPackResources.PathResourcesSupplier(packRoot),
                PackType.CLIENT_RESOURCES,
                selection);
        if (pack == null) {
            // readMetaAndCreate returns null when pack.mcmeta can't be read.
            TheSilverAge.LOGGER.warn("Recipe-override pack at '{}' has no readable metadata; skipping.", PACK_RESOURCE_ROOT);
            return;
        }
        event.addRepositorySource(consumer -> consumer.accept(pack));
    }

    /** On config save, re-apply the pack's selection to match the (new) effective value. */
    @SubscribeEvent
    public static void onConfigReloaded(@NotNull ModConfigEvent.Reloading event) {
        if (!event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) mc.execute(RecipeOverridePackHandler::syncFromState);
    }

    /**
     * Effective override state. When connected to a server that synced its value
     * ({@link ServerOverrideState}) that wins, so the texture pack matches the
     * server regardless of the local config; otherwise the local config value is
     * used. Defensive read in case the config spec hasn't loaded yet.
     */
    private static boolean desiredOverride() {
        boolean local;
        try {
            local = Configuration.OVERRIDE_VANILLA_RECIPES.get();
        } catch (Exception ignored) {
            local = true;
        }
        return ServerOverrideState.effective(local);
    }

    /** Apply the current desired state. Called from the join sync, disconnect, and config save. */
    public static void syncFromState() {
        applyPackState(desiredOverride());
    }

    /**
     * Toggle the pack's selection to match {@code enabled}, reloading resources
     * only if the selection actually changes (so single-player join/leave, where
     * the value is unchanged, causes no reload flash). Mirrors the Fabric handler.
     */
    private static void applyPackState(boolean enabled) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        PackRepository repo = mc.getResourcePackRepository();
        String packId = resolvePackId(repo);
        if (packId == null) return; // pack not available yet — nothing to toggle

        boolean isSelected = repo.getSelectedIds().contains(packId);
        if (enabled == isSelected) return; // already in the desired state — no reload

        ArrayList<String> next = new ArrayList<>(repo.getSelectedIds());
        if (enabled) {
            if (!next.contains(packId)) next.add(packId);
        } else {
            next.remove(packId);
        }
        repo.setSelected(next);
        mc.reloadResourcePacks();
    }

    /** Find the pack's id among the repository's available packs (robust to id formatting). */
    private static String resolvePackId(PackRepository repo) {
        for (String id : repo.getAvailableIds()) {
            if (id.equals(PACK_ID) || id.endsWith("/recipe_overrides") || id.endsWith(":recipe_overrides")) {
                return id;
            }
        }
        return null;
    }
}
