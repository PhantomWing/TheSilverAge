package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.utils.BlockUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
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

        simpleBlock(ModBlocks.SILVER_BLOCK);
        simpleBlock(ModBlocks.EXPOSED_SILVER);
        simpleBlock(ModBlocks.WEATHERED_SILVER);
        simpleBlock(ModBlocks.OXIDIZED_SILVER);

        simpleBlock(ModBlocks.CUT_SILVER);
        simpleBlock(ModBlocks.CHISELED_SILVER);
        simpleBlock(ModBlocks.SILVER_GRATE);

        stairs(ModBlocks.CUT_SILVER_STAIRS, ModBlocks.CUT_SILVER);
        slab(ModBlocks.CUT_SILVER_SLAB, ModBlocks.CUT_SILVER);
        door(ModBlocks.SILVER_DOOR);
        trapdoor(ModBlocks.SILVER_TRAPDOOR);
    }

    private void stairs(DeferredBlock<StairBlock> stairs, DeferredBlock<Block> parentBlock) {
        stairsBlock(stairs.get(), blockTexture(parentBlock.get()));
    }

    private void slab(DeferredBlock<SlabBlock> slab, DeferredBlock<Block> parentBlock) {
        ResourceLocation texture = blockTexture(parentBlock.get());
        slabBlock(slab.get(), texture, texture);
    }

    private void door(DeferredBlock<DoorBlock> door) {
        doorBlockWithRenderType(door.get(),
                BlockUtils.getBlockResourceLocation(door.get(), "bottom"),
                BlockUtils.getBlockResourceLocation(door.get(), "top"),
                RenderType.cutout().name
        );
    }

    private void trapdoor(DeferredBlock<TrapDoorBlock> trapdoor) {
        trapdoorBlockWithRenderType(trapdoor.get(),
                BlockUtils.getBlockResourceLocation(trapdoor.get()),
                true,
                RenderType.cutout().name
        );
    }

    private void simpleBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(), cubeAll(block.get()));
    }
}
