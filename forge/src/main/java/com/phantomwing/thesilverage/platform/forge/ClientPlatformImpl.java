package com.phantomwing.thesilverage.platform.forge;

import com.phantomwing.thesilverage.forge.item.ModItemProperties;

/**
 * Forge implementation of {@link ClientPlatform}.
 *
 * <p>Invoked from {@code TheSilverAgeForge}'s {@code FMLClientSetupEvent}
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
