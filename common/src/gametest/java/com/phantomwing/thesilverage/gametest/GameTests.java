package com.phantomwing.thesilverage.gametest;

import net.minecraft.gametest.framework.GameTestHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Every game test on this line. A test is its body and its entry here, and both read the same on every
 * line: how the list is registered changed at 1.21.5, which belongs to {@code GameTestRegistration}
 * alone, so porting a test never touches a registration class or a JSON.
 */
public final class GameTests {
    public static final List<Test> ALL = List.of(
            test("moon_phase_detector_full_moon", SilverBlocksGameTest::moonPhaseDetectorFullMoon),
            test("waxing_silver_block", SilverBlocksGameTest::waxingSilverBlock),
            test("silver_bulb_lighting", SilverBlocksGameTest::silverBulbLighting),
            test("wind_charge_opens_silver_door", SilverBlocksGameTest::windChargeOpensSilverDoor),
            test("wind_charge_opens_silver_trapdoor", SilverBlocksGameTest::windChargeOpensSilverTrapdoor),
            test("silverfish_drops_silver", SilverLootGameTest::silverfishDropsSilver),
            test("loot_entries_give_what_they_name", SilverLootGameTest::lootEntriesGiveWhatTheyName),
            test("jungle_temples_give_silver_horse_armor", SilverLootGameTest::jungleTemplesGiveSilverHorseArmor),
            test("silver_gear_in_stronghold_corridors", SilverLootGameTest::silverGearInStrongholdCorridors),
            test("silver_shovel_in_toolsmith_chests", SilverLootGameTest::silverShovelInToolsmithChests),
            test("silver_axe_in_trial_chamber_corridors", SilverLootGameTest::silverAxeInTrialChamberCorridors),
            test("silver_gear_in_trial_vaults", SilverLootGameTest::silverGearInTrialVaults),
            test("silver_sword_in_bastions", SilverLootGameTest::silverSwordInBastions),
            test("replacing_takes_neighbouring_stacks", SilverLootGameTest::replacingTakesNeighbouringStacks),
            test("mobs_hold_silver_tools", SilverGearGameTest::mobsHoldSilverTools),
            test("husks_wear_silver_armor", SilverGearGameTest::husksWearSilverArmor),
            test("mob_rolls_once", SilverGearGameTest::mobRollsOnce),
            test("converted_mobs_keep_their_roll", SilverGearGameTest::convertedMobsKeepTheirRoll),
            test("thorns_take_no_weapon_bonus", SilverGearGameTest::thornsTakeNoWeaponBonus),
            test("swapped_gear_keeps_its_wear", SilverGearGameTest::swappedGearKeepsItsWear),
            test("silver_trim_darkens_on_silver_armor", SilverGearGameTest::silverTrimDarkensOnSilverArmor),
            test("silver_wall_torch_drops_a_silver_torch", SilverBlocksGameTest::silverWallTorchDropsASilverTorch),
            test("config_condition_naming_no_setting_is_false", SilverRecipesGameTest::configConditionNamingNoSettingIsFalse),
            test("silver_joins_vanilla_tags", SilverTagsGameTest::silverJoinsVanillaTags),
            test("silver_counts_as_silver_for_werewolves", SilverTagsGameTest::silverCountsAsSilverForWerewolves),
            test("silver_ore_follows_its_config", SilverOreGameTest::silverOreFollowsItsConfig),
            test("silver_floors_hurt_the_undead", SilverFloorGameTest::silverFloorsHurtTheUndead).maxTicks(140),
            test("undead_cant_spawn_on_silver", SilverFloorGameTest::undeadCantSpawnOnSilver),
            test("undead_walk_round_silver", SilverFloorGameTest::undeadWalkRoundSilver),
            test("undead_never_pick_silver_to_go_to", SilverFloorGameTest::undeadNeverPickSilverToGoTo),
            test("silver_harms_what_smite_does", SilverFloorGameTest::silverHarmsWhatSmiteDoes),
            test("recipe_overrides_apply_by_default", SilverRecipesGameTest::recipeOverridesApplyByDefault),
            test("silver_bars_trapdoors_and_bricks_each_craft", SilverRecipesGameTest::silverBarsTrapdoorsAndBricksEachCraft),
            test("silver_data_outranks_neoforge", SilverRecipesGameTest::silverDataOutranksNeoForge),
            test("silver_arrows_hurt_the_undead_more", SilverArrowGameTest::silverArrowsHurtTheUndeadMore),
            test("silver_arrows_fly_faster", SilverArrowGameTest::silverArrowsFlyFaster),
            test("silver_arrows_are_arrows", SilverArrowGameTest::silverArrowsAreArrows),
            test("dispensers_fire_silver_arrows", SilverArrowGameTest::dispensersFireSilverArrows),
            test("silver_arrows_in_pillager_outposts", SilverLootGameTest::silverArrowsInPillagerOutposts),
            test("silver_arrow_loot_follows_its_config", SilverLootGameTest::silverArrowLootFollowsItsConfig),
            test("silver_armor_burns_undead_attackers", SilverArmorGameTest::silverArmorBurnsUndeadAttackers),
            test("silver_horse_armor_counts_as_a_full_set", SilverArmorGameTest::silverHorseArmorCountsAsAFullSet),
            test("hostile_mobs_dont_press_silver_plates", SilverPressurePlateGameTest::hostileMobsDontPressSilverPlates),
            test("tarnished_plates_need_more_mobs", SilverPressurePlateGameTest::tarnishedPlatesNeedMoreMobs),
            test("undead_keep_away_from_silver_torches", SilverLightGameTest::undeadKeepAwayFromSilverTorches),
            test("undead_keep_away_from_silver_lanterns", SilverLightGameTest::undeadKeepAwayFromSilverLanterns),
            test("silver_doesnt_tarnish_with_oxidation_off", SilverOxidationGameTest::silverDoesntTarnishWithOxidationOff),
            test("honeycomb_doesnt_wax_with_oxidation_off", SilverOxidationGameTest::honeycombDoesntWaxWithOxidationOff),
            test("clerics_buy_silver_ingots_once", SilverTradesGameTest::clericsBuySilverIngotsOnce),
            test("master_fletchers_sell_silver_arrows", SilverTradesGameTest::masterFletchersSellSilverArrows),
            test("options_guard_their_recipes", SilverRecipeConditionsGameTest::optionsGuardTheirRecipes),
            test("silver_ore_drops_raw_silver", SilverMaterialGameTest::silverOreDropsRawSilver),
            test("silk_touch_keeps_silver_ore", SilverMaterialGameTest::silkTouchKeepsSilverOre),
            test("silver_storage_recipes_round_trip", SilverMaterialGameTest::silverStorageRecipesRoundTrip),
            test("silver_gear_smelts_into_a_nugget", SilverMaterialGameTest::silverGearSmeltsIntoANugget),
            test("silver_blocks_need_an_iron_pickaxe", SilverMaterialGameTest::silverBlocksNeedAnIronPickaxe),
            test("silver_fittings_drop_to_any_pickaxe", SilverMaterialGameTest::silverFittingsDropToAnyPickaxe),
            test("the_stonecutter_cuts_silver_bricks_from_cut_silver", SilverMaterialGameTest::theStonecutterCutsSilverBricksFromCutSilver),
            test("silver_bulb_toggles_on_each_pulse", SilverMaterialGameTest::silverBulbTogglesOnEachPulse),
            test("horse_armor_has_no_durability", SilverMaterialGameTest::horseArmorHasNoDurability),
            test("blocks_are_named_by_their_own_keys", SilverMaterialGameTest::blocksAreNamedByTheirOwnKeys),
            test("dispensers_dont_wax_with_oxidation_off", SilverMaterialGameTest::dispensersDontWaxWithOxidationOff),
            test("silver_armor_stacks_with_thorns", SilverCombatGameTest::silverArmorStacksWithThorns),
            test("silver_swings_scale_with_their_charge", SilverCombatGameTest::silverSwingsScaleWithTheirCharge),
            test("silver_plates_hurt_only_what_stands_in_them", SilverCombatGameTest::silverPlatesHurtOnlyWhatStandsInThem));

    private GameTests() {
    }

    /** A required test in the mod's arena, with vanilla's default of 100 ticks. */
    private static Test test(String name, Consumer<GameTestHelper> body) {
        return new Test(name, body, "silver_test_arena", 100);
    }

    /** One test: its id in the mod's namespace, its body, the structure it runs in and its time limit. */
    public record Test(String name, Consumer<GameTestHelper> body, String structure, int maxTicks) {
        public Test maxTicks(int ticks) {
            return new Test(name, body, structure, ticks);
        }
    }
}
