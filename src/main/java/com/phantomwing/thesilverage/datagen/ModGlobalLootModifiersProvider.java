package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.loot.SilverfishDropSilverModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheSilverAge.MOD_ID);
    }

    @Override
    protected void start() {
        // Entity loot tables
        add("silver_nugget_from_silverfish", new SilverfishDropSilverModifier(
                new LootItemCondition[] {
                        defaultLootTable("entities/silverfish"),
                },
                ModItems.SILVER_NUGGET.get(),
                0,
                2
        ));
    }

    private LootItemCondition defaultLootTable(String name) {
        return new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace(name)).build();
    }
}
