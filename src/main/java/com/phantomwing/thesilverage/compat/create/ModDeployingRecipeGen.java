package com.phantomwing.thesilverage.compat.create;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.simibubi.create.api.data.recipe.DeployingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

/**
 * Generates Create "deploying" recipes so JEI (and other recipe viewers) display
 * silver waxing / deoxidising interactions in the Deployer category, the same way
 * Create does it for vanilla copper.
 * <p>
 * These recipes are purely for display — the actual in-world waxing/deoxidising is
 * already handled by the NeoForge {@code WAXABLES}/{@code OXIDIZABLES} data maps
 * (see {@link com.phantomwing.thesilverage.block.ModWaxables} and
 * {@link com.phantomwing.thesilverage.block.ModOxidizables}).
 */
public class ModDeployingRecipeGen extends DeployingRecipeGen {

    @SuppressWarnings("unused")
    GeneratedRecipe
        SILVER_BLOCK = oxidizationChain(
            List.of(lk(ModBlocks.SILVER_BLOCK), lk(ModBlocks.EXPOSED_SILVER), lk(ModBlocks.WEATHERED_SILVER), lk(ModBlocks.OXIDIZED_SILVER)),
            List.of(lk(ModBlocks.WAXED_SILVER_BLOCK), lk(ModBlocks.WAXED_EXPOSED_SILVER), lk(ModBlocks.WAXED_WEATHERED_SILVER), lk(ModBlocks.WAXED_OXIDIZED_SILVER))),

        CUT_SILVER = oxidizationChain(
            List.of(lk(ModBlocks.CUT_SILVER), lk(ModBlocks.EXPOSED_CUT_SILVER), lk(ModBlocks.WEATHERED_CUT_SILVER), lk(ModBlocks.OXIDIZED_CUT_SILVER)),
            List.of(lk(ModBlocks.WAXED_CUT_SILVER), lk(ModBlocks.WAXED_EXPOSED_CUT_SILVER), lk(ModBlocks.WAXED_WEATHERED_CUT_SILVER), lk(ModBlocks.WAXED_OXIDIZED_CUT_SILVER))),

        CUT_SILVER_SLAB = oxidizationChain(
            List.of(lk(ModBlocks.CUT_SILVER_SLAB), lk(ModBlocks.EXPOSED_CUT_SILVER_SLAB), lk(ModBlocks.WEATHERED_CUT_SILVER_SLAB), lk(ModBlocks.OXIDIZED_CUT_SILVER_SLAB)),
            List.of(lk(ModBlocks.WAXED_CUT_SILVER_SLAB), lk(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB), lk(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB), lk(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB))),

        CUT_SILVER_STAIRS = oxidizationChain(
            List.of(lk(ModBlocks.CUT_SILVER_STAIRS), lk(ModBlocks.EXPOSED_CUT_SILVER_STAIRS), lk(ModBlocks.WEATHERED_CUT_SILVER_STAIRS), lk(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS)),
            List.of(lk(ModBlocks.WAXED_CUT_SILVER_STAIRS), lk(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS), lk(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS), lk(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS))),

        CHISELED_SILVER = oxidizationChain(
            List.of(lk(ModBlocks.CHISELED_SILVER), lk(ModBlocks.EXPOSED_CHISELED_SILVER), lk(ModBlocks.WEATHERED_CHISELED_SILVER), lk(ModBlocks.OXIDIZED_CHISELED_SILVER)),
            List.of(lk(ModBlocks.WAXED_CHISELED_SILVER), lk(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER), lk(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER), lk(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER))),

        SILVER_GRATE = oxidizationChain(
            List.of(lk(ModBlocks.SILVER_GRATE), lk(ModBlocks.EXPOSED_SILVER_GRATE), lk(ModBlocks.WEATHERED_SILVER_GRATE), lk(ModBlocks.OXIDIZED_SILVER_GRATE)),
            List.of(lk(ModBlocks.WAXED_SILVER_GRATE), lk(ModBlocks.WAXED_EXPOSED_SILVER_GRATE), lk(ModBlocks.WAXED_WEATHERED_SILVER_GRATE), lk(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE))),

        SILVER_BULB = oxidizationChain(
            List.of(lk(ModBlocks.SILVER_BULB), lk(ModBlocks.EXPOSED_SILVER_BULB), lk(ModBlocks.WEATHERED_SILVER_BULB), lk(ModBlocks.OXIDIZED_SILVER_BULB)),
            List.of(lk(ModBlocks.WAXED_SILVER_BULB), lk(ModBlocks.WAXED_EXPOSED_SILVER_BULB), lk(ModBlocks.WAXED_WEATHERED_SILVER_BULB), lk(ModBlocks.WAXED_OXIDIZED_SILVER_BULB))),

        SILVER_TRAPDOOR = oxidizationChain(
            List.of(lk(ModBlocks.SILVER_TRAPDOOR), lk(ModBlocks.EXPOSED_SILVER_TRAPDOOR), lk(ModBlocks.WEATHERED_SILVER_TRAPDOOR), lk(ModBlocks.OXIDIZED_SILVER_TRAPDOOR)),
            List.of(lk(ModBlocks.WAXED_SILVER_TRAPDOOR), lk(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR), lk(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR), lk(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR))),

        SILVER_DOOR = oxidizationChain(
            List.of(lk(ModBlocks.SILVER_DOOR), lk(ModBlocks.EXPOSED_SILVER_DOOR), lk(ModBlocks.WEATHERED_SILVER_DOOR), lk(ModBlocks.OXIDIZED_SILVER_DOOR)),
            List.of(lk(ModBlocks.WAXED_SILVER_DOOR), lk(ModBlocks.WAXED_EXPOSED_SILVER_DOOR), lk(ModBlocks.WAXED_WEATHERED_SILVER_DOOR), lk(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR)));

    public ModDeployingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    /**
     * Wrap the recipe output so every generated recipe is gated on Create being loaded.
     * That way, when Create is absent at runtime, Minecraft silently skips these recipes
     * instead of logging errors about the unknown {@code create:deploying} serializer.
     */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput.withConditions(new ModLoadedCondition("create")));
    }

    /** Short alias for converting a {@code Supplier<? extends ItemLike>} into an {@code ItemLike} supplier. */
    private static Supplier<ItemLike> lk(Supplier<? extends ItemLike> supplier) {
        return supplier::get;
    }
}
