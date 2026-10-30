package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
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

    // Silver ore in newly generated chunks, read by the ore's placement (thesilverage:config_boolean).
    public static final String GENERATE_SILVER_ORE_ID = "generate_silver_ore";
    public static ModConfigSpec.BooleanValue GENERATE_SILVER_ORE;

    // Off turns off everything silver does to the undead, whatever the options after it say.
    public static final String SILVER_AFFECTS_UNDEAD_ID = "silver_affects_undead";
    public static ModConfigSpec.BooleanValue SILVER_AFFECTS_UNDEAD;

    // Innate anti-undead damage bonus on silver tools and Silver Arrows, and how much.
    public static final String ENABLE_SILVER_SMITE_ID = "enable_silver_smite";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_SMITE;
    public static final String SILVER_SMITE_DAMAGE_ID = "silver_smite_damage";
    public static ModConfigSpec.DoubleValue SILVER_SMITE_DAMAGE;
    public static final String SILVER_ARROW_SMITE_DAMAGE_ID = "silver_arrow_smite_damage";
    public static ModConfigSpec.DoubleValue SILVER_ARROW_SMITE_DAMAGE;

    // Silver armour hurting the undead that strike its wearer, and each piece's chance of it.
    public static final String ENABLE_SILVER_THORNS_ID = "enable_silver_thorns";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_THORNS;
    public static final String SILVER_THORNS_CHANCE_ID = "silver_thorns_chance";
    public static ModConfigSpec.DoubleValue SILVER_THORNS_CHANCE;

    // Silver weathering: oxidation over time + honeycomb waxing.
    public static final String ENABLE_SILVER_OXIDATION_ID = "enable_silver_oxidation";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_OXIDATION;

    // Create's Spout oxidizing silver with water; also needs enable_silver_oxidation.
    public static final String ENABLE_SPOUT_OXIDATION_ID = "enable_spout_oxidation";
    public static ModConfigSpec.BooleanValue ENABLE_SPOUT_OXIDATION;

    // Crafting silver tools and armor, off for packs whose other silver mod crafts its own from the same grids.
    public static final String ENABLE_SILVER_GEAR_RECIPES_ID = "enable_silver_gear_recipes";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_GEAR_RECIPES;

    // Mobs spawning with silver armor and tools.
    public static final String ENABLE_MOB_SILVER_EQUIPMENT_ID = "enable_mob_silver_equipment";
    public static ModConfigSpec.BooleanValue ENABLE_MOB_SILVER_EQUIPMENT;

    // Silver floors: hurting undead standing on silver, keeping them from spawning on it, and their walking round it.
    public static final String ENABLE_SILVER_FLOOR_DAMAGE_ID = "enable_silver_floor_damage";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_FLOOR_DAMAGE;

    // Silver light: idle undead keeping away from Silver Torches and Lanterns.
    public static final String UNDEAD_AVOID_SILVER_LIGHT_ID = "undead_avoid_silver_light";
    public static ModConfigSpec.BooleanValue UNDEAD_AVOID_SILVER_LIGHT;

    // Silver Arrows: their recipe, the Fletcher's trade and their loot. Ones already made still fly as they did.
    public static final String ENABLE_SILVER_ARROWS_ID = "enable_silver_arrows";
    public static ModConfigSpec.BooleanValue ENABLE_SILVER_ARROWS;

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
            case GENERATE_SILVER_ORE_ID -> Configuration.GENERATE_SILVER_ORE.get();
            case SILVER_AFFECTS_UNDEAD_ID -> Configuration.SILVER_AFFECTS_UNDEAD.get();
            case ENABLE_SILVER_SMITE_ID -> Configuration.ENABLE_SILVER_SMITE.get();
            case ENABLE_SILVER_THORNS_ID -> Configuration.ENABLE_SILVER_THORNS.get();
            case ENABLE_SILVER_OXIDATION_ID -> Configuration.ENABLE_SILVER_OXIDATION.get();
            case ENABLE_SPOUT_OXIDATION_ID -> Configuration.ENABLE_SPOUT_OXIDATION.get();
            case ENABLE_SILVER_GEAR_RECIPES_ID -> Configuration.ENABLE_SILVER_GEAR_RECIPES.get();
            case ENABLE_MOB_SILVER_EQUIPMENT_ID -> Configuration.ENABLE_MOB_SILVER_EQUIPMENT.get();
            case ENABLE_SILVER_FLOOR_DAMAGE_ID -> Configuration.ENABLE_SILVER_FLOOR_DAMAGE.get();
            case UNDEAD_AVOID_SILVER_LIGHT_ID -> Configuration.UNDEAD_AVOID_SILVER_LIGHT.get();
            case ENABLE_SILVER_ARROWS_ID -> Configuration.ENABLE_SILVER_ARROWS.get();
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
        GENERATE_SILVER_ORE = COMMON_BUILDER.define(GENERATE_SILVER_ORE_ID, true);
        SILVER_AFFECTS_UNDEAD = COMMON_BUILDER.define(SILVER_AFFECTS_UNDEAD_ID, true);
        ENABLE_SILVER_SMITE = COMMON_BUILDER.define(ENABLE_SILVER_SMITE_ID, true);
        SILVER_SMITE_DAMAGE = COMMON_BUILDER.defineInRange(SILVER_SMITE_DAMAGE_ID, 1.0, 0.0, SilverSmiteHandler.MAX_BONUS_DAMAGE);
        SILVER_ARROW_SMITE_DAMAGE = COMMON_BUILDER.defineInRange(SILVER_ARROW_SMITE_DAMAGE_ID, 2.0, 0.0, SilverSmiteHandler.MAX_BONUS_DAMAGE);
        ENABLE_SILVER_THORNS = COMMON_BUILDER.define(ENABLE_SILVER_THORNS_ID, true);
        SILVER_THORNS_CHANCE = COMMON_BUILDER.defineInRange(SILVER_THORNS_CHANCE_ID, 0.2, 0.0, 1.0);
        ENABLE_SILVER_OXIDATION = COMMON_BUILDER.define(ENABLE_SILVER_OXIDATION_ID, true);
        ENABLE_SPOUT_OXIDATION = COMMON_BUILDER.define(ENABLE_SPOUT_OXIDATION_ID, true);
        ENABLE_SILVER_GEAR_RECIPES = COMMON_BUILDER.define(ENABLE_SILVER_GEAR_RECIPES_ID, true);
        ENABLE_MOB_SILVER_EQUIPMENT = COMMON_BUILDER.define(ENABLE_MOB_SILVER_EQUIPMENT_ID, true);
        ENABLE_SILVER_FLOOR_DAMAGE = COMMON_BUILDER.define(ENABLE_SILVER_FLOOR_DAMAGE_ID, true);
        UNDEAD_AVOID_SILVER_LIGHT = COMMON_BUILDER.define(UNDEAD_AVOID_SILVER_LIGHT_ID, true);
        ENABLE_SILVER_ARROWS = COMMON_BUILDER.define(ENABLE_SILVER_ARROWS_ID, true);

        // Build config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
