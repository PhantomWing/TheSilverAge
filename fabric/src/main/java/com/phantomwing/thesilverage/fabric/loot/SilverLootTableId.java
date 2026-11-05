package com.phantomwing.thesilverage.fabric.loot;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Duck interface mixed onto {@code net.minecraft.world.level.storage.loot.LootTable}
 * (via {@code LootTableMixin}) so a rolled table can report its own id.
 *
 * <p>1.20.1's {@code LootTable} carries no id on Fabric (Forge patches one in, which its global loot modifiers'
 * {@code forge:loot_table_id} condition reads). The Silver loot mixin needs the rolled table's id to know which
 * {@link com.phantomwing.thesilverage.loot.SilverLootSpec} entries apply. The id is stamped once all loot tables
 * have loaded, from {@code TheSilverAgeFabric} (Fabric's {@code LootTableEvents.ALL_LOADED}).</p>
 */
public interface SilverLootTableId {
    /** The registry id of this loot table, or {@code null} if not stamped. */
    @Nullable
    ResourceLocation thesilverage$getLootTableId();

    /** Stamps this loot table's registry id (called once at load time). */
    void thesilverage$setLootTableId(ResourceLocation id);
}
