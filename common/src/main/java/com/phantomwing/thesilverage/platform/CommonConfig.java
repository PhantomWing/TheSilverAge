package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/** {@code @ExpectPlatform} bridge exposing the config booleans the common loot code needs. */
public final class CommonConfig {
    private CommonConfig() {
    }

    /** Gate for the structure-loot injections (ADD / REPLACE entries). */
    @ExpectPlatform
    public static boolean generateStructureLoot() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** Gate for the silverfish silver drop. */
    @ExpectPlatform
    public static boolean silverfishDropSilver() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** Gate for the vanilla-recipe overrides (original recipe kept as a {@code _fallback} when off). */
    @ExpectPlatform
    public static boolean overrideVanillaRecipes() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
