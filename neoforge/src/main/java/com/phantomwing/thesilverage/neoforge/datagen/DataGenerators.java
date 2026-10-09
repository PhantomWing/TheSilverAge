package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
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
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getWorldLookupProvider();

        // World layer: worldgen features, placements, trim material and biome modifiers.
        event.createWorldRegistryObjects(ModDatapackProvider.BUILDER, Set.of(TheSilverAge.MOD_ID));

        // 26.3 moved recipes, loot tables and advancements out of standalone providers and
        // into reloadable datapack registries. Recipes need two registries (they also emit
        // their unlock advancements), hence the multi-registry bootstrap. Only the listed
        // namespaces are written, and the vanilla recipes and advancements the mod overrides are
        // minecraft's.
        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(ModRecipeProvider.create())
                .add(Registries.LOOT_TABLE, new LootTableProvider(
                        Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(
                                ModBlockLootTableProvider::new, LootContextParamSets.BLOCK))))
                .add(Registries.ADVANCEMENT, new AdvancementProvider(
                        List.of(ModAdvancementProvider::new))),
                Set.of(TheSilverAge.MOD_ID, "minecraft"));

        event.addProvider(new ModDataMapProvider(output, lookupProvider));

        event.addProvider(new ModModelProvider(output));

        event.addProvider(new ModBlockTagsProvider(output, lookupProvider));
        event.addProvider(new ModItemTagsProvider(output, lookupProvider));
        event.addProvider(new ModBiomeTagsProvider(output, lookupProvider));
        event.addProvider(new ModEntityTypeTagsProvider(output, lookupProvider));
        // Recipes live on the reloadable layer in 26.3, so their tags need that lookup.
        event.addProvider(new ModRecipeTagsProvider(output, event.getReloadableLookupProvider()));

        event.addProvider(new ModGlobalLootModifierProvider(output, lookupProvider));

        // Must run BEFORE FabricConditionsProvider so its neoforge:conditions gate gets mirrored.
        event.addProvider(new ModVillagerTradeProvider(output, lookupProvider));

        // MUST be registered LAST: mirrors every neoforge:conditions into fabric:load_conditions.
        event.addProvider(new FabricConditionsProvider(output));
    }
}
