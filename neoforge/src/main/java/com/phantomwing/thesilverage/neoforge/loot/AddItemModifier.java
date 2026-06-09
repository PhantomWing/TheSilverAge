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
        this(conditionsIn, 0, item, count, count);
    }

    /** Code-construction constructor (datagen) — uses the default GLM priority 0. */
    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int minAmount, int maxAmount) {
        this(conditionsIn, 0, item, minAmount, maxAmount);
    }

    // 26.1: NeoForge added a `priority` int to LootModifier (codecStart now yields
    // (conditions[], priority)); the codec's apply() needs the priority as the 2nd parameter.
    public AddItemModifier(LootItemCondition[] conditionsIn, int priority, Item item, int minAmount, int maxAmount) {
        super(conditionsIn, priority);

        this.item = item;
        this.min = minAmount;
        this.max = maxAmount;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        // Check if the modifier is enabled in the config. If not, return the generated loot as is.
        if (!CommonConfig.generateStructureLoot()) {
            return generatedLoot;
        }

        // Delegate to the shared, loader-agnostic algorithm (single source of truth).
        SilverLootAlgorithms.applyAddItem(generatedLoot, context, this.item, this.min, this.max);

        return generatedLoot;
    }

    // Return our codec here.
    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
