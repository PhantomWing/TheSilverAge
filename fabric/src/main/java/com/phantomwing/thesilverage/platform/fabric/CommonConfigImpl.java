package com.phantomwing.thesilverage.platform.fabric;

/**
 * Fabric implementation of {@link com.phantomwing.thesilverage.platform.CommonConfig}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 *
 * <p>Phase 2 shell: the full Fabric config system is Phase 5. Both gates return
 * {@code true} here, which matches the NeoForge config defaults
 * ({@code GENERATE_STRUCTURE_LOOT = true}, {@code SILVERFISH_DROP_SILVER = true}),
 * so out-of-the-box loot behaviour is the same on both loaders.</p>
 */
public final class CommonConfigImpl {
    private CommonConfigImpl() {
    }

    /** TODO(phase 5): read the Fabric config. NeoForge default is {@code true}. */
    public static boolean generateStructureLoot() {
        return true;
    }

    /** TODO(phase 5): read the Fabric config. NeoForge default is {@code true}. */
    public static boolean silverfishDropSilver() {
        return true;
    }
}
