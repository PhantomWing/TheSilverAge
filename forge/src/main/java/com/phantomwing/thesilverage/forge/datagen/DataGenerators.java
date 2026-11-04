package com.phantomwing.thesilverage.forge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.forge.compat.create.ModDeployingRecipeGen;
import com.phantomwing.thesilverage.forge.compat.create.ModChannelingRecipeGen;
import com.phantomwing.thesilverage.forge.compat.create.ModFillingRecipeGen;
import com.phantomwing.thesilverage.forge.compat.create.ModPressingRecipeGen;
import com.phantomwing.thesilverage.forge.compat.create.ModWashingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TheSilverAge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output));

        generator.addProvider(event.includeServer(), new LootTableProvider(
            output,
            Set.of(),
            List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)))
        );

        generator.addProvider(
                // Tell generator to run only when server data are generating
                event.includeServer(),
                new ForgeAdvancementProvider(
                        output,
                        event.getLookupProvider(),
                        event.getExistingFileHelper(),
                        // Sub providers which generate the advancements
                        List.of(new ModAdvancementProvider())
                )
        );
        generator.addProvider(event.includeServer(), new AdvancementConditionsProvider(output));


        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));

        ModBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(output));

        ModDatapackProvider datapackProvider = generator.addProvider(event.includeServer(), new ModDatapackProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(output, datapackProvider.getRegistryProvider(), existingFileHelper));

        // Create mod compat: generate create:deploying recipes for silver waxing/deoxidising
        // so JEI displays them in the Deployer category.
        generator.addProvider(event.includeServer(), new ModDeployingRecipeGen(output));

        // Create mod compat: generate create:filling (spout) recipes that mirror the
        // Create: Oxidized addon, advancing silver blocks through their weathering chain
        // when splashed with water via a Spout.
        generator.addProvider(event.includeServer(), new ModFillingRecipeGen(output));

        // Create: Aquatic Ambitions compat: its fan channeling weathers silver a stage, as it does copper.
        generator.addProvider(event.includeServer(), new ModChannelingRecipeGen(output));

        // Create mod compat: generate create:pressing recipe for silver_ingot -> silver_sheet,
        // matching the pattern Create uses for its own iron/gold/copper/brass sheets.
        generator.addProvider(event.includeServer(), new ModPressingRecipeGen(output));

        // Create mod compat: create:splashing of Create's crushed raw silver into Silver Nuggets.
        generator.addProvider(event.includeServer(), new ModWashingRecipeGen(output));

        // MUST be registered LAST. Forge's DataGenerator.run() executes
        // providers sequentially in registration order (each future .join()-ed
        // before the next), so by the time this runs every conditional recipe /
        // advancement / Create-compat JSON above has been written to disk. It
        // post-processes the shared generated tree, adding a translated
        // `fabric:load_conditions` block beside every Forge-only
        // `conditions` block so the single shared data gates
        // identically on both loaders. See FabricConditionsProvider.
        generator.addProvider(event.includeServer(), new FabricConditionsProvider(output));
    }
}
