package com.phantomwing.thesilverage.platform.neoforge;

import net.neoforged.fml.ModList;

/**
 * NeoForge implementation of {@link CommonPlatform} (resolved by Architectury's
 * {@code @ExpectPlatform} transformer).
 */
public final class CommonPlatformImpl {
    private CommonPlatformImpl() {
    }

    /**
     * No-op on NeoForge: config registration and the firework-star recipe patch
     * both require the FML mod event bus / {@code FMLCommonSetupEvent} timing, so
     * they are performed directly by {@code TheSilverAgeNeoForge} (which has the
     * event bus). Keeping this empty preserves the original behaviour while still
     * satisfying the common {@code @ExpectPlatform} contract.
     */
    public static void onCommonSetup() {
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
