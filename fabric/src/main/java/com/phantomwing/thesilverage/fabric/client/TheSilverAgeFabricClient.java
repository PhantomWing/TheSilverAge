package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.client.ServerUndeadSettings;
import com.phantomwing.thesilverage.client.ModEntityRenderers;
import com.phantomwing.thesilverage.client.SilverSmiteTooltip;
import com.phantomwing.thesilverage.client.SilverThornsTooltip;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.particle.ModParticles;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.FlameParticle;

/** Fabric client entrypoint (the "client" entrypoint in fabric.mod.json). */
public final class TheSilverAgeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registers Moon Dial item-property override and block render layers.
        ClientPlatform.registerItemProperties();

        // The Silver Torch flame: vanilla flame behaviour over the violet sprite.
        ParticleProviderRegistry.getInstance().register(ModParticles.SILVER_FLAME.get(), FlameParticle.Provider::new);

        // "+1.5 Damage to Undead" line on silver tools. Cross-loader Architectury
        // tooltip event, so the same common class is called from the NeoForge client too.
        SilverSmiteTooltip.register();

        ModEntityRenderers.register();

        // "Thorns against Undead" line on silver armour. Same cross-loader event.
        SilverThornsTooltip.register();

        // Built-in resource pack carrying the silver brewing-stand / comparator
        // / repeater texture + model overrides. Auto-toggled by the
        // override_vanilla_recipes config value via an AutoConfig save listener
        // — mirrors the NeoForge AddPackFindersEvent re-evaluation pattern so
        // disabling the recipe-override config also drops the visual overrides.
        RecipeOverridePack.register();

        // Match the texture pack to the server's override value on join, revert on disconnect.
        ModNetworking.registerClientReceiver(RecipeOverridePack::refresh);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ServerOverrideState.clear();
            ServerUndeadSettings.clear();
            ModNetworking.setContentFromServer(null, null, null);
            client.execute(RecipeOverridePack::refresh);
        });

        TheSilverAge.LOGGER.info("Fabric client init: Moon Dial item-property override + render layers + recipe-override pack + server-sync registered.");
    }
}
