package com.phantomwing.thesilverage.block;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Single, loader-agnostic source of truth for the Silver oxidation and waxing
 * relationships.
 *
 * <p>Lifted verbatim from the original NeoForge {@code ModOxidizables.gather}
 * (oxidation steps, less → more) and {@code ModWaxables.gather} (unwaxed →
 * waxed), preserving every pair and its order. Both the NeoForge
 * {@code DataMapProvider} datagen (so the regenerated
 * {@code oxidizables.json}/{@code waxables.json} stay byte-identical) and the
 * Fabric runtime {@code OxidizableBlocksRegistry} registration iterate this
 * list — one weathering definition for both loaders.</p>
 *
 * <p>{@code ModBlocks} suppliers are wrapped in {@link Supplier} so the spec can
 * be referenced before block registration completes; the {@link Block} is
 * resolved lazily when {@link #oxidationPairs()} / {@link #waxablePairs()} are
 * iterated.</p>
 */
public final class SilverWeatheringSpec {
    private SilverWeatheringSpec() {
    }

    /**
     * An ordered weathering relationship.
     *
     * @param from oxidation: the less-oxidized block. waxing: the unwaxed block.
     * @param to   oxidation: the more-oxidized block. waxing: the waxed block.
     */
    public record Pair(Supplier<Block> from, Supplier<Block> to) {
    }

    private static void ox(List<Pair> out, Supplier<Block> less, Supplier<Block> more) {
        out.add(new Pair(less, more));
    }

    /**
     * The exact ordered oxidation steps (less-oxidized → next state). Matches
     * the original {@code ModOxidizables} {@code add(b, less, more)} sequence.
     */
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

        // Silver Lantern
        ox(p, ModBlocks.SILVER_LANTERN::get, ModBlocks.EXPOSED_SILVER_LANTERN::get);
        ox(p, ModBlocks.EXPOSED_SILVER_LANTERN::get, ModBlocks.WEATHERED_SILVER_LANTERN::get);
        ox(p, ModBlocks.WEATHERED_SILVER_LANTERN::get, ModBlocks.OXIDIZED_SILVER_LANTERN::get);

        // Silver Chain
        ox(p, ModBlocks.SILVER_CHAIN::get, ModBlocks.EXPOSED_SILVER_CHAIN::get);
        ox(p, ModBlocks.EXPOSED_SILVER_CHAIN::get, ModBlocks.WEATHERED_SILVER_CHAIN::get);
        ox(p, ModBlocks.WEATHERED_SILVER_CHAIN::get, ModBlocks.OXIDIZED_SILVER_CHAIN::get);

        // Silver Bars
        ox(p, ModBlocks.SILVER_BARS::get, ModBlocks.EXPOSED_SILVER_BARS::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BARS::get, ModBlocks.WEATHERED_SILVER_BARS::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BARS::get, ModBlocks.OXIDIZED_SILVER_BARS::get);

        return p;
    }

    /**
     * The exact ordered waxable pairs (unwaxed → waxed). Matches the original
     * {@code ModWaxables} {@code add(b, unwaxed, waxed)} sequence.
     */
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

        // Silver Lantern
        ox(p, ModBlocks.SILVER_LANTERN::get, ModBlocks.WAXED_SILVER_LANTERN::get);
        ox(p, ModBlocks.EXPOSED_SILVER_LANTERN::get, ModBlocks.WAXED_EXPOSED_SILVER_LANTERN::get);
        ox(p, ModBlocks.WEATHERED_SILVER_LANTERN::get, ModBlocks.WAXED_WEATHERED_SILVER_LANTERN::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_LANTERN::get, ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN::get);

        // Silver Chain
        ox(p, ModBlocks.SILVER_CHAIN::get, ModBlocks.WAXED_SILVER_CHAIN::get);
        ox(p, ModBlocks.EXPOSED_SILVER_CHAIN::get, ModBlocks.WAXED_EXPOSED_SILVER_CHAIN::get);
        ox(p, ModBlocks.WEATHERED_SILVER_CHAIN::get, ModBlocks.WAXED_WEATHERED_SILVER_CHAIN::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_CHAIN::get, ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN::get);

        // Silver Bars
        ox(p, ModBlocks.SILVER_BARS::get, ModBlocks.WAXED_SILVER_BARS::get);
        ox(p, ModBlocks.EXPOSED_SILVER_BARS::get, ModBlocks.WAXED_EXPOSED_SILVER_BARS::get);
        ox(p, ModBlocks.WEATHERED_SILVER_BARS::get, ModBlocks.WAXED_WEATHERED_SILVER_BARS::get);
        ox(p, ModBlocks.OXIDIZED_SILVER_BARS::get, ModBlocks.WAXED_OXIDIZED_SILVER_BARS::get);

        return p;
    }
}
