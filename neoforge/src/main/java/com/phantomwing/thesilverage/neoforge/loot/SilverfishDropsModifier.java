package com.phantomwing.thesilverage.neoforge.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.platform.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class SilverfishDropsModifier extends LootModifier {
    public static final MapCodec<SilverfishDropsModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item),
                    Codec.INT.fieldOf("minAmount").forGetter(e -> e.minAmount),
                    Codec.INT.fieldOf("maxAmount").forGetter(e -> e.maxAmount)
                    )
            ).apply(inst, SilverfishDropsModifier::new)
    );

    private final Item item;
    private final int minAmount;
    private final int maxAmount;

    // Datagen entry point; uses the default GLM priority 0.
    public SilverfishDropsModifier(LootItemCondition[] conditionsIn, Item item, int minAmount, int maxAmount) {
        this(conditionsIn, 0, item, minAmount, maxAmount);
    }

    // The `priority` 2nd arg is required by LootModifier's codecStart.
    public SilverfishDropsModifier(LootItemCondition[] conditionsIn, int priority, Item item, int minAmount, int maxAmount) {
        super(conditionsIn, priority);

        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (!CommonConfig.silverfishDropSilver()) {
            return generatedLoot;
        }

        for (LootItemCondition condition: this.conditions) {
            if (!condition.test(context))
            {
                return generatedLoot;
            }
        }

        SilverLootAlgorithms.applySilverfishDrops(generatedLoot, context, this.item, this.minAmount, this.maxAmount);

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
