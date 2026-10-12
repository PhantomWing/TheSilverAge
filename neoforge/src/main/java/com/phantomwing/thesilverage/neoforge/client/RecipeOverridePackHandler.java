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
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.jarcontents.JarContents;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.resource.JarContentsPackResources;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Built-in resource pack housing the silver-themed brewing-stand / comparator /
 * repeater textures + models. Whether it applies tracks the
 * {@code override_vanilla_recipes} setting — the server's value when connected
 * to one that synced it ({@link ServerOverrideState}), else the local config —
 * so the textures stay consistent with the (server-driven) recipe overrides.
 *
 * <p><b>How the toggle works.</b> {@link AddPackFindersEvent} fires only ONCE
 * (at {@link net.minecraft.server.packs.repository.PackRepository} construction),
 * but the {@code RepositorySource} we register is re-invoked on every reload
 * (it runs inside {@code PackRepository#discoverAvailable}). So the config gate
 * lives in the SOURCE LAMBDA, not the event handler: each reload re-evaluates
 * {@link #desiredOverride()} and either provides the pack (when overrides are on)
 * or omits it (when off → vanilla textures). The pack is {@code required} so when
 * provided it is force-selected at the INITIAL resource load — meaning the
 * default-on case shows the textures from the first frame with NO reload, the
 * same end result as Fabric's {@code DEFAULT_ENABLED}. No {@code PackRepository}
 * manipulation, no {@code options.txt} persistence needed.</p>
 *
 * <p>{@link #syncFromState()} (join receiver / disconnect / config save) requests
 * a reload only when the desired state actually changed, and the reload is
 * deferred by {@link #clientTick()} until it is safe — never during the
 * world-join loading screen, which would freeze the client.</p>
 */
// 1.21.6: buses unified — @EventBusSubscriber dropped the `bus` element (Bus enum removed).
@EventBusSubscriber(modid = TheSilverAge.MOD_ID, value = Dist.CLIENT)
public final class RecipeOverridePackHandler {
    private static final String PACK_ID = "builtin/" + TheSilverAge.MOD_ID + "/recipe_overrides";
    private static final String PACK_RESOURCE_ROOT = "resourcepacks/silver_recipe_overrides";

    /** What the pack-finder source last applied; lets {@link #clientTick()} skip a no-op reload. */
    private static volatile boolean appliedOverride = false;
    /** Whether a sync is queued, waiting for a safe moment to reload. */
    private static volatile boolean syncPending = false;
    /** Whether the pack registered: without it no reload can change appliedOverride, so every sync would reload again. */
    private static volatile boolean packAvailable = false;

    private RecipeOverridePackHandler() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        // 1.21.9 removed IModFile.findResource(String). Read through the mod file's contents, as NeoForge's own
        // addPackFinders does: its content roots are folders only in a dev run, and in a shipped jar the jar file itself.
        JarContents contents = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile().getContents();
        if (!contents.containsFile(PACK_RESOURCE_ROOT + "/pack.mcmeta")) {
            TheSilverAge.LOGGER.warn("Recipe-override pack '{}' not found in the mod file; skipping pack registration.", PACK_RESOURCE_ROOT);
            return;
        }
        packAvailable = true;

        // Register the source ONCE; its lambda re-runs on every reload, so the
        // config/server gate here is what makes reloads re-evaluate the state.
        event.addRepositorySource(consumer -> {
            boolean enabled = desiredOverride();
            appliedOverride = enabled;
            if (!enabled) return; // omit the pack → vanilla textures

            PackLocationInfo location = new PackLocationInfo(
                    PACK_ID,
                    Component.literal("The Silver Age: Recipe-Override Textures"),
                    PackSource.BUILT_IN,
                    Optional.empty());
            // required=true: force-selected whenever provided, including at the
            // initial resource load (no reload needed for default-on).
            // fixedPosition=true at TOP: overrides vanilla.
            PackSelectionConfig selection = new PackSelectionConfig(true, Pack.Position.TOP, true);

            Pack pack = Pack.readMetaAndCreate(
                    location,
                    new JarContentsPackResources.JarContentsResourcesSupplier(contents, PACK_RESOURCE_ROOT),
                    PackType.CLIENT_RESOURCES,
                    selection);
            if (pack != null) {
                consumer.accept(pack);
            } else {
                TheSilverAge.LOGGER.warn("Recipe-override pack at '{}' has no readable metadata; skipping.", PACK_RESOURCE_ROOT);
            }
        });
    }

    /** On config save, request a (deferred) re-evaluation of the pack state. */
    @SubscribeEvent
    public static void onConfigReloaded(@NotNull ModConfigEvent.Reloading event) {
        if (!event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) return;
        syncFromState();
    }

    /**
     * Effective override state. When connected to a server that synced its value
     * ({@link ServerOverrideState}) that wins; otherwise the local config value.
     * Defensive read in case the config spec hasn't loaded yet.
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

    /**
     * Request a pack-state sync. The reload (if needed) is deferred to
     * {@link #clientTick()}: reloading during the world-join loading screen
     * freezes the client. Called from the join receiver, disconnect, config save.
     */
    public static void syncFromState() {
        syncPending = true;
    }

    /**
     * Driven every client tick (registered on the game bus). Reloads — which
     * re-runs the pack-finder source and re-evaluates the gate — only when the
     * desired state differs from what is applied, and only once it is safe.
     */
    public static void clientTick() {
        if (!syncPending) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        if (mc.getOverlay() != null) return;                   // a resource reload is already in progress
        if (mc.screen instanceof LevelLoadingScreen) return; // still joining a world — reloading now hangs
        syncPending = false;
        if (packAvailable && appliedOverride != desiredOverride()) {
            mc.reloadResourcePacks();
        }
    }
}
