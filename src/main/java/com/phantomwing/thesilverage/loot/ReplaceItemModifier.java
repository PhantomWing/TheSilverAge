package com.phantomwing.thesilverage.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.utils.ItemUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class ReplaceItemModifier extends LootModifier
{
    public static final Supplier<MapCodec<ReplaceItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(
                            inst.group(
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter((m) -> m.item),
                                    BuiltInRegistries.ITEM.byNameCodec().listOf().fieldOf("removed_item").forGetter((m) -> m.removedItems),
                                    Codec.INT.fieldOf("in_stacks").forGetter((m) -> m.minStacks),
                                    Codec.INT.fieldOf("max_stacks").forGetter((m) -> m.maxStacks)
                            )
                    )
                    .apply(inst, ReplaceItemModifier::new)));

    private final Item item;
    private final List<Item> removedItems;
    private final int minStacks;
    private final int maxStacks;

    /**
     * This loot modifier replaces all stacks of the specified item with another item (keeping the stack size).
     */
    public ReplaceItemModifier(LootItemCondition[] conditions, ItemLike itemToAdd, List<Item> itemToReplace) {
        super(conditions);

        this.removedItems = itemToReplace;
        this.item = itemToAdd.asItem();
        this.minStacks = 0;
        this.maxStacks = 0;
    }

    /**
     * This loot modifier replaces a random number of stacks of the specified item with another item (keeping the stack size).
     */
    public ReplaceItemModifier(LootItemCondition[] conditions, ItemLike itemToAdd, List<Item> itemToReplace, int maxStacks) {
        super(conditions);

        this.removedItems = itemToReplace;
        this.item = itemToAdd.asItem();
        this.minStacks = maxStacks;
        this.maxStacks = maxStacks;
    }

    /**
     * This loot modifier replaces a number of stacks of the specified item with another item (keeping the stack size).
     */
    public ReplaceItemModifier(LootItemCondition[] conditions, ItemLike itemToAdd, List<Item> itemToReplace, int minStacks, int maxStacks) {
        super(conditions);

        this.removedItems = itemToReplace;
        this.item = itemToAdd.asItem();
        this.minStacks = minStacks;
        this.maxStacks = maxStacks;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext lootContext) {
        // Check if the modifier is enabled in the config. If not, return the generated loot as is.
        if (!Configuration.GENERATE_STRUCTURE_LOOT.get()) {
            return generatedLoot;
        }

        // Determine how many stacks to replace. If no max amount is set, replace all.
        // If a min and max amount is set, replace a random number of stacks between the two.
        ObjectArrayList<ItemStack> lootArray = new ObjectArrayList<>();
        int numberOfStacksToAdd = this.maxStacks > 0 ? UniformGenerator.between(this.minStacks, this.maxStacks).getInt(lootContext) : Integer.MAX_VALUE;
        final int[] stacksToAdd = {numberOfStacksToAdd};

        // Check if there are any items to replace. If not, return the generated loot as is.
        // Keep the replaced item's count, but cap it at the max stack size of the added item.
        if (numberOfStacksToAdd > 0) {
            generatedLoot.forEach((item) -> {
                if (removedItems.stream().anyMatch(item::is) && stacksToAdd[0] > 0) {
                    try {
                        ItemStack toAdd = ItemUtils.tryTransmuteStack(item, this.item);

                        generatedLoot.remove(item);
                        lootArray.add(toAdd);
                    } catch (Exception ignored) {
                        // If something goes wrong with the item replacement (e.g. invalid item), just skip it and keep the original item in the loot.
                    }

                    stacksToAdd[0] = stacksToAdd[0] - 1;
                }
            });
        }

        // Add the new items to the generated loot.
        if (!lootArray.isEmpty()) {
            generatedLoot.addAll(lootArray);
        }

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}