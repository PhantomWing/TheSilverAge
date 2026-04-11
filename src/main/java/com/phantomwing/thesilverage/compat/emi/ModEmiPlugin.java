package com.phantomwing.thesilverage.compat.emi;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import net.neoforged.neoforge.common.DataMapHooks;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * EMI integration plugin for The Silver Age.
 * <p>
 * Adds EMI "world interaction" recipes for every silver block that participates in
 * oxidation/waxing. EMI's built-in {@code VanillaPlugin} iterates
 * {@link net.minecraft.world.item.HoneycombItem#WAXABLES} and
 * {@link net.minecraft.world.level.block.WeatheringCopper#PREVIOUS_BY_BLOCK}, but NeoForge's
 * {@code DataMapHooks} does <em>not</em> merge the data map entries back into those
 * static fields — it only intercepts the lookup methods. That means EMI never sees our
 * silver blocks via its default iteration, so we register the recipes ourselves here,
 * mirroring the pattern used by EMI's {@code VanillaPlugin.basicWorld(...)} helper.
 * <p>
 * This class is safe to ship without EMI present: it's only loaded when EMI scans the
 * classpath for {@link EmiEntrypoint}-annotated classes, and nothing else references it.
 */
@EmiEntrypoint
public class ModEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        final EmiIngredient honeycomb = EmiStack.of(Items.HONEYCOMB);
        final EmiIngredient axes = EmiIngredient.of(ItemTags.AXES);

        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
            Block block = entry.get();
            ResourceLocation blockId = entry.getId();

            // Waxing: unwaxed block + honeycomb -> waxed block (honeycomb is consumed → catalyst=false)
            Block waxed = DataMapHooks.getBlockWaxed(block);
            if (waxed != null && waxed != block) {
                addRecipe(registry, EmiWorldInteractionRecipe.builder()
                        .id(syntheticId("waxing", blockId))
                        .leftInput(EmiStack.of(block))
                        .rightInput(honeycomb, false)
                        .output(EmiStack.of(waxed))
                        .build());
            }

            // Wax-stripping: waxed block + axe -> unwaxed block (axe not consumed → catalyst=true)
            Block unwaxed = DataMapHooks.getBlockUnwaxed(block);
            if (unwaxed != null && unwaxed != block) {
                addRecipe(registry, EmiWorldInteractionRecipe.builder()
                        .id(syntheticId("wax_stripping", blockId))
                        .leftInput(EmiStack.of(block))
                        .rightInput(axes, true)
                        .output(EmiStack.of(unwaxed))
                        .build());
            }

            // De-oxidising: oxidised block + axe -> previous weathering stage (axe not consumed → catalyst=true)
            Block previousStage = DataMapHooks.getPreviousOxidizedStage(block);
            if (previousStage != null && previousStage != block) {
                addRecipe(registry, EmiWorldInteractionRecipe.builder()
                        .id(syntheticId("deoxidising", blockId))
                        .leftInput(EmiStack.of(block))
                        .rightInput(axes, true)
                        .output(EmiStack.of(previousStage))
                        .build());
            }
        }
    }

    private static ResourceLocation syntheticId(String kind, ResourceLocation blockId) {
        return TheSilverAge.resourceLocation("/world/" + kind + "/" + blockId.getNamespace() + "/" + blockId.getPath());
    }

    /** Defensive wrapper mirroring EMI's own {@code addRecipeSafe}. */
    private static void addRecipe(EmiRegistry registry, EmiWorldInteractionRecipe recipe) {
        try {
            registry.addRecipe(recipe);
        } catch (Throwable t) {
            TheSilverAge.LOGGER.warn("Failed to register EMI world interaction recipe {}", recipe.getId(), t);
        }
    }
}
