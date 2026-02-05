package com.phantomwing.thesilverage.block;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

public class ModWaxables {
    public static void gather(DataMapProvider.Builder<Waxable, Block> b) {
        // Block of Silver
        add(b, ModBlocks.SILVER_BLOCK, ModBlocks.WAXED_SILVER_BLOCK);
        add(b, ModBlocks.EXPOSED_SILVER, ModBlocks.WAXED_EXPOSED_SILVER);
        add(b, ModBlocks.WEATHERED_SILVER, ModBlocks.WAXED_WEATHERED_SILVER);
        add(b, ModBlocks.OXIDIZED_SILVER, ModBlocks.WAXED_OXIDIZED_SILVER);

        // Cut Silver
        add(b, ModBlocks.CUT_SILVER, ModBlocks.WAXED_CUT_SILVER);
        add(b, ModBlocks.EXPOSED_CUT_SILVER, ModBlocks.WAXED_EXPOSED_CUT_SILVER);
        add(b, ModBlocks.WEATHERED_CUT_SILVER, ModBlocks.WAXED_WEATHERED_CUT_SILVER);
        add(b, ModBlocks.OXIDIZED_CUT_SILVER, ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

        // Cut Silver Slab
        add(b, ModBlocks.CUT_SILVER_SLAB, ModBlocks.WAXED_CUT_SILVER_SLAB);
        add(b, ModBlocks.EXPOSED_CUT_SILVER_SLAB, ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
        add(b, ModBlocks.WEATHERED_CUT_SILVER_SLAB, ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
        add(b, ModBlocks.OXIDIZED_CUT_SILVER_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

        // Cut Silver Stairs
        add(b, ModBlocks.CUT_SILVER_STAIRS, ModBlocks.WAXED_CUT_SILVER_STAIRS);
        add(b, ModBlocks.EXPOSED_CUT_SILVER_STAIRS, ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
        add(b, ModBlocks.WEATHERED_CUT_SILVER_STAIRS, ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
        add(b, ModBlocks.OXIDIZED_CUT_SILVER_STAIRS, ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

        // Chiseled Silver
        add(b, ModBlocks.CHISELED_SILVER, ModBlocks.WAXED_CHISELED_SILVER);
        add(b, ModBlocks.EXPOSED_CHISELED_SILVER, ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
        add(b, ModBlocks.WEATHERED_CHISELED_SILVER, ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
        add(b, ModBlocks.OXIDIZED_CHISELED_SILVER, ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

        // Silver Grate
        add(b, ModBlocks.SILVER_GRATE, ModBlocks.WAXED_SILVER_GRATE);
        add(b, ModBlocks.EXPOSED_SILVER_GRATE, ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
        add(b, ModBlocks.WEATHERED_SILVER_GRATE, ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
        add(b, ModBlocks.OXIDIZED_SILVER_GRATE, ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

        // Silver Trapdoor
        add(b, ModBlocks.SILVER_TRAPDOOR, ModBlocks.WAXED_SILVER_TRAPDOOR);
        add(b, ModBlocks.EXPOSED_SILVER_TRAPDOOR, ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR);
        add(b, ModBlocks.WEATHERED_SILVER_TRAPDOOR, ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR);
        add(b, ModBlocks.OXIDIZED_SILVER_TRAPDOOR, ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR);

        // Silver Door
        add(b, ModBlocks.SILVER_DOOR, ModBlocks.WAXED_SILVER_DOOR);
        add(b, ModBlocks.EXPOSED_SILVER_DOOR, ModBlocks.WAXED_EXPOSED_SILVER_DOOR);
        add(b, ModBlocks.WEATHERED_SILVER_DOOR, ModBlocks.WAXED_WEATHERED_SILVER_DOOR);
        add(b, ModBlocks.OXIDIZED_SILVER_DOOR, ModBlocks.WAXED_OXIDIZED_SILVER_DOOR);
    }

    private static <T extends Block> void add(DataMapProvider.Builder<Waxable, Block> builder, DeferredBlock<T> block, DeferredBlock<T> waxedBlock) {
        builder.add(block.getId(), new Waxable(waxedBlock.get()), false);
    }
}
