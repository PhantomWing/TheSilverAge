package com.phantomwing.thesilverage.fabric.config;

import com.phantomwing.thesilverage.TheSilverAge;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

/** Fabric config backed by Cloth Config AutoConfig; persists to config/thesilverage.json. */
@Config(name = TheSilverAge.MOD_ID)
public class TheSilverAgeFabricConfig implements ConfigData {
    public static final String SILVERFISH_DROP_SILVER_ID = "silverfish_drop_silver";
    public boolean silverfish_drop_silver = true;

    public static final String OVERRIDE_VANILLA_RECIPES_ID = "override_vanilla_recipes";
    public boolean override_vanilla_recipes = true;

    public static final String GENERATE_STRUCTURE_LOOT_ID = "generate_structure_loot";
    public boolean generate_structure_loot = true;

    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public boolean enable_villager_trades = true;

    public static final String ENABLE_WANDERING_TRADER_TRADES_ID = "enable_wandering_trader_trades";
    public boolean enable_wandering_trader_trades = true;

    public static TheSilverAgeFabricConfig get() {
        return AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).getConfig();
    }

    /** Must be called before the first get() — config is read very early (datapack load). */
    public static void register() {
        AutoConfig.register(TheSilverAgeFabricConfig.class, GsonConfigSerializer::new);
    }

    public static boolean getBooleanConfigurationValue(String id) {
        TheSilverAgeFabricConfig config = get();
        return switch (id) {
            case SILVERFISH_DROP_SILVER_ID -> config.silverfish_drop_silver;
            case ENABLE_VILLAGER_TRADES_ID -> config.enable_villager_trades;
            case ENABLE_WANDERING_TRADER_TRADES_ID -> config.enable_wandering_trader_trades;
            case OVERRIDE_VANILLA_RECIPES_ID -> config.override_vanilla_recipes;
            case GENERATE_STRUCTURE_LOOT_ID -> config.generate_structure_loot;
            default -> throw new Error("Invalid setting ID: " + id);
        };
    }
}
