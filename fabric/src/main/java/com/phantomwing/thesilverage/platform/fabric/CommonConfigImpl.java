package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;

/** Fabric impl of CommonConfig (@ExpectPlatform); delegates to TheSilverAgeFabricConfig. */
public final class CommonConfigImpl {
    private CommonConfigImpl() {
    }

    public static boolean generateStructureLoot() {
        return TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                TheSilverAgeFabricConfig.GENERATE_STRUCTURE_LOOT_ID);
    }

    public static boolean silverfishDropSilver() {
        return TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                TheSilverAgeFabricConfig.SILVERFISH_DROP_SILVER_ID);
    }

    public static boolean overrideVanillaRecipes() {
        return TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                TheSilverAgeFabricConfig.OVERRIDE_VANILLA_RECIPES_ID);
    }
}
