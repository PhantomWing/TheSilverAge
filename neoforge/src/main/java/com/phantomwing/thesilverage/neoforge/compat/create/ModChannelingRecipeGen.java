package com.phantomwing.thesilverage.neoforge.compat.create;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.neoforge.Configuration;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Create: Aquatic Ambitions' "channeling" recipes for silver, as it ships for copper: a fan blowing through
 * a Mechanical Conduit advances each block one weathering stage. Every step comes from {@link SilverWeatheringSpec}.
 * <p>
 * Written as JSON rather than through a recipe builder, since the mod isn't on the classpath. Gated on the mod being
 * loaded and on {@code enable_silver_oxidation}; {@code FabricConditionsProvider} adds the Fabric side.
 */
public class ModChannelingRecipeGen implements DataProvider {
    private static final String RECIPE_TYPE = ModIds.CREATE_AQUATIC_AMBITIONS + ":channeling";

    private final PackOutput.PathProvider recipes;

    public ModChannelingRecipeGen(PackOutput output) {
        this.recipes = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> writes = new ArrayList<>();
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
            ResourceLocation to = idOf(pair.to().get());
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "channeling/" + to.getPath());
            writes.add(DataProvider.saveStable(cache, recipe(idOf(pair.from().get()), to), recipes.json(id)));
        }
        return CompletableFuture.allOf(writes.toArray(CompletableFuture[]::new));
    }

    private static JsonObject recipe(ResourceLocation from, ResourceLocation to) {
        JsonObject modLoaded = new JsonObject();
        modLoaded.addProperty("type", "neoforge:mod_loaded");
        modLoaded.addProperty("modid", ModIds.CREATE_AQUATIC_AMBITIONS);
        JsonObject oxidation = new JsonObject();
        oxidation.addProperty("type", TheSilverAge.MOD_ID + ":config_boolean");
        oxidation.addProperty("settingId", Configuration.ENABLE_SILVER_OXIDATION_ID);
        JsonArray conditions = new JsonArray();
        conditions.add(modLoaded);
        conditions.add(oxidation);

        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", from.toString());
        JsonArray ingredients = new JsonArray();
        ingredients.add(ingredient);

        JsonObject result = new JsonObject();
        result.addProperty("id", to.toString());
        JsonArray results = new JsonArray();
        results.add(result);

        JsonObject recipe = new JsonObject();
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", RECIPE_TYPE);
        recipe.add("ingredients", ingredients);
        recipe.add("results", results);
        return recipe;
    }

    private static ResourceLocation idOf(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    @Override
    public @NotNull String getName() {
        return "Create: Aquatic Ambitions channeling recipes for " + TheSilverAge.MOD_ID;
    }
}
