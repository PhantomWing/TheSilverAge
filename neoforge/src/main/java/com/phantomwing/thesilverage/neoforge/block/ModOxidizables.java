package com.phantomwing.thesilverage.neoforge.block;

import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;

// Feeds the NeoForge oxidizables data map from the shared SilverWeatheringSpec.
public class ModOxidizables {
    public static void gather(DataMapProvider.Builder<Oxidizable, Block> b) {
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
            Block less = pair.from().get();
            Block more = pair.to().get();
            b.add(BuiltInRegistries.BLOCK.getKey(less), new Oxidizable(more), false);
        }
    }
}
