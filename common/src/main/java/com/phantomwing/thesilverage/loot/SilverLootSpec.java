package com.phantomwing.thesilverage.loot;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.Supplier;

/**
 * Single, loader-agnostic source of truth for every Silver loot injection.
 *
 * <p>{@code item} suppliers are lazy so this class can be loaded before item
 * registration completes; they resolve when {@link #entries()} is iterated.</p>
 */
public final class SilverLootSpec {
    private SilverLootSpec() {
    }

    public enum Op {
        /** Appends {@code [min,max]} of {@code item} (stack-splitting). */
        ADD,
        /** Transmutes up to {@code [minStacks,maxStacks]} (0 ⇒ all) matched stacks. */
        REPLACE,
        /** Tests carried conditions, then appends {@code [min,max]} of {@code item}. */
        SILVERFISH
    }

    /**
     * One loot injection.
     *
     * @param id             GLM datagen JSON file name (e.g. {@code silver_ingot_from_jungle_temple}).
     * @param targetLootTable vanilla loot table this applies to ({@code minecraft:...}).
     * @param chance         {@code random_chance} probability (the GLM {@code conditions(...)} chance arg).
     * @param op             which algorithm to run.
     * @param item           the item to add / transmute into.
     * @param min            ADD/SILVERFISH: min count. REPLACE: minStacks.
     * @param max            ADD/SILVERFISH: max count. REPLACE: maxStacks (0 ⇒ replace all).
     * @param removedItems   REPLACE only: items eligible to be replaced (empty for ADD/SILVERFISH).
     */
    public record Entry(String id, Identifier targetLootTable, float chance, Op op,
                        Supplier<Item> item, int min, int max, List<Supplier<Item>> removedItems) {
    }

    private static Identifier mc(String path) {
        return Identifier.withDefaultNamespace(path);
    }

    private static Supplier<Item> vanilla(Item item) {
        return () -> item;
    }

    private static final Identifier SILVERFISH_ENTITY = mc("entities/silverfish");
    private static final Identifier DESERT_PYRAMID = mc("chests/desert_pyramid");
    private static final Identifier JUNGLE_TEMPLE = mc("chests/jungle_temple");
    private static final Identifier SHIPWRECK_TREASURE = mc("chests/shipwreck_treasure");
    private static final Identifier SHIPWRECK_MAP = mc("chests/shipwreck_map");
    private static final Identifier SHIPWRECK_SUPPLY = mc("chests/shipwreck_supply");
    private static final Identifier BURIED_TREASURE = mc("chests/buried_treasure");
    private static final Identifier VILLAGE_ARMORER = mc("chests/village/village_armorer");
    private static final Identifier VILLAGE_TEMPLE = mc("chests/village/village_temple");
    private static final Identifier VILLAGE_TOOLSMITH = mc("chests/village/village_toolsmith");
    private static final Identifier VILLAGE_WEAPONSMITH = mc("chests/village/village_weaponsmith");
    private static final Identifier VILLAGE_CARTOGRAPHER = mc("chests/village/village_cartographer");
    private static final Identifier WEAPONSMITH_GIFT = mc("gameplay/hero_of_the_village/weaponsmith_gift");
    private static final Identifier ABANDONED_MINESHAFT = mc("chests/abandoned_mineshaft");
    private static final Identifier SIMPLE_DUNGEON = mc("chests/simple_dungeon");
    private static final Identifier STRONGHOLD_CORRIDOR = mc("chests/stronghold_corridor");
    private static final Identifier STRONGHOLD_CROSSING = mc("chests/stronghold_crossing");
    private static final Identifier STRONGHOLD_LIBRARY = mc("chests/stronghold_library");
    private static final Identifier PILLAGER_OUTPOST = mc("chests/pillager_outpost");
    private static final Identifier TRIAL_CHAMBERS_REWARD = mc("chests/trial_chambers/reward");
    private static final Identifier TRIAL_CHAMBERS_REWARD_OMINOUS = mc("chests/trial_chambers/reward_ominous");
    private static final Identifier TRIAL_CHAMBERS_INTERSECTION = mc("chests/trial_chambers/intersection");
    private static final Identifier TRIAL_CHAMBERS_INTERSECTION_BARREL = mc("chests/trial_chambers/intersection_barrel");
    private static final Identifier TRIAL_CHAMBERS_CORRIDOR_POT = mc("pots/trial_chambers/corridor");
    private static final Identifier ANCIENT_CITY = mc("chests/ancient_city");
    private static final Identifier END_CITY_TREASURE = mc("chests/end_city_treasure");
    private static final Identifier IGLOO_CHEST = mc("chests/igloo_chest");
    private static final Identifier WOODLAND_MANSION = mc("chests/woodland_mansion");
    private static final Identifier UNDERWATER_RUIN_BIG = mc("chests/underwater_ruin_big");
    private static final Identifier UNDERWATER_RUIN_SMALL = mc("chests/underwater_ruin_small");
    private static final Identifier OCEAN_RUIN_WARM_ARCHAEOLOGY = mc("archaeology/ocean_ruin_warm");
    private static final Identifier DESERT_WELL_ARCHAEOLOGY = mc("archaeology/desert_well");
    private static final Identifier TRAIL_RUINS_ARCHAEOLOGY_COMMON = mc("archaeology/trail_ruins_common");

