package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.neoforge.item.ModItemProperties;

// NeoForge implementation of ClientPlatform.
public final class ClientPlatformImpl {
    private ClientPlatformImpl() {
    }

    public static void registerItemProperties() {
        ModItemProperties.register();
    }
}
