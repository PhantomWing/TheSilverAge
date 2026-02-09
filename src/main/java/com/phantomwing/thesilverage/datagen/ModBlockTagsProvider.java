package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addModTags(provider);
        addCommonTags(provider);
        addMinecraftTags(provider);
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

                // Silver Grate
                .add(ModBlocks.SILVER_GRATE.get())
                .add(ModBlocks.EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.OXIDIZED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_EXPOSED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_WEATHERED_SILVER_GRATE.get())
                .add(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE.get())

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
}
