package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    // External "Sable" tags this mod contributes to (Create Aeronautics's
    // weight/volume system). Sable is not a compile-time dependency, so the
    // tags are referenced by string id; the generated JSONs are merged with
    // Sable's own at runtime when Sable is installed and are otherwise inert.
    private static final TagKey<Block> SABLE_HEAVY =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "heavy"));
    private static final TagKey<Block> SABLE_SUPER_LIGHT =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "super_light"));
    private static final TagKey<Block> SABLE_QUARTER_VOLUME =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "quarter_volume"));

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addModTags(provider);
        addCommonTags(provider);
        addMinecraftTags(provider);
        addSableTags(provider);
    }

    private void addModTags(HolderLookup.@NotNull Provider provider) {
        // Tool requirements
        tag(ModTags.Blocks.NEEDS_SILVER_TOOL).addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_SILVER_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL).remove(ModTags.Blocks.NEEDS_SILVER_TOOL);

        // Silver blocks
        tag(ModTags.Blocks.SILVER_BLOCKS)
                .add(ModBlocks.SILVER_ORE.get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocks.RAW_SILVER_BLOCK.get())

                .add(ModBlocks.MOON_PHASE_DETECTOR.get())

                // Block of Silver
                .add(ModBlocks.SILVER_BLOCK.get())
                .add(ModBlocks.EXPOSED_SILVER.get())
                .add(ModBlocks.WEATHERED_SILVER.get())
                .add(ModBlocks.OXIDIZED_SILVER.get())
                .add(ModBlocks.WAXED_SILVER_BLOCK.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER.get())

                // Cut Silver
                .add(ModBlocks.CUT_SILVER.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER.get())

                // Silver Bricks
                .add(ModBlocks.SILVER_BRICKS.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICKS.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICKS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS.get())

                // Silver Brick Slab
                .add(ModBlocks.SILVER_BRICK_SLAB.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get())

                // Silver Brick Stairs
                .add(ModBlocks.SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get())

                // Cut Silver Slab
                .add(ModBlocks.CUT_SILVER_SLAB.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB.get())

                // Cut Silver Stairs
                .add(ModBlocks.CUT_SILVER_STAIRS.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get())

                // Chiseled Silver
                .add(ModBlocks.CHISELED_SILVER.get())
                .add(ModBlocks.EXPOSED_CHISELED_SILVER.get())
                .add(ModBlocks.WEATHERED_CHISELED_SILVER.get())
                .add(ModBlocks.OXIDIZED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER.get())

                // Silver Pillar
                .add(ModBlocks.SILVER_PILLAR.get())
                .add(ModBlocks.EXPOSED_SILVER_PILLAR.get())
                .add(ModBlocks.WEATHERED_SILVER_PILLAR.get())
                .add(ModBlocks.OXIDIZED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR.get())

                // Silver Grate
                .add(ModBlocks.SILVER_GRATE.get())
                .add(ModBlocks.EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.OXIDIZED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE.get())

                // Silver Bulb
                .add(ModBlocks.SILVER_BULB.get())
                .add(ModBlocks.EXPOSED_SILVER_BULB.get())
                .add(ModBlocks.WEATHERED_SILVER_BULB.get())
                .add(ModBlocks.OXIDIZED_SILVER_BULB.get())
                .add(ModBlocks.WAXED_SILVER_BULB.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BULB.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BULB.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BULB.get())

                // Silver Trapdoor
                .add(ModBlocks.SILVER_TRAPDOOR.get())
                .add(ModBlocks.EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.OXIDIZED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR.get())

                // Silver Door
                .add(ModBlocks.SILVER_DOOR.get())
                .add(ModBlocks.EXPOSED_SILVER_DOOR.get())
                .add(ModBlocks.WEATHERED_SILVER_DOOR.get())
                .add(ModBlocks.OXIDIZED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR.get());

        // Silver Block family aggregation — the seven non-base variants only.
        // The base SILVER_BLOCK is intentionally excluded: it is in
        // #c:storage_blocks (only it can be uncrafted back to ingots), which
        // Sable already classifies as sable:heavy, so adding the base here
        // would be redundant. The waxed_silver_block (waxed UNAFFECTED) IS
        // included since it cannot be uncrafted and is not a storage block.
        tag(ModTags.Blocks.WEATHERED_SILVER_BLOCKS)
                .add(ModBlocks.EXPOSED_SILVER.get())
                .add(ModBlocks.WEATHERED_SILVER.get())
                .add(ModBlocks.OXIDIZED_SILVER.get())
                .add(ModBlocks.WAXED_SILVER_BLOCK.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER.get());

        // Cut Silver family — all 8 (the base block; slab + stairs are
        // covered separately by #minecraft:slabs / #minecraft:stairs which
        // Sable already references in sable:light / sable:half_volume).
        tag(ModTags.Blocks.CUT_SILVER_BLOCKS)
                .add(ModBlocks.CUT_SILVER.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER.get());

        // Chiseled Silver family — all 8.
        tag(ModTags.Blocks.CHISELED_SILVER_BLOCKS)
                .add(ModBlocks.CHISELED_SILVER.get())
                .add(ModBlocks.EXPOSED_CHISELED_SILVER.get())
                .add(ModBlocks.WEATHERED_CHISELED_SILVER.get())
                .add(ModBlocks.OXIDIZED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER.get());

        // Silver Brick family — full block only, all 8. The brick slab and
        // stairs are covered by #minecraft:slabs / #minecraft:stairs.
        tag(ModTags.Blocks.SILVER_BRICK_BLOCKS)
                .add(ModBlocks.SILVER_BRICKS.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICKS.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICKS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS.get());

        // Silver Pillar family — all 8.
        tag(ModTags.Blocks.SILVER_PILLARS)
                .add(ModBlocks.SILVER_PILLAR.get())
                .add(ModBlocks.EXPOSED_SILVER_PILLAR.get())
                .add(ModBlocks.WEATHERED_SILVER_PILLAR.get())
                .add(ModBlocks.OXIDIZED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR.get());

        // Silver Grate family — all 8.
        tag(ModTags.Blocks.SILVER_GRATES)
                .add(ModBlocks.SILVER_GRATE.get())
                .add(ModBlocks.EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.OXIDIZED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE.get());
    }

    private void addCommonTags(HolderLookup.@NotNull Provider provider) {
        // Storage blocks
        tag(CommonTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.SILVER_BLOCK.get());
        tag(CommonTags.Blocks.STORAGE_BLOCKS_RAW_SILVER).add(ModBlocks.RAW_SILVER_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(CommonTags.Blocks.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Blocks.STORAGE_BLOCKS_RAW_SILVER);

        // Ores
        tag(CommonTags.Blocks.ORES_SILVER).add(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(CommonTags.Blocks.ORES_SILVER);
        tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(ModBlocks.SILVER_ORE.get());
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.DEEPSLATE_SILVER_ORE.get());
        tag(Tags.Blocks.ORE_RATES_SINGULAR).addTag(CommonTags.Blocks.ORES_SILVER);
    }

    private void addMinecraftTags(HolderLookup.@NotNull Provider provider) {
        // Tool requirements
        tag(BlockTags.NEEDS_IRON_TOOL).addTag(ModTags.Blocks.SILVER_BLOCKS);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.SILVER_BLOCKS);

        // Beacon
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.SILVER_BLOCK.get())
                .add(ModBlocks.EXPOSED_SILVER.get())
                .add(ModBlocks.WEATHERED_SILVER.get())
                .add(ModBlocks.OXIDIZED_SILVER.get())
                .add(ModBlocks.WAXED_SILVER_BLOCK.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER.get());

        // Slabs
        tag(BlockTags.SLABS)
                .add(ModBlocks.SILVER_BRICK_SLAB.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get())
                .add(ModBlocks.CUT_SILVER_SLAB.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB.get());

        // Stairs
        tag(BlockTags.STAIRS)
                .add(ModBlocks.SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get())
                .add(ModBlocks.CUT_SILVER_STAIRS.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get());

        // Doors
        tag(BlockTags.DOORS)
                .add(ModBlocks.SILVER_DOOR.get())
                .add(ModBlocks.EXPOSED_SILVER_DOOR.get())
                .add(ModBlocks.WEATHERED_SILVER_DOOR.get())
                .add(ModBlocks.OXIDIZED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_DOOR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR.get());

        // Trapdoors
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.SILVER_TRAPDOOR.get())
                .add(ModBlocks.EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.OXIDIZED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());
    }

    /**
     * Sable (Create Aeronautics weight/volume system) compatibility.
     *
     * <p>Sable references vanilla aggregation tags directly — for example
     * {@code #minecraft:doors} / {@code #minecraft:trapdoors} are in
     * {@code sable:light} + {@code sable:super_light} + {@code sable:quarter_volume};
     * {@code #minecraft:slabs} / {@code #minecraft:stairs} are in
     * {@code sable:light} + {@code sable:half_volume}; {@code #c:storage_blocks}
     * is in {@code sable:heavy}. The Silver Age doors, trapdoors, slabs, stairs,
     * and the base SILVER_BLOCK / RAW_SILVER_BLOCK are therefore already
     * classified correctly via the existing tag memberships above.</p>
     *
     * <p>The entries here cover the gaps: the seven non-base SILVER_BLOCK
     * variants, the full Cut Silver / Chiseled Silver / Silver Bricks families
     * (all full-mass solid silver) go into {@code sable:heavy}; the Silver
     * Grates (lattice metal — parallel to Sable's {@code iron_bars} treatment
     * and BlockBox's {@code copper_bars}/{@code golden_bars}) go into
     * {@code sable:super_light} + {@code sable:quarter_volume}.</p>
     *
     * <p>Generated unconditionally — when Sable is absent the JSONs are inert,
     * when present they merge with Sable's own additively (no {@code "replace"}).</p>
     */
    private void addSableTags(HolderLookup.@NotNull Provider provider) {
        tag(SABLE_HEAVY)
                .addTag(ModTags.Blocks.WEATHERED_SILVER_BLOCKS)
                .addTag(ModTags.Blocks.CUT_SILVER_BLOCKS)
                .addTag(ModTags.Blocks.CHISELED_SILVER_BLOCKS)
                .addTag(ModTags.Blocks.SILVER_BRICK_BLOCKS)
                .addTag(ModTags.Blocks.SILVER_PILLARS);

        tag(SABLE_SUPER_LIGHT)
                .addTag(ModTags.Blocks.SILVER_GRATES);

        tag(SABLE_QUARTER_VOLUME)
                .addTag(ModTags.Blocks.SILVER_GRATES);
    }
}
