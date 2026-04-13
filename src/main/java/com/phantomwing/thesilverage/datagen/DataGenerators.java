package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.create.ModDeployingRecipeGen;
import com.phantomwing.thesilverage.compat.create.ModFillingRecipeGen;
import com.phantomwing.thesilverage.compat.create.ModPressingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
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

        generator.addProvider(event.includeServer(), ModLootTableProvider.create(output));

        generator.addProvider(
                event.includeServer(),
                new ForgeAdvancementProvider(
                        output,
                        lookupProvider,
                        existingFileHelper,
                        List.of(new ModAdvancementProvider())
                )
        );

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));

        ModBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(output));

        generator.addProvider(event.includeServer(), new ModDatapackProvider(output, lookupProvider));

        // Create mod compat recipe generators
        generator.addProvider(event.includeServer(), new ModDeployingRecipeGen(output));
        generator.addProvider(event.includeServer(), new ModFillingRecipeGen(output));
        generator.addProvider(event.includeServer(), new ModPressingRecipeGen(output));
    }
}
