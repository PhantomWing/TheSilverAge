package com.phantomwing.thesilverage.fabric;

import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.fabric.compat.create.CreateFabricCompat;
import com.phantomwing.thesilverage.fabric.condition.ConfigBooleanResourceCondition;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
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
        // MUST be first: the loot mixin and the thesilverage:config_boolean
        // resource condition read config very early (datapack load), so the
        // AutoConfig holder must be registered before anything else runs.
        TheSilverAgeFabricConfig.register();

        TheSilverAgeCommon.init();

        // Parity twin of the NeoForge `thesilverage:config_boolean` recipe
        // condition. The shared generated data carries BOTH dialects in each
        // conditional file (NeoForge `neoforge:conditions` +
        // Fabric `fabric:load_conditions`, the latter emitted by the NeoForge
        // datagen post-processor); this registers the runtime handler so Fabric
        // can evaluate the translated block. `fabric:all_mods_loaded` (the
        // create-gated recipes) is built into fabric-api and needs no
        // registration here.
        ResourceConditions.register(ConfigBooleanResourceCondition.TYPE);

        // Future-proof Create hook. No-ops today (no Create build exists for
        // Fabric 1.21.1 — external blocker). Recipe parity is already handled by
        // the shared condition-gated generated data; this is the live, guarded
        // home for any future runtime Create-Fabric integration.
        CreateFabricCompat.init();

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
