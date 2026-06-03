package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Client-only bootstrap for The Silver Age on NeoForge.
 *
 * <p><b>Why this is a separate class.</b> Everything here references client-only
 * types ({@link ConfigurationScreen}, {@link StyledConfigSectionScreen} →
 * {@code Screen}, {@link IConfigScreenFactory}, {@link ClientPlayerNetworkEvent},
 * {@link ClientTickEvent}, {@link FMLClientSetupEvent}). If these lambdas and
 * methods lived in the {@code @Mod} class, the JVM would resolve their referenced
 * types when that class is loaded/verified — forcing the client classes to load —
 * and NeoForge 1.21.1+'s dedicated-server class loader hard-rejects loading client
 * classes ("Attempted to load class net/minecraft/client/gui/screens/Screen for
 * invalid dist DEDICATED_SERVER"), crashing the server during mod construction even
 * though the code is guarded by {@code FMLEnvironment.dist.isClient()} at runtime.
 * The runtime guard is not enough: class verification happens before the branch.</p>
 *
 * <p>By isolating it here and calling {@link #init} via {@code invokestatic} from
 * behind the {@code isClient()} guard, this class (and the client types it names)
 * is only ever loaded/verified on a physical client.</p>
 */
public final class TheSilverAgeNeoForgeClient {
    private TheSilverAgeNeoForgeClient() {
    }

    public static void init(IEventBus modEventBus, ModContainer container) {
        // NeoForge's ConfigurationScreen for this mod's configs, with a custom
        // section screen so booleans render as a coloured Yes/No instead of ON/OFF.
        // The 3-arg ConfigurationScreen ctor takes a section-screen factory,
        // scoping the styling to this mod only.
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> new ConfigurationScreen(mc, parent, StyledConfigSectionScreen::new));

        modEventBus.addListener(TheSilverAgeNeoForgeClient::clientSetup);

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
