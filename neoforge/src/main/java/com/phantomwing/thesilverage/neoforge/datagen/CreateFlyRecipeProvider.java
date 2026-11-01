package com.phantomwing.thesilverage.neoforge.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.tags.CommonTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The recipes Create makes of silver, in the format of Create Fly, the Fabric port of Create, which keeps Create's mod
 * id and recipe types but not its JSON: one {@code ingredient} rather than a list, a Deployer's {@code target} and
 * {@code results} list, a Spout's {@code fluid_ingredient} in droplets. They go to the Fabric jar alone
 * ({@code fabric/src/generated/resources}, the path the data run passes as {@code thesilverage.datagen.fabricOutput}),
 * since NeoForge's Create would fail to parse them, and are written as JSON here because nothing on NeoForge has
 * Create Fly's classes.
 *
 * <p>The same recipes as 1.21.1's NeoForge Create compat: the Spout weathers each block a stage, the Deployer waxes it
 * with a Honeycomb Block and scrapes it with an axe (each taken from {@link SilverWeatheringSpec}, so a new silver
 * block gets them), the Press makes Silver Sheets, and Crushed Raw Silver is washed into nuggets or smelted. Create Fly
 * leaves out Create's crushing of other mods' silver, so the mod's ore and raw silver get crushing recipes of its own.
 * Each carries the Fabric conditions its NeoForge one carries.</p>
 */
public class CreateFlyRecipeProvider implements DataProvider {
    /** Create's 250 mB, in Fabric's droplets (81000 a bucket). */
    private static final int WATER_DROPLETS = 20250;
    private static final String CRUSHED_RAW_SILVER = "create:crushed_raw_silver";
    /** Fabric's top-level conditions key ({@code ResourceConditions.CONDITIONS_KEY}). */
    private static final String FABRIC_CONDITIONS = "fabric:load_conditions";

    private final PackOutput.PathProvider recipes;
    private final PackOutput.PathProvider advancements;

