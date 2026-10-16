package com.phantomwing.thesilverage.neoforge.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.platform.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
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

    // Replaces all stacks of the item, keeping stack size.
    public ReplaceItemModifier(LootItemCondition[] conditions, ItemLike itemToAdd, List<Item> itemToReplace) {
        this(conditions, 0, itemToAdd, itemToReplace, 0, 0);
    }

    // Replaces a random number of stacks of the item, keeping stack size.
    public ReplaceItemModifier(LootItemCondition[] conditions, ItemLike itemToAdd, List<Item> itemToReplace, int maxStacks) {
        this(conditions, 0, itemToAdd, itemToReplace, maxStacks, maxStacks);
    }

    // Datagen entry point; uses the default GLM priority 0.
    public ReplaceItemModifier(LootItemCondition[] conditions, ItemLike itemToAdd, List<Item> itemToReplace, int minStacks, int maxStacks) {
        this(conditions, 0, itemToAdd, itemToReplace, minStacks, maxStacks);
    }

    // The `priority` 2nd arg is required by LootModifier's codecStart.
    public ReplaceItemModifier(LootItemCondition[] conditions, int priority, ItemLike itemToAdd, List<Item> itemToReplace, int minStacks, int maxStacks) {
        super(conditions, priority);

        this.removedItems = itemToReplace;
        this.item = itemToAdd.asItem();
        this.minStacks = minStacks;
        this.maxStacks = maxStacks;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext lootContext) {
        if (!CommonConfig.generateStructureLoot()) {
            return generatedLoot;
        }

        SilverLootAlgorithms.applyReplaceItem(generatedLoot, lootContext, this.item, this.removedItems,
                this.minStacks, this.maxStacks);

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}