package com.phantomwing.thesilverage.neoforge.compat.create;

import java.util.concurrent.CompletableFuture;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.item.ModItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.WashingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

/**
 * Create "splashing" (washing) recipes: Create's {@code crushed_raw_silver} washed in a fan-blown water stream gives 9
 * Silver Nuggets. Create ships this only for the mods it knows, each with that mod's own nugget, so silver from this
 * mod needs its own; the yield is theirs. Create's crushing already takes this mod's ore and raw silver, by the
 * {@code c:ores/silver} and {@code c:raw_materials/silver} tags.
 */
public class ModWashingRecipeGen extends WashingRecipeGen {

    @SuppressWarnings("unused")
    GeneratedRecipe CRUSHED_RAW_SILVER = create("crushed_raw_silver", b -> b
            .require(AllItems.CRUSHED_SILVER.get())
            .output(ModItems.SILVER_NUGGET.get(), 9));

    public ModWashingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    /** Gated on Create being loaded, so without it the unknown {@code create:splashing} serializer is skipped quietly. */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput.withConditions(new ModLoadedCondition(ModIds.CREATE)));
    }
}
