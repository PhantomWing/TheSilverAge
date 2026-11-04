package com.phantomwing.thesilverage.forge.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import com.phantomwing.thesilverage.platform.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddItemModifier extends LootModifier {
    public static final Codec<AddItemModifier> CODEC = RecordCodecBuilder.create(inst ->
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
        if (!CommonConfig.generateStructureLoot() || !SilverLootSpec.itemEnabled(this.item)) {
            return generatedLoot;
        }

        // Delegate to the shared, loader-agnostic algorithm (single source of truth).
        SilverLootAlgorithms.applyAddItem(generatedLoot, context, this.item, this.min, this.max);

        return generatedLoot;
    }

    // Return our codec here.
    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
