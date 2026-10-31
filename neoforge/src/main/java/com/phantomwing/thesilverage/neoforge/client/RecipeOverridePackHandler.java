package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
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
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The built-in resource packs that give the vanilla blocks whose recipes take silver a silver look:
 * one per {@link RecipeOverride} with a texture pack (brewing stand, comparator, repeater). Each
 * applies while its override does - the server's when connected to one that synced them
 * ({@link ServerOverrideState}), else the local config - so the textures stay consistent with the
 * (server-driven) recipes.
 *
 * <p><b>How the toggle works.</b> {@link AddPackFindersEvent} fires only ONCE (at
 * {@link net.minecraft.server.packs.repository.PackRepository} construction), but the
 * {@code RepositorySource} registered here is re-invoked on every reload (it runs inside
 * {@code PackRepository#discoverAvailable}). So the gate lives in the source lambda: each reload
 * provides the packs whose override is on and omits the rest. The packs are {@code required}, so a
 * provided one is selected at the initial resource load too - the default-on case shows the
 * textures from the first frame with no reload.</p>
 *
 * <p>{@link #syncFromState()} (join receiver / disconnect / config save) asks for a re-evaluation,
 * which {@link #clientTick()} carries out once it is safe - never during the world-join loading
 * screen, which would freeze the client - and only when some pack's state actually differs, with
 * one reload for all of them.</p>
 */
@EventBusSubscriber(modid = TheSilverAge.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RecipeOverridePackHandler {
    /** The packs found in the mod file, by their override. One that isn't found never takes part. */
    private static final Map<RecipeOverride, Path> AVAILABLE = new EnumMap<>(RecipeOverride.class);
    /** Packs found whose metadata couldn't be read: they can't be applied either, so they don't take part. */
    private static final Set<RecipeOverride> UNREADABLE = Collections.synchronizedSet(EnumSet.noneOf(RecipeOverride.class));

    /** The packs the source last provided; lets {@link #clientTick()} skip a reload that changes nothing. */
    private static volatile Set<RecipeOverride> applied = EnumSet.noneOf(RecipeOverride.class);
    /** Whether a sync is queued, waiting for a safe moment to reload. */
    private static volatile boolean syncPending = false;

    private RecipeOverridePackHandler() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        for (RecipeOverride override : RecipeOverride.values()) {
            if (!override.hasTexturePack()) continue;
            String root = "resourcepacks/" + override.texturePack();
            Path packRoot = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile().findResource(root);
            if (packRoot == null || !Files.exists(packRoot.resolve("pack.mcmeta"))) {
                // In a dev run common's resources are only on the mod file's path through the
                // processResources copy in neoforge/build.gradle.
                TheSilverAge.LOGGER.warn("Recipe-override texture pack '{}' not found in the mod file; skipping it.", root);
                continue;
            }
            AVAILABLE.put(override, packRoot);
        }
        if (AVAILABLE.isEmpty()) return;

        // Registered once; its lambda re-runs on every reload, which is what re-evaluates the packs.
        event.addRepositorySource(consumer -> {
            Set<RecipeOverride> provided = EnumSet.noneOf(RecipeOverride.class);
            for (Map.Entry<RecipeOverride, Path> entry : AVAILABLE.entrySet()) {
                RecipeOverride override = entry.getKey();
                if (!ServerOverrideState.isOn(override)) continue; // omitted -> vanilla's textures

                PackLocationInfo location = new PackLocationInfo(
                        "builtin/" + TheSilverAge.MOD_ID + "/" + override.texturePack(),
                        Component.literal("The Silver Age: " + override.texturePack()),
                        PackSource.BUILT_IN,
                        Optional.empty());
                // required: selected whenever provided, at the initial load too. Fixed at the top, over vanilla.
                PackSelectionConfig selection = new PackSelectionConfig(true, Pack.Position.TOP, true);
                Pack pack = Pack.readMetaAndCreate(location,
                        new PathPackResources.PathResourcesSupplier(entry.getValue()), PackType.CLIENT_RESOURCES, selection);
                if (pack != null) {
                    consumer.accept(pack);
                    provided.add(override);
                } else {
                    UNREADABLE.add(override);
                    TheSilverAge.LOGGER.warn("Recipe-override texture pack '{}' has no readable metadata; skipping it.", override.texturePack());
                }
            }
            applied = provided;
        });
    }

    /** On config save, request a (deferred) re-evaluation of the packs. */
    @SubscribeEvent
    public static void onConfigReloaded(@NotNull ModConfigEvent.Reloading event) {
        if (!event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) return;
        syncFromState();
    }

    /** The packs that should be applied now. Only those found take part: a missing one can't be applied, and comparing it would reload on every sync. */
    private static Set<RecipeOverride> desired() {
        Set<RecipeOverride> desired = EnumSet.noneOf(RecipeOverride.class);
        for (RecipeOverride override : AVAILABLE.keySet()) {
            if (!UNREADABLE.contains(override) && ServerOverrideState.isOn(override)) desired.add(override);
        }
        return desired;
    }

    /**
     * Request a pack sync. The reload (if needed) is deferred to {@link #clientTick()}: reloading
     * during the world-join loading screen freezes the client. Called from the join receiver,
     * disconnect and config save.
     */
    public static void syncFromState() {
        syncPending = true;
    }

    /**
     * Driven every client tick (registered on the game bus). Reloads - which re-runs the source and
     * re-evaluates every pack - only when the packs wanted differ from those applied, and only once
     * it is safe.
     */
    public static void clientTick() {
        if (!syncPending) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        if (mc.getOverlay() != null) return;                   // a resource reload is already in progress
        if (mc.screen instanceof ReceivingLevelScreen) return; // still joining a world: reloading now hangs
        syncPending = false;
        if (!applied.equals(desired())) {
            mc.reloadResourcePacks();
        }
    }
}
