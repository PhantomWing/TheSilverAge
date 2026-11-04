package com.phantomwing.thesilverage.forge.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import com.phantomwing.thesilverage.platform.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 * Swaps an item of a loot roll for silver: {@code removed_item} names what may be swapped, {@code in_stacks} and
 * {@code max_stacks} how many stacks of it ({@link SilverLootAlgorithms#applyReplaceItem}).
 */
public class ReplaceItemModifier extends LootModifier {
    public static final Supplier<Codec<ReplaceItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).and(
                            inst.group(
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter((m) -> m.item),
                                    ResourceLocation.CODEC.listOf().fieldOf("removed_item").forGetter((m) -> m.removedItems),
                                    Codec.INT.fieldOf("in_stacks").forGetter((m) -> m.minStacks),
                                    Codec.INT.fieldOf("max_stacks").forGetter((m) -> m.maxStacks)
                            )
                    )
                    .apply(inst, ReplaceItemModifier::new)));

    private final Item item;
    /**
     * By id, read as the loot is rolled: 1.20.1's loot modifiers take no conditions, so one naming an item only another
     * mod adds (Vanilla Backport's Copper Nautilus Armor) has to load without that mod too. It then never matches.
     */
    private final List<ResourceLocation> removedItems;
    private final int minStacks;
    private final int maxStacks;

    public ReplaceItemModifier(LootItemCondition[] conditions, Item item, List<ResourceLocation> removedItems, int minStacks, int maxStacks) {
        super(conditions);
        this.item = item;
        this.removedItems = removedItems;
        this.minStacks = minStacks;
        this.maxStacks = maxStacks;
    }

    /** Swaps up to {@code maxStacks} stacks of {@code itemsToReplace}, at least {@code minStacks} where there are that many. */
    public static ReplaceItemModifier of(LootItemCondition[] conditions, Item item, List<Item> itemsToReplace, int minStacks, int maxStacks) {
        return new ReplaceItemModifier(conditions, item,
                itemsToReplace.stream().map(BuiltInRegistries.ITEM::getKey).toList(), minStacks, maxStacks);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext lootContext) {
        // Check if the modifier is enabled in the config. If not, return the generated loot as is.
        if (!CommonConfig.generateStructureLoot() || !SilverLootSpec.itemEnabled(this.item)) {
            return generatedLoot;
        }

        List<Item> removed = removedItems.stream()
                .flatMap(id -> BuiltInRegistries.ITEM.getOptional(id).stream())
                .toList();
        if (removed.isEmpty()) {
            return generatedLoot;
        }

        // Delegate to the shared, loader-agnostic algorithm (single source of truth).
        SilverLootAlgorithms.applyReplaceItem(generatedLoot, lootContext, this.item, removed, this.minStacks, this.maxStacks);

        return generatedLoot;
    }

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
