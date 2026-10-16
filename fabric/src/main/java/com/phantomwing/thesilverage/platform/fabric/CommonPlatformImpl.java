package com.phantomwing.thesilverage.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

/** Fabric impl of CommonPlatform (@ExpectPlatform). */
public final class CommonPlatformImpl {
    private CommonPlatformImpl() {
    }

    public static void onCommonSetup() {
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
