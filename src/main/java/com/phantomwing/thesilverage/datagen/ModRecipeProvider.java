package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.condition.ConfigBooleanCondition;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    private static final float XP_TINY = 0.1f;
    private static final float XP_MEDIUM = 1f;

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> output) {
        buildCraftingRecipes(output);
        buildRecipeOverrides(output);
    }

    private void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> output) {
        oreSmeltingRecipes(output, ModItems.RAW_SILVER.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);

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

        // Moon Dial
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MOON_DIAL.get(), 1)
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

        // Silver Bulb
        bulb(output, ModItems.SILVER_BULB.get(), ModItems.SILVER_BLOCK.get());
        waxable(output, ModItems.SILVER_BULB.get(), ModItems.WAXED_SILVER_BULB.get());
        waxable(output, ModItems.EXPOSED_SILVER_BULB.get(), ModItems.WAXED_EXPOSED_SILVER_BULB.get());
        waxable(output, ModItems.WEATHERED_SILVER_BULB.get(), ModItems.WAXED_WEATHERED_SILVER_BULB.get());
        waxable(output, ModItems.OXIDIZED_SILVER_BULB.get(), ModItems.WAXED_OXIDIZED_SILVER_BULB.get());
    }

    /** Add overrides for Vanilla Minecraft recipes. (Only if a recipe is enabled) */
    private void buildRecipeOverrides(@NotNull Consumer<FinishedRecipe> output) {
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
        ConfigBooleanCondition condition = new ConfigBooleanCondition(Configuration.OVERRIDE_VANILLA_RECIPES_ID);
        NotCondition notCondition = new NotCondition(condition);

        // Lodestone
        ConditionalRecipe.builder()
                .addCondition(condition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                        .pattern("###")
                        .pattern("#S#")
                        .pattern("###")
                        .define('#', Items.CHISELED_STONE_BRICKS)
                        .define('S', ModItems.SILVER_INGOT.get())
                        .unlockedBy(getHasName(ModItems.SILVER_INGOT.get()), has(ModItems.SILVER_INGOT.get()))
                        .save(consumer))
                .build(output, new ResourceLocation(TheSilverAge.MOD_ID, ItemUtils.getName(Items.LODESTONE)));
        ConditionalRecipe.builder()
                .addCondition(notCondition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                        .pattern("###")
                        .pattern("#S#")
                        .pattern("###")
                        .define('#', Items.CHISELED_STONE_BRICKS)
                        .define('S', Items.NETHERITE_INGOT)
                        .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)) // TODO: Change fallback to IRON_INGOT once updated to later Minecraft version
                        .save(consumer))
                .build(output, new ResourceLocation("minecraft", ItemUtils.getName(Items.LODESTONE) + "_fallback"));

        // Brewing Stand
        ConditionalRecipe.builder()
                .addCondition(condition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                        .pattern(" B ")
                        .pattern("SSS")
                        .define('B', Items.BLAZE_ROD)
                        .define('S', ModItems.SILVER_INGOT.get())
                        .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                        .save(consumer))
                .build(output, new ResourceLocation(TheSilverAge.MOD_ID, ItemUtils.getName(Items.BREWING_STAND)));
        ConditionalRecipe.builder()
                .addCondition(notCondition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                        .pattern(" B ")
                        .pattern("SSS")
                        .define('B', Items.BLAZE_ROD)
                        .define('S', Items.STONE)
                        .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                        .save(consumer))
                .build(output, new ResourceLocation("minecraft", ItemUtils.getName(Items.BREWING_STAND) + "_fallback"));

        // Redstone Comparator
        ConditionalRecipe.builder()
                .addCondition(condition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
                        .pattern(" T ")
                        .pattern("TQT")
                        .pattern("SSS")
                        .define('T', Items.REDSTONE_TORCH)
                        .define('Q', Items.QUARTZ)
                        .define('S', ModItems.SILVER_INGOT.get())
                        .unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH))
                        .save(consumer))
                .build(output, new ResourceLocation(TheSilverAge.MOD_ID, ItemUtils.getName(Items.COMPARATOR)));
        ConditionalRecipe.builder()
                .addCondition(notCondition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
                        .pattern(" T ")
                        .pattern("TQT")
                        .pattern("SSS")
                        .define('T', Items.REDSTONE_TORCH)
                        .define('Q', Items.QUARTZ)
                        .define('S', Items.STONE)
                        .unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH))
                        .save(consumer))
                .build(output, new ResourceLocation("minecraft", ItemUtils.getName(Items.COMPARATOR) + "_fallback"));

        // Redstone Repeater
        ConditionalRecipe.builder()
                .addCondition(condition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                        .pattern("TRT")
                        .pattern("SSS")
                        .define('R', Items.REDSTONE)
                        .define('T', Items.REDSTONE_TORCH)
                        .define('S', ModItems.SILVER_INGOT.get())
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(consumer))
                .build(output, new ResourceLocation(TheSilverAge.MOD_ID, ItemUtils.getName(Items.REPEATER)));
        ConditionalRecipe.builder()
                .addCondition(notCondition)
                .addRecipe(consumer -> ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                        .pattern("TRT")
                        .pattern("SSS")
                        .define('R', Items.REDSTONE)
                        .define('T', Items.REDSTONE_TORCH)
                        .define('S', Items.STONE)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(consumer))
                .build(output, new ResourceLocation("minecraft", ItemUtils.getName(Items.REPEATER) + "_fallback"));
    }

    private static void grateWithCutting(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, item, 4)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, item));
        stoneCutting(recipeOutput, item, material, 4);
    }

    private static void bulb(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item, 4)
                .pattern(" # ")
                .pattern("#R#")
                .pattern(" B ")
                .define('#', material)
                .define('R', Items.REDSTONE)
                .define('B', Items.BLAZE_ROD)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void stairsWithCutting(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 1);
        stairs(recipeOutput, item, material);
    }

    private static void stairs(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        stairBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void slabWithCutting(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 2);
        slab(recipeOutput, item, material);
    }

    private static void slab(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void door(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        doorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void trapdoor(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike material) {
        trapdoorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void oneToOne(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    protected static void horizontalRecipe(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private static void twoBytwo(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("##")
                .pattern("##")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private static void oneBytwo(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("#")
                .pattern("#")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private static void storageItemRecipes(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike item, ItemLike storageItem) {
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

    protected static void waxable(Consumer<FinishedRecipe> recipeOutput, ItemLike item, ItemLike result) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .requires(item)
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void sword(Consumer<FinishedRecipe> recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("#")
            .pattern("#")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void pickaxe(Consumer<FinishedRecipe> recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("###")
            .pattern(" S ")
            .pattern(" S ")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }
    protected static void axe(Consumer<FinishedRecipe> recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("##")
            .pattern("#S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void hoe(Consumer<FinishedRecipe> recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("##")
            .pattern(" S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void shovel(Consumer<FinishedRecipe> recipeOutput, ItemLike tool, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("#")
            .pattern("S")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void helmet(Consumer<FinishedRecipe> recipeOutput, ItemLike helmet, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("###")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void chestplate(Consumer<FinishedRecipe> recipeOutput, ItemLike chestplate, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void leggings(Consumer<FinishedRecipe> recipeOutput, ItemLike leggings, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void boots(Consumer<FinishedRecipe> recipeOutput, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void oreSmeltingRecipes(@NotNull Consumer<FinishedRecipe> recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        smelting(recipeOutput, RecipeCategory.MISC, material, result, experience, 200);
        blasting(recipeOutput, RecipeCategory.MISC, material, result, experience, 100); // Smoking is twice as fast
    }

    protected static void foodCookingRecipes(@NotNull Consumer<FinishedRecipe> recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        smelting(recipeOutput, RecipeCategory.FOOD, material, result, experience, 200);
        smoking(recipeOutput, RecipeCategory.FOOD, material, result, experience, 100); // Smoking is twice as fast
        campfireCooking(recipeOutput, RecipeCategory.FOOD, material, result, experience, 600); // Campfire cooking takes three times longer
    }

    protected static void smelting(@NotNull Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_smelting");
    }

    protected static void blasting(@NotNull Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.BLASTING_RECIPE)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_blasting");
    }

    protected static void smoking(@NotNull Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.SMOKING_RECIPE)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_smoking");
    }

    protected static void stoneCutting(@NotNull Consumer<FinishedRecipe> recipeOutput, @NotNull ItemLike result, @NotNull ItemLike material, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_stonecutting");
    }

    protected static void campfireCooking(@NotNull Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), category, result, experience, cookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_campfire_cooking");
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return TheSilverAge.MOD_ID + ":" + getConversionRecipeName(result, item);
    }
}
