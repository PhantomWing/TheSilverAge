package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
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
     * Lodestone and Glistering Melon Slice are left out: NeoForge ships its own copies of those two, and in a dev run
     * the mod's generated data is a mod of its own that nothing orders after NeoForge's.
     * {@link #silverDataOutranksNeoForge} checks the ordering that decides them in a real install.
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
     * The mod loads after NeoForge, so its Lodestone and Glistering Melon Slice overrides outrank NeoForge's own
     * copies of those recipes. Mods' data is layered in load order, which without the mods.toml's ordering
     * followed a hash of the mod ids.
     */
    public static void silverDataOutranksNeoForge(GameTestHelper helper) {
        List<String> loadOrder = List.copyOf(Platform.getModIds());
        int neoForge = loadOrder.indexOf("neoforge");
        if (neoForge >= 0 && loadOrder.indexOf(TheSilverAge.MOD_ID) < neoForge) {
            fail(helper, "The Silver Age loads before NeoForge, so NeoForge's Lodestone and Glistering Melon Slice recipes replace its overrides");
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
