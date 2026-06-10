package com.phantomwing.thesilverage.block;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Source of truth for the Silver oxidation and waxing relationships. */
public final class SilverWeatheringSpec {
    private SilverWeatheringSpec() {
    }

    /** Ordered pair: oxidation less→more, or waxing unwaxed→waxed. */
    public record Pair(Supplier<Block> from, Supplier<Block> to) {
    }

    private static void ox(List<Pair> out, Supplier<Block> less, Supplier<Block> more) {
        out.add(new Pair(less, more));
    }

    /** Ordered oxidation steps (less-oxidized → next state). */
    public static List<Pair> oxidationPairs() {
        List<Pair> p = new ArrayList<>();

        // Block of Silver
        ox(p, ModBlocks.SILVER_BLOCK::get, ModBlocks.EXPOSED_SILVER::get);
        ox(p, ModBlocks.EXPOSED_SILVER::get, ModBlocks.WEATHERED_SILVER::get);
        ox(p, ModBlocks.WEATHERED_SILVER::get, ModBlocks.OXIDIZED_SILVER::get);

        // Cut Silver
        ox(p, ModBlocks.CUT_SILVER::get, ModBlocks.EXPOSED_CUT_SILVER::get);
        ox(p, ModBlocks.EXPOSED_CUT_SILVER::get, ModBlocks.WEATHERED_CUT_SILVER::get);
        ox(p, ModBlocks.WEATHERED_CUT_SILVER::get, ModBlocks.OXIDIZED_CUT_SILVER::get);

        // Silver Bricks
        ox(p, ModBlocks.SILVER_BRICKS::get, ModBlocks.EXPOSED_SILVER_BRICKS::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BRICKS::get, ModBlocks.WEATHERED_SILVER_BRICKS::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BRICKS::get, ModBlocks.OXIDIZED_SILVER_BRICKS::get);

        // Silver Brick Slab
        ox(p, ModBlocks.SILVER_BRICK_SLAB::get, ModBlocks.EXPOSED_SILVER_BRICK_SLAB::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BRICK_SLAB::get, ModBlocks.WEATHERED_SILVER_BRICK_SLAB::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BRICK_SLAB::get, ModBlocks.OXIDIZED_SILVER_BRICK_SLAB::get);

        // Silver Brick Stairs
        ox(p, ModBlocks.SILVER_BRICK_STAIRS::get, ModBlocks.EXPOSED_SILVER_BRICK_STAIRS::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BRICK_STAIRS::get, ModBlocks.WEATHERED_SILVER_BRICK_STAIRS::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BRICK_STAIRS::get, ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS::get);

        // Cut Silver Slab
        ox(p, ModBlocks.CUT_SILVER_SLAB::get, ModBlocks.EXPOSED_CUT_SILVER_SLAB::get);
        ox(p, ModBlocks.EXPOSED_CUT_SILVER_SLAB::get, ModBlocks.WEATHERED_CUT_SILVER_SLAB::get);
        ox(p, ModBlocks.WEATHERED_CUT_SILVER_SLAB::get, ModBlocks.OXIDIZED_CUT_SILVER_SLAB::get);

        // Cut Silver Stairs
        ox(p, ModBlocks.CUT_SILVER_STAIRS::get, ModBlocks.EXPOSED_CUT_SILVER_STAIRS::get);
        ox(p, ModBlocks.EXPOSED_CUT_SILVER_STAIRS::get, ModBlocks.WEATHERED_CUT_SILVER_STAIRS::get);
        ox(p, ModBlocks.WEATHERED_CUT_SILVER_STAIRS::get, ModBlocks.OXIDIZED_CUT_SILVER_STAIRS::get);

        // Chiseled Silver
        ox(p, ModBlocks.CHISELED_SILVER::get, ModBlocks.EXPOSED_CHISELED_SILVER::get);
        ox(p, ModBlocks.EXPOSED_CHISELED_SILVER::get, ModBlocks.WEATHERED_CHISELED_SILVER::get);
        ox(p, ModBlocks.WEATHERED_CHISELED_SILVER::get, ModBlocks.OXIDIZED_CHISELED_SILVER::get);

        // Silver Pillar
        ox(p, ModBlocks.SILVER_PILLAR::get, ModBlocks.EXPOSED_SILVER_PILLAR::get);
        ox(p, ModBlocks.EXPOSED_SILVER_PILLAR::get, ModBlocks.WEATHERED_SILVER_PILLAR::get);
        ox(p, ModBlocks.WEATHERED_SILVER_PILLAR::get, ModBlocks.OXIDIZED_SILVER_PILLAR::get);

        // Silver Grate
        ox(p, ModBlocks.SILVER_GRATE::get, ModBlocks.EXPOSED_SILVER_GRATE::get);
        ox(p, ModBlocks.EXPOSED_SILVER_GRATE::get, ModBlocks.WEATHERED_SILVER_GRATE::get);
        ox(p, ModBlocks.WEATHERED_SILVER_GRATE::get, ModBlocks.OXIDIZED_SILVER_GRATE::get);

        // Silver Bulb
        ox(p, ModBlocks.SILVER_BULB::get, ModBlocks.EXPOSED_SILVER_BULB::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BULB::get, ModBlocks.WEATHERED_SILVER_BULB::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BULB::get, ModBlocks.OXIDIZED_SILVER_BULB::get);

        // Silver Trapdoor
        ox(p, ModBlocks.SILVER_TRAPDOOR::get, ModBlocks.EXPOSED_SILVER_TRAPDOOR::get);
        ox(p, ModBlocks.EXPOSED_SILVER_TRAPDOOR::get, ModBlocks.WEATHERED_SILVER_TRAPDOOR::get);
        ox(p, ModBlocks.WEATHERED_SILVER_TRAPDOOR::get, ModBlocks.OXIDIZED_SILVER_TRAPDOOR::get);

        // Silver Door
        ox(p, ModBlocks.SILVER_DOOR::get, ModBlocks.EXPOSED_SILVER_DOOR::get);
        ox(p, ModBlocks.EXPOSED_SILVER_DOOR::get, ModBlocks.WEATHERED_SILVER_DOOR::get);
        ox(p, ModBlocks.WEATHERED_SILVER_DOOR::get, ModBlocks.OXIDIZED_SILVER_DOOR::get);

        return p;
    }

