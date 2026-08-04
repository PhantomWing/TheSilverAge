package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ModItemColors;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.client.SilverSmiteTooltip;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.particle.ModParticles;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;

/**
 * Fabric client entrypoint for The Silver Age.
 *
 * <p>Registered as the {@code "client"} entrypoint in {@code fabric.mod.json}.
 * Fabric has no {@code FMLClientSetupEvent} equivalent; client-only setup runs
 * from a {@link ClientModInitializer}. This mirrors the NeoForge
 * {@code FMLClientSetupEvent} → {@code ClientPlatform.registerItemProperties()}
 * path, so the Moon Dial {@code moon_phase} item-property override is registered
 * on Fabric exactly as it is on NeoForge (both delegate to the shared
 * {@code com.phantomwing.thesilverage.client.ModItemProperties}).</p>
 */
public final class TheSilverAgeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlatform.registerItemProperties();

        // Tints the Moon Dial's moon layer to the active Enhanced Celestials
        // lunar event. Cross-loader Architectury registry, shared with NeoForge.
        ModItemColors.register();

        // The Silver Torch flame: vanilla flame behaviour over the violet sprite.
        ParticleFactoryRegistry.getInstance().register(ModParticles.SILVER_FLAME.get(), FlameParticle.Provider::new);

        // "+1.5 Damage to Undead" line on silver tools. Cross-loader Architectury
        // tooltip event, so the same common class is called from the NeoForge client too.
        SilverSmiteTooltip.register();

        // Map doors / trapdoors / grates to their cutout/translucent render
        // layers. NeoForge bakes this into the generated block-model JSON via
        // its "render_type" field; Fabric ignores that field, so without this
        // call any pixel marked transparent in those textures renders as
        // opaque black on Fabric.
        ModRenderLayers.register();

        // Built-in resource pack carrying the silver brewing-stand / comparator
        // / repeater texture + model overrides. Auto-toggled by the
        // override_vanilla_recipes config value via an AutoConfig save listener
        // — mirrors the NeoForge AddPackFindersEvent re-evaluation pattern so
        // disabling the recipe-override config also drops the visual overrides.
        RecipeOverridePack.register();

        // Match the recipe-override texture pack to the server on join, and
        // revert to the local config value on disconnect. The server sends its
        // override_vanilla_recipes value via ModNetworking; the receiver caches
        // it in ServerOverrideState and re-applies the pack. (Single-player
        // flows through the same path via the integrated server.)
        ModNetworking.registerClientReceiver(RecipeOverridePack::refresh);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ServerOverrideState.clear();
            client.execute(RecipeOverridePack::refresh);
        });

        TheSilverAge.LOGGER.info("Fabric client init: Moon Dial item-property override + render layers + recipe-override pack + server-sync registered.");
    }
}
