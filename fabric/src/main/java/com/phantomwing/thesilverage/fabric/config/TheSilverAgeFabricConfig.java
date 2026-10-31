package com.phantomwing.thesilverage.fabric.config;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.combat.SilverThornsHandler;
import com.phantomwing.thesilverage.network.ModNetworking;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.InteractionResult;
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

    public static final String GENERATE_SILVER_ORE_ID = "generate_silver_ore";
    public boolean generate_silver_ore = true;

    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public boolean enable_villager_trades = true;

    public static final String ENABLE_WANDERING_TRADER_TRADES_ID = "enable_wandering_trader_trades";
    public boolean enable_wandering_trader_trades = true;

    // Off turns off everything silver does to the undead, whatever the options after it say.
    public static final String SILVER_AFFECTS_UNDEAD_ID = "silver_affects_undead";
    public boolean silver_affects_undead = true;

    public static final String ENABLE_SILVER_SMITE_ID = "enable_silver_smite";
    public boolean enable_silver_smite = true;

    // Written out rather than widened from the handlers' floats, which the file would show as 0.20000000298023224.
    public double silver_smite_damage = 1.0;

    public double silver_arrow_smite_damage = SilverSmiteHandler.ARROW_BONUS_DAMAGE;

    public static final String ENABLE_SILVER_THORNS_ID = "enable_silver_thorns";
    public boolean enable_silver_thorns = true;

    public double silver_thorns_chance = 0.2;

    public static final String ENABLE_SILVER_OXIDATION_ID = "enable_silver_oxidation";
    public boolean enable_silver_oxidation = true;

    public static final String ENABLE_SPOUT_OXIDATION_ID = "enable_spout_oxidation";
    public boolean enable_spout_oxidation = true;

    public static final String ENABLE_SILVER_GEAR_RECIPES_ID = "enable_silver_gear_recipes";
    public boolean enable_silver_gear_recipes = true;

    public static final String ENABLE_MOB_SILVER_EQUIPMENT_ID = "enable_mob_silver_equipment";
    public boolean enable_mob_silver_equipment = true;

    public static final String ENABLE_SILVER_FLOOR_DAMAGE_ID = "enable_silver_floor_damage";
    public boolean enable_silver_floor_damage = true;

    public static final String UNDEAD_AVOID_SILVER_LIGHT_ID = "undead_avoid_silver_light";
    public boolean undead_avoid_silver_light = true;

    public static final String ENABLE_SILVER_ARROWS_ID = "enable_silver_arrows";
    public boolean enable_silver_arrows = true;

    /** Keeps the numbers in NeoForge's ranges, which a hand-edited file can step out of. */
    @Override
    public void validatePostLoad() {
        silver_smite_damage = smiteDamage();
        silver_arrow_smite_damage = arrowSmiteDamage();
        silver_thorns_chance = thornsChance();
    }

    // Read through these rather than the fields: Mod Menu's screen saves without validating, and a NaN in the file
    // passed the clamp and every check against it, so silver armour hurt on every undead hit.
    public double smiteDamage() {
        return inRange(silver_smite_damage, SilverSmiteHandler.MAX_BONUS_DAMAGE, SilverSmiteHandler.BONUS_DAMAGE);
    }

    public double arrowSmiteDamage() {
        return inRange(silver_arrow_smite_damage, SilverSmiteHandler.MAX_BONUS_DAMAGE, SilverSmiteHandler.ARROW_BONUS_DAMAGE);
    }

    public double thornsChance() {
        return inRange(silver_thorns_chance, 1.0, SilverThornsHandler.CHANCE_PER_PIECE);
    }

    private static double inRange(double value, double max, double fallback) {
        return Double.isFinite(value) ? Math.clamp(value, 0.0, max) : fallback;
    }

    public static TheSilverAgeFabricConfig get() {
        return AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).getConfig();
    }

    /**
     * Registers the config holder + serializer. MUST be called before the first
     * {@link #get()} — i.e. at the very start of the Fabric entrypoint, ahead of
     * the loot mixin / {@code thesilverage:config_boolean} resource condition,
     * both of which read config very early (datapack load). The file is read again
     * on every {@code /reload}, before the recipes' conditions are, as NeoForge reads
     * its own whenever it changes: an edit to it takes without a restart.
     */
    public static void register() {
        AutoConfig.register(TheSilverAgeFabricConfig.class, GsonConfigSerializer::new);
        AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).registerSaveListener((holder, config) -> {
            ModNetworking.configChanged();
            return InteractionResult.PASS;
        });
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resources) ->
                AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).load());
    }

    /**
     * Mirrors NeoForge {@code Configuration.getBooleanConfigurationValue} exactly: an id no option has is false, with a
     * warning, since a datapack's {@code thesilverage:config_boolean} condition can name anything.
     */
    public static boolean getBooleanConfigurationValue(String id) {
        Boolean value = booleanValueOf(get(), id);
        if (value == null) {
            TheSilverAge.LOGGER.warn("thesilverage:config_boolean names no setting: {}", id);
            return false;
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
            case GENERATE_SILVER_ORE_ID -> config.generate_silver_ore;
            case SILVER_AFFECTS_UNDEAD_ID -> config.silver_affects_undead;
            case ENABLE_SILVER_SMITE_ID -> config.enable_silver_smite;
            case ENABLE_SILVER_THORNS_ID -> config.enable_silver_thorns;
            case ENABLE_SILVER_OXIDATION_ID -> config.enable_silver_oxidation;
            case ENABLE_SPOUT_OXIDATION_ID -> config.enable_spout_oxidation;
            case ENABLE_SILVER_GEAR_RECIPES_ID -> config.enable_silver_gear_recipes;
            case ENABLE_MOB_SILVER_EQUIPMENT_ID -> config.enable_mob_silver_equipment;
            case ENABLE_SILVER_FLOOR_DAMAGE_ID -> config.enable_silver_floor_damage;
            case UNDEAD_AVOID_SILVER_LIGHT_ID -> config.undead_avoid_silver_light;
            case ENABLE_SILVER_ARROWS_ID -> config.enable_silver_arrows;
            case "override_lodestone_recipe" -> config.override_lodestone_recipe;
            case "override_brewing_stand_recipe" -> config.override_brewing_stand_recipe;
            case "override_comparator_recipe" -> config.override_comparator_recipe;
            case "override_repeater_recipe" -> config.override_repeater_recipe;
            case "override_glistering_melon_recipe" -> config.override_glistering_melon_recipe;
            default -> null;
        };
    }
}
