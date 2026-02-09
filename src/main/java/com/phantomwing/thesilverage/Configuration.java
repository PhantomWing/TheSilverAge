package com.phantomwing.thesilverage;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Configuration {
    public static ModConfigSpec COMMON_CONFIG;

    // COMMON
    public static final String SILVERFISH_DROP_SILVER_ID = "silverfish_drop_silver";
    public static ModConfigSpec.BooleanValue SILVERFISH_DROP_SILVER;

    public static final String OVERRIDE_VANILLA_RECIPES_ID = "override_vanilla_recipes";
    public static ModConfigSpec.BooleanValue OVERRIDE_VANILLA_RECIPES;

    public static final String GENERATE_STRUCTURE_LOOT_ID = "generate_structure_loot";
    public static ModConfigSpec.BooleanValue GENERATE_STRUCTURE_LOOT;

    public static final String CHANCE_SILVER_ORE_ID = "silver_ore_chance";
    public static ModConfigSpec.IntValue CHANCE_SILVER_ORE;

    // Villager trades
    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public static ModConfigSpec.BooleanValue ENABLE_VILLAGER_TRADES;

    // Wandering Trader trades
    public static final String ENABLE_WANDERING_TRADER_TRADES_ID = "enable_wandering_trader_trades";
    public static ModConfigSpec.BooleanValue ENABLE_WANDERING_TRADER_TRADES;

    public static int getIntConfigurationValue(String id) {
        return switch (id) {
            case CHANCE_SILVER_ORE_ID -> Configuration.CHANCE_SILVER_ORE.get();
            default -> throw new Error("Invalid setting ID: " + id);
        };
    }

    public static boolean getBooleanConfigurationValue(String id) {
        return switch (id) {
            case SILVERFISH_DROP_SILVER_ID -> Configuration.SILVERFISH_DROP_SILVER.get();
            case ENABLE_VILLAGER_TRADES_ID -> Configuration.ENABLE_VILLAGER_TRADES.get();
            case ENABLE_WANDERING_TRADER_TRADES_ID -> Configuration.ENABLE_WANDERING_TRADER_TRADES.get();
            case OVERRIDE_VANILLA_RECIPES_ID -> Configuration.OVERRIDE_VANILLA_RECIPES.get();
            case GENERATE_STRUCTURE_LOOT_ID -> Configuration.GENERATE_STRUCTURE_LOOT.get();
            default -> throw new Error("Invalid setting ID: " + id);
        };
    }

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        // General settings
        SILVERFISH_DROP_SILVER = COMMON_BUILDER.comment("Should Silverfish drop Silver Nuggets?").define(SILVERFISH_DROP_SILVER_ID, true);
        OVERRIDE_VANILLA_RECIPES = COMMON_BUILDER.comment("Tweaks some existing crafting recipes to use Silver instead. (e.g. Brewing Stand, Repeater, Comparator, ...)").define(OVERRIDE_VANILLA_RECIPES_ID, true);
        ENABLE_VILLAGER_TRADES = COMMON_BUILDER.comment("Should villagers trade The Silver Age items? (May reduce chances of other trades appearing)").define(ENABLE_VILLAGER_TRADES_ID, true);
        ENABLE_WANDERING_TRADER_TRADES = COMMON_BUILDER.comment("Should the Wandering Trader sell The Silver Age items?").define(ENABLE_WANDERING_TRADER_TRADES_ID, true);
        GENERATE_STRUCTURE_LOOT = COMMON_BUILDER.comment("Generate The Silver Age loot in structures?").define(GENERATE_STRUCTURE_LOOT_ID, true);

        // Silver generation settings
        CHANCE_SILVER_ORE = COMMON_BUILDER.comment("Chance of generating silver ore. Smaller value = more frequent. Provide zero to disable generation.")
                .defineInRange(CHANCE_SILVER_ORE_ID, 32, 0, Integer.MAX_VALUE);

        // Build config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
