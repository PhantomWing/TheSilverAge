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
            test("mobs_hold_silver_tools", SilverGearGameTest::mobsHoldSilverTools),
            test("husks_wear_silver_armor", SilverGearGameTest::husksWearSilverArmor),
            test("mob_rolls_once", SilverGearGameTest::mobRollsOnce),
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
            test("silver_data_outranks_neoforge", SilverRecipesGameTest::silverDataOutranksNeoForge));

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
