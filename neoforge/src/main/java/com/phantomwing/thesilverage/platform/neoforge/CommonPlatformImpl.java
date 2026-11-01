package com.phantomwing.thesilverage.platform.neoforge;

import net.neoforged.fml.ModList;

// NeoForge implementation of CommonPlatform.
public final class CommonPlatformImpl {
    private CommonPlatformImpl() {
    }

    // No-op: NeoForge does this work in TheSilverAgeNeoForge, which has the FML event bus.
    public static void onCommonSetup() {
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
