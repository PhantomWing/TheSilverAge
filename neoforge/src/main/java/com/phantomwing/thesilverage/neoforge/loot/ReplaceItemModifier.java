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
        if (!CommonConfig.generateStructureLoot()) {
            return generatedLoot;
        }

        // Delegate to the shared, loader-agnostic algorithm (single source of truth).
        SilverLootAlgorithms.applyReplaceItem(generatedLoot, lootContext, this.item, this.removedItems,
                this.minStacks, this.maxStacks);

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}