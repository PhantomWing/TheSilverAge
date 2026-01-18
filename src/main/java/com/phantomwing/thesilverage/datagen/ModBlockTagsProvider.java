package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
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

        // Silver blocks
        tag(ModTags.Blocks.SILVER_BLOCKS)
                .addTag(CommonTags.Blocks.ORES_SILVER)
                .addTag(CommonTags.Blocks.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Blocks.STORAGE_BLOCKS_RAW_SILVER);

        // Tool requirements
        tag(ModTags.Blocks.NEEDS_SILVER_TOOL).addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_SILVER_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL).remove(ModTags.Blocks.NEEDS_SILVER_TOOL);
        tag(BlockTags.NEEDS_IRON_TOOL).addTag(ModTags.Blocks.SILVER_BLOCKS);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.SILVER_BLOCKS);
    }
}
