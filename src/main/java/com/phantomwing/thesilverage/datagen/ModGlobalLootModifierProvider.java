package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.loot.ReplaceItemModifier;
import com.phantomwing.thesilverage.loot.SilverfishDropsModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    @Override
    protected void start() {
        // Silverfish
        add("silver_nugget_from_silverfish", new SilverfishDropsModifier(
                conditions(EntityType.SILVERFISH),
                ModItems.SILVER_NUGGET.get(),
                0,
                2
        ));

        // Desert Pyramid
        add("silver_horse_armor_from_desert_pyramid", new ReplaceItemModifier(
                conditions(BuiltInLootTables.DESERT_PYRAMID, 0.25f),
                ModItems.SILVER_HORSE_ARMOR,
                List.of(Items.IRON_HORSE_ARMOR),
                1
        ));

        // Jungle Temple
        add("silver_ingot_from_jungle_temple", new ReplaceItemModifier(
                conditions(BuiltInLootTables.JUNGLE_TEMPLE, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT),
                1,
                2
        ));
        add("silver_horse_armor_from_jungle_temple", new ReplaceItemModifier(
                conditions(BuiltInLootTables.JUNGLE_TEMPLE, 0.33f),
                ModItems.SILVER_INGOT,
                List.of(Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR), // TODO: Add Copper Horse Armor when added
                1
        ));

        // Shipwrecks
        // TODO: Add Silver Nautilus Armor to SHIPWRECK_SUPPLY, SHIPWRECK_MAP and SHIPWRECK_TREASURE when added
        add("silver_ingot_from_shipwreck_treasure", new ReplaceItemModifier(
                conditions(BuiltInLootTables.SHIPWRECK_TREASURE, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT),
                1,
                2
        ));
        add("silver_nugget_from_shipwreck_treasure", new ReplaceItemModifier(
                conditions(BuiltInLootTables.SHIPWRECK_TREASURE, 0.5f),
                ModItems.SILVER_NUGGET,
                List.of(Items.GOLD_NUGGET, Items.IRON_NUGGET),
                1,
                3
        ));
        add("moon_dial_from_shipwreck_map", new ReplaceItemModifier(
                conditions(BuiltInLootTables.SHIPWRECK_MAP, 0.33f), // Replace Clock/Compass with Moon Dial
                ModItems.MOON_DIAL,
                List.of(Items.CLOCK, Items.COMPASS),
                1,
                2
        ));

        // Buried Treasure
        // TODO: Add Silver Nautilus Armor to BURIED_TREASURE
        add("silver_ingot_from_buried_treasure", new ReplaceItemModifier(
                conditions(BuiltInLootTables.BURIED_TREASURE, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT) // 50% chance to replace all Gold Ingot with Silver Ingot
        ));
        add("silver_sword_from_buried_treasure", new ReplaceItemModifier(
                conditions(BuiltInLootTables.BURIED_TREASURE, 0.5f),
                ModItems.SILVER_SWORD,
                List.of(Items.IRON_SWORD), // 50% chance to replace 1x Iron Sword with 1x Silver Sword
                1
        ));

        // Village
        add("silver_helmet_from_village_armorer", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_ARMORER, 0.1f),
                ModItems.SILVER_HELMET,
                List.of(Items.IRON_HELMET), // 10% chance to replace 1x Iron Helmet with 1x Silver Helmet
                1
        ));
        add("silver_ingot_from_village_temple", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_TEMPLE, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT), // 50% chance to replace 1 stack of Gold Ingot with Silver Ingot
                1
        ));
        add("silver_ingot_from_village_toolsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_TOOLSMITH, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT) // 50% chance to replace all Gold Ingot with Silver Ingot
        ));
        add("silver_pickaxe_from_village_toolsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_TOOLSMITH, 0.1f),
                ModItems.SILVER_PICKAXE,
                List.of(Items.IRON_PICKAXE), // 10% chance to replace 1x Iron Pickaxe with 1x Silver Pickaxe
                1
        ));
        add("silver_ingot_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT) // 50% chance to replace all Gold Ingot with Silver Ingot
        ));
        add("silver_pickaxe_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1f),
                ModItems.SILVER_PICKAXE,
                List.of(Items.IRON_PICKAXE), // 10% chance to replace 1x Iron Pickaxe with 1x Silver Pickaxe
                1
        ));
        add("silver_sword_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1f),
                ModItems.SILVER_SWORD,
                List.of(Items.IRON_SWORD),
                1
        ));
        add("silver_helmet_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1f),
                ModItems.SILVER_HELMET,
                List.of(Items.IRON_HELMET),
                1
        ));
        add("silver_chestplate_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1f),
                ModItems.SILVER_CHESTPLATE,
                List.of(Items.IRON_CHESTPLATE),
                1
        ));
        add("silver_leggings_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1f),
                ModItems.SILVER_LEGGINGS,
                List.of(Items.IRON_LEGGINGS),
                1
        ));
        add("silver_boots_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1f),
                ModItems.SILVER_BOOTS,
                List.of(Items.IRON_BOOTS),
                1
        ));
        add("silver_horse_armor_from_village_weaponsmith", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.33f),
                ModItems.SILVER_HORSE_ARMOR,
                List.of(Items.GOLDEN_HORSE_ARMOR, Items.IRON_HORSE_ARMOR), // TODO: Add Copper Horse Armor when added
                1
        ));
        add("moon_dial_from_village_cartographer", new ReplaceItemModifier(
                conditions(BuiltInLootTables.VILLAGE_CARTOGRAPHER, 0.25f),
                ModItems.MOON_DIAL,
                List.of(Items.COMPASS),
                1
        ));

        // Mineshaft
        add("silver_ingot_from_mineshaft", new ReplaceItemModifier(
                conditions(BuiltInLootTables.ABANDONED_MINESHAFT, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT) // 50% chance to replace all Gold Ingot with Silver Ingot
        ));
        add("silver_pickaxe_from_mineshaft", new ReplaceItemModifier(
                conditions(BuiltInLootTables.ABANDONED_MINESHAFT, 0.1f),
                ModItems.SILVER_PICKAXE,
                List.of(Items.IRON_PICKAXE)
        ));

        // Monster room / Simple Dungeon
        add("silver_ingot_from_simple_dungeon", new ReplaceItemModifier(
                conditions(BuiltInLootTables.SIMPLE_DUNGEON, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT) // 50% chance to replace all Gold Ingot with Silver Ingot
        ));
        add("silver_horse_armor_from_simple_dungeon", new ReplaceItemModifier(
                conditions(BuiltInLootTables.SIMPLE_DUNGEON, 0.25f),
                ModItems.SILVER_HORSE_ARMOR,
                List.of(Items.GOLDEN_HORSE_ARMOR, Items.IRON_HORSE_ARMOR), // TODO: Add Copper Horse Armor when added
                1
        ));

        // Stronghold
        add("silver_horse_armor_from_stronghold_corridor", new ReplaceItemModifier(
                conditions(BuiltInLootTables.STRONGHOLD_CORRIDOR, 0.33f), // Altar
                ModItems.SILVER_HORSE_ARMOR,
                List.of(Items.GOLDEN_HORSE_ARMOR, Items.IRON_HORSE_ARMOR), // TODO: Add Copper Horse Armor when added
                1
        ));
        add("silver_ingot_from_stronghold_corridor", new ReplaceItemModifier(
                conditions(BuiltInLootTables.STRONGHOLD_CORRIDOR), // Altar
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT),
                0,
                2
        ));
        add("silver_ingot_from_stronghold_crossing", new ReplaceItemModifier(
                conditions(BuiltInLootTables.STRONGHOLD_CROSSING), // Storeroom
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT),
                0,
                2
        ));
        add("silver_pickaxe_from_stronghold_crossing", new ReplaceItemModifier(
                conditions(BuiltInLootTables.STRONGHOLD_CROSSING, 0.1f), // Storeroom
                ModItems.SILVER_PICKAXE,
                List.of(Items.IRON_PICKAXE),
                1
        ));
        add("moon_dial_from_stronghold_library", new ReplaceItemModifier(
                conditions(BuiltInLootTables.STRONGHOLD_LIBRARY, 0.25f),
                ModItems.MOON_DIAL,
                List.of(Items.COMPASS),
                1
        ));

        // Pillager Outpost
        add("silver_ingot_from_pillager_outpost", new ReplaceItemModifier(
                conditions(BuiltInLootTables.PILLAGER_OUTPOST), // Storeroom
                ModItems.SILVER_INGOT,
                List.of(Items.IRON_INGOT),
                0,
                2
        ));

        // Trial Chambers
        add("silver_ingot_from_trial_chambers_reward", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, 0.25f), // Storeroom
                ModItems.SILVER_INGOT,
                List.of(Items.IRON_INGOT),
                1
        ));
        add("silver_block_from_trial_chambers_reward_ominous", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS, 0.1f), // Storeroom
                ModItems.SILVER_BLOCK,
                List.of(Items.IRON_BLOCK),
                1
        ));
        add("silver_block_from_trial_chambers_intersection", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION, 0.1f), // Storeroom
                ModItems.SILVER_BLOCK,
                List.of(Items.IRON_BLOCK),
                1
        ));
        add("silver_axe_from_trial_chambers_intersection_barrel", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL, 0.5f), // Storeroom
                ModItems.SILVER_AXE,
                List.of(Items.GOLDEN_AXE),
                1
        ));
        add("silver_pickaxe_from_trial_chambers_intersection_barrel", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL, 0.5f), // Storeroom
                ModItems.SILVER_PICKAXE,
                List.of(Items.GOLDEN_PICKAXE),
                1
        ));
        add("silver_ingot_from_trial_chambers_corridor_pot", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_POT, 0.25f), // Storeroom
                ModItems.SILVER_INGOT,
                List.of(Items.IRON_INGOT),
                1
        ));

        // Ancient City
        add("silver_leggings_from_ancient_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.ANCIENT_CITY, 0.25f), // Storeroom
                ModItems.SILVER_LEGGINGS,
                List.of(Items.IRON_LEGGINGS),
                1
        ));
        add("moon_dial_from_ancient_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.ANCIENT_CITY, 0.25f), // Storeroom
                ModItems.MOON_DIAL,
                List.of(Items.COMPASS),
                1
        ));

        // End City
        add("silver_ingot_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT)
        ));
        add("silver_pickaxe_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_PICKAXE,
                List.of(Items.IRON_PICKAXE), // 10% chance to replace 1x Iron Pickaxe with 1x Silver Pickaxe
                1
        ));
        add("silver_shovel_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_SHOVEL,
                List.of(Items.IRON_SHOVEL), // 10% chance to replace 1x Iron Shovel with 1x Silver Shovel
                1
        ));
        add("silver_sword_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_SWORD,
                List.of(Items.IRON_SWORD),
                1
        ));
        add("silver_helmet_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_HELMET,
                List.of(Items.IRON_HELMET),
                1
        ));
        add("silver_chestplate_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_CHESTPLATE,
                List.of(Items.IRON_CHESTPLATE),
                1
        ));
        add("silver_leggings_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_LEGGINGS,
                List.of(Items.IRON_LEGGINGS),
                1
        ));
        add("silver_boots_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.1f),
                ModItems.SILVER_BOOTS,
                List.of(Items.IRON_BOOTS),
                1
        ));
        add("silver_horse_armor_from_end_city", new ReplaceItemModifier(
                conditions(BuiltInLootTables.END_CITY_TREASURE, 0.33f),
                ModItems.SILVER_HORSE_ARMOR,
                List.of(Items.GOLDEN_HORSE_ARMOR, Items.IRON_HORSE_ARMOR), // TODO: Add Copper Horse Armor when added
                1
        ));

        // Igloo
        add("silver_nugget_from_igloo_chest", new ReplaceItemModifier(
                conditions(BuiltInLootTables.IGLOO_CHEST, 0.5f),
                ModItems.SILVER_NUGGET,
                List.of(Items.GOLD_NUGGET) // 50% chance to replace all Gold Nugget with Silver Nugget
        ));

        // Woodland Mansion
        add("silver_ingot_from_woodland_mansion", new ReplaceItemModifier(
                conditions(BuiltInLootTables.WOODLAND_MANSION, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.GOLD_INGOT) // 50% chance to replace all Gold Ingot with Silver Ingot
        ));

        // Underwater Ruins
        // TODO: Add Silver Nautilus Armor to UNDERWATER_RUIN_BIG and UNDERWATER_RUIN_SMALL when added
        add("silver_nugget_from_underwater_ruin_big", new ReplaceItemModifier(
                conditions(BuiltInLootTables.UNDERWATER_RUIN_BIG, 0.5f),
                ModItems.SILVER_NUGGET,
                List.of(Items.GOLD_NUGGET)
        ));
        add("silver_helmet_from_underwater_ruin_big", new ReplaceItemModifier(
                conditions(BuiltInLootTables.UNDERWATER_RUIN_BIG, 0.5f),
                ModItems.SILVER_HELMET,
                List.of(Items.GOLDEN_HELMET)
        ));
        add("silver_helmet_from_underwater_ruin_small", new ReplaceItemModifier(
                conditions(BuiltInLootTables.UNDERWATER_RUIN_SMALL, 0.5f),
                ModItems.SILVER_HELMET,
                List.of(Items.GOLDEN_HELMET)
        ));
        add("silver_axe_from_underwater_ruin_small", new ReplaceItemModifier(
                conditions(BuiltInLootTables.UNDERWATER_RUIN_SMALL, 0.25f),
                ModItems.SILVER_AXE,
                List.of(Items.STONE_AXE)
        ));
        add("silver_chestplate_from_underwater_ruin_small", new ReplaceItemModifier(
                conditions(BuiltInLootTables.UNDERWATER_RUIN_SMALL, 0.1f),
                ModItems.SILVER_CHESTPLATE,
                List.of(Items.LEATHER_CHESTPLATE)
        ));

        // Ocean Ruins
        add("silver_nugget_from_ocean_ruin_warm_archaeology", new ReplaceItemModifier(
                conditions(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, 0.5f),
                ModItems.SILVER_NUGGET,
                List.of(Items.GOLD_NUGGET)
        ));
        add("silver_axe_from_ocean_ruin_warm_archaeology", new ReplaceItemModifier(
                conditions(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, 0.25f),
                ModItems.SILVER_AXE,
                List.of(Items.IRON_AXE)
        ));
        add("silver_hoe_from_ocean_ruin_warm_archaeology", new ReplaceItemModifier(
                conditions(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, 0.25f),
                ModItems.SILVER_HOE,
                List.of(Items.WOODEN_HOE)
        ));

        // Desert Well
        add("silver_nugget_from_desert_well_archaeology", new ReplaceItemModifier(
                conditions(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, 0.5f),
                ModItems.SILVER_NUGGET,
                List.of(Items.STICK)
        ));

        // Trail Ruins
        add("silver_ingot_from_trail_ruins_archaeology_common", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, 0.5f),
                ModItems.SILVER_INGOT,
                List.of(Items.WHITE_DYE)
        ));
        add("silver_nugget_from_trail_ruins_archaeology_common", new ReplaceItemModifier(
                conditions(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, 0.5f),
                ModItems.SILVER_NUGGET,
                List.of(Items.GOLD_NUGGET)
        ));
    }

    private LootItemCondition[] conditions(EntityType<?> entityType, LootItemCondition... extraConditions) {
        return conditions(entityType.getDefaultLootTable(), 1.0f, extraConditions);
    }

    private LootItemCondition[] conditions(ResourceKey<LootTable> key, LootItemCondition... extraConditions) {
        return conditions(key, 1.0f, extraConditions);
    }

    private LootItemCondition[] conditions(ResourceKey<LootTable> key, float chance, LootItemCondition... extraConditions) {
        LootItemCondition[] conditions = new LootItemCondition[extraConditions.length + 2];

        conditions[0] = isVanillaLootTable(key);
        conditions[1] = LootItemRandomChanceCondition.randomChance(chance).build();

        System.arraycopy(extraConditions, 0, conditions, 1, extraConditions.length);

        return conditions;
    }

    private LootItemCondition isVanillaLootTable(ResourceKey<LootTable> key) {
        return new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace(key.location().getPath())).build();
    }
}
