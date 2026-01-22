package com.phantomwing.thesilverage.block;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;

public class ModOxidizables {
    public static void gather(DataMapProvider.Builder<Oxidizable, Block> b) {
        // Block of Silver
        add(b, ModBlocks.SILVER_BLOCK, ModBlocks.EXPOSED_SILVER);
        add(b, ModBlocks.EXPOSED_SILVER, ModBlocks.WEATHERED_SILVER);
        add(b, ModBlocks.WEATHERED_SILVER, ModBlocks.OXIDIZED_SILVER);

        // Cut Silver
        add(b, ModBlocks.CUT_SILVER, ModBlocks.EXPOSED_CUT_SILVER);
        add(b, ModBlocks.EXPOSED_CUT_SILVER, ModBlocks.WEATHERED_CUT_SILVER);
        add(b, ModBlocks.WEATHERED_CUT_SILVER, ModBlocks.OXIDIZED_CUT_SILVER);

        // Cut Silver Slab
        add(b, ModBlocks.CUT_SILVER_SLAB, ModBlocks.EXPOSED_CUT_SILVER_SLAB);
        add(b, ModBlocks.EXPOSED_CUT_SILVER_SLAB, ModBlocks.WEATHERED_CUT_SILVER_SLAB);
        add(b, ModBlocks.WEATHERED_CUT_SILVER_SLAB, ModBlocks.OXIDIZED_CUT_SILVER_SLAB);

        // Cut Silver Stairs
        add(b, ModBlocks.CUT_SILVER_STAIRS, ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
        add(b, ModBlocks.EXPOSED_CUT_SILVER_STAIRS, ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
        add(b, ModBlocks.WEATHERED_CUT_SILVER_STAIRS, ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);

        // Chiseled Silver
        add(b, ModBlocks.CHISELED_SILVER, ModBlocks.EXPOSED_CHISELED_SILVER);
        add(b, ModBlocks.EXPOSED_CHISELED_SILVER, ModBlocks.WEATHERED_CHISELED_SILVER);
        add(b, ModBlocks.WEATHERED_CHISELED_SILVER, ModBlocks.OXIDIZED_CHISELED_SILVER);

        // Silver Grate
        add(b, ModBlocks.SILVER_GRATE, ModBlocks.EXPOSED_SILVER_GRATE);
        add(b, ModBlocks.EXPOSED_SILVER_GRATE, ModBlocks.WEATHERED_SILVER_GRATE);
        add(b, ModBlocks.WEATHERED_SILVER_GRATE, ModBlocks.OXIDIZED_SILVER_GRATE);
    }

    private static <T extends Block> void add(DataMapProvider.Builder<Oxidizable, Block> builder, DeferredBlock<T> block, DeferredBlock<T> oxidizedBlock) {
        builder.add(block.getId(), new Oxidizable(oxidizedBlock.get()), false);
    }
}
