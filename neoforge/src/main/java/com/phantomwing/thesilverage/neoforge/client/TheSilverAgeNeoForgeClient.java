package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.client.ModItemColors;
import com.phantomwing.thesilverage.client.ModItemProperties;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.client.ServerUndeadSettings;
import com.phantomwing.thesilverage.client.ModEntityRenderers;
import com.phantomwing.thesilverage.client.SilverSmiteTooltip;
import com.phantomwing.thesilverage.client.SilverThornsTooltip;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.particle.ModParticles;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
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
 * code is guarded by {@code FMLEnvironment.getDist().isClient()} at runtime.</p>
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
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> new ConfigurationScreen(mc, parent, StyledConfigSectionScreen::new));

        modEventBus.addListener(TheSilverAgeNeoForgeClient::clientSetup);
        modEventBus.addListener(TheSilverAgeNeoForgeClient::registerParticleProviders);

        // "+1.5 Damage to Undead" line on silver tools. Cross-loader Architectury
        // tooltip event, so the same common class is called from the Fabric client too.
        SilverSmiteTooltip.register();

        ModEntityRenderers.register();

        // "Thorns against Undead" line on silver armour. Same cross-loader event.
        SilverThornsTooltip.register();

        // MUST use this event (fires before item models parse); FMLClientSetupEvent is too late and the range_dispatch fails to parse.
        modEventBus.addListener((RegisterRangeSelectItemModelPropertyEvent e) ->
                e.register(ModItemProperties.MOON_PHASE, ModItemProperties.MoonPhaseProperty.MAP_CODEC));
        // The moon's lunar event tint (thesilverage:lunar_event), named by the dial's items/ definition: before item
        // models are parsed, as the property is.
        modEventBus.addListener((RegisterColorHandlersEvent.ItemTintSources e) ->
                e.register(ModItemColors.LUNAR_EVENT, ModItemColors.LunarEventTint.MAP_CODEC));

        // MUST register during mod construction, not clientSetup: payload binding happens before FMLClientSetupEvent.
        ModNetworking.registerClientReceiver(RecipeOverridePackHandler::syncFromState);
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut e) -> {
            ServerOverrideState.clear();
            ServerUndeadSettings.clear();
            ModNetworking.setContentFromServer(null, null);
            RecipeOverridePackHandler.syncFromState();
        });
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post e) -> RecipeOverridePackHandler.clientTick());
    }

    /** The Silver Torch flame: vanilla flame behaviour over the violet sprite. */
    private static void registerParticleProviders(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SILVER_FLAME.get(), FlameParticle.Provider::new);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientPlatform::registerItemProperties);
    }
}
