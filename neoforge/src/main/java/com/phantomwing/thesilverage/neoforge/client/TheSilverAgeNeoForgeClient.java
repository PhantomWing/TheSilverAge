package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.client.ModItemProperties;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Client-only bootstrap for The Silver Age on NeoForge.
 *
 * <p><b>Why this is a separate class.</b> Everything here references client-only
 * types ({@link ConfigurationScreen}, {@link StyledConfigSectionScreen} →
 * {@code Screen}, {@link IConfigScreenFactory}, {@link ClientPlayerNetworkEvent},
 * {@link ClientTickEvent}, {@link FMLClientSetupEvent}). If these lambdas lived in
 * the {@code @Mod} class, the JVM would verify their synthetic lambda methods when
 * that class loads — forcing the client types to resolve — and NeoForge 1.21.2+'s
 * dedicated-server class loader hard-rejects loading client classes ("invalid dist
 * DEDICATED_SERVER"), crashing the server during mod construction even though the
 * code is guarded by {@code FMLEnvironment.dist.isClient()} at runtime.</p>
 *
 * <p>By isolating it here and calling {@link #init} via {@code invokestatic} from
 * behind the {@code isClient()} guard, this class (and the client types it names)
 * is only ever loaded/verified on a physical client. Same verifier-isolation trick
 * used for the optional Farmer's Delight knife.</p>
 */
public final class TheSilverAgeNeoForgeClient {
    private TheSilverAgeNeoForgeClient() {
    }

    public static void init(IEventBus modEventBus, ModContainer container) {
        // NeoForge's ConfigurationScreen for this mod's configs, with a custom
        // section screen so booleans render as a coloured Yes/No instead of ON/OFF.
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> new ConfigurationScreen(mc, parent, StyledConfigSectionScreen::new));

        modEventBus.addListener(TheSilverAgeNeoForgeClient::clientSetup);

        // Register the Moon Dial's custom thesilverage:moon_phase range-select property.
        // MUST use this event (fires before item models are parsed) — registering it at
        // FMLClientSetupEvent is too late and the moon_dial range_dispatch fails to parse
        // with "Unknown element id: thesilverage:moon_phase".
        modEventBus.addListener((RegisterRangeSelectItemModelPropertyEvent e) ->
                e.register(ModItemProperties.MOON_PHASE, ModItemProperties.MoonPhaseProperty.MAP_CODEC));

        // Recipe-override server-sync client receiver. MUST be registered during
        // mod construction, NOT in clientSetup: Architectury's NeoForge adaptor
        // defers payload binding to RegisterPayloadHandlersEvent, which fires before
        // FMLClientSetupEvent — registering from clientSetup binds too late. On
        // receipt the value is cached in ServerOverrideState and
        // RecipeOverridePackHandler re-applies the pack's SELECTION. On disconnect
        // (game-bus LoggingOut) the value is cleared and the pack reverts to config.
        ModNetworking.registerClientReceiver(RecipeOverridePackHandler::syncFromState);
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut e) -> {
            ServerOverrideState.clear();
            RecipeOverridePackHandler.syncFromState();
        });
        // The sync above is deferred; this tick applies it once it's safe to reload
        // (never during the world-join loading screen — that hangs).
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post e) -> RecipeOverridePackHandler.clientTick());
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientPlatform::registerItemProperties);
    }
}