    /** The complete ordered list of loot injections. */
    public static List<Entry> entries() {
        return List.of(
                // Silverfish
                new Entry("silver_nugget_from_silverfish", SILVERFISH_ENTITY, 1.0f, Op.SILVERFISH,
                        ModItems.SILVER_NUGGET::get, 0, 2, List.of()),

                // Desert Pyramid
                new Entry("silver_horse_armor_from_desert_pyramid", DESERT_PYRAMID, 0.25f, Op.REPLACE,
                        ModItems.SILVER_HORSE_ARMOR::get, 1, 1, List.of(vanilla(Items.IRON_HORSE_ARMOR))),

                // Jungle Temple
                new Entry("silver_ingot_from_jungle_temple", JUNGLE_TEMPLE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 2, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_horse_armor_from_jungle_temple", JUNGLE_TEMPLE, 0.33f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 1,
                        List.of(vanilla(Items.IRON_HORSE_ARMOR), vanilla(Items.GOLDEN_HORSE_ARMOR))),

                // Shipwrecks
                new Entry("silver_ingot_from_shipwreck_treasure", SHIPWRECK_TREASURE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 2, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_nugget_from_shipwreck_treasure", SHIPWRECK_TREASURE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_NUGGET::get, 1, 3,
                        List.of(vanilla(Items.GOLD_NUGGET), vanilla(Items.IRON_NUGGET))),
                new Entry("moon_dial_from_shipwreck_map", SHIPWRECK_MAP, 0.33f, Op.REPLACE,
                        ModItems.MOON_DIAL::get, 1, 2,
                        List.of(vanilla(Items.CLOCK), vanilla(Items.COMPASS))),

                // Buried Treasure
                new Entry("silver_ingot_from_buried_treasure", BURIED_TREASURE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_sword_from_buried_treasure", BURIED_TREASURE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_SWORD::get, 1, 1, List.of(vanilla(Items.IRON_SWORD))),

                // Village
                new Entry("silver_helmet_from_village_armorer", VILLAGE_ARMORER, 0.1f, Op.REPLACE,
                        ModItems.SILVER_HELMET::get, 1, 1, List.of(vanilla(Items.IRON_HELMET))),
                new Entry("silver_ingot_from_village_temple", VILLAGE_TEMPLE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 1, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_ingot_from_village_toolsmith", VILLAGE_TOOLSMITH, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_pickaxe_from_village_toolsmith", VILLAGE_TOOLSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_PICKAXE::get, 1, 1, List.of(vanilla(Items.IRON_PICKAXE))),
                new Entry("silver_ingot_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_pickaxe_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_PICKAXE::get, 1, 1, List.of(vanilla(Items.IRON_PICKAXE))),
                new Entry("silver_sword_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_SWORD::get, 1, 1, List.of(vanilla(Items.IRON_SWORD))),
                new Entry("silver_helmet_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_HELMET::get, 1, 1, List.of(vanilla(Items.IRON_HELMET))),
                new Entry("silver_chestplate_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_CHESTPLATE::get, 1, 1, List.of(vanilla(Items.IRON_CHESTPLATE))),
                new Entry("silver_leggings_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_LEGGINGS::get, 1, 1, List.of(vanilla(Items.IRON_LEGGINGS))),
                new Entry("silver_boots_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.1f, Op.REPLACE,
                        ModItems.SILVER_BOOTS::get, 1, 1, List.of(vanilla(Items.IRON_BOOTS))),
                new Entry("silver_horse_armor_from_village_weaponsmith", VILLAGE_WEAPONSMITH, 0.33f, Op.REPLACE,
                        ModItems.SILVER_HORSE_ARMOR::get, 1, 1,
                        List.of(vanilla(Items.GOLDEN_HORSE_ARMOR), vanilla(Items.IRON_HORSE_ARMOR))),
                new Entry("moon_dial_from_village_cartographer", VILLAGE_CARTOGRAPHER, 0.25f, Op.REPLACE,
                        ModItems.MOON_DIAL::get, 1, 1, List.of(vanilla(Items.COMPASS))),

                // Villager gifts
                new Entry("silver_axe_from_weaponsmith_gift", WEAPONSMITH_GIFT, 0.25f, Op.REPLACE,
                        ModItems.SILVER_AXE::get, 1, 1,
                        List.of(vanilla(Items.STONE_AXE), vanilla(Items.IRON_AXE), vanilla(Items.GOLDEN_AXE))),

                // Mineshaft
                new Entry("silver_ingot_from_mineshaft", ABANDONED_MINESHAFT, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_pickaxe_from_mineshaft", ABANDONED_MINESHAFT, 0.1f, Op.REPLACE,
                        ModItems.SILVER_PICKAXE::get, 0, 0, List.of(vanilla(Items.IRON_PICKAXE))),

                // Monster room / Simple Dungeon
                new Entry("silver_ingot_from_simple_dungeon", SIMPLE_DUNGEON, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_horse_armor_from_simple_dungeon", SIMPLE_DUNGEON, 0.25f, Op.REPLACE,
                        ModItems.SILVER_HORSE_ARMOR::get, 1, 1,
                        List.of(vanilla(Items.GOLDEN_HORSE_ARMOR), vanilla(Items.IRON_HORSE_ARMOR))),

                // Stronghold
                new Entry("silver_horse_armor_from_stronghold_corridor", STRONGHOLD_CORRIDOR, 0.33f, Op.REPLACE,
                        ModItems.SILVER_HORSE_ARMOR::get, 1, 1,
                        List.of(vanilla(Items.GOLDEN_HORSE_ARMOR), vanilla(Items.IRON_HORSE_ARMOR))),
                new Entry("silver_ingot_from_stronghold_corridor", STRONGHOLD_CORRIDOR, 1.0f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 2, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_ingot_from_stronghold_crossing", STRONGHOLD_CROSSING, 1.0f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 2, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_pickaxe_from_stronghold_crossing", STRONGHOLD_CROSSING, 0.1f, Op.REPLACE,
                        ModItems.SILVER_PICKAXE::get, 1, 1, List.of(vanilla(Items.IRON_PICKAXE))),
                new Entry("moon_dial_from_stronghold_library", STRONGHOLD_LIBRARY, 0.25f, Op.REPLACE,
                        ModItems.MOON_DIAL::get, 1, 1, List.of(vanilla(Items.COMPASS))),

                // Pillager Outpost
                new Entry("silver_ingot_from_pillager_outpost", PILLAGER_OUTPOST, 1.0f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 2, List.of(vanilla(Items.IRON_INGOT))),

                // Trial Chambers
                new Entry("silver_ingot_from_trial_chambers_reward", TRIAL_CHAMBERS_REWARD, 0.25f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 1, List.of(vanilla(Items.IRON_INGOT))),
                new Entry("silver_block_from_trial_chambers_reward_ominous", TRIAL_CHAMBERS_REWARD_OMINOUS, 0.1f, Op.REPLACE,
                        ModItems.SILVER_BLOCK::get, 1, 1, List.of(vanilla(Items.IRON_BLOCK))),
                new Entry("silver_block_from_trial_chambers_intersection", TRIAL_CHAMBERS_INTERSECTION, 0.1f, Op.REPLACE,
                        ModItems.SILVER_BLOCK::get, 1, 1, List.of(vanilla(Items.IRON_BLOCK))),
                new Entry("silver_axe_from_trial_chambers_intersection_barrel", TRIAL_CHAMBERS_INTERSECTION_BARREL, 0.5f, Op.REPLACE,
                        ModItems.SILVER_AXE::get, 1, 1, List.of(vanilla(Items.GOLDEN_AXE))),
                new Entry("silver_pickaxe_from_trial_chambers_intersection_barrel", TRIAL_CHAMBERS_INTERSECTION_BARREL, 0.5f, Op.REPLACE,
                        ModItems.SILVER_PICKAXE::get, 1, 1, List.of(vanilla(Items.GOLDEN_PICKAXE))),
                new Entry("silver_ingot_from_trial_chambers_corridor_pot", TRIAL_CHAMBERS_CORRIDOR_POT, 0.25f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 1, List.of(vanilla(Items.IRON_INGOT))),

                // Ancient City
                new Entry("silver_leggings_from_ancient_city", ANCIENT_CITY, 0.5f, Op.REPLACE,
                        ModItems.SILVER_LEGGINGS::get, 1, 1, List.of(vanilla(Items.IRON_LEGGINGS))),
                new Entry("moon_dial_from_ancient_city", ANCIENT_CITY, 0.5f, Op.REPLACE,
                        ModItems.MOON_DIAL::get, 1, 1, List.of(vanilla(Items.COMPASS))),
                new Entry("silver_ingot_from_ancient_city", ANCIENT_CITY, 0.25f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 1, 2, List.of(vanilla(Items.COAL))),

                // End City
                new Entry("silver_ingot_from_end_city", END_CITY_TREASURE, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),
                new Entry("silver_pickaxe_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_PICKAXE::get, 1, 1, List.of(vanilla(Items.IRON_PICKAXE))),
                new Entry("silver_shovel_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_SHOVEL::get, 1, 1, List.of(vanilla(Items.IRON_SHOVEL))),
                new Entry("silver_sword_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_SWORD::get, 1, 1, List.of(vanilla(Items.IRON_SWORD))),
                new Entry("silver_helmet_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_HELMET::get, 1, 1, List.of(vanilla(Items.IRON_HELMET))),
                new Entry("silver_chestplate_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_CHESTPLATE::get, 1, 1, List.of(vanilla(Items.IRON_CHESTPLATE))),
                new Entry("silver_leggings_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_LEGGINGS::get, 1, 1, List.of(vanilla(Items.IRON_LEGGINGS))),
                new Entry("silver_boots_from_end_city", END_CITY_TREASURE, 0.1f, Op.REPLACE,
                        ModItems.SILVER_BOOTS::get, 1, 1, List.of(vanilla(Items.IRON_BOOTS))),
                new Entry("silver_horse_armor_from_end_city", END_CITY_TREASURE, 0.33f, Op.REPLACE,
                        ModItems.SILVER_HORSE_ARMOR::get, 1, 1,
                        List.of(vanilla(Items.GOLDEN_HORSE_ARMOR), vanilla(Items.IRON_HORSE_ARMOR))),

                // Igloo
                new Entry("silver_nugget_from_igloo_chest", IGLOO_CHEST, 0.5f, Op.REPLACE,
                        ModItems.SILVER_NUGGET::get, 0, 0, List.of(vanilla(Items.GOLD_NUGGET))),

                // Woodland Mansion
                new Entry("silver_ingot_from_woodland_mansion", WOODLAND_MANSION, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.GOLD_INGOT))),

                // Underwater Ruins
                new Entry("silver_nugget_from_underwater_ruin_big", UNDERWATER_RUIN_BIG, 0.5f, Op.REPLACE,
                        ModItems.SILVER_NUGGET::get, 0, 0, List.of(vanilla(Items.GOLD_NUGGET))),
                new Entry("silver_helmet_from_underwater_ruin_big", UNDERWATER_RUIN_BIG, 0.5f, Op.REPLACE,
                        ModItems.SILVER_HELMET::get, 0, 0, List.of(vanilla(Items.GOLDEN_HELMET))),
                new Entry("silver_helmet_from_underwater_ruin_small", UNDERWATER_RUIN_SMALL, 0.5f, Op.REPLACE,
                        ModItems.SILVER_HELMET::get, 0, 0, List.of(vanilla(Items.GOLDEN_HELMET))),
                new Entry("silver_axe_from_underwater_ruin_small", UNDERWATER_RUIN_SMALL, 0.25f, Op.REPLACE,
                        ModItems.SILVER_AXE::get, 0, 0, List.of(vanilla(Items.STONE_AXE))),
                new Entry("silver_chestplate_from_underwater_ruin_small", UNDERWATER_RUIN_SMALL, 0.1f, Op.REPLACE,
                        ModItems.SILVER_CHESTPLATE::get, 0, 0, List.of(vanilla(Items.LEATHER_CHESTPLATE))),

                // Nautilus Armor
                new Entry("silver_nautilus_armor_from_buried_treasure", BURIED_TREASURE, 0.25f, Op.REPLACE,
                        ModItems.SILVER_NAUTILUS_ARMOR::get, 1, 1, List.of(vanilla(Items.COPPER_NAUTILUS_ARMOR))),
                new Entry("silver_nautilus_armor_from_shipwreck_treasure", SHIPWRECK_TREASURE, 0.25f, Op.REPLACE,
                        ModItems.SILVER_NAUTILUS_ARMOR::get, 1, 1, List.of(vanilla(Items.COPPER_NAUTILUS_ARMOR))),
                new Entry("silver_nautilus_armor_from_shipwreck_supply", SHIPWRECK_SUPPLY, 0.25f, Op.REPLACE,
                        ModItems.SILVER_NAUTILUS_ARMOR::get, 1, 1, List.of(vanilla(Items.COPPER_NAUTILUS_ARMOR))),
                new Entry("silver_nautilus_armor_from_shipwreck_map", SHIPWRECK_MAP, 0.25f, Op.REPLACE,
                        ModItems.SILVER_NAUTILUS_ARMOR::get, 1, 1, List.of(vanilla(Items.COPPER_NAUTILUS_ARMOR))),
                new Entry("silver_nautilus_armor_from_underwater_ruin_big", UNDERWATER_RUIN_BIG, 0.25f, Op.REPLACE,
                        ModItems.SILVER_NAUTILUS_ARMOR::get, 1, 1, List.of(vanilla(Items.COPPER_NAUTILUS_ARMOR))),
                new Entry("silver_nautilus_armor_from_underwater_ruin_small", UNDERWATER_RUIN_SMALL, 0.25f, Op.REPLACE,
                        ModItems.SILVER_NAUTILUS_ARMOR::get, 1, 1, List.of(vanilla(Items.COPPER_NAUTILUS_ARMOR))),

                // Ocean Ruins
                new Entry("silver_nugget_from_ocean_ruin_warm_archaeology", OCEAN_RUIN_WARM_ARCHAEOLOGY, 0.5f, Op.REPLACE,
                        ModItems.SILVER_NUGGET::get, 0, 0, List.of(vanilla(Items.GOLD_NUGGET))),
                new Entry("silver_axe_from_ocean_ruin_warm_archaeology", OCEAN_RUIN_WARM_ARCHAEOLOGY, 0.25f, Op.REPLACE,
                        ModItems.SILVER_AXE::get, 0, 0, List.of(vanilla(Items.IRON_AXE))),
                new Entry("silver_hoe_from_ocean_ruin_warm_archaeology", OCEAN_RUIN_WARM_ARCHAEOLOGY, 0.25f, Op.REPLACE,
                        ModItems.SILVER_HOE::get, 0, 0, List.of(vanilla(Items.WOODEN_HOE))),

                // Desert Well
                new Entry("silver_nugget_from_desert_well_archaeology", DESERT_WELL_ARCHAEOLOGY, 0.5f, Op.REPLACE,
                        ModItems.SILVER_NUGGET::get, 0, 0, List.of(vanilla(Items.STICK))),

                // Trail Ruins
                new Entry("silver_ingot_from_trail_ruins_archaeology_common", TRAIL_RUINS_ARCHAEOLOGY_COMMON, 0.5f, Op.REPLACE,
                        ModItems.SILVER_INGOT::get, 0, 0, List.of(vanilla(Items.DYE.pick(DyeColor.WHITE)))),
                new Entry("silver_nugget_from_trail_ruins_archaeology_common", TRAIL_RUINS_ARCHAEOLOGY_COMMON, 0.5f, Op.REPLACE,
                        ModItems.SILVER_NUGGET::get, 0, 0, List.of(vanilla(Items.GOLD_NUGGET)))
        );
    }
}
