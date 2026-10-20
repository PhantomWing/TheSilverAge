package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public class Configuration {
    public static ModConfigSpec COMMON_CONFIG;

    // COMMON
    public static final String SILVERFISH_DROP_SILVER_ID = "silverfish_drop_silver";
    public static ModConfigSpec.BooleanValue SILVERFISH_DROP_SILVER;

    public static final String OVERRIDE_VANILLA_RECIPES_ID = "override_vanilla_recipes";
    public static ModConfigSpec.BooleanValue OVERRIDE_VANILLA_RECIPES;

    // One option per vanilla recipe override, each also needing the master switch above.
    public static final Map<RecipeOverride, ModConfigSpec.BooleanValue> RECIPE_OVERRIDES = new EnumMap<>(RecipeOverride.class);

    public static final String GENERATE_STRUCTURE_LOOT_ID = "generate_structure_loot";
    public static ModConfigSpec.BooleanValue GENERATE_STRUCTURE_LOOT;

    // Innate anti-undead damage bonus on silver tools.
    public static final String ENABLE_SILVER_SMITE_ID = "enable_silver_smite";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_SMITE;

    // Innate anti-undead damage reduction on silver armour.
    public static final String ENABLE_UNDEAD_PROTECTION_ID = "enable_undead_protection";
    public static ModConfigSpec.BooleanValue ENABLE_UNDEAD_PROTECTION;

    // Silver weathering: oxidation over time + honeycomb waxing.
    public static final String ENABLE_SILVER_OXIDATION_ID = "enable_silver_oxidation";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_OXIDATION;

    // Mobs spawning with silver armor and tools.
    public static final String ENABLE_MOB_SILVER_EQUIPMENT_ID = "enable_mob_silver_equipment";
    public static ModConfigSpec.BooleanValue ENABLE_MOB_SILVER_EQUIPMENT;

    // Silver floors: hurting undead standing on silver, keeping them from spawning on it, and their walking round it.
    public static final String ENABLE_SILVER_FLOOR_DAMAGE_ID = "enable_silver_floor_damage";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_FLOOR_DAMAGE;

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
            case ENABLE_SILVER_SMITE_ID -> Configuration.ENABLE_SILVER_SMITE.get();
            case ENABLE_UNDEAD_PROTECTION_ID -> Configuration.ENABLE_UNDEAD_PROTECTION.get();
            case ENABLE_SILVER_OXIDATION_ID -> Configuration.ENABLE_SILVER_OXIDATION.get();
            case ENABLE_MOB_SILVER_EQUIPMENT_ID -> Configuration.ENABLE_MOB_SILVER_EQUIPMENT.get();
            case ENABLE_SILVER_FLOOR_DAMAGE_ID -> Configuration.ENABLE_SILVER_FLOOR_DAMAGE.get();
            default -> {
                for (Map.Entry<RecipeOverride, ModConfigSpec.BooleanValue> entry : RECIPE_OVERRIDES.entrySet()) {
                    if (entry.getKey().settingId().equals(id)) {
                        yield entry.getValue().get();
                    }
                }
                // A datapack's config_boolean condition can name anything: one that names no setting is false.
                TheSilverAge.LOGGER.warn("thesilverage:config_boolean names no setting: {}", id);
                yield false;
            }
        };
    }

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        // General settings
        SILVERFISH_DROP_SILVER = COMMON_BUILDER.define(SILVERFISH_DROP_SILVER_ID, true);
        OVERRIDE_VANILLA_RECIPES = COMMON_BUILDER.define(OVERRIDE_VANILLA_RECIPES_ID, true);
        for (RecipeOverride override : RecipeOverride.values()) {
            RECIPE_OVERRIDES.put(override, COMMON_BUILDER.define(override.settingId(), true));
        }
        ENABLE_VILLAGER_TRADES = COMMON_BUILDER.define(ENABLE_VILLAGER_TRADES_ID, true);
        ENABLE_WANDERING_TRADER_TRADES = COMMON_BUILDER.define(ENABLE_WANDERING_TRADER_TRADES_ID, true);
        GENERATE_STRUCTURE_LOOT = COMMON_BUILDER.define(GENERATE_STRUCTURE_LOOT_ID, true);
        ENABLE_SILVER_SMITE = COMMON_BUILDER.define(ENABLE_SILVER_SMITE_ID, true);
        ENABLE_UNDEAD_PROTECTION = COMMON_BUILDER.define(ENABLE_UNDEAD_PROTECTION_ID, true);
        ENABLE_SILVER_OXIDATION = COMMON_BUILDER.define(ENABLE_SILVER_OXIDATION_ID, true);
        ENABLE_MOB_SILVER_EQUIPMENT = COMMON_BUILDER.define(ENABLE_MOB_SILVER_EQUIPMENT_ID, true);
        ENABLE_SILVER_FLOOR_DAMAGE = COMMON_BUILDER.define(ENABLE_SILVER_FLOOR_DAMAGE_ID, true);

        // Build config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
