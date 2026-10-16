package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public class DataGenerators {
    // Subscribe to the Client event: its full-client environment runs the server-side
    // providers (recipes/loot/tags) fine alongside the model providers.
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.addProvider(new ModRecipeProvider.Runner(output, lookupProvider));

        event.addProvider(new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));

        event.addProvider(new AdvancementProvider(output, lookupProvider, List.of(new ModAdvancementProvider())));

        event.addProvider(new ModDataMapProvider(output, lookupProvider));

        event.addProvider(new ModModelProvider(output));

        event.addProvider(new ModBlockTagsProvider(output, lookupProvider));
        event.addProvider(new ModItemTagsProvider(output, lookupProvider));
        event.addProvider(new ModBiomeTagsProvider(output, lookupProvider));
        event.addProvider(new ModEntityTypeTagsProvider(output, lookupProvider));

        event.addProvider(new ModGlobalLootModifierProvider(output, lookupProvider));

        event.addProvider(new ModDatapackProvider(output, lookupProvider));

        // Must run BEFORE FabricConditionsProvider so its neoforge:conditions gate gets mirrored.
        event.addProvider(new ModVillagerTradeProvider(output, lookupProvider));

        // MUST be registered LAST: mirrors every neoforge:conditions into fabric:load_conditions.
        event.addProvider(new FabricConditionsProvider(output));
    }
}
