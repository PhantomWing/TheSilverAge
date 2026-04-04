package com.phantomwing.thesilverage.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.Configuration;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SilverfishDropsModifier extends LootModifier {
    public static final Supplier<Codec<SilverfishDropsModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst ->
                    codecStart(inst).and(inst.group(
                            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(e -> e.item),
                            Codec.INT.fieldOf("minAmount").forGetter(e -> e.minAmount),
                            Codec.INT.fieldOf("maxAmount").forGetter(e -> e.maxAmount)
                    )).apply(inst, SilverfishDropsModifier::new)
            )
    );

    private final Item item;
    private final int minAmount;
    private final int maxAmount;

    public SilverfishDropsModifier(LootItemCondition[] conditionsIn, Item item, int minAmount, int maxAmount) {
        super(conditionsIn);

        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (!Configuration.SILVERFISH_DROP_SILVER.get()) {
            return generatedLoot;
        }

        for (LootItemCondition condition: this.conditions) {
            if (!condition.test(context))
            {
                return generatedLoot;
            }
        }

        int count = UniformGenerator.between(minAmount, maxAmount).getInt(context);
        if (count > 0) {
            generatedLoot.add(new ItemStack(this.item, count));
        }

        return generatedLoot;
    }

    // Return our codec here.
    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
