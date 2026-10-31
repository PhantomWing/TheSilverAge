package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import dev.architectury.platform.Platform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;

import java.util.Arrays;
import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for the vanilla recipes silver takes part in. Listed in {@link GameTests}. */
public final class SilverRecipesGameTest {
    private SilverRecipesGameTest() {
    }

    /**
     * With the default config every vanilla recipe override applies, the silver recipe taking the vanilla one's place.
     * Lodestone and Glistering Melon Slice, which NeoForge ships copies of too, are {@link #silverDataOutranksNeoForge}'s.
     */
    public static void recipeOverridesApplyByDefault(GameTestHelper helper) {
        Item silver = ModItems.SILVER_INGOT.get();
        Item torch = Items.REDSTONE_TORCH;

        expect(helper, Items.REPEATER, CraftingInput.of(3, 2, stacks(
                torch, Items.REDSTONE, torch,
                silver, silver, silver)));
        expect(helper, Items.COMPARATOR, CraftingInput.of(3, 3, stacks(
                Items.AIR, torch, Items.AIR,
                torch, Items.QUARTZ, torch,
                silver, silver, silver)));
        expect(helper, Items.BREWING_STAND, CraftingInput.of(3, 2, stacks(
                Items.AIR, Items.BLAZE_ROD, Items.AIR,
                silver, silver, silver)));
        helper.succeed();
    }

    /**
     * Two rows of three Silver Ingots make Silver Bars, a square of four a Silver Trapdoor, and a square of Cut Silver
     * Silver Bricks. Where two recipes took one grid, it crafted whichever the recipe map listed first, and the other
     * couldn't be made at all: the trapdoor once took the bars' rows, and the bricks the trapdoor's square.
     */
    public static void silverBarsTrapdoorsAndBricksEachCraft(GameTestHelper helper) {
        Item silver = ModItems.SILVER_INGOT.get();
        Item cut = ModItems.CUT_SILVER.get();
        expect(helper, ModItems.SILVER_BARS.get(), CraftingInput.of(3, 2, stacks(
                silver, silver, silver,
                silver, silver, silver)));
        expect(helper, ModItems.SILVER_TRAPDOOR.get(), CraftingInput.of(2, 2, stacks(
                silver, silver,
                silver, silver)));
        expect(helper, ModItems.SILVER_BRICKS.get(), CraftingInput.of(2, 2, stacks(
                cut, cut,
                cut, cut)));
        helper.succeed();
    }

    /** A config_boolean condition naming no setting is false rather than an error that stops the datapack reload. */
    public static void configConditionNamingNoSettingIsFalse(GameTestHelper helper) {
        if (CommonConfig.booleanSetting("no_such_setting")) {
            fail(helper, "A config_boolean condition naming no setting is true");
        }
        helper.succeed();
    }

    /**
     * The Lodestone and Glistering Melon Slice overrides outrank NeoForge's own copies of those recipes. Mods' data is
     * layered in load order, which without the mods.toml's ordering followed a hash of the mod ids, and in a NeoForge
     * dev run common's jar was a mod of its own that nothing ordered, so the dev client crafted NeoForge's. An override
     * the config turns off leaves its fallback, and nothing to outrank.
     */
    public static void silverDataOutranksNeoForge(GameTestHelper helper) {
        List<String> loadOrder = List.copyOf(Platform.getModIds());
        int neoForge = loadOrder.indexOf("neoforge");
        if (neoForge >= 0 && loadOrder.indexOf(TheSilverAge.MOD_ID) < neoForge) {
            fail(helper, "The Silver Age loads before NeoForge, so NeoForge's Lodestone and Glistering Melon Slice recipes replace its overrides");
        }
        Item silver = ModItems.SILVER_INGOT.get();
        Item nugget = ModItems.SILVER_NUGGET.get();
        Item bricks = Items.CHISELED_STONE_BRICKS;
        Item melon = Items.MELON_SLICE;
        if (CommonConfig.recipeOverride(RecipeOverride.LODESTONE)) {
            expect(helper, Items.LODESTONE, CraftingInput.of(3, 3, stacks(
                    bricks, bricks, bricks,
                    bricks, silver, bricks,
                    bricks, bricks, bricks)));
        }
        if (CommonConfig.recipeOverride(RecipeOverride.GLISTERING_MELON)) {
            expect(helper, Items.GLISTERING_MELON_SLICE, CraftingInput.of(3, 3, stacks(
                    nugget, nugget, nugget,
                    nugget, melon, nugget,
                    nugget, nugget, nugget)));
        }
        helper.succeed();
    }

    private static void expect(GameTestHelper helper, Item result, CraftingInput input) {
        ItemStack crafted = TestCompat.craft(helper.getLevel(), input);
        if (!crafted.is(result)) {
            fail(helper, "Silver didn't craft " + BuiltInRegistries.ITEM.getKey(result) + "; got " + crafted);
        }
    }

    private static List<ItemStack> stacks(Item... items) {
        return Arrays.stream(items).map(ItemStack::new).toList();
    }
}
