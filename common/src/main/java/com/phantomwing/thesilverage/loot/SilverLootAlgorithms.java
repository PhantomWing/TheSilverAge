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
 * <p>This is the single source of truth for the loot algorithms. Both the
 * NeoForge Global Loot Modifiers ({@code AddItemModifier},
 * {@code ReplaceItemModifier}, {@code SilverfishDropsModifier}) and the Fabric
 * loot-roll mixin delegate here, so behaviour is byte-for-byte identical on both
 * loaders. The logic is intentionally pure: it mutates a
 * {@link ObjectArrayList}{@code <}{@link ItemStack}{@code >} (the same list type
 * the loot system rolls into) and uses {@link LootContext} only for the random
 * rolls (so the {@link UniformGenerator} draws match the original GLM exactly).</p>
 *
 * <p>The per-loader caller is responsible for the config gate and the
 * loot-table-id / random-chance condition checks (these mirror the GLM
 * {@code conditions(...)}: {@code loot_table_id} + {@code random_chance}); the
 * methods here implement only the post-roll mutation, exactly as the original
 * {@code doApply} bodies did.</p>
 */
public final class SilverLootAlgorithms {
    private SilverLootAlgorithms() {
    }

    /**
     * Mirrors {@code AddItemModifier.doApply}: rolls a count in
     * {@code [min, max]} and appends that many of {@code item}, splitting into
     * multiple stacks when the count exceeds the item's max stack size.
     *
     * <p>The {@code GENERATE_STRUCTURE_LOOT} config gate is applied by the
     * caller (it was the first statement of the original {@code doApply}).</p>
     */
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
     * Mirrors {@code ReplaceItemModifier.doApply}: replaces up to
     * {@code [minStacks, maxStacks]} stacks (when {@code maxStacks <= 0}, all of
     * them) whose item is in {@code removedItems} with {@code item}, keeping the
     * stack's count and carrying durability/enchantments via
     * {@link ItemUtils#tryTransmuteStack}. This is a post-roll mutation: matched
     * stacks are removed from {@code generatedLoot} and the transmuted stacks are
     * appended.
     *
     * <p>The {@code GENERATE_STRUCTURE_LOOT} config gate is applied by the
     * caller.</p>
     */
    public static void applyReplaceItem(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext,
                                        Item item, List<Item> removedItems, int minStacks, int maxStacks) {
        // Determine how many stacks to replace. If no max amount is set, replace all.
        // If a min and max amount is set, replace a random number of stacks between the two.
        ObjectArrayList<ItemStack> lootArray = new ObjectArrayList<>();
        int numberOfStacksToAdd = maxStacks > 0
                ? UniformGenerator.between(minStacks, maxStacks).getInt(lootContext)
                : Integer.MAX_VALUE;
        final int[] stacksToAdd = {numberOfStacksToAdd};

        // Check if there are any items to replace. If not, return the generated loot as is.
        // Keep the replaced item's count, but cap it at the max stack size of the added item.
        if (numberOfStacksToAdd > 0) {
            generatedLoot.forEach((stack) -> {
                if (removedItems.stream().anyMatch(stack::is) && stacksToAdd[0] > 0) {
                    try {
                        ItemStack toAdd = ItemUtils.tryTransmuteStack(stack, item);

                        generatedLoot.remove(stack);
                        lootArray.add(toAdd);
                    } catch (Exception ignored) {
                        // If something goes wrong with the item replacement (e.g. invalid item),
                        // just skip it and keep the original item in the loot.
                    }

                    stacksToAdd[0] = stacksToAdd[0] - 1;
                }
            });
        }

        // Add the new items to the generated loot.
        if (!lootArray.isEmpty()) {
            generatedLoot.addAll(lootArray);
        }
    }

    /**
     * Mirrors {@code SilverfishDropsModifier.doApply} (after the carried
     * conditions have been tested by the caller): rolls a count in
     * {@code [min, max]} and appends a single stack of that many {@code item}.
     *
     * <p>The {@code SILVERFISH_DROP_SILVER} config gate and the carried
     * loot-table-id / random-chance conditions are applied by the caller.</p>
     */
    public static void applySilverfishDrops(ObjectArrayList<ItemStack> generatedLoot, LootContext context,
                                            Item item, int min, int max) {
        int count = UniformGenerator.between(min, max).getInt(context);
        if (count > 0) {
            generatedLoot.add(new ItemStack(item, count));
        }
    }
}
