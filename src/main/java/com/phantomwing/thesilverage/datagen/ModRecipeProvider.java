package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.condition.ConfigBooleanCondition;
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
        oreSmeltingRecipes(output, ModItems.RAW_SILVER, ModItems.SILVER_INGOT, XP_MEDIUM);

        // Storage item recipes
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_NUGGET, ModItems.SILVER_INGOT);
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.RAW_SILVER, ModItems.RAW_SILVER_BLOCK);

        // Tools
        sword(output, ModItems.SILVER_SWORD, ModItems.SILVER_INGOT);
        pickaxe(output, ModItems.SILVER_PICKAXE, ModItems.SILVER_INGOT);
        axe(output, ModItems.SILVER_AXE, ModItems.SILVER_INGOT);
        hoe(output, ModItems.SILVER_HOE, ModItems.SILVER_INGOT);
        shovel(output, ModItems.SILVER_SHOVEL, ModItems.SILVER_INGOT);

        // Armor
        helmet(output, ModItems.SILVER_HELMET, ModItems.SILVER_INGOT);
        chestplate(output, ModItems.SILVER_CHESTPLATE, ModItems.SILVER_INGOT);
        leggings(output, ModItems.SILVER_LEGGINGS, ModItems.SILVER_INGOT);
        boots(output, ModItems.SILVER_BOOTS, ModItems.SILVER_INGOT);

        // Smelting tools/armor into nuggets
        oreSmeltingRecipes(output, ModItems.SILVER_SWORD, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_PICKAXE, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_AXE, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HOE, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_SHOVEL, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HELMET, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_CHESTPLATE, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_LEGGINGS, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_BOOTS, ModItems.SILVER_NUGGET, XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HORSE_ARMOR, ModItems.SILVER_NUGGET, XP_TINY);
        
        // Block of Silver
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_INGOT, ModItems.SILVER_BLOCK);
        waxable(output, ModItems.SILVER_BLOCK, ModItems.WAXED_SILVER_BLOCK);
        waxable(output, ModItems.EXPOSED_SILVER, ModItems.WAXED_EXPOSED_SILVER);
        waxable(output, ModItems.WEATHERED_SILVER, ModItems.WAXED_WEATHERED_SILVER);
        waxable(output, ModItems.OXIDIZED_SILVER, ModItems.WAXED_OXIDIZED_SILVER);

        // Cut Silver
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_SILVER, ModItems.SILVER_BLOCK, 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_CUT_SILVER, ModItems.EXPOSED_SILVER, 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_CUT_SILVER, ModItems.WEATHERED_SILVER, 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_CUT_SILVER, ModItems.OXIDIZED_SILVER, 4);

        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_CUT_SILVER, ModItems.WAXED_SILVER_BLOCK, 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_CUT_SILVER, ModItems.WAXED_EXPOSED_SILVER, 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_CUT_SILVER, ModItems.WAXED_WEATHERED_SILVER, 4);
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_CUT_SILVER, ModItems.WAXED_OXIDIZED_SILVER, 4);

        stoneCutting(output, ModItems.CUT_SILVER, ModItems.SILVER_BLOCK, 4);
        stoneCutting(output, ModItems.EXPOSED_CUT_SILVER, ModItems.EXPOSED_SILVER, 4);
        stoneCutting(output, ModItems.WEATHERED_CUT_SILVER, ModItems.WEATHERED_SILVER, 4);
        stoneCutting(output, ModItems.OXIDIZED_CUT_SILVER, ModItems.OXIDIZED_SILVER, 4);

        stoneCutting(output, ModItems.WAXED_CUT_SILVER, ModItems.WAXED_SILVER_BLOCK, 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER, ModItems.WAXED_EXPOSED_SILVER, 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER, ModItems.WAXED_WEATHERED_SILVER, 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER, ModItems.WAXED_OXIDIZED_SILVER, 4);

        waxable(output, ModItems.CUT_SILVER, ModItems.WAXED_CUT_SILVER);
        waxable(output, ModItems.EXPOSED_CUT_SILVER, ModItems.WAXED_EXPOSED_CUT_SILVER);
        waxable(output, ModItems.WEATHERED_CUT_SILVER, ModItems.WAXED_WEATHERED_CUT_SILVER);
        waxable(output, ModItems.OXIDIZED_CUT_SILVER, ModItems.WAXED_OXIDIZED_CUT_SILVER);

        // Cut Silver Stairs
        stairsWithCutting(output, ModItems.CUT_SILVER_STAIRS, ModItems.CUT_SILVER);
        stairsWithCutting(output, ModItems.EXPOSED_CUT_SILVER_STAIRS, ModItems.EXPOSED_CUT_SILVER);
        stairsWithCutting(output, ModItems.WEATHERED_CUT_SILVER_STAIRS, ModItems.WEATHERED_CUT_SILVER);
        stairsWithCutting(output, ModItems.OXIDIZED_CUT_SILVER_STAIRS, ModItems.OXIDIZED_CUT_SILVER);

        stairsWithCutting(output, ModItems.WAXED_CUT_SILVER_STAIRS, ModItems.WAXED_CUT_SILVER);
        stairsWithCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS, ModItems.WAXED_EXPOSED_CUT_SILVER);
        stairsWithCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS, ModItems.WAXED_WEATHERED_CUT_SILVER);
        stairsWithCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS, ModItems.WAXED_OXIDIZED_CUT_SILVER);

        stoneCutting(output, ModItems.CUT_SILVER_STAIRS, ModItems.SILVER_BLOCK, 4);
        stoneCutting(output, ModItems.EXPOSED_CUT_SILVER_STAIRS, ModItems.EXPOSED_SILVER, 4);
        stoneCutting(output, ModItems.WEATHERED_CUT_SILVER_STAIRS, ModItems.WEATHERED_SILVER, 4);
        stoneCutting(output, ModItems.OXIDIZED_CUT_SILVER_STAIRS, ModItems.OXIDIZED_SILVER, 4);

        stoneCutting(output, ModItems.WAXED_CUT_SILVER_STAIRS, ModItems.WAXED_SILVER_BLOCK, 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS, ModItems.WAXED_EXPOSED_SILVER, 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS, ModItems.WAXED_WEATHERED_SILVER, 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS, ModItems.WAXED_OXIDIZED_SILVER, 4);

        waxable(output, ModItems.CUT_SILVER_STAIRS, ModItems.WAXED_CUT_SILVER_STAIRS);
        waxable(output, ModItems.EXPOSED_CUT_SILVER_STAIRS, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS);
        waxable(output, ModItems.WEATHERED_CUT_SILVER_STAIRS, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS);
        waxable(output, ModItems.OXIDIZED_CUT_SILVER_STAIRS, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

        // Cut Silver Slab
        slabWithCutting(output, ModItems.CUT_SILVER_SLAB, ModItems.CUT_SILVER);
        slabWithCutting(output, ModItems.EXPOSED_CUT_SILVER_SLAB, ModItems.EXPOSED_CUT_SILVER);
        slabWithCutting(output, ModItems.WEATHERED_CUT_SILVER_SLAB, ModItems.WEATHERED_CUT_SILVER);
        slabWithCutting(output, ModItems.OXIDIZED_CUT_SILVER_SLAB, ModItems.OXIDIZED_CUT_SILVER);

        slabWithCutting(output, ModItems.WAXED_CUT_SILVER_SLAB, ModItems.WAXED_CUT_SILVER);
        slabWithCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB, ModItems.WAXED_EXPOSED_CUT_SILVER);
        slabWithCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB, ModItems.WAXED_WEATHERED_CUT_SILVER);
        slabWithCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB, ModItems.WAXED_OXIDIZED_CUT_SILVER);

        stoneCutting(output, ModItems.CUT_SILVER_SLAB, ModItems.SILVER_BLOCK, 8);
        stoneCutting(output, ModItems.EXPOSED_CUT_SILVER_SLAB, ModItems.EXPOSED_SILVER, 8);
        stoneCutting(output, ModItems.WEATHERED_CUT_SILVER_SLAB, ModItems.WEATHERED_SILVER, 8);
        stoneCutting(output, ModItems.OXIDIZED_CUT_SILVER_SLAB, ModItems.OXIDIZED_SILVER, 8);

        stoneCutting(output, ModItems.WAXED_CUT_SILVER_SLAB, ModItems.WAXED_SILVER_BLOCK, 8);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB, ModItems.WAXED_EXPOSED_SILVER, 8);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB, ModItems.WAXED_WEATHERED_SILVER, 8);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB, ModItems.WAXED_OXIDIZED_SILVER, 8);

        waxable(output, ModItems.CUT_SILVER_SLAB, ModItems.WAXED_CUT_SILVER_SLAB);
        waxable(output, ModItems.EXPOSED_CUT_SILVER_SLAB, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB);
        waxable(output, ModItems.WEATHERED_CUT_SILVER_SLAB, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB);
        waxable(output, ModItems.OXIDIZED_CUT_SILVER_SLAB, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB);

        // Chiseled Silver
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SILVER, ModItems.CUT_SILVER_SLAB, 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_CHISELED_SILVER, ModItems.EXPOSED_CUT_SILVER_SLAB, 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_CHISELED_SILVER, ModItems.WEATHERED_CUT_SILVER_SLAB, 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_CHISELED_SILVER, ModItems.OXIDIZED_CUT_SILVER_SLAB, 2);

        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_CHISELED_SILVER, ModItems.WAXED_CUT_SILVER_SLAB, 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_CHISELED_SILVER, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB, 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_CHISELED_SILVER, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB, 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_CHISELED_SILVER, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB, 2);

        stoneCutting(output, ModItems.CHISELED_SILVER, ModItems.CUT_SILVER, 1);
        stoneCutting(output, ModItems.EXPOSED_CHISELED_SILVER, ModItems.EXPOSED_CUT_SILVER, 1);
        stoneCutting(output, ModItems.WEATHERED_CHISELED_SILVER, ModItems.WEATHERED_CUT_SILVER, 1);
        stoneCutting(output, ModItems.OXIDIZED_CHISELED_SILVER, ModItems.OXIDIZED_CUT_SILVER, 1);

        stoneCutting(output, ModItems.WAXED_CHISELED_SILVER, ModItems.WAXED_CUT_SILVER, 1);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CHISELED_SILVER, ModItems.WAXED_EXPOSED_CUT_SILVER, 1);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CHISELED_SILVER, ModItems.WAXED_WEATHERED_CUT_SILVER, 1);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CHISELED_SILVER, ModItems.WAXED_OXIDIZED_CUT_SILVER, 1);

        stoneCutting(output, ModItems.CHISELED_SILVER, ModItems.SILVER_BLOCK, 4);
        stoneCutting(output, ModItems.EXPOSED_CHISELED_SILVER, ModItems.EXPOSED_SILVER, 4);
        stoneCutting(output, ModItems.WEATHERED_CHISELED_SILVER, ModItems.WEATHERED_SILVER, 4);
        stoneCutting(output, ModItems.OXIDIZED_CHISELED_SILVER, ModItems.OXIDIZED_SILVER, 4);

        stoneCutting(output, ModItems.WAXED_CHISELED_SILVER, ModItems.WAXED_SILVER_BLOCK, 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_CHISELED_SILVER, ModItems.WAXED_EXPOSED_SILVER, 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_CHISELED_SILVER, ModItems.WAXED_WEATHERED_SILVER, 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_CHISELED_SILVER, ModItems.WAXED_OXIDIZED_SILVER, 4);

        waxable(output, ModItems.CHISELED_SILVER, ModItems.WAXED_CHISELED_SILVER);
        waxable(output, ModItems.EXPOSED_CHISELED_SILVER, ModItems.WAXED_EXPOSED_CHISELED_SILVER);
        waxable(output, ModItems.WEATHERED_CHISELED_SILVER, ModItems.WAXED_WEATHERED_CHISELED_SILVER);
        waxable(output, ModItems.OXIDIZED_CHISELED_SILVER, ModItems.WAXED_OXIDIZED_CHISELED_SILVER);

        // Silver Grate
        grateWithCutting(output, ModItems.SILVER_GRATE, ModItems.SILVER_BLOCK);
        grateWithCutting(output, ModItems.EXPOSED_SILVER_GRATE, ModItems.EXPOSED_SILVER);
        grateWithCutting(output, ModItems.WEATHERED_SILVER_GRATE, ModItems.WEATHERED_SILVER);
        grateWithCutting(output, ModItems.OXIDIZED_SILVER_GRATE, ModItems.OXIDIZED_SILVER);

        grateWithCutting(output, ModItems.WAXED_SILVER_GRATE, ModItems.WAXED_SILVER_BLOCK);
        grateWithCutting(output, ModItems.WAXED_EXPOSED_SILVER_GRATE, ModItems.WAXED_EXPOSED_SILVER);
        grateWithCutting(output, ModItems.WAXED_WEATHERED_SILVER_GRATE, ModItems.WAXED_WEATHERED_SILVER);
        grateWithCutting(output, ModItems.WAXED_OXIDIZED_SILVER_GRATE, ModItems.WAXED_OXIDIZED_SILVER);

        waxable(output, ModItems.SILVER_GRATE, ModItems.WAXED_SILVER_GRATE);
        waxable(output, ModItems.EXPOSED_SILVER_GRATE, ModItems.WAXED_EXPOSED_SILVER_GRATE);
        waxable(output, ModItems.WEATHERED_SILVER_GRATE, ModItems.WAXED_WEATHERED_SILVER_GRATE);
        waxable(output, ModItems.OXIDIZED_SILVER_GRATE, ModItems.WAXED_OXIDIZED_SILVER_GRATE);

        // Silver Door
        door(output, ModItems.SILVER_DOOR, ModItems.SILVER_INGOT);
        waxable(output, ModItems.SILVER_DOOR, ModItems.WAXED_SILVER_DOOR);
        waxable(output, ModItems.EXPOSED_SILVER_DOOR, ModItems.WAXED_EXPOSED_SILVER_DOOR);
        waxable(output, ModItems.WEATHERED_SILVER_DOOR, ModItems.WAXED_WEATHERED_SILVER_DOOR);
        waxable(output, ModItems.OXIDIZED_SILVER_DOOR, ModItems.WAXED_OXIDIZED_SILVER_DOOR);

        // Silver Trapdoor
        trapdoor(output, ModItems.SILVER_TRAPDOOR, ModItems.SILVER_INGOT);
        waxable(output, ModItems.SILVER_TRAPDOOR, ModItems.WAXED_SILVER_TRAPDOOR);
        waxable(output, ModItems.EXPOSED_SILVER_TRAPDOOR, ModItems.WAXED_EXPOSED_SILVER_TRAPDOOR);
        waxable(output, ModItems.WEATHERED_SILVER_TRAPDOOR, ModItems.WAXED_WEATHERED_SILVER_TRAPDOOR);
        waxable(output, ModItems.OXIDIZED_SILVER_TRAPDOOR, ModItems.WAXED_OXIDIZED_SILVER_TRAPDOOR);
    }

    /** Add overrides for Vanilla Minecraft recipes. (Only if a recipe is enabled) */
    private void buildRecipeOverrides(@NotNull RecipeOutput output) {
        ICondition condition = new ConfigBooleanCondition(Configuration.OVERRIDE_VANILLA_RECIPES_ID);
        RecipeOutput conditionalOutput = output.withConditions(condition);
        RecipeOutput fallbackOutput = output.withConditions(new NotCondition(condition));

        // Lodestone
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.CHISELED_STONE_BRICKS)
                .define('S', ModItems.SILVER_INGOT)
                .unlockedBy(getHasName(ModItems.SILVER_INGOT), has(ModItems.SILVER_INGOT))
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
                .define('S', ModItems.SILVER_INGOT)
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.BREWING_STAND) + "_fallback");  // Original recipe if override is disabled

        // Glistering Melon Slice
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLISTERING_MELON_SLICE, 1)
                .pattern("###")
                .pattern("#M#")
                .pattern("###")
                .define('#', ModTags.Items.GLISTERING_NUGGETS)
                .define('M', Items.MELON_SLICE)
                .unlockedBy(getHasName(Items.MELON_SLICE), has(Items.MELON_SLICE))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLISTERING_MELON_SLICE, 1)
                .pattern("###")
                .pattern("#M#")
                .pattern("###")
                .define('#', Items.GOLD_NUGGET)
                .define('M', Items.MELON_SLICE)
                .unlockedBy(getHasName(Items.MELON_SLICE), has(Items.MELON_SLICE))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.GLISTERING_MELON_SLICE) + "_fallback");  // Original recipe if override is disabled

        // Name Tag
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NAME_TAG, 1)
                .requires(Items.PAPER)
                .requires(ModItems.SILVER_NUGGET)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(conditionalOutput);
        // TODO: Add fallback recipe when Name Tag recipe is added in later Minecraft version

        // Redstone Comparator
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
                .pattern(" T ")
                .pattern("TQT")
                .pattern("SSS")
                .define('T', Items.REDSTONE_TORCH)
                .define('Q', Items.QUARTZ)
                .define('S', ModItems.SILVER_INGOT)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                .pattern("TRT")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('T', Items.REDSTONE_TORCH)
                .define('S', ModItems.SILVER_INGOT)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("###")
            .pattern(" S ")
            .pattern(" S ")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }
    protected static void axe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("##")
            .pattern("#S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void hoe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("##")
            .pattern(" S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void shovel(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
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
