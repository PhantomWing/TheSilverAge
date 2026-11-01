package com.phantomwing.thesilverage.loot;

import com.phantomwing.thesilverage.utils.ItemUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

/**
 * Loader-agnostic implementations of the three Silver loot operations.
 *
 * <p>The caller handles the config gate and loot-table-id / random-chance
 * condition checks; the methods here implement only the post-roll mutation.</p>
 */
public final class SilverLootAlgorithms {
    private SilverLootAlgorithms() {
    }

    /** Rolls a count in {@code [min, max]} and appends that many of {@code item}, stack-splitting as needed. */
    public static void applyAddItem(ObjectArrayList<ItemStack> generatedLoot, LootContext context,
                                    Item item, int min, int max) {
        int count = UniformGenerator.between(min, max).getInt(context);
        if (count > 0) {
            ItemStack addedStack = new ItemStack(item, count);

            if (addedStack.getCount() < addedStack.getMaxStackSize()) {
                generatedLoot.add(addedStack);
            } else {
                int i = addedStack.getCount();

                while (i > 0) {
                    ItemStack subStack = addedStack.copy();
                    subStack.setCount(Math.min(addedStack.getMaxStackSize(), i));
                    i -= subStack.getCount();
                    generatedLoot.add(subStack);
                }
            }
        }
    }

    /**
     * Replaces up to {@code [minStacks, maxStacks]} stacks ({@code maxStacks <= 0} ⇒ all) whose item
     * is in {@code removedItems} with {@code item}, transmuting via {@link ItemUtils#tryTransmuteStack}.
     */
    public static void applyReplaceItem(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext,
                                        Item item, List<Item> removedItems, int minStacks, int maxStacks) {
        ObjectArrayList<ItemStack> lootArray = new ObjectArrayList<>();
        int numberOfStacksToAdd = maxStacks > 0
                ? UniformGenerator.between(minStacks, maxStacks).getInt(lootContext)
                : Integer.MAX_VALUE;
        final int[] stacksToAdd = {numberOfStacksToAdd};

        if (numberOfStacksToAdd > 0) {
            generatedLoot.forEach((stack) -> {
                if (removedItems.stream().anyMatch(stack::is) && stacksToAdd[0] > 0) {
                    try {
                        ItemStack toAdd = ItemUtils.tryTransmuteStack(stack, item);

                        generatedLoot.remove(stack);
                        lootArray.add(toAdd);
                    } catch (Exception ignored) {
                        // On failure, keep the original item in the loot.
                    }

                    stacksToAdd[0] = stacksToAdd[0] - 1;
                }
            });
        }

        if (!lootArray.isEmpty()) {
            generatedLoot.addAll(lootArray);
        }
    }

    /** Rolls a count in {@code [min, max]} and appends a single stack of that many {@code item}. */
    public static void applySilverfishDrops(ObjectArrayList<ItemStack> generatedLoot, LootContext context,
                                            Item item, int min, int max) {
        int count = UniformGenerator.between(min, max).getInt(context);
        if (count > 0) {
            generatedLoot.add(new ItemStack(item, count));
        }
    }
}
