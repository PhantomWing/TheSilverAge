package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

/** Fabric client entrypoint (the "client" entrypoint in fabric.mod.json). */
public final class TheSilverAgeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registers Moon Dial item-property override and block render layers.
        ClientPlatform.registerItemProperties();

        RecipeOverridePack.register();

        // Match the texture pack to the server's override value on join, revert on disconnect.
        ModNetworking.registerClientReceiver(RecipeOverridePack::refresh);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ServerOverrideState.clear();
            client.execute(RecipeOverridePack::refresh);
        });

        TheSilverAge.LOGGER.info("Fabric client init: Moon Dial item-property override + render layers + recipe-override pack + server-sync registered.");
    }
}
