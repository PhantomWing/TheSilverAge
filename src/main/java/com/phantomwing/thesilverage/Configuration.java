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

    // Villager trades
    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public static ModConfigSpec.BooleanValue ENABLE_VILLAGER_TRADES;

    // Wandering Trader trades
    public static final String ENABLE_WANDERING_TRADER_TRADES_ID = "enable_wandering_trader_trades";
    public static ModConfigSpec.BooleanValue ENABLE_WANDERING_TRADER_TRADES;

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
        SILVERFISH_DROP_SILVER = COMMON_BUILDER.define(SILVERFISH_DROP_SILVER_ID, true);
        OVERRIDE_VANILLA_RECIPES = COMMON_BUILDER.define(OVERRIDE_VANILLA_RECIPES_ID, true);
        ENABLE_VILLAGER_TRADES = COMMON_BUILDER.define(ENABLE_VILLAGER_TRADES_ID, true);
        ENABLE_WANDERING_TRADER_TRADES = COMMON_BUILDER.define(ENABLE_WANDERING_TRADER_TRADES_ID, true);
        GENERATE_STRUCTURE_LOOT = COMMON_BUILDER.define(GENERATE_STRUCTURE_LOOT_ID, true);

        // Build config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
