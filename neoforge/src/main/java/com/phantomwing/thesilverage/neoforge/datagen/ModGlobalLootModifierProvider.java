package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import com.phantomwing.thesilverage.neoforge.loot.AddItemModifier;
import com.phantomwing.thesilverage.neoforge.loot.ReplaceItemModifier;
import com.phantomwing.thesilverage.neoforge.loot.SilverfishDropsModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/** Emits one Global Loot Modifier per {@link SilverLootSpec.Entry}. */
public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    @Override
    protected void start() {
        for (SilverLootSpec.Entry entry : SilverLootSpec.entries()) {
            Optional<Holder<LootItemCondition>> conditions = conditions(entry);
            Item item = entry.item().get();

            switch (entry.op()) {
                case SILVERFISH -> add(entry.id(), new SilverfishDropsModifier(
                        conditions, item, entry.min(), entry.max()));
                case REPLACE -> add(entry.id(), new ReplaceItemModifier(
                        conditions, item,
                        entry.removedItems().stream().map(java.util.function.Supplier::get).toList(),
                        entry.min(), entry.max()));
                case ADD -> add(entry.id(), new AddItemModifier(
                        conditions, item, entry.min(), entry.max()));
            }
        }
    }

    // 26.3 takes a single optional condition, so the pair is folded into an all-of.
    private Optional<Holder<LootItemCondition>> conditions(SilverLootSpec.Entry entry) {
        return Optional.of(Holder.direct(AllOfCondition.allOf(
                new LootTableIdCondition.Builder(entry.targetLootTable()),
                LootItemRandomChanceCondition.randomChance(entry.chance())
        ).build()));
    }
}
