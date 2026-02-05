package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.utils.BlockUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TheSilverAge.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SILVER_ORE);
        simpleBlock(ModBlocks.DEEPSLATE_SILVER_ORE);
        simpleBlock(ModBlocks.RAW_SILVER_BLOCK);

        // Block of Silver
        simpleBlock(ModBlocks.SILVER_BLOCK);
        simpleBlock(ModBlocks.EXPOSED_SILVER);
        simpleBlock(ModBlocks.WEATHERED_SILVER);
        simpleBlock(ModBlocks.OXIDIZED_SILVER);
        blockWithTexture(ModBlocks.WAXED_SILVER_BLOCK, ModBlocks.SILVER_BLOCK);
        blockWithTexture(ModBlocks.WAXED_EXPOSED_SILVER, ModBlocks.EXPOSED_SILVER);
        blockWithTexture(ModBlocks.WAXED_WEATHERED_SILVER, ModBlocks.WEATHERED_SILVER);
        blockWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER, ModBlocks.OXIDIZED_SILVER);

        // Cut Silver
        simpleBlock(ModBlocks.CUT_SILVER);
        simpleBlock(ModBlocks.EXPOSED_CUT_SILVER);
        simpleBlock(ModBlocks.WEATHERED_CUT_SILVER);
        simpleBlock(ModBlocks.OXIDIZED_CUT_SILVER);
        blockWithTexture(ModBlocks.WAXED_CUT_SILVER, ModBlocks.CUT_SILVER);
        blockWithTexture(ModBlocks.WAXED_EXPOSED_CUT_SILVER, ModBlocks.EXPOSED_CUT_SILVER);
        blockWithTexture(ModBlocks.WAXED_WEATHERED_CUT_SILVER, ModBlocks.WEATHERED_CUT_SILVER);
        blockWithTexture(ModBlocks.WAXED_OXIDIZED_CUT_SILVER, ModBlocks.OXIDIZED_CUT_SILVER);

        // Cut Silver Stairs
        stairs(ModBlocks.CUT_SILVER_STAIRS, ModBlocks.CUT_SILVER);
        stairs(ModBlocks.EXPOSED_CUT_SILVER_STAIRS, ModBlocks.EXPOSED_CUT_SILVER);
        stairs(ModBlocks.WEATHERED_CUT_SILVER_STAIRS, ModBlocks.WEATHERED_CUT_SILVER);
        stairs(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS, ModBlocks.OXIDIZED_CUT_SILVER);
        stairs(ModBlocks.WAXED_CUT_SILVER_STAIRS, ModBlocks.CUT_SILVER);
        stairs(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS, ModBlocks.EXPOSED_CUT_SILVER);
        stairs(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS, ModBlocks.WEATHERED_CUT_SILVER);
        stairs(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS, ModBlocks.OXIDIZED_CUT_SILVER);

        // Cut Silver Slab
        slab(ModBlocks.CUT_SILVER_SLAB, ModBlocks.CUT_SILVER);
        slab(ModBlocks.EXPOSED_CUT_SILVER_SLAB, ModBlocks.EXPOSED_CUT_SILVER);
        slab(ModBlocks.WEATHERED_CUT_SILVER_SLAB, ModBlocks.WEATHERED_CUT_SILVER);
        slab(ModBlocks.OXIDIZED_CUT_SILVER_SLAB, ModBlocks.OXIDIZED_CUT_SILVER);
        slab(ModBlocks.WAXED_CUT_SILVER_SLAB, ModBlocks.CUT_SILVER);
        slab(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB, ModBlocks.EXPOSED_CUT_SILVER);
        slab(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB, ModBlocks.WEATHERED_CUT_SILVER);
        slab(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB, ModBlocks.OXIDIZED_CUT_SILVER);

        // Chiseled Silver
        horizontalBlock(ModBlocks.CHISELED_SILVER);
        horizontalBlock(ModBlocks.EXPOSED_CHISELED_SILVER);
        horizontalBlock(ModBlocks.WEATHERED_CHISELED_SILVER);
        horizontalBlock(ModBlocks.OXIDIZED_CHISELED_SILVER);
        horizontalBlockWithTexture(ModBlocks.WAXED_CHISELED_SILVER, ModBlocks.CHISELED_SILVER);
        horizontalBlockWithTexture(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER, ModBlocks.EXPOSED_CHISELED_SILVER);
        horizontalBlockWithTexture(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER, ModBlocks.WEATHERED_CHISELED_SILVER);
        horizontalBlockWithTexture(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER, ModBlocks.OXIDIZED_CHISELED_SILVER);

        // Silver Grate
        translucentBlock(ModBlocks.SILVER_GRATE);
        translucentBlock(ModBlocks.EXPOSED_SILVER_GRATE);
        translucentBlock(ModBlocks.WEATHERED_SILVER_GRATE);
        translucentBlock(ModBlocks.OXIDIZED_SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_SILVER_GRATE, ModBlocks.SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_EXPOSED_SILVER_GRATE, ModBlocks.EXPOSED_SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_WEATHERED_SILVER_GRATE, ModBlocks.WEATHERED_SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE, ModBlocks.OXIDIZED_SILVER_GRATE);

        // Silver Trapdoor
        trapdoor(ModBlocks.SILVER_TRAPDOOR);
        trapdoor(ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoor(ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoor(ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_SILVER_TRAPDOOR, ModBlocks.SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR, ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR, ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR, ModBlocks.OXIDIZED_SILVER_TRAPDOOR);

        door(ModBlocks.SILVER_DOOR);
        door(ModBlocks.EXPOSED_SILVER_DOOR);
        door(ModBlocks.WEATHERED_SILVER_DOOR);
        door(ModBlocks.OXIDIZED_SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_SILVER_DOOR, ModBlocks.SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_DOOR, ModBlocks.EXPOSED_SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_DOOR, ModBlocks.WEATHERED_SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR, ModBlocks.OXIDIZED_SILVER_DOOR);
    }

    private void stairs(DeferredBlock<StairBlock> stairs, DeferredBlock<Block> parentBlock) {
        stairsBlock(stairs.get(), blockTexture(parentBlock.get()));
    }

    private void slab(DeferredBlock<SlabBlock> slab, DeferredBlock<Block> parentBlock) {
        ResourceLocation texture = blockTexture(parentBlock.get());
        slabBlock(slab.get(), texture, texture);
    }

    private void door(DeferredBlock<DoorBlock> doorBlock) {
        this.doorWithTexture(doorBlock, doorBlock);
    }

    private void doorWithTexture(DeferredBlock<DoorBlock> doorBlock, DeferredBlock<DoorBlock> textureBlock) {
        doorBlockWithRenderType(doorBlock.get(),
                BlockUtils.getBlockResourceLocation(textureBlock.get(), "bottom"),
                BlockUtils.getBlockResourceLocation(textureBlock.get(), "top"),
                RenderType.cutout().name
        );
    }

    private void trapdoor(DeferredBlock<TrapDoorBlock> trapdoor) {
        this.trapdoorWithTexture(trapdoor, trapdoor);
    }

    private void trapdoorWithTexture(DeferredBlock<TrapDoorBlock> trapdoor, DeferredBlock<TrapDoorBlock> textureBlock) {
        trapdoorBlockWithRenderType(trapdoor.get(),
                BlockUtils.getBlockResourceLocation(textureBlock.get()),
                true,
                RenderType.cutout().name
        );
    }

    private void simpleBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlock(DeferredBlock<Block> block) {
        horizontalBlock(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlockWithTexture(DeferredBlock<Block> block, DeferredBlock<Block> textureBlock) {
        ModelFile cubeAll = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get()));
        horizontalBlock(block.get(), cubeAll);
    }

    private void pillarBlock(DeferredBlock<Block> block) {
        ResourceLocation side = BlockUtils.getBlockResourceLocation(block.get());
        ResourceLocation end = BlockUtils.getBlockResourceLocation(block.get(), "top");
        simpleBlock(block.get(), this.models().cubeColumn(BlockUtils.getName(block.get()), side, end));
    }

    private void pillarBlockWithTexture(DeferredBlock<Block> block, DeferredBlock<Block> textureBlock) {
        ResourceLocation side = BlockUtils.getBlockResourceLocation(textureBlock.get());
        ResourceLocation end = BlockUtils.getBlockResourceLocation(textureBlock.get(), "top");
        simpleBlock(block.get(), this.models().cubeColumn(BlockUtils.getName(block.get()), side, end));
    }

    private void blockWithTexture(DeferredBlock<Block> block, DeferredBlock<Block> textureBlock) {
        ModelFile cubeAll = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get()));
        simpleBlock(block.get(), cubeAll);
    }

    private void translucentBlock(DeferredBlock<Block> block) {
        this.translucentBlock(block, block);
    }

    private void translucentBlock(DeferredBlock<Block> block, DeferredBlock<Block> textureBlock) {
        ModelFile translucentCube = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get())).renderType(RenderType.translucent().name);
        this.getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(translucentCube));
    }
}
