package com.phantomwing.thesilverage.neoforge.compat.create;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.compat.ModIds;
import com.simibubi.create.api.data.recipe.DeployingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

/**
 * Generates Create "deploying" recipes so JEI (and other recipe viewers) display
 * silver waxing / deoxidising interactions in the Deployer category, the same way
 * Create does it for vanilla copper.
 * <p>
 * These recipes are purely for display — the actual in-world waxing/deoxidising is
 * already handled by the NeoForge {@code WAXABLES}/{@code OXIDIZABLES} data maps
 * (see {@link com.phantomwing.thesilverage.neoforge.block.ModWaxables} and
 * {@link com.phantomwing.thesilverage.neoforge.block.ModOxidizables}).
 * <p>
 * Every family comes from {@link SilverWeatheringSpec}, which those data maps are built from too. A list of its own
 * here fell behind: the bricks and pillar shipped without these recipes, and the lantern, chain, bars and brick wall
 * were left out as they were added.
 */
public class ModDeployingRecipeGen extends DeployingRecipeGen {
    public ModDeployingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);

        Map<Block, Block> waxedOf = new IdentityHashMap<>();
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
            waxedOf.put(pair.from().get(), pair.to().get());
        }
        for (List<Block> chain : weatheringChains()) {
            List<Supplier<ItemLike>> waxed = new ArrayList<>();
            for (Block stage : chain) {
                Block waxedStage = waxedOf.get(stage);
                if (waxedStage == null) {
                    throw new IllegalStateException("No waxed form of " + stage + " in SilverWeatheringSpec");
                }
                waxed.add(lk(waxedStage));
            }
            oxidizationChain(chain.stream().map(ModDeployingRecipeGen::lk).toList(), waxed);
        }
    }

    /**
     * Wrap the recipe output so every generated recipe is gated on Create being loaded.
     * That way, when Create is absent at runtime, Minecraft silently skips these recipes
     * instead of logging errors about the unknown {@code create:deploying} serializer.
     *
     * <p>Also gated on {@code enable_silver_oxidation}, including the axe (scrape)
     * displays. Scraping itself keeps working in-world so anyone holding leftover
     * waxed or oxidized blocks can still undo them, but with the option off from
     * the start those blocks are never obtainable, so listing the interactions in
     * a recipe viewer would only be noise.</p>
     */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput.withConditions(
                new ModLoadedCondition(ModIds.CREATE),
                new ConfigBooleanCondition(Configuration.ENABLE_SILVER_OXIDATION_ID)));
    }

    /** Each family's stages, fresh to oxidized: from every block nothing weathers into, along its oxidation steps. */
    private static List<List<Block>> weatheringChains() {
        Map<Block, Block> next = new LinkedHashMap<>();
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
            next.put(pair.from().get(), pair.to().get());
        }
        Set<Block> weatheredInto = new HashSet<>(next.values());
        List<List<Block>> chains = new ArrayList<>();
        for (Block fresh : next.keySet()) {
            if (weatheredInto.contains(fresh)) {
                continue;
            }
            List<Block> chain = new ArrayList<>();
            for (Block stage = fresh; stage != null; stage = next.get(stage)) {
                chain.add(stage);
            }
            chains.add(chain);
        }
        return chains;
    }

    private static Supplier<ItemLike> lk(ItemLike item) {
        return () -> item;
    }
}
