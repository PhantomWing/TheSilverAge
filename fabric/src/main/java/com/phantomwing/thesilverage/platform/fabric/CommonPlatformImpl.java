package com.phantomwing.thesilverage.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

/**
 * Fabric implementation of {@link CommonPlatform} (resolved by Architectury's
 * {@code @ExpectPlatform} transformer).
 *
 * <p>Phase 1 shell: Fabric feature parity (config, firework-star recipe patch)
 * is a later phase. {@code onCommonSetup} is intentionally a no-op for now;
 * {@code isModLoaded} is wired so the Create-gated creative-tab entry behaves
 * correctly.</p>
 */
public final class CommonPlatformImpl {
    private CommonPlatformImpl() {
    }

    /** TODO(phase 4/5): Fabric config + firework-star recipe parity. No-op shell. */
    public static void onCommonSetup() {
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
