package com.phantomwing.thesilverage.fabric;

import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * Fabric entrypoint for The Silver Age.
 *
 * <p>Delegates the loader-agnostic bootstrap to {@link TheSilverAgeCommon#init()}
 * and wires the one Fabric-only piece needed for loot parity: stamping each
 * loaded {@code LootTable} with its registry id so the {@code LootTableMixin}
 * (the Fabric equivalent of the NeoForge Global Loot Modifiers) knows which
 * {@link com.phantomwing.thesilverage.loot.SilverLootSpec} entries to apply at
 * roll time.</p>
 */
public final class TheSilverAgeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TheSilverAgeCommon.init();

        // Vanilla LootTable carries no id of its own. Once all loot tables are
        // loaded, stamp each instance with its registry id (read back by the
        // Silver loot mixin — mirrors the GLM neoforge:loot_table_id condition).
        LootTableEvents.ALL_LOADED.register((resourceManager, lootRegistry) ->
                lootRegistry.entrySet().forEach(e -> {
                    LootTable table = e.getValue();
                    if (table instanceof SilverLootTableId holder) {
                        holder.thesilverage$setLootTableId(e.getKey().location());
                    }
                }));
    }
}
