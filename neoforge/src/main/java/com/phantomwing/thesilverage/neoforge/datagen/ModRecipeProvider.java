package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    private static final float XP_TINY = 0.1f;
    private static final float XP_MEDIUM = 1f;

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        buildCraftingRecipes(output);
        buildRecipeOverrides(output);
    }

    private void buildCraftingRecipes(@NotNull RecipeOutput output) {
        oreSmeltingRecipes(output, ModItems.RAW_SILVER.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);
        oreSmeltingRecipes(output, ModItems.SILVER_ORE.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);
        oreSmeltingRecipes(output, ModItems.DEEPSLATE_SILVER_ORE.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);

        // Create compat: smelt Create's crushed_raw_silver into our silver ingot.
        // Create ships this bridge for IC2 / IE / Galosphere / Iceandfire / Oreganized / Thermal
        // but not for The Silver Age — see com.simibubi.create.foundation.data.recipe.CreateMixingRecipeGen.
        // Wrapping the output via withConditions gates every recipe emitted through `createGated` on Create being present.
        var createGated = output.withConditions(new ModLoadedCondition(ModIds.CREATE));
        oreSmeltingRecipes(createGated, com.simibubi.create.AllItems.CRUSHED_SILVER.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);

        // Storage item recipes
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), ModItems.SILVER_INGOT.get());
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.RAW_SILVER.get(), ModItems.RAW_SILVER_BLOCK.get());

        // Tools
        sword(output, ModItems.SILVER_SWORD.get(), ModItems.SILVER_INGOT.get());
        pickaxe(output, ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_INGOT.get());
        axe(output, ModItems.SILVER_AXE.get(), ModItems.SILVER_INGOT.get());
        hoe(output, ModItems.SILVER_HOE.get(), ModItems.SILVER_INGOT.get());
        shovel(output, ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_INGOT.get());

        // Armor
        helmet(output, ModItems.SILVER_HELMET.get(), ModItems.SILVER_INGOT.get());
        chestplate(output, ModItems.SILVER_CHESTPLATE.get(), ModItems.SILVER_INGOT.get());
        leggings(output, ModItems.SILVER_LEGGINGS.get(), ModItems.SILVER_INGOT.get());
        boots(output, ModItems.SILVER_BOOTS.get(), ModItems.SILVER_INGOT.get());

        // Smelting tools/armor into nuggets
        oreSmeltingRecipes(output, ModItems.SILVER_SWORD.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_AXE.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HOE.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HELMET.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_CHESTPLATE.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_LEGGINGS.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_BOOTS.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HORSE_ARMOR.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);

        // Silver Bulb
        bulb(output, ModItems.SILVER_BLOCK.get(), ModItems.SILVER_BULB.get());
        bulb(output, ModItems.EXPOSED_SILVER.get(), ModItems.EXPOSED_SILVER_BULB.get());
        bulb(output, ModItems.WEATHERED_SILVER.get(), ModItems.WEATHERED_SILVER_BULB.get());
        bulb(output, ModItems.OXIDIZED_SILVER.get(), ModItems.OXIDIZED_SILVER_BULB.get());
        bulb(output, ModItems.WAXED_SILVER_BLOCK.get(), ModItems.WAXED_SILVER_BULB.get());
        bulb(output, ModItems.WAXED_EXPOSED_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER_BULB.get());
        bulb(output, ModItems.WAXED_WEATHERED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER_BULB.get());
        bulb(output, ModItems.WAXED_OXIDIZED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER_BULB.get());
        waxable(output, ModItems.SILVER_BULB.get(), ModItems.WAXED_SILVER_BULB.get());
        waxable(output, ModItems.EXPOSED_SILVER_BULB.get(), ModItems.WAXED_EXPOSED_SILVER_BULB.get());
        waxable(output, ModItems.WEATHERED_SILVER_BULB.get(), ModItems.WAXED_WEATHERED_SILVER_BULB.get());
        waxable(output, ModItems.OXIDIZED_SILVER_BULB.get(), ModItems.WAXED_OXIDIZED_SILVER_BULB.get());

        // Moon Dial
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOON_DIAL.get(), 1)
                .pattern(" S ")
                .pattern("SRS")
                .pattern(" S ")
                .define('R', Items.REDSTONE)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(output);

        // Moon Phase Detector
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.MOON_PHASE_DETECTOR.get(), 1)
                .pattern("GGG")
                .pattern("AAA")
                .pattern("SSS")
                .define('G', Items.GLASS)
                .define('A', Items.AMETHYST_SHARD)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(output);
        
        // Block of Silver
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), ModItems.SILVER_BLOCK.get());
        waxable(output, ModItems.SILVER_BLOCK.get(), ModItems.WAXED_SILVER_BLOCK.get());
        waxable(output, ModItems.EXPOSED_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get());
        waxable(output, ModItems.WEATHERED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get());
        waxable(output, ModItems.OXIDIZED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get());

        // Cut Silver
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_SILVER.get(), ModItems.SILVER_BLOCK.get(), 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_CUT_SILVER.get(), ModItems.EXPOSED_SILVER.get(), 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_CUT_SILVER.get(), ModItems.WEATHERED_SILVER.get(), 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_CUT_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_CUT_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.CUT_SILVER.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.EXPOSED_CUT_SILVER.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WEATHERED_CUT_SILVER.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.WAXED_CUT_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(output, ModItems.CUT_SILVER.get(), ModItems.WAXED_CUT_SILVER.get());
        waxable(output, ModItems.EXPOSED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get());
        waxable(output, ModItems.WEATHERED_CUT_SILVER.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get());
        waxable(output, ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get());

        // Silver Bricks. Crafted from 2x2 Silver Ingots (4 -> 4). Silver Ingot
        // has no weather states, so only the base block has a recipe: the
        // exposed/weathered/tarnished variants form by natural oxidation of
        // placed Silver Bricks (vanilla copper behaviour), and the waxed
        // variants are made by applying Honeycomb to the matching unwaxed one.
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.SILVER_BRICKS.get(), ModItems.SILVER_INGOT.get(), 4);

        waxable(output, ModItems.SILVER_BRICKS.get(), ModItems.WAXED_SILVER_BRICKS.get());
        waxable(output, ModItems.EXPOSED_SILVER_BRICKS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        waxable(output, ModItems.WEATHERED_SILVER_BRICKS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        waxable(output, ModItems.OXIDIZED_SILVER_BRICKS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        // Cut Silver Stairs
        stairsWithCutting(output, ModItems.CUT_SILVER_STAIRS.get(), ModItems.CUT_SILVER.get());
        stairsWithCutting(output, ModItems.EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.EXPOSED_CUT_SILVER.get());
        stairsWithCutting(output, ModItems.WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WEATHERED_CUT_SILVER.get());
        stairsWithCutting(output, ModItems.OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.OXIDIZED_CUT_SILVER.get());

        stairsWithCutting(output, ModItems.WAXED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_CUT_SILVER.get());
        stairsWithCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get());
        stairsWithCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get());
        stairsWithCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get());

        stoneCutting(output, ModItems.CUT_SILVER_STAIRS.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.WAXED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(output, ModItems.CUT_SILVER_STAIRS.get(), ModItems.WAXED_CUT_SILVER_STAIRS.get());
        waxable(output, ModItems.EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get());
        waxable(output, ModItems.WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get());
        waxable(output, ModItems.OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get());

        // Cut Silver Slab
        slabWithCutting(output, ModItems.CUT_SILVER_SLAB.get(), ModItems.CUT_SILVER.get());
        slabWithCutting(output, ModItems.EXPOSED_CUT_SILVER_SLAB.get(), ModItems.EXPOSED_CUT_SILVER.get());
        slabWithCutting(output, ModItems.WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WEATHERED_CUT_SILVER.get());
        slabWithCutting(output, ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.OXIDIZED_CUT_SILVER.get());

        slabWithCutting(output, ModItems.WAXED_CUT_SILVER_SLAB.get(), ModItems.WAXED_CUT_SILVER.get());
        slabWithCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get());
        slabWithCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get());
        slabWithCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get());

        stoneCutting(output, ModItems.CUT_SILVER_SLAB.get(), ModItems.SILVER_BLOCK.get(), 8);
        stoneCutting(output, ModItems.EXPOSED_CUT_SILVER_SLAB.get(), ModItems.EXPOSED_SILVER.get(), 8);
        stoneCutting(output, ModItems.WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WEATHERED_SILVER.get(), 8);
        stoneCutting(output, ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.OXIDIZED_SILVER.get(), 8);

        stoneCutting(output, ModItems.WAXED_CUT_SILVER_SLAB.get(), ModItems.WAXED_SILVER_BLOCK.get(), 8);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 8);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 8);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 8);

        waxable(output, ModItems.CUT_SILVER_SLAB.get(), ModItems.WAXED_CUT_SILVER_SLAB.get());
        waxable(output, ModItems.EXPOSED_CUT_SILVER_SLAB.get(), ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get());
        waxable(output, ModItems.WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get());
        waxable(output, ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get());

        // Chiseled Silver
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SILVER.get(), ModItems.CUT_SILVER_SLAB.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.EXPOSED_CUT_SILVER_SLAB.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WEATHERED_CUT_SILVER_SLAB.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), 2);

        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_CUT_SILVER_SLAB.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get(), 2);

        stoneCutting(output, ModItems.CHISELED_SILVER.get(), ModItems.CUT_SILVER.get(), 1);
        stoneCutting(output, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.EXPOSED_CUT_SILVER.get(), 1);
        stoneCutting(output, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WEATHERED_CUT_SILVER.get(), 1);
        stoneCutting(output, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.OXIDIZED_CUT_SILVER.get(), 1);

        stoneCutting(output, ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_CUT_SILVER.get(), 1);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get(), 1);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get(), 1);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get(), 1);

        stoneCutting(output, ModItems.CHISELED_SILVER.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(output, ModItems.CHISELED_SILVER.get(), ModItems.WAXED_CHISELED_SILVER.get());
        waxable(output, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CHISELED_SILVER.get());
        waxable(output, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_CHISELED_SILVER.get());
        waxable(output, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get());

        // Silver Grate
        grateWithCutting(output, ModItems.SILVER_GRATE.get(), ModItems.SILVER_BLOCK.get());
        grateWithCutting(output, ModItems.EXPOSED_SILVER_GRATE.get(), ModItems.EXPOSED_SILVER.get());
        grateWithCutting(output, ModItems.WEATHERED_SILVER_GRATE.get(), ModItems.WEATHERED_SILVER.get());
        grateWithCutting(output, ModItems.OXIDIZED_SILVER_GRATE.get(), ModItems.OXIDIZED_SILVER.get());

        grateWithCutting(output, ModItems.WAXED_SILVER_GRATE.get(), ModItems.WAXED_SILVER_BLOCK.get());
        grateWithCutting(output, ModItems.WAXED_EXPOSED_SILVER_GRATE.get(), ModItems.WAXED_EXPOSED_SILVER.get());
        grateWithCutting(output, ModItems.WAXED_WEATHERED_SILVER_GRATE.get(), ModItems.WAXED_WEATHERED_SILVER.get());
        grateWithCutting(output, ModItems.WAXED_OXIDIZED_SILVER_GRATE.get(), ModItems.WAXED_OXIDIZED_SILVER.get());

        waxable(output, ModItems.SILVER_GRATE.get(), ModItems.WAXED_SILVER_GRATE.get());
        waxable(output, ModItems.EXPOSED_SILVER_GRATE.get(), ModItems.WAXED_EXPOSED_SILVER_GRATE.get());
        waxable(output, ModItems.WEATHERED_SILVER_GRATE.get(), ModItems.WAXED_WEATHERED_SILVER_GRATE.get());
        waxable(output, ModItems.OXIDIZED_SILVER_GRATE.get(), ModItems.WAXED_OXIDIZED_SILVER_GRATE.get());

        // Silver Door
        door(output, ModItems.SILVER_DOOR.get(), ModItems.SILVER_INGOT.get());
        waxable(output, ModItems.SILVER_DOOR.get(), ModItems.WAXED_SILVER_DOOR.get());
        waxable(output, ModItems.EXPOSED_SILVER_DOOR.get(), ModItems.WAXED_EXPOSED_SILVER_DOOR.get());
        waxable(output, ModItems.WEATHERED_SILVER_DOOR.get(), ModItems.WAXED_WEATHERED_SILVER_DOOR.get());
        waxable(output, ModItems.OXIDIZED_SILVER_DOOR.get(), ModItems.WAXED_OXIDIZED_SILVER_DOOR.get());

        // Silver Trapdoor
        trapdoor(output, ModItems.SILVER_TRAPDOOR.get(), ModItems.SILVER_INGOT.get());
        waxable(output, ModItems.SILVER_TRAPDOOR.get(), ModItems.WAXED_SILVER_TRAPDOOR.get());
        waxable(output, ModItems.EXPOSED_SILVER_TRAPDOOR.get(), ModItems.WAXED_EXPOSED_SILVER_TRAPDOOR.get());
        waxable(output, ModItems.WEATHERED_SILVER_TRAPDOOR.get(), ModItems.WAXED_WEATHERED_SILVER_TRAPDOOR.get());
        waxable(output, ModItems.OXIDIZED_SILVER_TRAPDOOR.get(), ModItems.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());
    }

    /** Add overrides for Vanilla Minecraft recipes. (Only if a recipe is enabled) */
    private void buildRecipeOverrides(@NotNull RecipeOutput output) {
        // Glistering Melon Slice
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLISTERING_MELON_SLICE, 1)
                .pattern("###")
                .pattern("#M#")
                .pattern("###")
                .define('#', ModTags.Items.GLISTERING_NUGGETS)
                .define('M', Items.MELON_SLICE)
                .unlockedBy(getHasName(Items.MELON_SLICE), has(Items.MELON_SLICE))
                .save(output);

        // Conditional overrides:
        ICondition condition = new ConfigBooleanCondition(Configuration.OVERRIDE_VANILLA_RECIPES_ID);
        RecipeOutput conditionalOutput = output.withConditions(condition);
        RecipeOutput fallbackOutput = output.withConditions(new NotCondition(condition));

        // Lodestone
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.CHISELED_STONE_BRICKS)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(ModItems.SILVER_INGOT.get()), has(ModItems.SILVER_INGOT.get()))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.CHISELED_STONE_BRICKS)
                .define('S', Items.NETHERITE_INGOT)
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)) // TODO: Change fallback to IRON_INGOT once updated to later Minecraft version
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.LODESTONE) + "_fallback"); // Original recipe if override is disabled

        // Brewing Stand
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.BREWING_STAND) + "_fallback");  // Original recipe if override is disabled

        // Name Tag
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NAME_TAG, 1)
//                .requires(Items.PAPER)
//                .requires(ModItems.SILVER_NUGGET.get())
//                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
//                .save(conditionalOutput);
        // TODO: Tweak Name Tag recipe when it is added in later Minecraft version

        // Redstone Comparator
        // The S slot accepts any item in the #thesilverage:redstone_silver_components tag.
        // Currently populated with silver_ingot + silver_sheet (sheet is Create-only, but the
        // ingot path always works). Addons may contribute extra silver forms to the tag.
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
                .pattern(" T ")
                .pattern("TQT")
                .pattern("SSS")
                .define('T', Items.REDSTONE_TORCH)
                .define('Q', Items.QUARTZ)
                .define('S', ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
                .pattern(" T ")
                .pattern("TQT")
                .pattern("SSS")
                .define('T', Items.REDSTONE_TORCH)
                .define('Q', Items.QUARTZ)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.COMPARATOR) + "_fallback");  // Original recipe if override is disabled

        // Redstone Repeater
        // Same tag-based S slot as the Comparator override — see above.
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                .pattern("TRT")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('T', Items.REDSTONE_TORCH)
                .define('S', ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                .pattern("TRT")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('T', Items.REDSTONE_TORCH)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.REPEATER) + "_fallback");  // Original recipe if override is disabled
    }

    private static void stairsWithCutting(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 1);
        stairs(recipeOutput, item, material);
    }

    private static void stairs(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stairBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void slabWithCutting(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 2);
        slab(recipeOutput, item, material);
    }

    private static void slab(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void door(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        doorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void trapdoor(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        trapdoorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void oneToOne(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    protected static void horizontalRecipe(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private static void twoBytwo(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("##")
                .pattern("##")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private static void oneBytwo(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("#")
                .pattern("#")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private static void grateWithCutting(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));

        stoneCutting(recipeOutput, result, material, 4);
    }

    private static void bulb(RecipeOutput output, ItemLike block, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result, 1)
                .pattern(" S ")
                .pattern("SBS")
                .pattern(" R ")
                .define('R', Items.REDSTONE)
                .define('S', block)
                .define('B', Items.BLAZE_ROD)
                .unlockedBy(getHasName(block), has(block))
                .save(output);
    }

    private static void storageItemRecipes(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike storageItem) {
        // From item to storageItem
        ShapedRecipeBuilder.shaped(category, storageItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, storageItem));

        // From storageItem to item
        ShapelessRecipeBuilder.shapeless(category, item, 9)
                .requires(storageItem)
                .unlockedBy(getHasName(storageItem), has(storageItem))
                .save(recipeOutput, getRecipeName(storageItem, item));
    }

    protected static void waxable(RecipeOutput recipeOutput, ItemLike item, ItemLike result) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .requires(item)
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void sword(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("#")
            .pattern("#")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void pickaxe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("###")
            .pattern(" S ")
            .pattern(" S ")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }
    protected static void axe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("##")
            .pattern("#S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void hoe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("##")
            .pattern(" S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void shovel(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("#")
            .pattern("S")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void helmet(RecipeOutput recipeOutput, ItemLike helmet, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("###")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void chestplate(RecipeOutput recipeOutput, ItemLike chestplate, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void leggings(RecipeOutput recipeOutput, ItemLike leggings, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void boots(RecipeOutput recipeOutput, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void oreSmeltingRecipes(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        smelting(recipeOutput, RecipeCategory.MISC, material, result, experience, 200);
        blasting(recipeOutput, RecipeCategory.MISC, material, result, experience, 100); // Smoking is twice as fast
    }

    protected static void foodCookingRecipes(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        smelting(recipeOutput, RecipeCategory.FOOD, material, result, experience, 200);
        smoking(recipeOutput, RecipeCategory.FOOD, material, result, experience, 100); // Smoking is twice as fast
        campfireCooking(recipeOutput, RecipeCategory.FOOD, material, result, experience, 600); // Campfire cooking takes three times longer
    }

    protected static void smelting(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_smelting");
    }

    protected static void blasting(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_blasting");
    }

    protected static void smoking(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_smoking");
    }

    protected static void stoneCutting(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike result, @NotNull ItemLike material, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_stonecutting");
    }

    protected static void campfireCooking(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_campfire_cooking");
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return TheSilverAge.MOD_ID + ":" + getConversionRecipeName(result, item);
    }
}
