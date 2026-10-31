package com.phantomwing.thesilverage.neoforge.compat.create;

import java.util.concurrent.CompletableFuture;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.compat.ModIds;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.material.Fluids;
import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
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
 * Every step comes from {@link SilverWeatheringSpec}, as {@link ModDeployingRecipeGen}'s do: a list of its own here
 * left out the bricks, pillar, wall, bars, chain and lantern.
 */
public class ModFillingRecipeGen extends FillingRecipeGen {

    /** Amount of water (in mB, matching Create's copper oxidation recipes from Create: Oxidized). */
    private static final int WATER_AMOUNT = 250;

    public ModFillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);

        // Named <next_stage>_from_oxidising.
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
            createWithDeferredId(idWithSuffix(() -> pair.to().get(), "_from_oxidising"), b -> b
                    .require(Fluids.WATER, WATER_AMOUNT)
                    .require(pair.from().get())
                    .output(pair.to().get()));
        }
    }

    /**
     * Gated on Create being loaded, so a game without it skips these rather than logging errors about the unknown
     * {@code create:filling} serializer; on {@code enable_silver_oxidation}, or Create would get round that toggle;
     * and on {@code enable_create_spout_oxidation}, for packs that want silver to weather only with time.
     */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput.withConditions(
                new ModLoadedCondition(ModIds.CREATE),
                new ConfigBooleanCondition(Configuration.ENABLE_SILVER_OXIDATION_ID),
                new ConfigBooleanCondition(Configuration.ENABLE_CREATE_SPOUT_OXIDATION_ID)));
    }
}