    public CreateFlyRecipeProvider(PackOutput fabricOutput) {
        this.recipes = fabricOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
        this.advancements = fabricOutput.createPathProvider(PackOutput.Target.DATA_PACK, "advancement");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> saved = new ArrayList<>();

        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
            JsonObject filling = recipe("create:filling", conditions(Configuration.ENABLE_SILVER_OXIDATION_ID,
                    Configuration.ENABLE_CREATE_SPOUT_OXIDATION_ID));
            filling.addProperty("ingredient", id(pair.from().get()));
            JsonObject water = new JsonObject();
            water.addProperty("type", "fluid_stack");
            water.addProperty("fluid", "minecraft:water");
            water.addProperty("amount", WATER_DROPLETS);
            filling.add("fluid_ingredient", water);
            filling.add("result", output(id(pair.to().get()), 1, 1));
            saved.add(save(cache, "filling/" + path(pair.to().get()) + "_from_oxidising", filling));

            saved.add(save(cache, "deploying/" + path(pair.from().get()) + "_from_deoxidising",
                    deploying(pair.to().get(), "#minecraft:axes", pair.from().get())));
        }
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
            saved.add(save(cache, "deploying/" + path(pair.to().get()) + "_from_adding_wax",
                    deploying(pair.from().get(), "minecraft:honeycomb_block", pair.to().get())));
            saved.add(save(cache, "deploying/" + path(pair.from().get()) + "_from_removing_wax",
                    deploying(pair.to().get(), "#minecraft:axes", pair.from().get())));
        }

        JsonObject pressing = recipe("create:pressing", conditions());
        pressing.addProperty("ingredient", tag(CommonTags.Items.INGOTS_SILVER));
        pressing.add("results", outputs(output(id(ModItems.SILVER_SHEET.get()), 1, 1)));
        saved.add(save(cache, "pressing/silver_sheet", pressing));

        JsonObject splashing = recipe("create:splashing", conditions());
        splashing.addProperty("ingredient", CRUSHED_RAW_SILVER);
        splashing.add("results", outputs(output(id(ModItems.SILVER_NUGGET.get()), 9, 1)));
        saved.add(save(cache, "splashing/crushed_raw_silver", splashing));

        // Create's own crushing of silver, which Create Fly doesn't ship.
        saved.add(save(cache, "crushing/silver_ore", crushing(CommonTags.Items.ORES_SILVER,
                output(CRUSHED_RAW_SILVER, 1, 1), output(CRUSHED_RAW_SILVER, 1, 0.75f),
                output("create:experience_nugget", 1, 0.75f))));
        saved.add(save(cache, "crushing/raw_silver", crushing(CommonTags.Items.RAW_MATERIALS_SILVER,
                output(CRUSHED_RAW_SILVER, 1, 1), output("create:experience_nugget", 1, 0.75f))));
        saved.add(save(cache, "crushing/raw_silver_block", crushing(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER,
                output(CRUSHED_RAW_SILVER, 9, 1), output("create:experience_nugget", 9, 0.75f))));

        saved.addAll(cooking(cache, "minecraft:smelting", "smelting", 200));
        saved.addAll(cooking(cache, "minecraft:blasting", "blasting", 100));

        return CompletableFuture.allOf(saved.toArray(CompletableFuture[]::new));
    }

    /** Crushed Raw Silver smelted into a Silver Ingot, as raw silver is, with its recipe book unlock. */
    private List<CompletableFuture<?>> cooking(CachedOutput cache, String type, String kind, int time) {
        String name = "silver_ingot_from_crushed_raw_silver_" + kind;
        JsonObject cooking = recipe(type, conditions());
        cooking.addProperty("category", "misc");
        cooking.addProperty("cookingtime", time);
        cooking.addProperty("experience", 1.0f);
        cooking.addProperty("ingredient", CRUSHED_RAW_SILVER);
        cooking.add("result", output(id(ModItems.SILVER_INGOT.get()), 1, 1));

        String recipeId = TheSilverAge.MOD_ID + ":" + name;
        JsonObject unlock = new JsonObject();
        unlock.addProperty("parent", "minecraft:recipes/root");
        JsonObject criteria = new JsonObject();
        JsonObject hasItem = new JsonObject();
        hasItem.addProperty("trigger", "minecraft:inventory_changed");
        JsonObject itemConditions = new JsonObject();
        JsonArray items = new JsonArray();
        JsonObject predicate = new JsonObject();
        predicate.addProperty("items", CRUSHED_RAW_SILVER);
        items.add(predicate);
        itemConditions.add("items", items);
        hasItem.add("conditions", itemConditions);
        criteria.add("has_crushed_raw_silver", hasItem);
        JsonObject hasRecipe = new JsonObject();
        hasRecipe.addProperty("trigger", "minecraft:recipe_unlocked");
        JsonObject recipeConditions = new JsonObject();
        recipeConditions.addProperty("recipe", recipeId);
        hasRecipe.add("conditions", recipeConditions);
        criteria.add("has_the_recipe", hasRecipe);
        unlock.add("criteria", criteria);
        JsonArray requirements = new JsonArray();
        JsonArray anyOf = new JsonArray();
        anyOf.add("has_the_recipe");
        anyOf.add("has_crushed_raw_silver");
        requirements.add(anyOf);
        unlock.add("requirements", requirements);
        JsonObject rewards = new JsonObject();
        JsonArray rewardRecipes = new JsonArray();
        rewardRecipes.add(recipeId);
        rewards.add("recipes", rewardRecipes);
        unlock.add("rewards", rewards);
        unlock.add(FABRIC_CONDITIONS, conditions());

        return List.of(save(cache, name, cooking),
                DataProvider.saveStable(cache, unlock, advancements.json(TheSilverAge.resourceLocation("recipes/misc/" + name))));
    }

    /** A Deployer applying {@code held} (an item or {@code #tag}) to {@code target}, the held item kept. */
    private static JsonObject deploying(ItemLike target, String held, ItemLike result) {
        JsonObject deploying = recipe("create:deploying", conditions(Configuration.ENABLE_SILVER_OXIDATION_ID));
        deploying.addProperty("target", id(target));
        deploying.addProperty("ingredient", held);
        deploying.add("results", outputs(output(id(result), 1, 1)));
        deploying.addProperty("keep_held_item", true);
        return deploying;
    }

    private static JsonObject crushing(TagKey<Item> input, JsonObject... results) {
        JsonObject crushing = recipe("create:crushing", conditions());
        crushing.addProperty("ingredient", tag(input));
        crushing.addProperty("processing_time", 400);
        crushing.add("results", outputs(results));
        return crushing;
    }

    private static JsonObject recipe(String type, JsonArray conditions) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", type);
        recipe.add(FABRIC_CONDITIONS, conditions);
        return recipe;
    }

    /** Create Fly loaded, and each of {@code settings} on. */
    private static JsonArray conditions(String... settings) {
        JsonArray conditions = new JsonArray();
        JsonObject create = new JsonObject();
        create.addProperty("condition", "fabric:all_mods_loaded");
        JsonArray mods = new JsonArray();
        mods.add(ModIds.CREATE);
        create.add("values", mods);
        conditions.add(create);
        for (String setting : settings) {
            JsonObject option = new JsonObject();
            option.addProperty("condition", "thesilverage:config_boolean");
            option.addProperty("settingId", setting);
            conditions.add(option);
        }
        return conditions;
    }

    /** One of Create Fly's {@code ProcessingOutput}s, or a filling's {@code ItemStackTemplate}: the defaults left out. */
    private static JsonObject output(String id, int count, float chance) {
        JsonObject output = new JsonObject();
        output.addProperty("id", id);
        if (count != 1) {
            output.addProperty("count", count);
        }
        if (chance != 1) {
            output.addProperty("chance", chance);
        }
        return output;
    }

    private static JsonArray outputs(JsonObject... outputs) {
        JsonArray array = new JsonArray();
        for (JsonObject output : outputs) {
            array.add(output);
        }
        return array;
    }

    private CompletableFuture<?> save(CachedOutput cache, String name, JsonObject json) {
        return DataProvider.saveStable(cache, json, recipes.json(TheSilverAge.resourceLocation(name)));
    }

    private static String id(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).toString();
    }

    private static String path(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private static String tag(TagKey<Item> tag) {
        return "#" + tag.location();
    }

    @Override
    public @NotNull String getName() {
        return "The Silver Age Create Fly recipes";
    }
}
