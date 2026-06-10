package com.phantomwing.thesilverage.fabric;

import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.fabric.condition.ConfigBooleanResourceCondition;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import com.phantomwing.thesilverage.fabric.villager.ModVillagerTrades;
import com.phantomwing.thesilverage.fabric.world.ModWorldGen;
import com.phantomwing.thesilverage.firework.ModFireworks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
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

        ResourceConditions.register(ConfigBooleanResourceCondition.TYPE);

        // Stamp each loaded LootTable with its registry id (vanilla tables carry none); read by LootTableMixin.
        LootTableEvents.ALL_LOADED.register((resourceManager, lootRegistry) ->
                lootRegistry.entrySet().forEach(e -> {
                    LootTable table = e.getValue();
                    if (table instanceof SilverLootTableId holder) {
                        holder.thesilverage$setLootTableId(e.getKey().identifier());
                    }
                }));
    }
}
