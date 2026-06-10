package com.phantomwing.thesilverage.fabric.loot;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

/** Duck interface on vanilla LootTable (which carries no id) so a rolled table can report its own id. */
public interface SilverLootTableId {
    @Nullable
    Identifier thesilverage$getLootTableId();

    void thesilverage$setLootTableId(Identifier id);
}
