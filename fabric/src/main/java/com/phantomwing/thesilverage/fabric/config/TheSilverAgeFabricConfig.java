package com.phantomwing.thesilverage.fabric.config;

import com.phantomwing.thesilverage.TheSilverAge;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.jetbrains.annotations.Nullable;

/**
 * Fabric config, backed by Cloth Config's AutoConfig (mirrors the
 * RusticDelight-Fabric pattern). Persists to {@code config/thesilverage.json}.
 *
 * <p>The option ids and their {@code true} defaults are kept 1:1 with the
 * NeoForge {@code Configuration} ({@code ModConfigSpec}) so a user editing
 * configs sees the same five switches with identical semantics on both loaders.
 * Cross-loader code reaches the three loot/recipe gates through the
 * {@code @ExpectPlatform CommonConfig} bridge, whose Fabric impl delegates here
 * (NeoForge's impl delegates to {@code ModConfigSpec}).</p>
 */
@Config(name = TheSilverAge.MOD_ID)
public class TheSilverAgeFabricConfig implements ConfigData {
    public static final String SILVERFISH_DROP_SILVER_ID = "silverfish_drop_silver";
    public boolean silverfish_drop_silver = true;

    public static final String OVERRIDE_VANILLA_RECIPES_ID = "override_vanilla_recipes";
    public boolean override_vanilla_recipes = true;

    // One per RecipeOverride, named by its settingId; each also needs override_vanilla_recipes.
    public boolean override_lodestone_recipe = true;
    public boolean override_brewing_stand_recipe = true;
    public boolean override_comparator_recipe = true;
    public boolean override_repeater_recipe = true;
    public boolean override_glistering_melon_recipe = true;

    public static final String GENERATE_STRUCTURE_LOOT_ID = "generate_structure_loot";
    public boolean generate_structure_loot = true;

    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public boolean enable_villager_trades = true;

    public static final String ENABLE_WANDERING_TRADER_TRADES_ID = "enable_wandering_trader_trades";
    public boolean enable_wandering_trader_trades = true;

    public static final String ENABLE_SILVER_SMITE_ID = "enable_silver_smite";
    public boolean enable_silver_smite = true;

    public static final String ENABLE_UNDEAD_PROTECTION_ID = "enable_undead_protection";
    public boolean enable_undead_protection = true;

    public static final String ENABLE_SILVER_OXIDATION_ID = "enable_silver_oxidation";
    public boolean enable_silver_oxidation = true;

    public static final String ENABLE_MOB_SILVER_EQUIPMENT_ID = "enable_mob_silver_equipment";
    public boolean enable_mob_silver_equipment = true;

    public static final String ENABLE_SILVER_FLOOR_DAMAGE_ID = "enable_silver_floor_damage";
    public boolean enable_silver_floor_damage = true;

    public static TheSilverAgeFabricConfig get() {
        return AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).getConfig();
    }

    /**
     * Registers the config holder + serializer. MUST be called before the first
     * {@link #get()} — i.e. at the very start of the Fabric entrypoint, ahead of
     * the loot mixin / {@code thesilverage:config_boolean} resource condition,
     * both of which read config very early (datapack load).
     */
    public static void register() {
        AutoConfig.register(TheSilverAgeFabricConfig.class, GsonConfigSerializer::new);
    }

    /**
     * Mirrors NeoForge {@code Configuration.getBooleanConfigurationValue} exactly,
     * including throwing on an unknown id (callers only ever pass the five ids
     * above; throwing keeps Fabric/NeoForge behaviour identical).
     */
    public static boolean getBooleanConfigurationValue(String id) {
        Boolean value = booleanValueOf(get(), id);
        if (value == null) {
            throw new Error("Invalid setting ID: " + id);
        }
        return value;
    }

    /** A boolean option's value by its id, or null when no option has that id. */
    @Nullable
    public static Boolean booleanValueOf(TheSilverAgeFabricConfig config, String id) {
        return switch (id) {
            case SILVERFISH_DROP_SILVER_ID -> config.silverfish_drop_silver;
            case ENABLE_VILLAGER_TRADES_ID -> config.enable_villager_trades;
            case ENABLE_WANDERING_TRADER_TRADES_ID -> config.enable_wandering_trader_trades;
            case OVERRIDE_VANILLA_RECIPES_ID -> config.override_vanilla_recipes;
            case GENERATE_STRUCTURE_LOOT_ID -> config.generate_structure_loot;
            case ENABLE_SILVER_SMITE_ID -> config.enable_silver_smite;
            case ENABLE_UNDEAD_PROTECTION_ID -> config.enable_undead_protection;
            case ENABLE_SILVER_OXIDATION_ID -> config.enable_silver_oxidation;
            case ENABLE_MOB_SILVER_EQUIPMENT_ID -> config.enable_mob_silver_equipment;
            case ENABLE_SILVER_FLOOR_DAMAGE_ID -> config.enable_silver_floor_damage;
            case "override_lodestone_recipe" -> config.override_lodestone_recipe;
            case "override_brewing_stand_recipe" -> config.override_brewing_stand_recipe;
            case "override_comparator_recipe" -> config.override_comparator_recipe;
            case "override_repeater_recipe" -> config.override_repeater_recipe;
            case "override_glistering_melon_recipe" -> config.override_glistering_melon_recipe;
            default -> null;
        };
    }
}