    /** Ordered waxable pairs (unwaxed → waxed). */
    public static List<Pair> waxablePairs() {
        List<Pair> p = new ArrayList<>();

        // Block of Silver
        ox(p, ModBlocks.SILVER_BLOCK::get, ModBlocks.WAXED_SILVER_BLOCK::get);
        ox(p, ModBlocks.EXPOSED_SILVER::get, ModBlocks.WAXED_EXPOSED_SILVER::get);
        ox(p, ModBlocks.WEATHERED_SILVER::get, ModBlocks.WAXED_WEATHERED_SILVER::get);
        ox(p, ModBlocks.OXIDIZED_SILVER::get, ModBlocks.WAXED_OXIDIZED_SILVER::get);

        // Cut Silver
        ox(p, ModBlocks.CUT_SILVER::get, ModBlocks.WAXED_CUT_SILVER::get);
        ox(p, ModBlocks.EXPOSED_CUT_SILVER::get, ModBlocks.WAXED_EXPOSED_CUT_SILVER::get);
        ox(p, ModBlocks.WEATHERED_CUT_SILVER::get, ModBlocks.WAXED_WEATHERED_CUT_SILVER::get);
        ox(p, ModBlocks.OXIDIZED_CUT_SILVER::get, ModBlocks.WAXED_OXIDIZED_CUT_SILVER::get);

        // Silver Bricks
        ox(p, ModBlocks.SILVER_BRICKS::get, ModBlocks.WAXED_SILVER_BRICKS::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BRICKS::get, ModBlocks.WAXED_EXPOSED_SILVER_BRICKS::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BRICKS::get, ModBlocks.WAXED_WEATHERED_SILVER_BRICKS::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_BRICKS::get, ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS::get);

        // Silver Brick Slab
        ox(p, ModBlocks.SILVER_BRICK_SLAB::get, ModBlocks.WAXED_SILVER_BRICK_SLAB::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BRICK_SLAB::get, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BRICK_SLAB::get, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_BRICK_SLAB::get, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB::get);

        // Silver Brick Stairs
        ox(p, ModBlocks.SILVER_BRICK_STAIRS::get, ModBlocks.WAXED_SILVER_BRICK_STAIRS::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BRICK_STAIRS::get, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BRICK_STAIRS::get, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS::get, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS::get);

        // Cut Silver Slab
        ox(p, ModBlocks.CUT_SILVER_SLAB::get, ModBlocks.WAXED_CUT_SILVER_SLAB::get);
        ox(p, ModBlocks.EXPOSED_CUT_SILVER_SLAB::get, ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB::get);
        ox(p, ModBlocks.WEATHERED_CUT_SILVER_SLAB::get, ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB::get);
        ox(p, ModBlocks.OXIDIZED_CUT_SILVER_SLAB::get, ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB::get);

        // Cut Silver Stairs
        ox(p, ModBlocks.CUT_SILVER_STAIRS::get, ModBlocks.WAXED_CUT_SILVER_STAIRS::get);
        ox(p, ModBlocks.EXPOSED_CUT_SILVER_STAIRS::get, ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS::get);
        ox(p, ModBlocks.WEATHERED_CUT_SILVER_STAIRS::get, ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS::get);
        ox(p, ModBlocks.OXIDIZED_CUT_SILVER_STAIRS::get, ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS::get);

        // Chiseled Silver
        ox(p, ModBlocks.CHISELED_SILVER::get, ModBlocks.WAXED_CHISELED_SILVER::get);
        ox(p, ModBlocks.EXPOSED_CHISELED_SILVER::get, ModBlocks.WAXED_EXPOSED_CHISELED_SILVER::get);
        ox(p, ModBlocks.WEATHERED_CHISELED_SILVER::get, ModBlocks.WAXED_WEATHERED_CHISELED_SILVER::get);
        ox(p, ModBlocks.OXIDIZED_CHISELED_SILVER::get, ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER::get);

        // Silver Pillar
        ox(p, ModBlocks.SILVER_PILLAR::get, ModBlocks.WAXED_SILVER_PILLAR::get);
        ox(p, ModBlocks.EXPOSED_SILVER_PILLAR::get, ModBlocks.WAXED_EXPOSED_SILVER_PILLAR::get);
        ox(p, ModBlocks.WEATHERED_SILVER_PILLAR::get, ModBlocks.WAXED_WEATHERED_SILVER_PILLAR::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_PILLAR::get, ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR::get);

        // Silver Grate
        ox(p, ModBlocks.SILVER_GRATE::get, ModBlocks.WAXED_SILVER_GRATE::get);
        ox(p, ModBlocks.EXPOSED_SILVER_GRATE::get, ModBlocks.WAXED_EXPOSED_SILVER_GRATE::get);
        ox(p, ModBlocks.WEATHERED_SILVER_GRATE::get, ModBlocks.WAXED_WEATHERED_SILVER_GRATE::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_GRATE::get, ModBlocks.WAXED_OXIDIZED_SILVER_GRATE::get);

        // Silver Bulb
        ox(p, ModBlocks.SILVER_BULB::get, ModBlocks.WAXED_SILVER_BULB::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BULB::get, ModBlocks.WAXED_EXPOSED_SILVER_BULB::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BULB::get, ModBlocks.WAXED_WEATHERED_SILVER_BULB::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_BULB::get, ModBlocks.WAXED_OXIDIZED_SILVER_BULB::get);

        // Silver Trapdoor
        ox(p, ModBlocks.SILVER_TRAPDOOR::get, ModBlocks.WAXED_SILVER_TRAPDOOR::get);
        ox(p, ModBlocks.EXPOSED_SILVER_TRAPDOOR::get, ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR::get);
        ox(p, ModBlocks.WEATHERED_SILVER_TRAPDOOR::get, ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_TRAPDOOR::get, ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR::get);

        // Silver Door
        ox(p, ModBlocks.SILVER_DOOR::get, ModBlocks.WAXED_SILVER_DOOR::get);
        ox(p, ModBlocks.EXPOSED_SILVER_DOOR::get, ModBlocks.WAXED_EXPOSED_SILVER_DOOR::get);
        ox(p, ModBlocks.WEATHERED_SILVER_DOOR::get, ModBlocks.WAXED_WEATHERED_SILVER_DOOR::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_DOOR::get, ModBlocks.WAXED_OXIDIZED_SILVER_DOOR::get);

        return p;
    }
}
