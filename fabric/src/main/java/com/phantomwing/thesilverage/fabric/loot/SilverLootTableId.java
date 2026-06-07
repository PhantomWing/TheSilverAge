package com.phantomwing.thesilverage.fabric.loot;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * Duck interface mixed onto {@code net.minecraft.world.level.storage.loot.LootTable}
 * (via {@code LootTableMixin}) so a rolled table can report its own id.
 *
 * <p>Vanilla 1.21.1 {@code LootTable} carries no id of its own (NeoForge adds
 * {@code getLootTableId()} via a patch; Fabric/Mojmap does not). The Silver
 * loot mixin needs the rolled table's id to know which {@link
 * com.phantomwing.thesilverage.loot.SilverLootSpec} entries apply — mirroring
 * the NeoForge GLM {@code neoforge:loot_table_id} condition. The id is stamped
 * once, after all loot tables load, by iterating the loot {@code Registry} in
 * {@code TheSilverAgeFabric} (Fabric Loot API v2 {@code LootTableEvents.ALL_LOADED}).</p>
 */
public interface SilverLootTableId {
    /** The registry id of this loot table, or {@code null} if not stamped. */
    @Nullable
    Identifier thesilverage$getLootTableId();

    /** Stamps this loot table's registry id (called once at load time). */
    void thesilverage$setLootTableId(Identifier id);
}
