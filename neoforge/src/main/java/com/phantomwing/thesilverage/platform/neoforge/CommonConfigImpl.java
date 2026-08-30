package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.neoforge.Configuration;

/**
 * NeoForge implementation of {@link com.phantomwing.thesilverage.platform.CommonConfig}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 *
 * <p>Delegates straight to the NeoForge {@code ModConfigSpec} values so common
 * loot gates exactly as the original GLM {@code doApply} bodies did.</p>
 */
public final class CommonConfigImpl {
    private CommonConfigImpl() {
    }

    public static boolean generateStructureLoot() {
        return Configuration.GENERATE_STRUCTURE_LOOT.get();
    }

    public static boolean silverfishDropSilver() {
        return Configuration.SILVERFISH_DROP_SILVER.get();
    }

    public static boolean overrideVanillaRecipes() {
        return Configuration.OVERRIDE_VANILLA_RECIPES.get();
    }

    public static boolean silverSmite() {
        return Configuration.ENABLE_SILVER_SMITE.get();
    }

    public static boolean undeadProtection() {
        return Configuration.ENABLE_UNDEAD_PROTECTION.get();
    }

    public static boolean silverOxidation() {
        return Configuration.ENABLE_SILVER_OXIDATION.get();
    }
}
