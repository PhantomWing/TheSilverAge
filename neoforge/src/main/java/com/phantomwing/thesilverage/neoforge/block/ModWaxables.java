package com.phantomwing.thesilverage.neoforge.block;

import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

/**
 * Feeds the NeoForge {@code waxables} data map from the common
 * {@link SilverWeatheringSpec} (single source of truth, shared with the Fabric
 * runtime registration). The generated {@code waxables.json} is byte-identical
 * to the previously hand-written provider's output.
 */
public class ModWaxables {
    public static void gather(DataMapProvider.Builder<Waxable, Block> b) {
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
            Block unwaxed = pair.from().get();
            Block waxed = pair.to().get();
            b.add(BuiltInRegistries.BLOCK.getKey(unwaxed), new Waxable(waxed), false);
        }
    }
}
