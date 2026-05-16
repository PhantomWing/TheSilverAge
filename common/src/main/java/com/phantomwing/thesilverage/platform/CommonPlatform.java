package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge for loader-specific bootstrap that has no
 * Architectury equivalent.
 *
 * <p>Each method is implemented per loader under
 * {@code com.phantomwing.thesilverage.<loader>.platform.CommonPlatformImpl}
 * (Architectury rewrites the call sites at build time).</p>
 */
public final class CommonPlatform {
    private CommonPlatform() {
    }

    /**
     * Invoked once from {@code TheSilverAgeCommon.init()} after registries are
     * set up.
     *
     * <p>NeoForge: registers the {@code COMMON} config and patches the firework
     * star recipe shape ingredient/lookup map (via the AT-exposed static fields)
     * during {@code FMLCommonSetupEvent}.<br>
     * Fabric: no-op shell for now (TODO(phase 4/5): config + firework parity).</p>
     */
    @ExpectPlatform
    public static void onCommonSetup() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Returns whether the given mod id is loaded at runtime.
     *
     * <p>Used by {@code ModItems} for the Create-gated creative-tab entry. Bridged
     * because the loaded-mods query differs per loader (NeoForge {@code ModList} /
     * Fabric {@code FabricLoader}).</p>
     */
    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
