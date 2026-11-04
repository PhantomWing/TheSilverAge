package com.phantomwing.thesilverage.forge.client;

import com.phantomwing.thesilverage.client.ModEntityRenderers;
import com.phantomwing.thesilverage.client.ModItemColors;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.client.ServerUndeadSettings;
import com.phantomwing.thesilverage.client.SilverSmiteTooltip;
import com.phantomwing.thesilverage.client.SilverThornsTooltip;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.forge.compat.vanillabackport.SilverSpear;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.particle.ModParticles;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.client.particle.FlameParticle;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only bootstrap for The Silver Age on Forge.
 *
 * <p>A class of its own, reached only behind the {@code isClient()} guard in the {@code @Mod} class: the client types
 * its lambdas name would otherwise be resolved when that class is verified, on a dedicated server too. Forge 1.20.1 has
 * no config screen of its own to offer, so the config is edited in its file.</p>
 */
public final class TheSilverAgeForgeClient {
    private TheSilverAgeForgeClient() {
    }

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(TheSilverAgeForgeClient::clientSetup);
        modEventBus.addListener(TheSilverAgeForgeClient::registerParticleProviders);

        // "+1.5 Damage to Undead" line on silver tools. Cross-loader Architectury
        // tooltip event, so the same common class is called from the Fabric client too.
        SilverSmiteTooltip.register();

        ModEntityRenderers.register();

        // "Thorns against Undead" line on silver armour. Same cross-loader event.
        SilverThornsTooltip.register();

        // Recipe-override server-sync client receiver, registered during mod construction. On receipt the value is
        // cached in ServerOverrideState and RecipeOverridePackHandler re-applies the packs. On disconnect the value is
        // cleared and the packs revert to the config.
        ModNetworking.registerClientReceiver(RecipeOverridePackHandler::syncFromState);
        MinecraftForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut e) -> {
            ServerOverrideState.clear();
            ServerUndeadSettings.clear();
            ModNetworking.setContentFromServer(null, null, null);
            RecipeOverridePackHandler.syncFromState();
        });
        // The sync above is deferred; this tick applies it once it's safe to reload
        // (never during the world-join loading screen — that hangs).
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent e) -> {
            if (e.phase == TickEvent.Phase.END) {
                RecipeOverridePackHandler.clientTick();
            }
        });
    }

    /** The Silver Torch flame: vanilla flame behaviour over the violet sprite. */
    private static void registerParticleProviders(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SILVER_FLAME.get(), FlameParticle.Provider::new);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientPlatform::registerItemProperties);

        // Tints the Moon Dial's moon layer to the active Enhanced Celestials lunar event. Architectury 9 adds it to the
        // game's item colours at once, so it waits for the client, which datagen's run hasn't got.
        event.enqueueWork(ModItemColors::register);

        // The Silver Spear held as a spear where Vanilla Backport makes it one. Backported Spears picks the held
        // model through the item model's own override.
        if (CommonPlatform.isModLoaded(ModIds.VANILLA_BACKPORT) && !CommonPlatform.isModLoaded(ModIds.BACKPORTED_SPEARS)) {
            SilverSpear.registerHeldModel();
        }
    }
}
