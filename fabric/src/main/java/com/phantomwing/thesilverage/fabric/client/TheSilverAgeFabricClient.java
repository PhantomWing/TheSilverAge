package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.fabricmc.api.ClientModInitializer;

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

        // Map doors / trapdoors / grates to their cutout/translucent render
        // layers. NeoForge bakes this into the generated block-model JSON via
        // its "render_type" field; Fabric ignores that field, so without this
        // call any pixel marked transparent in those textures renders as
        // opaque black on Fabric.
        ModRenderLayers.register();

        TheSilverAge.LOGGER.info("Fabric client init: Moon Dial item-property override + render layers registered.");
    }
}
