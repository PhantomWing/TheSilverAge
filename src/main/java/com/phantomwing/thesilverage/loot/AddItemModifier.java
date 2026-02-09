package com.phantomwing.thesilverage.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.Configuration;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddItemModifier extends LootModifier {
    public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item),
                    Codec.INT.fieldOf("min").forGetter(e -> e.min),
                    Codec.INT.fieldOf("max").forGetter(e -> e.max)
                    )
            ).apply(inst, AddItemModifier::new)
    );

    private final Item item;
    private final int min;
    private final int max;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int count) {
        super(conditionsIn);

        this.item = item;
        this.min = count;
        this.max = count;
    }

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int minAmount, int maxAmount) {
        super(conditionsIn);

        this.item = item;
        this.min = minAmount;
        this.max = maxAmount;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        // Check if the modifier is enabled in the config. If not, return the generated loot as is.
        if (!Configuration.GENERATE_STRUCTURE_LOOT.get()) {
            return generatedLoot;
        }

        int count = UniformGenerator.between(min, max).getInt(context);
        if (count > 0) {
            ItemStack addedStack = new ItemStack(this.item, count);

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

        return generatedLoot;
    }

    // Return our codec here.
    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
