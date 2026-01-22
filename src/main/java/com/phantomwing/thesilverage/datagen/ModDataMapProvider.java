package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.block.ModOxidizables;
import com.phantomwing.thesilverage.block.ModWaxables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NotNull Provider provider) {
        ModOxidizables.gather(builder(NeoForgeDataMaps.OXIDIZABLES));
        ModWaxables.gather(builder(NeoForgeDataMaps.WAXABLES));
    }
}
