package com.phantomwing.thesilverage.compat.create;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.compat.ModIds;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

/**
 * Generates Create "filling" (spouting) recipes for silver blocks, mirroring the
 * recipes the <a href="https://modrinth.com/mod/create_oxidized">Create: Oxidized</a>
 * addon ships for vanilla copper. Pouring a small amount of water onto an unoxidised
 * or partially oxidised silver block via a Create Spout advances it to the next
 * weathering stage.
 * <p>
 * The recipes are gated on Create being loaded (not Create:Oxidized) because
 * {@code create:filling} is Create's native recipe type — the addon mod only
 * <em>uses</em> it, it doesn't provide it. That means silver spout-oxidation works
 * for any user who has Create installed, whether or not they also have Create:Oxidized.
 * <p>
 * This class mirrors the structure of {@link ModDeployingRecipeGen}.
 */
public class ModFillingRecipeGen extends FillingRecipeGen {

    /** Amount of water (in mB, matching Create's copper oxidation recipes from Create: Oxidized). */
    private static final int WATER_AMOUNT = 250;

    @SuppressWarnings("unused")
    GeneratedRecipe
        SILVER_BLOCK = oxidationChain(List.of(
            lk(ModBlocks.SILVER_BLOCK), lk(ModBlocks.EXPOSED_SILVER), lk(ModBlocks.WEATHERED_SILVER), lk(ModBlocks.OXIDIZED_SILVER))),

        CUT_SILVER = oxidationChain(List.of(
            lk(ModBlocks.CUT_SILVER), lk(ModBlocks.EXPOSED_CUT_SILVER), lk(ModBlocks.WEATHERED_CUT_SILVER), lk(ModBlocks.OXIDIZED_CUT_SILVER))),

        CUT_SILVER_SLAB = oxidationChain(List.of(
            lk(ModBlocks.CUT_SILVER_SLAB), lk(ModBlocks.EXPOSED_CUT_SILVER_SLAB), lk(ModBlocks.WEATHERED_CUT_SILVER_SLAB), lk(ModBlocks.OXIDIZED_CUT_SILVER_SLAB))),

        CUT_SILVER_STAIRS = oxidationChain(List.of(
            lk(ModBlocks.CUT_SILVER_STAIRS), lk(ModBlocks.EXPOSED_CUT_SILVER_STAIRS), lk(ModBlocks.WEATHERED_CUT_SILVER_STAIRS), lk(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS))),

        CHISELED_SILVER = oxidationChain(List.of(
            lk(ModBlocks.CHISELED_SILVER), lk(ModBlocks.EXPOSED_CHISELED_SILVER), lk(ModBlocks.WEATHERED_CHISELED_SILVER), lk(ModBlocks.OXIDIZED_CHISELED_SILVER))),

        SILVER_GRATE = oxidationChain(List.of(
            lk(ModBlocks.SILVER_GRATE), lk(ModBlocks.EXPOSED_SILVER_GRATE), lk(ModBlocks.WEATHERED_SILVER_GRATE), lk(ModBlocks.OXIDIZED_SILVER_GRATE))),

        SILVER_BULB = oxidationChain(List.of(
            lk(ModBlocks.SILVER_BULB), lk(ModBlocks.EXPOSED_SILVER_BULB), lk(ModBlocks.WEATHERED_SILVER_BULB), lk(ModBlocks.OXIDIZED_SILVER_BULB))),

        SILVER_TRAPDOOR = oxidationChain(List.of(
            lk(ModBlocks.SILVER_TRAPDOOR), lk(ModBlocks.EXPOSED_SILVER_TRAPDOOR), lk(ModBlocks.WEATHERED_SILVER_TRAPDOOR), lk(ModBlocks.OXIDIZED_SILVER_TRAPDOOR))),

        SILVER_DOOR = oxidationChain(List.of(
            lk(ModBlocks.SILVER_DOOR), lk(ModBlocks.EXPOSED_SILVER_DOOR), lk(ModBlocks.WEATHERED_SILVER_DOOR), lk(ModBlocks.OXIDIZED_SILVER_DOOR)));

    public ModFillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    /**
     * Build a chain of {@code N-1} filling recipes that advance each block to the next
     * weathering stage via a water spout, named {@code <next_stage>_from_oxidising}.
     */
    private GeneratedRecipe oxidationChain(List<Supplier<ItemLike>> chain) {
        GeneratedRecipe last = null;
        for (int i = 0; i < chain.size() - 1; i++) {
            Supplier<ItemLike> from = chain.get(i);
            Supplier<ItemLike> to = chain.get(i + 1);
            last = createWithDeferredId(idWithSuffix(to, "_from_oxidising"), b -> b
                    .require(Fluids.WATER, WATER_AMOUNT)
                    .require(from.get())
                    .output(to.get()));
        }
        return last;
    }

    /**
     * Wrap the recipe output so every generated recipe is gated on Create being loaded.
     * That way, when Create is absent at runtime, Minecraft silently skips these recipes
     * instead of logging errors about the unknown {@code create:filling} serializer.
     */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput.withConditions(new ModLoadedCondition(ModIds.CREATE)));
    }

    /** Short alias for converting a {@code Supplier<? extends ItemLike>} into an {@code ItemLike} supplier. */
    private static Supplier<ItemLike> lk(Supplier<? extends ItemLike> supplier) {
        return supplier::get;
    }
}
