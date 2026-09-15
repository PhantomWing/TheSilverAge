package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BooleanSupplier;

/**
 * Built-in pack with the silver re-skins of the brewing stand, comparator and repeater, provided
 * only while {@code override_vanilla_recipes} is on. The source is re-run on every resource reload.
 */
@Mod.EventBusSubscriber(modid = TheSilverAge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RecipeOverridePackHandler {
    public static final String PACK_ID = "builtin/" + TheSilverAge.MOD_ID + "/recipe_overrides";
    private static final String PACK_RESOURCE_ROOT = "resourcepacks/silver_recipe_overrides";

    /** What the pack source last applied; {@link Sync} reloads when the config disagrees. */
    private static volatile boolean appliedOverride = true;

    private RecipeOverridePackHandler() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) {
            return;
        }

        Path packRoot = packRoot();
        if (packRoot == null) {
            TheSilverAge.LOGGER.warn("Recipe-override pack root '{}' not found in the mod file; skipping.", PACK_RESOURCE_ROOT);
            return;
        }
        event.addRepositorySource(source(packRoot, RecipeOverridePackHandler::desiredOverride));
    }

    public static RepositorySource source(Path packRoot, BooleanSupplier enabled) {
        return consumer -> {
            boolean on = enabled.getAsBoolean();
            appliedOverride = on;
            if (!on) {
                return;
            }

            Pack pack = Pack.readMetaAndCreate(
                    PACK_ID,
                    Component.literal("The Silver Age: Recipe-Override Textures"),
                    true,
                    (id) -> new PathPackResources(id, packRoot, false),
                    PackType.CLIENT_RESOURCES,
                    Pack.Position.TOP,
                    PackSource.BUILT_IN);
            if (pack != null) {
                consumer.accept(pack);
            } else {
                TheSilverAge.LOGGER.warn("Recipe-override pack at '{}' has no readable metadata; skipping.", PACK_RESOURCE_ROOT);
            }
        };
    }

    public static boolean desiredOverride() {
        return readOverride(Configuration.COMMON_CONFIG, Configuration.OVERRIDE_VANILLA_RECIPES);
    }

    /** The first client pack scan runs before Forge loads COMMON configs, so fall back to the default until then. */
    public static boolean readOverride(ForgeConfigSpec spec, ForgeConfigSpec.BooleanValue value) {
        return !spec.isLoaded() || value.get();
    }

    public static boolean appliedOverride() {
        return appliedOverride;
    }

    @Nullable
    public static Path packRoot() {
        Path root = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile().findResource(PACK_RESOURCE_ROOT);
        return root != null && Files.exists(root) ? root : null;
    }

    /** Applies a config value that differs from the last pack scan: at startup, or after a config edit. */
    @Mod.EventBusSubscriber(modid = TheSilverAge.MOD_ID, value = Dist.CLIENT)
    public static final class Sync {
        private Sync() {
        }

        @SubscribeEvent
        public static void onClientTick(@NotNull TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END || desiredOverride() == appliedOverride) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();
            // Reloading during a resource load or the world-join screens can hang the client.
            if (minecraft.getOverlay() != null || minecraft.screen instanceof LevelLoadingScreen || minecraft.screen instanceof ReceivingLevelScreen) {
                return;
            }

            appliedOverride = desiredOverride();
            minecraft.reloadResourcePacks();
        }
    }
}
