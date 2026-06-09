package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    private static final float XP_TINY = 0.1f;
    private static final float XP_MEDIUM = 1f;

    // 1.21.2 reworked RecipeProvider: the recipe-building helpers (shaped,
    // shapeless, has, stairBuilder, ...) are now INSTANCE methods that inject the
    // provider's item HolderGetter, and buildRecipes() takes no args. We keep a
    // reference to the RecipeOutput so the conditional vanilla-override recipes
    // can still wrap it via output.withConditions(...).
    private final RecipeOutput output;
    /** Kept so the firework-star override can resolve item tags (#dyes / #skulls) to HolderSets
     *  for the FireworkStarRecipe ingredients (Ingredient.of has no TagKey overload). */
    private final HolderLookup.Provider registries;

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.output = output;
        this.registries = registries;
    }

    @Override
    protected void buildRecipes() {
        buildCraftingRecipes(this.output);
        buildRecipeOverrides(this.output);
    }

    /**
     * DataProvider runner. 1.21.2 split RecipeProvider into the builder (above)
     * and a {@link RecipeProvider.Runner} that the {@link net.minecraft.data.DataGenerator}
     * actually registers; the runner instantiates the provider once the registries
     * future resolves.
     */
    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "The Silver Age Recipes";
        }
    }

    private void buildCraftingRecipes(@NotNull RecipeOutput output) {
        oreSmeltingRecipes(output, ModItems.RAW_SILVER.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);
        oreSmeltingRecipes(output, ModItems.SILVER_ORE.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);
        oreSmeltingRecipes(output, ModItems.DEEPSLATE_SILVER_ORE.get(), ModItems.SILVER_INGOT.get(), XP_MEDIUM);

        // NOTE: Create is not yet available past MC 1.21.1, so the crushed_raw_silver
        // -> silver_ingot smelting bridge is dropped on this branch. Re-add it (gated
        // on ModIds.CREATE via withConditions) once Create ships for 1.21.3.

        // Storage item recipes
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), ModItems.SILVER_INGOT.get());
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.RAW_SILVER.get(), ModItems.RAW_SILVER_BLOCK.get());

        // Tools
        sword(output, ModItems.SILVER_SWORD.get(), ModItems.SILVER_INGOT.get());
        pickaxe(output, ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_INGOT.get());
        axe(output, ModItems.SILVER_AXE.get(), ModItems.SILVER_INGOT.get());
        hoe(output, ModItems.SILVER_HOE.get(), ModItems.SILVER_INGOT.get());
        shovel(output, ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_INGOT.get());
        spear(output, ModItems.SILVER_SPEAR.get(), ModItems.SILVER_INGOT.get());

        // Farmer's Delight compat: Silver Knife (silver ingot over a stick).
        // Gated on FD being present — without FD the item is a hidden fallback,
        // so it has no recipe.
        var fdGated = output.withConditions(new ModLoadedCondition(ModIds.FARMERS_DELIGHT));
        shaped(RecipeCategory.TOOLS, ModItems.SILVER_KNIFE.get(), 1)
                .pattern("X")
                .pattern("I")
                .define('X', ModItems.SILVER_INGOT.get())
                .define('I', Items.STICK)
                .unlockedBy(getHasName(ModItems.SILVER_INGOT.get()), has(ModItems.SILVER_INGOT.get()))
                .save(fdGated);

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
        oreSmeltingRecipes(output, ModItems.SILVER_SPEAR.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HELMET.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_CHESTPLATE.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_LEGGINGS.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_BOOTS.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_HORSE_ARMOR.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);
        oreSmeltingRecipes(output, ModItems.SILVER_NAUTILUS_ARMOR.get(), ModItems.SILVER_NUGGET.get(), XP_TINY);

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
        shaped(RecipeCategory.TOOLS, ModItems.MOON_DIAL.get(), 1)
                .pattern(" S ")
                .pattern("SRS")
                .pattern(" S ")
                .define('R', Items.REDSTONE)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(output);

        // Moon Phase Detector
        shaped(RecipeCategory.REDSTONE, ModItems.MOON_PHASE_DETECTOR.get(), 1)
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
        twoBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.SILVER_BRICKS.get(), ModItems.SILVER_INGOT.get(), 4);

        stoneCutting(output, ModItems.SILVER_BRICKS.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.EXPOSED_SILVER_BRICKS.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WEATHERED_SILVER_BRICKS.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.OXIDIZED_SILVER_BRICKS.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.WAXED_SILVER_BRICKS.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_SILVER_BRICKS.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_SILVER_BRICKS.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(output, ModItems.SILVER_BRICKS.get(), ModItems.WAXED_SILVER_BRICKS.get());
        waxable(output, ModItems.EXPOSED_SILVER_BRICKS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        waxable(output, ModItems.WEATHERED_SILVER_BRICKS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        waxable(output, ModItems.OXIDIZED_SILVER_BRICKS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        // Silver Brick Stairs — three paths per variant:
        //   1) Crafting-table stair shape from the matching Silver Bricks (4)
        //   2) Stonecutter from the matching Silver Bricks (1 -> 1)
        //   3) Stonecutter from the matching Silver Block (1 -> 4)
        // Paths 1+2 come from stairsWithCutting; path 3 is the extra
        // stoneCutting block below. Mirrors the Cut Silver Stairs pattern.
        stairsWithCutting(output, ModItems.SILVER_BRICK_STAIRS.get(), ModItems.SILVER_BRICKS.get());
        stairsWithCutting(output, ModItems.EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.EXPOSED_SILVER_BRICKS.get());
        stairsWithCutting(output, ModItems.WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WEATHERED_SILVER_BRICKS.get());
        stairsWithCutting(output, ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.OXIDIZED_SILVER_BRICKS.get());

        stairsWithCutting(output, ModItems.WAXED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_SILVER_BRICKS.get());
        stairsWithCutting(output, ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        stairsWithCutting(output, ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        stairsWithCutting(output, ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        stoneCutting(output, ModItems.SILVER_BRICK_STAIRS.get(), ModItems.SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.OXIDIZED_SILVER.get(), 4);

        stoneCutting(output, ModItems.WAXED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_SILVER_BLOCK.get(), 4);
        stoneCutting(output, ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 4);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 4);

        waxable(output, ModItems.SILVER_BRICK_STAIRS.get(), ModItems.WAXED_SILVER_BRICK_STAIRS.get());
        waxable(output, ModItems.EXPOSED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get());
        waxable(output, ModItems.WEATHERED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get());
        waxable(output, ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get());

        // Silver Brick Slab — three paths per variant:
        //   1) Crafting-table slab shape from the matching Silver Bricks (6)
        //   2) Stonecutter from the matching Silver Bricks (1 -> 2)
        //   3) Stonecutter from the matching Silver Block (1 -> 8)
        // Paths 1+2 come from slabWithCutting; path 3 is the extra
        // stoneCutting block below. Mirrors the Cut Silver Slab pattern.
        slabWithCutting(output, ModItems.SILVER_BRICK_SLAB.get(), ModItems.SILVER_BRICKS.get());
        slabWithCutting(output, ModItems.EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.EXPOSED_SILVER_BRICKS.get());
        slabWithCutting(output, ModItems.WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WEATHERED_SILVER_BRICKS.get());
        slabWithCutting(output, ModItems.OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.OXIDIZED_SILVER_BRICKS.get());

        slabWithCutting(output, ModItems.WAXED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_SILVER_BRICKS.get());
        slabWithCutting(output, ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER_BRICKS.get());
        slabWithCutting(output, ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER_BRICKS.get());
        slabWithCutting(output, ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICKS.get());

        stoneCutting(output, ModItems.SILVER_BRICK_SLAB.get(), ModItems.SILVER_BLOCK.get(), 8);
        stoneCutting(output, ModItems.EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.EXPOSED_SILVER.get(), 8);
        stoneCutting(output, ModItems.WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WEATHERED_SILVER.get(), 8);
        stoneCutting(output, ModItems.OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.OXIDIZED_SILVER.get(), 8);

        stoneCutting(output, ModItems.WAXED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_SILVER_BLOCK.get(), 8);
        stoneCutting(output, ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 8);
        stoneCutting(output, ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 8);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 8);

        waxable(output, ModItems.SILVER_BRICK_SLAB.get(), ModItems.WAXED_SILVER_BRICK_SLAB.get());
        waxable(output, ModItems.EXPOSED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get());
        waxable(output, ModItems.WEATHERED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get());
        waxable(output, ModItems.OXIDIZED_SILVER_BRICK_SLAB.get(), ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get());

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

        // Silver Pillar — three obtainment paths per variant (mirrors vanilla quartz_pillar):
        //   1) Crafting table 1x2 vertical of Silver Block (2 -> 2)
        //   2) Stonecutter from Silver Block (1 -> 1)
        //   3) Honeycomb waxing (unwaxed -> waxed)
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.SILVER_PILLAR.get(), ModItems.SILVER_BLOCK.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.EXPOSED_SILVER_PILLAR.get(), ModItems.EXPOSED_SILVER.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WEATHERED_SILVER_PILLAR.get(), ModItems.WEATHERED_SILVER.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.OXIDIZED_SILVER_PILLAR.get(), ModItems.OXIDIZED_SILVER.get(), 2);

        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_SILVER_PILLAR.get(), ModItems.WAXED_SILVER_BLOCK.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_EXPOSED_SILVER_PILLAR.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_WEATHERED_SILVER_PILLAR.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 2);
        oneBytwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.WAXED_OXIDIZED_SILVER_PILLAR.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 2);

        stoneCutting(output, ModItems.SILVER_PILLAR.get(), ModItems.SILVER_BLOCK.get(), 1);
        stoneCutting(output, ModItems.EXPOSED_SILVER_PILLAR.get(), ModItems.EXPOSED_SILVER.get(), 1);
        stoneCutting(output, ModItems.WEATHERED_SILVER_PILLAR.get(), ModItems.WEATHERED_SILVER.get(), 1);
        stoneCutting(output, ModItems.OXIDIZED_SILVER_PILLAR.get(), ModItems.OXIDIZED_SILVER.get(), 1);

        stoneCutting(output, ModItems.WAXED_SILVER_PILLAR.get(), ModItems.WAXED_SILVER_BLOCK.get(), 1);
        stoneCutting(output, ModItems.WAXED_EXPOSED_SILVER_PILLAR.get(), ModItems.WAXED_EXPOSED_SILVER.get(), 1);
        stoneCutting(output, ModItems.WAXED_WEATHERED_SILVER_PILLAR.get(), ModItems.WAXED_WEATHERED_SILVER.get(), 1);
        stoneCutting(output, ModItems.WAXED_OXIDIZED_SILVER_PILLAR.get(), ModItems.WAXED_OXIDIZED_SILVER.get(), 1);

        waxable(output, ModItems.SILVER_PILLAR.get(), ModItems.WAXED_SILVER_PILLAR.get());
        waxable(output, ModItems.EXPOSED_SILVER_PILLAR.get(), ModItems.WAXED_EXPOSED_SILVER_PILLAR.get());
        waxable(output, ModItems.WEATHERED_SILVER_PILLAR.get(), ModItems.WAXED_WEATHERED_SILVER_PILLAR.get());
        waxable(output, ModItems.OXIDIZED_SILVER_PILLAR.get(), ModItems.WAXED_OXIDIZED_SILVER_PILLAR.get());

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
        shaped(RecipeCategory.MISC, Items.GLISTERING_MELON_SLICE, 1)
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
        shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.CHISELED_STONE_BRICKS)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(ModItems.SILVER_INGOT.get()), has(ModItems.SILVER_INGOT.get()))
                .save(conditionalOutput);
        shaped(RecipeCategory.MISC, Items.LODESTONE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.CHISELED_STONE_BRICKS)
                .define('S', Items.NETHERITE_INGOT)
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)) // TODO: Change fallback to IRON_INGOT once updated to later Minecraft version
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.LODESTONE) + "_fallback"); // Original recipe if override is disabled

        // Brewing Stand
        shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', ModItems.SILVER_INGOT.get())
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(conditionalOutput);
        shaped(RecipeCategory.MISC, Items.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("SSS")
                .define('B', Items.BLAZE_ROD)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.BREWING_STAND) + "_fallback");  // Original recipe if override is disabled

        // Name Tag
