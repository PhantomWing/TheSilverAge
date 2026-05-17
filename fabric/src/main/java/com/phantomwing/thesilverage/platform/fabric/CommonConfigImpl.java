package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;

/**
 * Fabric implementation of {@link com.phantomwing.thesilverage.platform.CommonConfig}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 *
 * <p>Delegates to the Cloth/AutoConfig-backed {@link TheSilverAgeFabricConfig}
 * (Phase 5) — the parity twin of NeoForge's {@code ModConfigSpec}. The three
 * gates resolve from the same option ids and {@code true} defaults on both
 * loaders, so loot and recipe-override behaviour stays identical.</p>
 */
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
