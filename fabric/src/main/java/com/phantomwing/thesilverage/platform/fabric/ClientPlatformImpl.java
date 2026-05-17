package com.phantomwing.thesilverage.platform.fabric;

/**
 * Fabric implementation of {@link com.phantomwing.thesilverage.platform.ClientPlatform}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 *
 * <p>Delegates to the shared, loader-agnostic
 * {@link com.phantomwing.thesilverage.client.ModItemProperties} (pure vanilla
 * {@code ItemProperties.register}) — identical id/predicate to NeoForge.
 * Invoked from the Fabric client entrypoint
 * ({@code com.phantomwing.thesilverage.fabric.client.TheSilverAgeFabricClient},
 * registered as the {@code "client"} entrypoint in {@code fabric.mod.json}).</p>
 */
public final class ClientPlatformImpl {
    private ClientPlatformImpl() {
    }

    public static void registerItemProperties() {
        com.phantomwing.thesilverage.client.ModItemProperties.register();
    }
}