//        shapeless(RecipeCategory.MISC, Items.NAME_TAG, 1)
//                .requires(Items.PAPER)
//                .requires(ModItems.SILVER_NUGGET.get())
//                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
//                .save(conditionalOutput);
        // TODO: Tweak Name Tag recipe when it is added in later Minecraft version

        // Redstone Comparator
        // The S slot accepts any item in the #thesilverage:redstone_silver_components tag.
        // Currently populated with silver_ingot + silver_sheet (sheet is Create-only, but the
        // ingot path always works). Addons may contribute extra silver forms to the tag.
        shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
                .pattern(" T ")
                .pattern("TQT")
                .pattern("SSS")
                .define('T', Items.REDSTONE_TORCH)
                .define('Q', Items.QUARTZ)
                .define('S', ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH))
                .save(conditionalOutput);
        shaped(RecipeCategory.REDSTONE, Items.COMPARATOR, 1)
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
        shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                .pattern("TRT")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('T', Items.REDSTONE_TORCH)
                .define('S', ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(conditionalOutput);
        shaped(RecipeCategory.REDSTONE, Items.REPEATER, 1)
                .pattern("TRT")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('T', Items.REDSTONE_TORCH)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.REPEATER) + "_fallback");  // Original recipe if override is disabled

        // Firework Star — Silver Nugget parity (silver nugget acts as a STAR shape, like gold).
        // 26.1 made the firework_star recipe data-driven (a `shapes` map of Shape->Ingredient),
        // replacing the old static FireworkStarRecipe.SHAPE_BY_ITEM map the mod used to mutate.
        // We override the vanilla recipe so the STAR shape accepts gold OR silver nugget.
        fireworkStarOverride(conditionalOutput, fallbackOutput);
    }

    /**
     * Emits a {@code minecraft:firework_star} override whose STAR shape accepts gold OR silver
     * nugget (conditional, gated by OVERRIDE_VANILLA_RECIPES), plus a {@code _fallback} that is the
     * exact vanilla recipe (STAR = gold nugget only) for when the override is disabled. Mirrors the
     * comparator/repeater override scheme. Every other field replicates vanilla firework_star.
     */
    private void fireworkStarOverride(RecipeOutput conditionalOutput, RecipeOutput fallbackOutput) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        // Ingredient.of has no TagKey overload — resolve #minecraft:dyes / #minecraft:skulls to HolderSets.
        Ingredient dye = Ingredient.of(items.getOrThrow(ItemTags.DYES));
        Ingredient skulls = Ingredient.of(items.getOrThrow(ItemTags.SKULLS));
        Ingredient fuel = Ingredient.of(Items.GUNPOWDER);
        Ingredient trail = Ingredient.of(Items.DIAMOND);
        Ingredient twinkle = Ingredient.of(Items.GLOWSTONE_DUST);
        ItemStackTemplate result = new ItemStackTemplate(Items.FIREWORK_STAR);

        // NOTE: the FireworkStarRecipe canonical (record) constructor order is
        // (shapes, trail, twinkle, fuel, dye, result) — NOT (dye, fuel, trail, twinkle).
        // Verified by diffing the generated _fallback against vanilla firework_star.json.

        // Shared (non-star) shapes — identical to vanilla.
        Ingredient burst = Ingredient.of(Items.FEATHER);
        Ingredient largeBall = Ingredient.of(Items.FIRE_CHARGE);

        // Conditional (override ON): STAR accepts gold + silver nugget.
        Map<FireworkExplosion.Shape, Ingredient> silverShapes = Map.of(
                FireworkExplosion.Shape.BURST, burst,
                FireworkExplosion.Shape.CREEPER, skulls,
                FireworkExplosion.Shape.LARGE_BALL, largeBall,
                FireworkExplosion.Shape.STAR, Ingredient.of(Items.GOLD_NUGGET, ModItems.SILVER_NUGGET.get()));
        SpecialRecipeBuilder.special(() -> new FireworkStarRecipe(silverShapes, trail, twinkle, fuel, dye, result))
                .save(conditionalOutput, "minecraft:" + ItemUtils.getName(Items.FIREWORK_STAR));

        // Fallback (override OFF): exact vanilla recipe — STAR = gold nugget only.
        Map<FireworkExplosion.Shape, Ingredient> vanillaShapes = Map.of(
                FireworkExplosion.Shape.BURST, burst,
                FireworkExplosion.Shape.CREEPER, skulls,
                FireworkExplosion.Shape.LARGE_BALL, largeBall,
                FireworkExplosion.Shape.STAR, Ingredient.of(Items.GOLD_NUGGET));
        SpecialRecipeBuilder.special(() -> new FireworkStarRecipe(vanillaShapes, trail, twinkle, fuel, dye, result))
                .save(fallbackOutput, "minecraft:" + ItemUtils.getName(Items.FIREWORK_STAR) + "_fallback");
    }

    private void stairsWithCutting(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 1);
        stairs(recipeOutput, item, material);
    }

    private void stairs(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stairBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private void slabWithCutting(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        stoneCutting(recipeOutput, item, material, 2);
        slab(recipeOutput, item, material);
    }

    private void slab(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private void door(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        doorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    private void trapdoor(RecipeOutput recipeOutput, ItemLike item, ItemLike material) {
        trapdoorBuilder(item, Ingredient.of(material))
                .group(ItemUtils.getName(material))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected void oneToOne(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        shapeless(category, result, count)
                .requires(material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    protected void horizontalRecipe(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        shaped(category, result, count)
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private void twoBytwo(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        shaped(category, result, count)
                .pattern("##")
                .pattern("##")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private void oneBytwo(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int count) {
        shaped(category, result, count)
                .pattern("#")
                .pattern("#")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));
    }

    private void grateWithCutting(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getRecipeName(material, result));

        stoneCutting(recipeOutput, result, material, 4);
    }

    private void bulb(RecipeOutput output, ItemLike block, ItemLike result) {
        shaped(RecipeCategory.REDSTONE, result, 1)
                .pattern(" S ")
                .pattern("SBS")
                .pattern(" R ")
                .define('R', Items.REDSTONE)
                .define('S', block)
                .define('B', Items.BLAZE_ROD)
                .unlockedBy(getHasName(block), has(block))
                .save(output);
    }

    private void storageItemRecipes(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike storageItem) {
        // From item to storageItem
        shaped(category, storageItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, storageItem));

        // From storageItem to item
        shapeless(category, item, 9)
                .requires(storageItem)
                .unlockedBy(getHasName(storageItem), has(storageItem))
                .save(recipeOutput, getRecipeName(storageItem, item));
    }

    protected void waxable(RecipeOutput recipeOutput, ItemLike item, ItemLike result) {
        shapeless(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .requires(item)
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected void sword(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        shaped(RecipeCategory.COMBAT, tool)
            .pattern("#")
            .pattern("#")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected void spear(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        // Diagonal: one material at top-right + two sticks (mirrors vanilla copper_spear).
        shaped(RecipeCategory.COMBAT, tool)
            .pattern("  #")
            .pattern(" S ")
            .pattern("S  ")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected void pickaxe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        shaped(RecipeCategory.TOOLS, tool)
            .pattern("###")
            .pattern(" S ")
            .pattern(" S ")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }
    protected void axe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        shaped(RecipeCategory.TOOLS, tool)
            .pattern("##")
            .pattern("#S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected void hoe(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        shaped(RecipeCategory.TOOLS, tool)
            .pattern("##")
            .pattern(" S")
            .pattern(" S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected void shovel(RecipeOutput recipeOutput, ItemLike tool, ItemLike material) {
        shaped(RecipeCategory.TOOLS, tool)
            .pattern("#")
            .pattern("S")
            .pattern("S")
            .define('#', material)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(material), has(material))
            .save(recipeOutput);
    }

    protected void helmet(RecipeOutput recipeOutput, ItemLike helmet, ItemLike material) {
        shaped(RecipeCategory.COMBAT, helmet)
                .pattern("###")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected void chestplate(RecipeOutput recipeOutput, ItemLike chestplate, ItemLike material) {
        shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected void leggings(RecipeOutput recipeOutput, ItemLike leggings, ItemLike material) {
        shaped(RecipeCategory.COMBAT, leggings)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected void boots(RecipeOutput recipeOutput, ItemLike boots, ItemLike material) {
        shaped(RecipeCategory.COMBAT, boots)
                .pattern("# #")
                .pattern("# #")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected void oreSmeltingRecipes(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        smelting(recipeOutput, RecipeCategory.MISC, material, result, experience, 200);
        blasting(recipeOutput, RecipeCategory.MISC, material, result, experience, 100); // Smoking is twice as fast
    }

    protected void foodCookingRecipes(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        smelting(recipeOutput, RecipeCategory.FOOD, material, result, experience, 200);
        smoking(recipeOutput, RecipeCategory.FOOD, material, result, experience, 100); // Smoking is twice as fast
        campfireCooking(recipeOutput, RecipeCategory.FOOD, material, result, experience, 600); // Campfire cooking takes three times longer
    }

    // 26.1: SimpleCookingRecipeBuilder.generic(... RecipeSerializer, Factory) was removed in favour
    // of dedicated smelting()/blasting()/smoking()/campfireCooking() builders. smelting/blasting also
    // take a CookingBookCategory (the recipe-book grouping) derived here from the RecipeCategory.
    private static CookingBookCategory cookingBookCategory(RecipeCategory category) {
        return category == RecipeCategory.FOOD ? CookingBookCategory.FOOD : CookingBookCategory.MISC;
    }

    protected void smelting(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(material), category, cookingBookCategory(category), result, experience, cookingTime)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_smelting");
    }

    protected void blasting(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(material), category, cookingBookCategory(category), result, experience, cookingTime)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_blasting");
    }

    protected void smoking(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .smoking(Ingredient.of(material), category, result, experience, cookingTime)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_smoking");
    }

    protected void stoneCutting(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike result, @NotNull ItemLike material, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_" + ItemUtils.getName(material) + "_stonecutting");
    }

    protected void campfireCooking(@NotNull RecipeOutput recipeOutput, RecipeCategory category, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .campfireCooking(Ingredient.of(material), category, result, experience, cookingTime)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ItemUtils.getNameWithNamespace(result) + "_from_campfire_cooking");
    }

    protected String getRecipeName(ItemLike item, ItemLike result) {
        return TheSilverAge.MOD_ID + ":" + getConversionRecipeName(result, item);
    }
}
