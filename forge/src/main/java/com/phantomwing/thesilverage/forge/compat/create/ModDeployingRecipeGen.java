package com.phantomwing.thesilverage.forge.compat.create;

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
import com.phantomwing.thesilverage.forge.datagen.RecipeOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import java.util.function.Consumer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import com.phantomwing.thesilverage.forge.Configuration;
import com.phantomwing.thesilverage.forge.condition.ConfigBooleanCondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

/**
 * Generates Create "deploying" recipes so JEI (and other recipe viewers) display
 * silver waxing / deoxidising interactions in the Deployer category, the same way
 * Create does it for vanilla copper.
 * <p>
 * These recipes are purely for display — the actual in-world waxing/deoxidising is
 * already handled by the NeoForge {@code WAXABLES}/{@code OXIDIZABLES} data maps
 * (see {@link com.phantomwing.thesilverage.forge.block.ModWaxables} and
 * {@link com.phantomwing.thesilverage.forge.block.ModOxidizables}).
 * <p>
 * Every family comes from {@link SilverWeatheringSpec}, which those data maps are built from too. A list of its own
 * here fell behind: the bricks and pillar shipped without these recipes, and the lantern, chain, bars and brick wall
 * were left out as they were added.
 */
public class ModDeployingRecipeGen extends DeployingRecipeGen {
    public ModDeployingRecipeGen(PackOutput output) {
        super(output, TheSilverAge.MOD_ID);

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
            silverChain(chain.stream().map(ModDeployingRecipeGen::lk).toList(), waxed);
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
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildRecipes(RecipeOutput.of(consumer).withConditions(
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

    /**
     * A family's scraping, waxing and wax removal on a Deployer, as Create's own copper chain on 1.21. Create 1.20.1's
     * {@code oxidizationChain} takes no waxed blocks, so the three kinds are made here.
     */
    private void silverChain(List<Supplier<ItemLike>> unwaxed, List<Supplier<ItemLike>> waxed) {
        for (int i = 1; i < unwaxed.size(); i++) {
            Supplier<ItemLike> from = unwaxed.get(i);
            Supplier<ItemLike> to = unwaxed.get(i - 1);
            createWithDeferredId(idWithSuffix(to, "_from_deoxidising"), b -> b
                    .require(from.get())
                    .require(ItemTags.AXES)
                    .toolNotConsumed()
                    .output(to.get()));
        }
        for (int i = 0; i < unwaxed.size(); i++) {
            Supplier<ItemLike> nonWaxed = unwaxed.get(i);
            Supplier<ItemLike> waxedBlock = waxed.get(i);
            createWithDeferredId(idWithSuffix(waxedBlock, "_from_adding_wax"), b -> b
                    .require(nonWaxed.get())
                    .require(Items.HONEYCOMB)
                    .toolNotConsumed()
                    .output(waxedBlock.get()));
            createWithDeferredId(idWithSuffix(nonWaxed, "_from_removing_wax"), b -> b
                    .require(waxedBlock.get())
                    .require(ItemTags.AXES)
                    .toolNotConsumed()
                    .output(nonWaxed.get()));
        }
    }

    private static Supplier<ItemLike> lk(ItemLike item) {
        return () -> item;
    }
}
