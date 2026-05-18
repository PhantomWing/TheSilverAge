package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.neoforge.item.ModItemProperties;

/**
 * NeoForge implementation of {@link ClientPlatform}.
 *
 * <p>Invoked from {@code TheSilverAgeNeoForge}'s {@code FMLClientSetupEvent}
 * handler (client dist only), mirroring the original mod's
 * {@code ClientModEvents.onClientSetup}.</p>
 */
public final class ClientPlatformImpl {
    private ClientPlatformImpl() {
    }

    public static void registerItemProperties() {
        ModItemProperties.register();
    }
}
