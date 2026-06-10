package com.phantomwing.thesilverage.platform.fabric;

/** Fabric impl of ClientPlatform (@ExpectPlatform). */
public final class ClientPlatformImpl {
    private ClientPlatformImpl() {
    }

    public static void registerItemProperties() {
        com.phantomwing.thesilverage.client.ModItemProperties.register();
    }
}
