package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.AndCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    private static final float XP_TINY = 0.1f;
    private static final float XP_MEDIUM = 1f;

    // Recipes that use silver take the c: tags, so another mod's silver works in them too. The
    // storage block and nugget conversions and the smelting keep this mod's own items: other
    // silver mods have the same recipes for theirs, and two recipes matching one grid clash.
    private static final TagKey<Item> SILVER_INGOTS = CommonTags.Items.INGOTS_SILVER;
    private static final TagKey<Item> SILVER_NUGGETS = CommonTags.Items.NUGGETS_SILVER;

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

        // Every recipe producing a weathered or waxed variant is gated on
        // enable_silver_oxidation: with oxidation off those variants are
        // unobtainable, so their recipes would only clutter the recipe book / JEI.
        var oxidationGated = output.withConditions(new ConfigBooleanCondition(Configuration.ENABLE_SILVER_OXIDATION_ID));
        oreSmeltingRecipes(createGated, com.simibubi.create.AllItems.CRUSHED_SILVER.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);

        // Storage item recipes
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), ModItems.SILVER_INGOT.get());
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.RAW_SILVER.get(), ModItems.RAW_SILVER_BLOCK.get());

        // Tools
        sword(output, ModItems.SILVER_SWORD.get(), SILVER_INGOTS);
        pickaxe(output, ModItems.SILVER_PICKAXE.get(), SILVER_INGOTS);
        axe(output, ModItems.SILVER_AXE.get(), SILVER_INGOTS);
        hoe(output, ModItems.SILVER_HOE.get(), SILVER_INGOTS);
        shovel(output, ModItems.SILVER_SHOVEL.get(), SILVER_INGOTS);

        // Farmer's Delight compat: Silver Knife (silver ingot over a stick).
        // Gated on FD being present — without FD the item is a hidden fallback,
        // so it has no recipe.
        var fdGated = output.withConditions(new ModLoadedCondition(ModIds.FARMERS_DELIGHT));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SILVER_KNIFE.get(), 1)
                .pattern("X")
                .pattern("I")
                .define('X', SILVER_INGOTS)
                .define('I', Items.STICK)
                .unlockedBy(getHasName(SILVER_INGOTS), has(SILVER_INGOTS))
                .save(fdGated);

        // Armor
        helmet(output, ModItems.SILVER_HELMET.get(), SILVER_INGOTS);
        chestplate(output, ModItems.SILVER_CHESTPLATE.get(), SILVER_INGOTS);
        leggings(output, ModItems.SILVER_LEGGINGS.get(), SILVER_INGOTS);
        boots(output, ModItems.SILVER_BOOTS.get(), SILVER_INGOTS);

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
        bulb(oxidationGated, ModItems.EXPOSED_SILVER.get(), ModItems.EXPOSED_SILVER_BULB.get());
        bulb(oxidationGated, ModItems.WEATHERED_SILVER.get(), ModItems.WEATHERED_SILVER_BULB.get());
        bulb(oxidationGated, ModItems.OXIDIZED_SILVER.get(), ModItems.OXIDIZED_SILVER_BULB.get());
        bulb(oxidationGated, ModItems.WAXED_SILVER_BLOCK.get(), ModItems.WAXED_SILVER_BULB.get());
        bulb(oxidationGated, ModItems.WAXED_EXPOSED_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER_BULB.get());
        bulb(oxidationGated, ModItems.WAXED_WEATHERED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER_BULB.get());
        bulb(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER_BULB.get());
        waxable(oxidationGated, ModItems.SILVER_BULB.get(), ModItems.WAXED_SILVER_BULB.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_BULB.get(), ModItems.WAXED_EXPOSED_SILVER_BULB.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_BULB.get(), ModItems.WAXED_WEATHERED_SILVER_BULB.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_BULB.get(), ModItems.WAXED_OXIDIZED_SILVER_BULB.get());

        // Moon Dial
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOON_DIAL.get(), 1)
                .pattern(" S ")
                .pattern("SRS")
                .pattern(" S ")
                .define('R', Items.REDSTONE)
                .define('S', SILVER_INGOTS)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(output);

        // Moon Phase Detector
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.MOON_PHASE_DETECTOR.get(), 1)
                .pattern("GGG")
                .pattern("AAA")
                .pattern("SSS")
                .define('G', Items.GLASS)
                .define('A', Items.AMETHYST_SHARD)
                .define('S', SILVER_INGOTS)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(output);
        
        // Block of Silver
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), ModItems.SILVER_BLOCK.get());
        waxable(oxidationGated, ModItems.SILVER_BLOCK.get(), ModItems.WAXED_SILVER_BLOCK.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get());

        // Cut Silver
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_SILVER.get(), ModItems.SILVER_BLOCK.get(), 4);
        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_CUT_SILVER.get(), ModItems.EXPOSED_SILVER.get(), 4);
        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_CUT_SILVER.get(), ModItems.WEATHERED_SILVER.get(), 4);
        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_CUT_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_CUT_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        twoBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.CUT_SILVER.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.EXPOSED_CUT_SILVER.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WEATHERED_CUT_SILVER.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(oxidationGated, ModItems.WAXED_CUT_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_CUT_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(oxidationGated, ModItems.CUT_SILVER.get(), ModItems.WAXED_CUT_SILVER.get());
        waxable(oxidationGated, ModItems.EXPOSED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get());
        waxable(oxidationGated, ModItems.WEATHERED_CUT_SILVER.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get());
        waxable(oxidationGated, ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get());

        // Silver Bricks. Two crafting paths to the base block:
        //   - Crafting table: 2x2 Silver Ingots -> 4 Silver Bricks. Silver
        //     Ingot has no weather states, so this only produces the base
        //     SILVER_BRICKS variant.
        //   - Stonecutter: 1 Silver Block (any weather/waxed variant) -> 4
        //     of the matching brick variant. Mirrors the Cut Silver pattern
        //     and gives players a direct path to every brick variant without
        //     waiting on natural oxidation.
        // Waxed brick variants are additionally obtainable via Honeycomb on
        // the matching unwaxed one (the waxable() chain below); placed
        // silver_bricks also still oxidize naturally over time.
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SILVER_BRICKS.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', SILVER_INGOTS)
                .unlockedBy(getHasName(SILVER_INGOTS), has(SILVER_INGOTS))
                .save(output, getRecipeName(ModItems.SILVER_INGOT.get(), ModItems.SILVER_BRICKS.get()));

        stoneCutting(output, ModItems.SILVER_BRICKS.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICKS.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICKS.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICKS.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(oxidationGated, ModItems.WAXED_SILVER_BRICKS.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICKS.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICKS.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(oxidationGated, ModItems.SILVER_BRICKS.get(), ModItems.WAXED_SILVER_BRICKS.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_BRICKS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_BRICKS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_BRICKS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        // Silver Brick Stairs — three paths per variant:
        //   1) Crafting-table stair shape from the matching Silver Bricks (4)
        //   2) Stonecutter from the matching Silver Bricks (1 -> 1)
        //   3) Stonecutter from the matching Silver Block (1 -> 4)
        // Paths 1+2 come from stairsWithCutting; path 3 is the extra
        // stoneCutting block below. Mirrors the Cut Silver Stairs pattern.
        // Silver Brick Wall
        wallWithCutting(output, ModItems.SILVER_BRICK_WALL.get(), ModItems.SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_WALL.get(), ModItems.EXPOSED_SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_WALL.get(), ModItems.WEATHERED_SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_WALL.get(), ModItems.OXIDIZED_SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.WAXED_SILVER_BRICK_WALL.get(), ModItems.WAXED_SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICK_WALL.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICK_WALL.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        wallWithCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICK_WALL.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        stoneCutting(output, ModItems.SILVER_BRICK_WALL.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_WALL.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_WALL.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_WALL.get(), ModItems.OXIDIZED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_SILVER_BRICK_WALL.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICK_WALL.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICK_WALL.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICK_WALL.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(oxidationGated, ModItems.SILVER_BRICK_WALL.get(), ModItems.WAXED_SILVER_BRICK_WALL.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_WALL.get(), ModItems.WAXED_EXPOSED_SILVER_BRICK_WALL.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_WALL.get(), ModItems.WAXED_WEATHERED_SILVER_BRICK_WALL.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_WALL.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICK_WALL.get());

        stairsWithCutting(output, ModItems.SILVER_BRICK_STAIRS.get(), ModItems.SILVER_BRICKS.get());
        stairsWithCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.EXPOSED_SILVER_BRICKS.get());
        stairsWithCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WEATHERED_SILVER_BRICKS.get());
        stairsWithCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.OXIDIZED_SILVER_BRICKS.get());

        stairsWithCutting(oxidationGated, ModItems.WAXED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_SILVER_BRICKS.get());
        stairsWithCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        stairsWithCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        stairsWithCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        stoneCutting(output, ModItems.SILVER_BRICK_STAIRS.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(oxidationGated, ModItems.WAXED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(oxidationGated, ModItems.SILVER_BRICK_STAIRS.get(), ModItems.WAXED_SILVER_BRICK_STAIRS.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get());

        // Silver Brick Slab — three paths per variant:
        //   1) Crafting-table slab shape from the matching Silver Bricks (6)
        //   2) Stonecutter from the matching Silver Bricks (1 -> 2)
        //   3) Stonecutter from the matching Silver Block (1 -> 8)
        // Paths 1+2 come from slabWithCutting; path 3 is the extra
        // stoneCutting block below. Mirrors the Cut Silver Slab pattern.
        slabWithCutting(output, ModItems.SILVER_BRICK_SLAB.get(), ModItems.SILVER_BRICKS.get());
        slabWithCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.EXPOSED_SILVER_BRICKS.get());
        slabWithCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WEATHERED_SILVER_BRICKS.get());
        slabWithCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.OXIDIZED_SILVER_BRICKS.get());

        slabWithCutting(oxidationGated, ModItems.WAXED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_SILVER_BRICKS.get());
        slabWithCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        slabWithCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        slabWithCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        stoneCutting(output, ModItems.SILVER_BRICK_SLAB.get(), ModItems.SILVER_BLOCK.get(), 8);
        stoneCutting(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.EXPOSED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WEATHERED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.OXIDIZED_SILVER.get(), 8);

        stoneCutting(oxidationGated, ModItems.WAXED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_SILVER_BLOCK.get(), 8);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 8);

        waxable(oxidationGated, ModItems.SILVER_BRICK_SLAB.get(), ModItems.WAXED_SILVER_BRICK_SLAB.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get());

        // Cut Silver Stairs
        stairsWithCutting(output, ModItems.CUT_SILVER_STAIRS.get(), ModItems.CUT_SILVER.get());
        stairsWithCutting(oxidationGated, ModItems.EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.EXPOSED_CUT_SILVER.get());
        stairsWithCutting(oxidationGated, ModItems.WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WEATHERED_CUT_SILVER.get());
        stairsWithCutting(oxidationGated, ModItems.OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.OXIDIZED_CUT_SILVER.get());

        stairsWithCutting(oxidationGated, ModItems.WAXED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_CUT_SILVER.get());
        stairsWithCutting(oxidationGated, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get());
        stairsWithCutting(oxidationGated, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get());
        stairsWithCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get());

        stoneCutting(output, ModItems.CUT_SILVER_STAIRS.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(oxidationGated, ModItems.WAXED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(oxidationGated, ModItems.CUT_SILVER_STAIRS.get(), ModItems.WAXED_CUT_SILVER_STAIRS.get());
        waxable(oxidationGated, ModItems.EXPOSED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get());
        waxable(oxidationGated, ModItems.WEATHERED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get());
        waxable(oxidationGated, ModItems.OXIDIZED_CUT_SILVER_STAIRS.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get());

        // Cut Silver Slab
        slabWithCutting(output, ModItems.CUT_SILVER_SLAB.get(), ModItems.CUT_SILVER.get());
        slabWithCutting(oxidationGated, ModItems.EXPOSED_CUT_SILVER_SLAB.get(), ModItems.EXPOSED_CUT_SILVER.get());
        slabWithCutting(oxidationGated, ModItems.WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WEATHERED_CUT_SILVER.get());
        slabWithCutting(oxidationGated, ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.OXIDIZED_CUT_SILVER.get());

        slabWithCutting(oxidationGated, ModItems.WAXED_CUT_SILVER_SLAB.get(), ModItems.WAXED_CUT_SILVER.get());
        slabWithCutting(oxidationGated, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get());
        slabWithCutting(oxidationGated, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get());
        slabWithCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get());

        stoneCutting(output, ModItems.CUT_SILVER_SLAB.get(), ModItems.SILVER_BLOCK.get(), 8);
        stoneCutting(oxidationGated, ModItems.EXPOSED_CUT_SILVER_SLAB.get(), ModItems.EXPOSED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WEATHERED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.OXIDIZED_SILVER.get(), 8);

        stoneCutting(oxidationGated, ModItems.WAXED_CUT_SILVER_SLAB.get(), ModItems.WAXED_SILVER_BLOCK.get(), 8);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 8);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 8);

        waxable(oxidationGated, ModItems.CUT_SILVER_SLAB.get(), ModItems.WAXED_CUT_SILVER_SLAB.get());
        waxable(oxidationGated, ModItems.EXPOSED_CUT_SILVER_SLAB.get(), ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get());
        waxable(oxidationGated, ModItems.WEATHERED_CUT_SILVER_SLAB.get(), ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get());
        waxable(oxidationGated, ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get());

        // Chiseled Silver
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SILVER.get(), ModItems.CUT_SILVER_SLAB.get(), 1);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.EXPOSED_CUT_SILVER_SLAB.get(), 1);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WEATHERED_CUT_SILVER_SLAB.get(), 1);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.OXIDIZED_CUT_SILVER_SLAB.get(), 1);

        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_CUT_SILVER_SLAB.get(), 1);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get(), 1);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get(), 1);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get(), 1);

        stoneCutting(output, ModItems.CHISELED_SILVER.get(), ModItems.CUT_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.EXPOSED_CUT_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WEATHERED_CUT_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.OXIDIZED_CUT_SILVER.get(), 1);

        stoneCutting(oxidationGated, ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_CUT_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_CUT_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get(), 1);

        stoneCutting(output, ModItems.CHISELED_SILVER.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(oxidationGated, ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(oxidationGated, ModItems.CHISELED_SILVER.get(), ModItems.WAXED_CHISELED_SILVER.get());
        waxable(oxidationGated, ModItems.EXPOSED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CHISELED_SILVER.get());
        waxable(oxidationGated, ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_WEATHERED_CHISELED_SILVER.get());
        waxable(oxidationGated, ModItems.OXIDIZED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get());

        // Silver Pillar — three obtainment paths per variant (mirrors vanilla quartz_pillar):
        //   1) Crafting table 1x2 vertical of Silver Block (2 -> 2)
        //   2) Stonecutter from Silver Block (1 -> 1)
        //   3) Honeycomb waxing (unwaxed -> waxed)
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.SILVER_PILLAR.get(), ModItems.SILVER_BLOCK.get(), 2);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_SILVER_PILLAR.get(), ModItems.EXPOSED_SILVER.get(), 2);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_SILVER_PILLAR.get(), ModItems.WEATHERED_SILVER.get(), 2);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_SILVER_PILLAR.get(), ModItems.OXIDIZED_SILVER.get(), 2);

        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_SILVER_PILLAR.get(), ModItems.WAXED_SILVER_BLOCK.get(), 2);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_SILVER_PILLAR.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 2);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_SILVER_PILLAR.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 2);
        oneBytwo(oxidationGated, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_SILVER_PILLAR.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 2);

        stoneCutting(output, ModItems.SILVER_PILLAR.get(), ModItems.SILVER_BLOCK.get(), 1);
        stoneCutting(oxidationGated, ModItems.EXPOSED_SILVER_PILLAR.get(), ModItems.EXPOSED_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WEATHERED_SILVER_PILLAR.get(), ModItems.WEATHERED_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.OXIDIZED_SILVER_PILLAR.get(), ModItems.OXIDIZED_SILVER.get(), 1);

        stoneCutting(oxidationGated, ModItems.WAXED_SILVER_PILLAR.get(), ModItems.WAXED_SILVER_BLOCK.get(), 1);
        stoneCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_PILLAR.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_PILLAR.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 1);
        stoneCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_PILLAR.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 1);

        waxable(oxidationGated, ModItems.SILVER_PILLAR.get(), ModItems.WAXED_SILVER_PILLAR.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_PILLAR.get(), ModItems.WAXED_EXPOSED_SILVER_PILLAR.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_PILLAR.get(), ModItems.WAXED_WEATHERED_SILVER_PILLAR.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_PILLAR.get(), ModItems.WAXED_OXIDIZED_SILVER_PILLAR.get());

        // Silver Grate
        grateWithCutting(output, ModItems.SILVER_GRATE.get(), ModItems.SILVER_BLOCK.get());
        grateWithCutting(oxidationGated, ModItems.EXPOSED_SILVER_GRATE.get(), ModItems.EXPOSED_SILVER.get());
        grateWithCutting(oxidationGated, ModItems.WEATHERED_SILVER_GRATE.get(), ModItems.WEATHERED_SILVER.get());
        grateWithCutting(oxidationGated, ModItems.OXIDIZED_SILVER_GRATE.get(), ModItems.OXIDIZED_SILVER.get());

        grateWithCutting(oxidationGated, ModItems.WAXED_SILVER_GRATE.get(), ModItems.WAXED_SILVER_BLOCK.get());
        grateWithCutting(oxidationGated, ModItems.WAXED_EXPOSED_SILVER_GRATE.get(), ModItems.WAXED_EXPOSED_SILVER.get());
        grateWithCutting(oxidationGated, ModItems.WAXED_WEATHERED_SILVER_GRATE.get(), ModItems.WAXED_WEATHERED_SILVER.get());
        grateWithCutting(oxidationGated, ModItems.WAXED_OXIDIZED_SILVER_GRATE.get(), ModItems.WAXED_OXIDIZED_SILVER.get());

        waxable(oxidationGated, ModItems.SILVER_GRATE.get(), ModItems.WAXED_SILVER_GRATE.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_GRATE.get(), ModItems.WAXED_EXPOSED_SILVER_GRATE.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_GRATE.get(), ModItems.WAXED_WEATHERED_SILVER_GRATE.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_GRATE.get(), ModItems.WAXED_OXIDIZED_SILVER_GRATE.get());

        // Silver Door
        door(output, ModItems.SILVER_DOOR.get(), SILVER_INGOTS);
        waxable(oxidationGated, ModItems.SILVER_DOOR.get(), ModItems.WAXED_SILVER_DOOR.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_DOOR.get(), ModItems.WAXED_EXPOSED_SILVER_DOOR.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_DOOR.get(), ModItems.WAXED_WEATHERED_SILVER_DOOR.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_DOOR.get(), ModItems.WAXED_OXIDIZED_SILVER_DOOR.get());

        // Silver Trapdoor
        trapdoor(output, ModItems.SILVER_TRAPDOOR.get(), SILVER_INGOTS);
        waxable(oxidationGated, ModItems.SILVER_TRAPDOOR.get(), ModItems.WAXED_SILVER_TRAPDOOR.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_TRAPDOOR.get(), ModItems.WAXED_EXPOSED_SILVER_TRAPDOOR.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_TRAPDOOR.get(), ModItems.WAXED_WEATHERED_SILVER_TRAPDOOR.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_TRAPDOOR.get(), ModItems.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());

        // Silver Pressure Plate - two ingots side by side, as vanilla's weighted plates
        pressurePlate(output, ModItems.SILVER_PRESSURE_PLATE.get(), SILVER_INGOTS);
        waxable(oxidationGated, ModItems.SILVER_PRESSURE_PLATE.get(), ModItems.WAXED_SILVER_PRESSURE_PLATE.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_PRESSURE_PLATE.get(), ModItems.WAXED_EXPOSED_SILVER_PRESSURE_PLATE.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_PRESSURE_PLATE.get(), ModItems.WAXED_WEATHERED_SILVER_PRESSURE_PLATE.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_PRESSURE_PLATE.get(), ModItems.WAXED_OXIDIZED_SILVER_PRESSURE_PLATE.get());

        // Silver Torch — mirrors the vanilla Copper Torch shape (nugget over coal over stick).
        silverTorch(output, ModItems.SILVER_TORCH.get(), SILVER_NUGGETS);

        // Silver Lantern
        lantern(output, ModItems.SILVER_LANTERN.get(), SILVER_NUGGETS, ModItems.SILVER_TORCH.get());
        waxable(oxidationGated, ModItems.SILVER_LANTERN.get(), ModItems.WAXED_SILVER_LANTERN.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_LANTERN.get(), ModItems.WAXED_EXPOSED_SILVER_LANTERN.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_LANTERN.get(), ModItems.WAXED_WEATHERED_SILVER_LANTERN.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_LANTERN.get(), ModItems.WAXED_OXIDIZED_SILVER_LANTERN.get());

        // Silver Chain
        chain(output, ModItems.SILVER_CHAIN.get(), SILVER_INGOTS, SILVER_NUGGETS);
        waxable(oxidationGated, ModItems.SILVER_CHAIN.get(), ModItems.WAXED_SILVER_CHAIN.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_CHAIN.get(), ModItems.WAXED_EXPOSED_SILVER_CHAIN.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_CHAIN.get(), ModItems.WAXED_WEATHERED_SILVER_CHAIN.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_CHAIN.get(), ModItems.WAXED_OXIDIZED_SILVER_CHAIN.get());

        // Silver Bars
        bars(output, ModItems.SILVER_BARS.get(), SILVER_INGOTS);
        waxable(oxidationGated, ModItems.SILVER_BARS.get(), ModItems.WAXED_SILVER_BARS.get());
        waxable(oxidationGated, ModItems.EXPOSED_SILVER_BARS.get(), ModItems.WAXED_EXPOSED_SILVER_BARS.get());
        waxable(oxidationGated, ModItems.WEATHERED_SILVER_BARS.get(), ModItems.WAXED_WEATHERED_SILVER_BARS.get());
        waxable(oxidationGated, ModItems.OXIDIZED_SILVER_BARS.get(), ModItems.WAXED_OXIDIZED_SILVER_BARS.get());
    }

    /** Torch: nugget over coal/charcoal over a stick, yields 4 (vanilla Copper Torch shape). */
    private static void silverTorch(RecipeOutput output, ItemLike result, TagKey<Item> nugget) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 4)
                .pattern("N")
                .pattern("X")
                .pattern("#")
                .define('N', nugget)
                .define('X', Ingredient.of(Items.COAL, Items.CHARCOAL))
                .define('#', Items.STICK)
                .unlockedBy(getHasName(nugget), has(nugget))
                .save(output);
    }

    /** Lantern: 8 nuggets around a torch (vanilla lantern shape). */
    private static void lantern(RecipeOutput output, ItemLike result, TagKey<Item> nugget, ItemLike torch) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 1)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .define('X', nugget)
                .define('#', torch)
                .unlockedBy(getHasName(nugget), has(nugget))
                .save(output);
    }

    /** Chain: nugget / ingot / nugget vertically (vanilla chain shape). */
    private static void chain(RecipeOutput output, ItemLike result, TagKey<Item> ingot, TagKey<Item> nugget) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 1)
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .define('I', ingot)
                .define('N', nugget)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(output);
    }

    /** Bars: 6 ingots in a 3x2, yields 16 (vanilla iron/copper bars shape). */
    private static void bars(RecipeOutput output, ItemLike result, TagKey<Item> ingot) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 16)
                .pattern("###")
                .pattern("###")
                .define('#', ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(output);
    }

    /**
     * Overrides for vanilla recipes, saved under the vanilla recipe's own id so they replace it. Each
     * applies while the master switch and its own option are both on; otherwise its {@code _fallback},
     * the vanilla recipe as it was, takes its place, since the override's file hides vanilla's either way.
     */
    private void buildRecipeOverrides(@NotNull RecipeOutput output) {
        ICondition master = new ConfigBooleanCondition(Configuration.OVERRIDE_VANILLA_RECIPES_ID);

        // Glistering Melon Slice
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLISTERING_MELON_SLICE, 1)
                .pattern("###")
                .pattern("#M#")
                .pattern("###")
                .define('#', ModTags.Items.GLISTERING_NUGGETS)
                .define('M', Items.MELON_SLICE)
                .unlockedBy(getHasName(Items.MELON_SLICE), has(Items.MELON_SLICE))
                .save(overrideOutput(output, master, RecipeOverride.GLISTERING_MELON));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLISTERING_MELON_SLICE, 1)
                .pattern("###")
                .pattern("#M#")
                .pattern("###")
                .define('#', Items.GOLD_NUGGET)
                .define('M', Items.MELON_SLICE)
                .unlockedBy(getHasName(Items.MELON_SLICE), has(Items.MELON_SLICE))
                .save(fallbackOutput(output, master, RecipeOverride.GLISTERING_MELON), "minecraft:" + ItemUtils.getName(Items.GLISTERING_MELON_SLICE) + "_fallback");

        // Lodestone
        RecipeOutput conditionalOutput = overrideOutput(output, master, RecipeOverride.LODESTONE);
        RecipeOutput fallbackOutput = fallbackOutput(output, master, RecipeOverride.LODESTONE);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.CHISELED_STONE_BRICKS)
                .define('S', SILVER_INGOTS)
                .unlockedBy(getHasName(SILVER_INGOTS), has(SILVER_INGOTS))
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
        conditionalOutput = overrideOutput(output, master, RecipeOverride.BREWING_STAND);
        fallbackOutput = fallbackOutput(output, master, RecipeOverride.BREWING_STAND);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', SILVER_INGOTS)
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(conditionalOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
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
        conditionalOutput = overrideOutput(output, master, RecipeOverride.COMPARATOR);
        fallbackOutput = fallbackOutput(output, master, RecipeOverride.COMPARATOR);
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
        conditionalOutput = overrideOutput(output, master, RecipeOverride.REPEATER);
        fallbackOutput = fallbackOutput(output, master, RecipeOverride.REPEATER);
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

    private static ICondition overrideCondition(ICondition master, RecipeOverride override) {
        return new AndCondition(List.of(master, new ConfigBooleanCondition(override.settingId())));
    }

    private static RecipeOutput overrideOutput(RecipeOutput output, ICondition master, RecipeOverride override) {
        return output.withConditions(overrideCondition(master, override));
    }

    private static RecipeOutput fallbackOutput(RecipeOutput output, ICondition master, RecipeOverride override) {
        return output.withConditions(new NotCondition(overrideCondition(master, override)));
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

    /** Wall: 6 from a 3x2 of the material, plus a 1:1 stonecutting shortcut. */
    private static void wallWithCutting(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 1);
        wall(recipeOutput, item, material);
    }

    private static void wall(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        wallBuilder(RecipeCategory.BUILDING_BLOCKS, item, Ingredient.of(material))
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

    private static void door(RecipeOutput recipeOutput, ItemLike item, TagKey<Item> material) {
        doorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(ModItems.SILVER_INGOT.get()))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void pressurePlate(RecipeOutput recipeOutput, ItemLike item, TagKey<Item> material) {
        pressurePlateBuilder(RecipeCategory.REDSTONE, item, Ingredient.of(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private static void trapdoor(RecipeOutput recipeOutput, ItemLike item, TagKey<Item> material) {
        trapdoorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(ModItems.SILVER_INGOT.get()))
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
        // Yields 4, matching vanilla's Copper Bulb.
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result, 4)
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

    protected static void sword(RecipeOutput recipeOutput, ItemLike tool, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool)
            .pattern("#")
            .pattern("#")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void pickaxe(RecipeOutput recipeOutput, ItemLike tool, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("###")
            .pattern(" S ")
            .pattern(" S ")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }
    protected static void axe(RecipeOutput recipeOutput, ItemLike tool, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("##")
            .pattern("#S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void hoe(RecipeOutput recipeOutput, ItemLike tool, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("##")
            .pattern(" S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void shovel(RecipeOutput recipeOutput, ItemLike tool, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool)
            .pattern("#")
            .pattern("S")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected static void helmet(RecipeOutput recipeOutput, ItemLike helmet, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("###")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void chestplate(RecipeOutput recipeOutput, ItemLike chestplate, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void leggings(RecipeOutput recipeOutput, ItemLike leggings, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void boots(RecipeOutput recipeOutput, ItemLike boots, TagKey<Item> material) {
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

    protected static String getHasName(TagKey<Item> tag) {
        return "has_" + tag.location().getPath().replace('/', '_');
    }
}
