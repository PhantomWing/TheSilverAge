package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

// GatherDataEvent fires on the MOD bus. NeoForge's @EventBusSubscriber now
// defaults to the GAME bus, so the bus must be set explicitly (1.21.2+ change —
// the old default registered this on the wrong bus and crashed mod construction).
@EventBusSubscriber(modid = TheSilverAge.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        
        generator.addProvider(event.includeServer(), new ModRecipeProvider.Runner(output, lookupProvider));

        generator.addProvider(event.includeServer(), new LootTableProvider(
            output,
            Set.of(),
            List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
            lookupProvider)
        );

        generator.addProvider(
                // Tell generator to run only when server data are generating
                event.includeServer(),
                new AdvancementProvider(
                        output,
                        event.getLookupProvider(),
                        event.getExistingFileHelper(),
                        // Sub providers which generate the advancements
                        List.of(new ModAdvancementProvider())
                )
        );

        generator.addProvider(event.includeServer(), new ModDataMapProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ModSpriteSourceProvider(output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));

        ModBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(output, lookupProvider));

        generator.addProvider(event.includeServer(), new ModDatapackProvider(output, lookupProvider));

        // MUST be registered LAST. NeoForge's DataGenerator.run() executes
        // providers sequentially in registration order (each future .join()-ed
        // before the next), so by the time this runs every conditional recipe /
        // advancement JSON above has been written to disk. It
        // post-processes the shared generated tree, adding a translated
        // `fabric:load_conditions` block beside every NeoForge-only
        // `neoforge:conditions` block so the single shared data gates
        // identically on both loaders. See FabricConditionsProvider.
        generator.addProvider(event.includeServer(), new FabricConditionsProvider(output));
    }
}
