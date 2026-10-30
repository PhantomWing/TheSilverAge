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

    public static boolean booleanSetting(String settingId) {
        return Configuration.getBooleanConfigurationValue(settingId);
    }

    public static boolean silverAffectsUndead() {
        return Configuration.SILVER_AFFECTS_UNDEAD.get();
    }

    public static boolean silverSmite() {
        return silverAffectsUndead() && Configuration.ENABLE_SILVER_SMITE.get();
    }

    public static float silverSmiteDamage() {
        return Configuration.SILVER_SMITE_DAMAGE.get().floatValue();
    }

    public static float silverArrowSmiteDamage() {
        return Configuration.SILVER_ARROW_SMITE_DAMAGE.get().floatValue();
    }

    public static boolean silverThorns() {
        return silverAffectsUndead() && Configuration.ENABLE_SILVER_THORNS.get();
    }

    public static float silverThornsChance() {
        return Configuration.SILVER_THORNS_CHANCE.get().floatValue();
    }

    public static boolean silverOxidation() {
        return Configuration.ENABLE_SILVER_OXIDATION.get();
    }

    public static boolean mobSilverEquipment() {
        return Configuration.ENABLE_MOB_SILVER_EQUIPMENT.get();
    }

    public static boolean silverFloorDamage() {
        return silverAffectsUndead() && Configuration.ENABLE_SILVER_FLOOR_DAMAGE.get();
    }

    public static boolean undeadAvoidSilverLight() {
        return silverAffectsUndead() && Configuration.UNDEAD_AVOID_SILVER_LIGHT.get();
    }

    public static boolean silverArrows() {
        return Configuration.ENABLE_SILVER_ARROWS.get();
    }
}
