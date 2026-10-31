package com.phantomwing.thesilverage.platform;

import com.phantomwing.thesilverage.recipe.RecipeOverride;
import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge exposing the config booleans the common loot
 * code needs.
 *
 * <p>The config system itself stays loader-specific in Phase 2 (NeoForge
 * {@code ModConfigSpec}; full Fabric config is Phase 5). Common loot
 * ({@link com.phantomwing.thesilverage.loot.SilverLootSpec} consumers) reaches
 * the gate values through this bridge — mirroring Phase 1's
 * {@link CommonPlatform#isModLoaded(String)} pattern.</p>
 *
 * <p>Implemented per loader at
 * {@code com.phantomwing.thesilverage.platform.<loader>.CommonConfigImpl}
 * (Architectury rewrites the call sites at build time).</p>
 */
public final class CommonConfig {
    private CommonConfig() {
    }

    /**
     * Gate for the structure-loot injections (ADD / REPLACE entries). NeoForge:
     * {@code Configuration.GENERATE_STRUCTURE_LOOT.get()}. Fabric: {@code true}
     * until the Phase 5 Fabric config exists.
     */
    @ExpectPlatform
    public static boolean generateStructureLoot() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for the silverfish silver drop. NeoForge:
     * {@code Configuration.SILVERFISH_DROP_SILVER.get()}. Fabric: {@code true}
     * until the Phase 5 Fabric config exists.
     */
    @ExpectPlatform
    public static boolean silverfishDropSilver() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for the vanilla-recipe overrides (lodestone / brewing stand /
     * comparator / repeater silver crafts, with the original recipe kept as a
     * {@code _fallback} when this is off). NeoForge:
     * {@code Configuration.OVERRIDE_VANILLA_RECIPES.get()}. Fabric: {@code true}
     * until the Phase 5 Fabric config exists — matches the NeoForge default
     * ({@code OVERRIDE_VANILLA_RECIPES = true}).
     *
     * <p>Read at datapack-load time by the Fabric {@code thesilverage:config_boolean}
     * resource condition (the parity twin of the NeoForge
     * {@code ConfigBooleanCondition}), so the same conditional/fallback recipe
     * pair resolves identically on both loaders.</p>
     */
    @ExpectPlatform
    public static boolean overrideVanillaRecipes() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * A boolean option by its id, as the {@code thesilverage:config_boolean} recipe condition names it.
     * False, with a warning, for an id no option has.
     */
    @ExpectPlatform
    public static boolean booleanSetting(String settingId) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** Whether a vanilla recipe override applies: the master switch and its own option are both on. */
    public static boolean recipeOverride(RecipeOverride override) {
        return overrideVanillaRecipes() && booleanSetting(override.settingId());
    }

    /**
     * Master switch for everything silver does to the undead: with it off, {@link #silverSmite()},
     * {@link #silverThorns()}, {@link #silverFloorDamage()} and {@link #undeadAvoidSilverLight()} are all off, whatever
     * their own options say.
     */
    @ExpectPlatform
    public static boolean silverAffectsUndead() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for the innate anti-undead damage on silver tools and Silver Arrows
     * ({@link com.phantomwing.thesilverage.combat.SilverSmiteHandler}): {@code enable_silver_smite}, and
     * {@link #silverAffectsUndead()}.
     */
    @ExpectPlatform
    public static boolean silverSmite() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** A silver tool's extra damage to the undead, {@code silver_smite_damage}; the Silver Hoe deals half. */
    @ExpectPlatform
    public static float silverSmiteDamage() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** A Silver Arrow's extra damage to the undead, {@code silver_arrow_smite_damage}. */
    @ExpectPlatform
    public static float silverArrowSmiteDamage() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for silver armour hurting the undead that strike its wearer
     * ({@link com.phantomwing.thesilverage.combat.SilverThornsHandler}): {@code enable_silver_thorns}, and
     * {@link #silverAffectsUndead()}.
     */
    @ExpectPlatform
    public static boolean silverThorns() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** Each worn piece of silver armour's chance of hurting an undead attacker, {@code silver_thorns_chance}. */
    @ExpectPlatform
    public static float silverThornsChance() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for silver weathering: oxidation over time and honeycomb waxing. When
     * off, silver blocks never advance a weather stage and refuse honeycomb, but
     * the oxidizable/waxable mappings stay registered so an axe can still scrape
     * existing blocks back down.
     */
    @ExpectPlatform
    public static boolean silverOxidation() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for mobs spawning with silver gear
     * ({@link com.phantomwing.thesilverage.armor.MonsterArmorHandler}): silver armor
     * in place of golden, silver swords, axes and shovels in place of iron.
     */
    @ExpectPlatform
    public static boolean mobSilverEquipment() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for silver floors ({@link com.phantomwing.thesilverage.combat.SilverFloor}): hurting the mobs silver harms
     * as they stand on silver, keeping them from spawning on it and making them walk round it. Also off with
     * {@link #silverAffectsUndead()}.
     */
    @ExpectPlatform
    public static boolean silverFloorDamage() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for idle undead keeping away from silver light
     * ({@link com.phantomwing.thesilverage.combat.AvoidSilverLightGoal}): the Silver Torch and Lantern. Also off with
     * {@link #silverAffectsUndead()}.
     */
    @ExpectPlatform
    public static boolean undeadAvoidSilverLight() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for getting Silver Arrows: the Fletcher's trade and their loot here, their recipe through its condition.
     * Arrows already made still fly faster and burn the undead.
     */
    @ExpectPlatform
    public static boolean silverArrows() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
