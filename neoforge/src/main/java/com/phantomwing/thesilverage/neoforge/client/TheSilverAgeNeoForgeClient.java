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

// Client-only bootstrap, kept in a separate class so the dedicated server never
// classloads/verifies the client-only types it references (which NeoForge rejects).
public final class TheSilverAgeNeoForgeClient {
    private TheSilverAgeNeoForgeClient() {
    }

    public static void init(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> new ConfigurationScreen(mc, parent, StyledConfigSectionScreen::new));

        modEventBus.addListener(TheSilverAgeNeoForgeClient::clientSetup);

        // MUST use this event (fires before item models parse); FMLClientSetupEvent is too late and the range_dispatch fails to parse.
        modEventBus.addListener((RegisterRangeSelectItemModelPropertyEvent e) ->
                e.register(ModItemProperties.MOON_PHASE, ModItemProperties.MoonPhaseProperty.MAP_CODEC));

        // MUST register during mod construction, not clientSetup: payload binding happens before FMLClientSetupEvent.
        ModNetworking.registerClientReceiver(RecipeOverridePackHandler::syncFromState);
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut e) -> {
            ServerOverrideState.clear();
            RecipeOverridePackHandler.syncFromState();
        });
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post e) -> RecipeOverridePackHandler.clientTick());
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientPlatform::registerItemProperties);
    }
}
