package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/** {@code @ExpectPlatform} bridge for loader-specific bootstrap that has no Architectury equivalent. */
public final class CommonPlatform {
    private CommonPlatform() {
    }

    /** Invoked once from {@code TheSilverAgeCommon.init()} after registries are set up. */
    @ExpectPlatform
    public static void onCommonSetup() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** Returns whether the given mod id is loaded at runtime. */
    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
