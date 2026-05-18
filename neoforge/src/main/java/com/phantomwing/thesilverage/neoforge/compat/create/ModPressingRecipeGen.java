package com.phantomwing.thesilverage.neoforge.compat.create;

import java.util.concurrent.CompletableFuture;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

/**
 * Generates Create "pressing" recipes for silver, giving the mod parity with vanilla
 * copper / iron / gold / brass sheets. A Silver Ingot pressed by a Mechanical Press
 * yields a {@link ModItems#SILVER_SHEET}, matching how Create produces
 * {@code COPPER_SHEET}, {@code IRON_SHEET}, and {@code GOLDEN_SHEET}
 * (see {@code com.simibubi.create.foundation.data.recipe.CreatePressingRecipeGen}).
 * <p>
 * The input uses the {@code c:ingots/silver} common tag so the recipe also matches
 * silver ingots contributed by other mods — same pattern Create itself uses via
 * {@code CreateRecipeProvider.I.copper()} and friends.
 * <p>
 * This class mirrors the structure of {@link ModDeployingRecipeGen} and
 * {@link ModFillingRecipeGen}.
 */
public class ModPressingRecipeGen extends PressingRecipeGen {

    @SuppressWarnings("unused")
    GeneratedRecipe SILVER_SHEET_RECIPE = create("silver_sheet", b -> b
            .require(CommonTags.Items.INGOTS_SILVER)
            .output(ModItems.SILVER_SHEET.get()));

    public ModPressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    /**
     * Wrap the recipe output so every generated recipe is gated on Create being loaded.
     * That way, when Create is absent at runtime, Minecraft silently skips these recipes
     * instead of logging errors about the unknown {@code create:pressing} serializer.
     */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput.withConditions(new ModLoadedCondition(ModIds.CREATE)));
    }
}
