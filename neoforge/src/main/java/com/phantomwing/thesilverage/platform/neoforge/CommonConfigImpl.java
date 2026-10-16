package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.neoforge.Configuration;

// NeoForge implementation of CommonConfig.
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
}
