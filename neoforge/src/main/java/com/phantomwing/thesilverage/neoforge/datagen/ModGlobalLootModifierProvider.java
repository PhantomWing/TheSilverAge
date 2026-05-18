package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import com.phantomwing.thesilverage.neoforge.loot.AddItemModifier;
import com.phantomwing.thesilverage.neoforge.loot.ReplaceItemModifier;
import com.phantomwing.thesilverage.neoforge.loot.SilverfishDropsModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generates the Silver Global Loot Modifier JSON.
 *
 * <p>This provider no longer hard-codes the ~64 modifier instances: it iterates
 * the common {@link SilverLootSpec} (single source of truth, shared with the
 * Fabric loot mixin) and emits one GLM per {@link SilverLootSpec.Entry}. The
 * generated JSON — file names, condition list ({@code neoforge:loot_table_id} +
 * {@code minecraft:random_chance}), modifier type and parameters — is
 * byte-identical to the previously hand-written provider's output, so the
 * committed {@code data/thesilverage/loot_modifiers/*.json} regenerates with a
 * zero diff.</p>
 */
public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    @Override
    protected void start() {
        for (SilverLootSpec.Entry entry : SilverLootSpec.entries()) {
            LootItemCondition[] conditions = conditions(entry);
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

    /**
     * Builds the GLM condition list exactly as the original
     * {@code conditions(...)} helpers did: {@code [loot_table_id,
     * random_chance(chance)]}. The spec's {@code targetLootTable} is already the
     * default-namespaced {@code minecraft:<path>} form the original
     * {@code isVanillaLootTable(key)} produced.
     */
    private LootItemCondition[] conditions(SilverLootSpec.Entry entry) {
        return new LootItemCondition[]{
                new LootTableIdCondition.Builder(entry.targetLootTable()).build(),
                LootItemRandomChanceCondition.randomChance(entry.chance()).build()
        };
    }
}
