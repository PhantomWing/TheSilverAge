package com.phantomwing.thesilverage.fabric;

import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.fabric.condition.ConfigBooleanResourceCondition;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import com.phantomwing.thesilverage.fabric.mixin.PoiTypesAccessor;
import com.phantomwing.thesilverage.fabric.villager.ModVillagerTrades;
import com.phantomwing.thesilverage.fabric.world.ModWorldGen;
import com.phantomwing.thesilverage.firework.ModFireworks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.storage.loot.LootTable;

/** Fabric entrypoint for The Silver Age. */
public final class TheSilverAgeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // MUST be first: config is read very early (datapack load) by the loot mixin and resource condition.
        TheSilverAgeFabricConfig.register();

        TheSilverAgeCommon.init();

        ModFireworks.register();

        ModVillagerTrades.register();

        // Attaches silver-ore placed features to biomes (Fabric ignores NeoForge's add_features JSON).
        ModWorldGen.register();

        registerLightningRods();

        // Parity twin of the NeoForge `thesilverage:config_boolean` recipe
        // condition. The shared generated data carries BOTH dialects in each
        // conditional file (NeoForge `neoforge:conditions` +
        // Fabric `fabric:load_conditions`, the latter emitted by the NeoForge
        // datagen post-processor); this registers the runtime handler so Fabric
        // can evaluate the translated block. `fabric:all_mods_loaded` (the
        // create-gated recipes) is built into fabric-api and needs no
        // registration here.
        ResourceConditions.register(ConfigBooleanResourceCondition.TYPE);

        // Create Fly, the Fabric port of Create, needs nothing here: its recipes are
        // data, which NeoForge's datagen writes in its format into fabric/src/generated
        // (CreateFlyRecipeProvider), gated on fabric:all_mods_loaded create.

        // Stamp each loaded LootTable with its registry id (vanilla tables carry none); read by LootTableMixin.
        LootTableEvents.ALL_LOADED.register((resourceManager, lootRegistry) ->
                lootRegistry.entrySet().forEach(e -> {
                    LootTable table = e.getValue();
                    if (table instanceof SilverLootTableId holder) {
                        holder.thesilverage$setLootTableId(e.getKey().identifier());
                    }
                }));
    }

    /**
     * Lightning finds a rod by its point of interest, which vanilla gives its own copper rods alone. NeoForge adds
     * the Silver Lightning Rods through its {@code ExtendPoiTypesEvent}.
     */
    private static void registerLightningRods() {
        Holder<PoiType> lightningRod = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getOrThrow(PoiTypes.LIGHTNING_ROD);
        ModBlocks.silverLightningRods().forEach(rod -> rod.getStateDefinition().getPossibleStates()
                .forEach(state -> PoiTypesAccessor.thesilverage$typeByState().put(state, lightningRod)));
    }
}
