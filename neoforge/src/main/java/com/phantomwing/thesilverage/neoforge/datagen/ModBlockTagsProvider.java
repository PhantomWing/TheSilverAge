package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    /** 26.2: TagAppender#add takes a ResourceKey, so the chains below go through a wrapper that still takes blocks. */
    @Override
    protected ValueAppender<Block> tag(TagKey<Block> tag) {
        return new ValueAppender<>(super.tag(tag), value -> value.builtInRegistryHolder().key());
    }

    // Sable (Create Aeronautics) tags, referenced by string id since Sable is not a compile dependency.
    private static final TagKey<Block> SABLE_HEAVY =
            TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("sable", "heavy"));
    private static final TagKey<Block> SABLE_SUPER_LIGHT =
            TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("sable", "super_light"));
    private static final TagKey<Block> SABLE_QUARTER_VOLUME =
            TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("sable", "quarter_volume"));
    private static final TagKey<Block> SABLE_HALF_VOLUME =
            TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("sable", "half_volume"));

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

                // Silver Brick Wall
                .add(ModBlocks.SILVER_BRICK_WALL.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_WALL.get())

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
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR.get())

                // Silver Lightning Rod
                .add(ModBlocks.SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.OXIDIZED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_LIGHTNING_ROD.get());
        // NOTE: the Silver Torch is deliberately absent — it is instant-break
        // (no requiresCorrectToolForDrops), so it needs no tool/tier tags.

        // Silver that hurts the mobs silver harms as they stand on it: every block made of refined silver, at every
        // stage, waxed or not, which is what the weathering spec pairs. The pressure plates among them, which a mob
        // stands in rather than on. Not the ores, Raw Silver Block or Moon Phase Detector.
        ValueAppender<Block> silverFloors = tag(ModTags.Blocks.SILVER_FLOORS);
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
            silverFloors.add(pair.from().get(), pair.to().get());
        }

        // Seven non-base variants; base SILVER_BLOCK excluded (already in #c:storage_blocks).
        tag(ModTags.Blocks.WEATHERED_SILVER_BLOCKS)
                .add(ModBlocks.EXPOSED_SILVER.get())
                .add(ModBlocks.WEATHERED_SILVER.get())
                .add(ModBlocks.OXIDIZED_SILVER.get())
                .add(ModBlocks.WAXED_SILVER_BLOCK.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER.get());

        // Cut Silver
        tag(ModTags.Blocks.CUT_SILVER_BLOCKS)
                .add(ModBlocks.CUT_SILVER.get())
                .add(ModBlocks.EXPOSED_CUT_SILVER.get())
                .add(ModBlocks.WEATHERED_CUT_SILVER.get())
                .add(ModBlocks.OXIDIZED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_EXPOSED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_CUT_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_CUT_SILVER.get());

        // Chiseled Silver
        tag(ModTags.Blocks.CHISELED_SILVER_BLOCKS)
                .add(ModBlocks.CHISELED_SILVER.get())
                .add(ModBlocks.EXPOSED_CHISELED_SILVER.get())
                .add(ModBlocks.WEATHERED_CHISELED_SILVER.get())
                .add(ModBlocks.OXIDIZED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER.get())
                .add(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER.get());

        // Silver Bricks
        tag(ModTags.Blocks.SILVER_BRICK_BLOCKS)
                .add(ModBlocks.SILVER_BRICKS.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICKS.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICKS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICKS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS.get());

        // Silver Pillar
        tag(ModTags.Blocks.SILVER_PILLARS)
                .add(ModBlocks.SILVER_PILLAR.get())
                .add(ModBlocks.EXPOSED_SILVER_PILLAR.get())
                .add(ModBlocks.WEATHERED_SILVER_PILLAR.get())
                .add(ModBlocks.OXIDIZED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_PILLAR.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR.get());

        // Silver Grate
        tag(ModTags.Blocks.SILVER_GRATES)
                .add(ModBlocks.SILVER_GRATE.get())
                .add(ModBlocks.EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.OXIDIZED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE.get());
        tag(ModTags.Blocks.SILVER_BARS)
                .add(ModBlocks.SILVER_BARS.get())
                .add(ModBlocks.EXPOSED_SILVER_BARS.get())
                .add(ModBlocks.WEATHERED_SILVER_BARS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BARS.get());
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
        // Silver ore drops 1-3 Raw Silver, so it is dense, as copper ore is. By id, since c:ores/silver holds other
        // mods' silver ores too, which may drop one.
        tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get());

        // Chains
        tag(Tags.Blocks.CHAINS)
                .add(ModBlocks.SILVER_CHAIN.get())
                .add(ModBlocks.EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN.get());
    }

    private void addMinecraftTags(HolderLookup.@NotNull Provider provider) {
        // 1.21.9's tags of the copper age's bars, chains, lanterns and rods: bars of two kinds side by side hide the faces
        // between them, Channeling's lightning strikes at a rod in the lightning rod tag, and the skeleton trap doesn't.
        tag(BlockTags.BARS)
                .add(ModBlocks.SILVER_BARS.get())
                .add(ModBlocks.EXPOSED_SILVER_BARS.get())
                .add(ModBlocks.WEATHERED_SILVER_BARS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BARS.get());
        tag(BlockTags.CHAINS)
                .add(ModBlocks.SILVER_CHAIN.get())
                .add(ModBlocks.EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN.get());
        tag(BlockTags.LANTERNS)
                .add(ModBlocks.SILVER_LANTERN.get())
                .add(ModBlocks.EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.OXIDIZED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN.get());
        tag(BlockTags.LIGHTNING_RODS)
                .add(ModBlocks.SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.OXIDIZED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_LIGHTNING_ROD.get());

        // Tool requirements
        tag(BlockTags.NEEDS_IRON_TOOL).addTag(ModTags.Blocks.SILVER_BLOCKS);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.SILVER_BLOCKS);

        // Any pickaxe, as vanilla's lantern, chain and iron bars, so they stay out of SILVER_BLOCKS and its iron tool.
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SILVER_LANTERN.get())
                .add(ModBlocks.EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.OXIDIZED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN.get())
                .add(ModBlocks.SILVER_CHAIN.get())
                .add(ModBlocks.EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.SILVER_BARS.get())
                .add(ModBlocks.EXPOSED_SILVER_BARS.get())
                .add(ModBlocks.WEATHERED_SILVER_BARS.get())
                .add(ModBlocks.OXIDIZED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BARS.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BARS.get());

        // Pressure plates. Like vanilla's weighted plates they take any pickaxe, so they stay out of
        // SILVER_BLOCKS and its iron tool requirement; the tag also lets a wall keep its post under one.
        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.EXPOSED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WEATHERED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.OXIDIZED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_PRESSURE_PLATE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.EXPOSED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WEATHERED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.OXIDIZED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_PRESSURE_PLATE.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_PRESSURE_PLATE.get());

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

        // Lets the Silver Torch sit on top of walls and fences: a wall only keeps its
        // centre post for blocks in this tag (vanilla lists torch / soul_torch /
        // redstone_torch / copper_torch here). Only the standing torch belongs; the
        // wall variant attaches to the side.
        tag(BlockTags.WALL_POST_OVERRIDE).add(ModBlocks.SILVER_TORCH.get());

        // Silver light idle undead keep away from: the torch, on the floor or a wall, and the lantern that holds one,
        // at every stage and waxed or not, as vanilla's soul torch and soul lantern both turn piglins away.
        tag(ModTags.Blocks.UNDEAD_REPELLENTS)
                .add(ModBlocks.SILVER_TORCH.get(), ModBlocks.SILVER_WALL_TORCH.get())
                .add(ModBlocks.SILVER_LANTERN.get(), ModBlocks.EXPOSED_SILVER_LANTERN.get(),
                        ModBlocks.WEATHERED_SILVER_LANTERN.get(), ModBlocks.OXIDIZED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_SILVER_LANTERN.get(), ModBlocks.WAXED_EXPOSED_SILVER_LANTERN.get(),
                        ModBlocks.WAXED_WEATHERED_SILVER_LANTERN.get(), ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN.get());

        // A goat ramming silver ore loses a horn, as on iron or copper ore (vanilla lists only the stone ores).
        tag(BlockTags.SNAPS_GOAT_HORN).add(ModBlocks.SILVER_ORE.get());
        // Caves are carved after the ore is placed: as vanilla's iron and copper ores and raw blocks, silver's are carved
        // through, or a cave crossing them leaves the ore standing in it.
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get(),
                ModBlocks.RAW_SILVER_BLOCK.get());

        // Walls
        tag(BlockTags.WALLS)
                .add(ModBlocks.SILVER_BRICK_WALL.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_WALL.get());

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
        // Silver doors open by hand, so villagers open them too, as they do a copper door.
        tag(BlockTags.MOB_INTERACTABLE_DOORS)
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

    /** Sable weight/volume classifications; inert when Sable is absent, additively merged when present. */
    private void addSableTags(HolderLookup.@NotNull Provider provider) {
        tag(SABLE_HEAVY)
                .addTag(ModTags.Blocks.WEATHERED_SILVER_BLOCKS)
                .addTag(ModTags.Blocks.CUT_SILVER_BLOCKS)
                .addTag(ModTags.Blocks.CHISELED_SILVER_BLOCKS)
                .addTag(ModTags.Blocks.SILVER_BRICK_BLOCKS)
                .addTag(ModTags.Blocks.SILVER_PILLARS)
                .add(ModBlocks.SILVER_BRICK_WALL.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_WALL.get());

        // The wall is half a block, as Sable has vanilla's walls, and solid silver, as The Lead Age's lead is.
        tag(SABLE_HALF_VOLUME)
                .add(ModBlocks.SILVER_BRICK_WALL.get())
                .add(ModBlocks.EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.OXIDIZED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_WALL.get());

        // The chain and lantern are open work as the bars are, as Sable has vanilla's.
        tag(SABLE_SUPER_LIGHT)
                .addTag(ModTags.Blocks.SILVER_GRATES)
                .addTag(ModTags.Blocks.SILVER_BARS)
                .add(ModBlocks.SILVER_CHAIN.get())
                .add(ModBlocks.EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.SILVER_LANTERN.get())
                .add(ModBlocks.EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.OXIDIZED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN.get());

        tag(SABLE_QUARTER_VOLUME)
                .addTag(ModTags.Blocks.SILVER_GRATES)
                .addTag(ModTags.Blocks.SILVER_BARS)
                .add(ModBlocks.SILVER_CHAIN.get())
                .add(ModBlocks.EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN.get())
                .add(ModBlocks.SILVER_LANTERN.get())
                .add(ModBlocks.EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.OXIDIZED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_LANTERN.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN.get());
    }
}
