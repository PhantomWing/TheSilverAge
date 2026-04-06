package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.HorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.block.custom.SilverBulbBlock;
import com.phantomwing.thesilverage.utils.BlockUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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

        // Redstone blocks
        moonPhaseDetector(ModBlocks.MOON_PHASE_DETECTOR);

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

        // Silver Trapdoor
        trapdoor(ModBlocks.SILVER_TRAPDOOR);
        trapdoor(ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoor(ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoor(ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_SILVER_TRAPDOOR, ModBlocks.SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR, ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR, ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoorWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR, ModBlocks.OXIDIZED_SILVER_TRAPDOOR);

        // Silver Door
        door(ModBlocks.SILVER_DOOR);
        door(ModBlocks.EXPOSED_SILVER_DOOR);
        door(ModBlocks.WEATHERED_SILVER_DOOR);
        door(ModBlocks.OXIDIZED_SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_SILVER_DOOR, ModBlocks.SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_DOOR, ModBlocks.EXPOSED_SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_DOOR, ModBlocks.WEATHERED_SILVER_DOOR);
        doorWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR, ModBlocks.OXIDIZED_SILVER_DOOR);

        // Silver Grate
        translucentBlock(ModBlocks.SILVER_GRATE);
        translucentBlock(ModBlocks.EXPOSED_SILVER_GRATE);
        translucentBlock(ModBlocks.WEATHERED_SILVER_GRATE);
        translucentBlock(ModBlocks.OXIDIZED_SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_SILVER_GRATE, ModBlocks.SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_EXPOSED_SILVER_GRATE, ModBlocks.EXPOSED_SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_WEATHERED_SILVER_GRATE, ModBlocks.WEATHERED_SILVER_GRATE);
        translucentBlock(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE, ModBlocks.OXIDIZED_SILVER_GRATE);

        // Silver Bulb
        bulb(ModBlocks.SILVER_BULB);
        bulb(ModBlocks.EXPOSED_SILVER_BULB);
        bulb(ModBlocks.WEATHERED_SILVER_BULB);
        bulb(ModBlocks.OXIDIZED_SILVER_BULB);
        bulb(ModBlocks.WAXED_SILVER_BULB, ModBlocks.SILVER_BULB);
        bulb(ModBlocks.WAXED_EXPOSED_SILVER_BULB, ModBlocks.EXPOSED_SILVER_BULB);
        bulb(ModBlocks.WAXED_WEATHERED_SILVER_BULB, ModBlocks.WEATHERED_SILVER_BULB);
        bulb(ModBlocks.WAXED_OXIDIZED_SILVER_BULB, ModBlocks.OXIDIZED_SILVER_BULB);
    }

    private void stairs(RegistryObject<? extends StairBlock> stairs, RegistryObject<Block> parentBlock) {
        stairsBlock(stairs.get(), blockTexture(parentBlock.get()));
    }

    private void slab(RegistryObject<? extends SlabBlock> slab, RegistryObject<Block> parentBlock) {
        ResourceLocation texture = blockTexture(parentBlock.get());
        slabBlock(slab.get(), texture, texture);
    }

    private void door(RegistryObject<? extends DoorBlock> doorBlock) {
        this.doorWithTexture(doorBlock, doorBlock);
    }

    private void doorWithTexture(RegistryObject<? extends DoorBlock> doorBlock, RegistryObject<? extends DoorBlock> textureBlock) {
        doorBlockWithRenderType(doorBlock.get(),
                BlockUtils.getBlockResourceLocation(textureBlock.get(), "bottom"),
                BlockUtils.getBlockResourceLocation(textureBlock.get(), "top"),
                "cutout"
        );
    }

    private void trapdoor(RegistryObject<? extends TrapDoorBlock> trapdoor) {
        this.trapdoorWithTexture(trapdoor, trapdoor);
    }

    private void trapdoorWithTexture(RegistryObject<? extends TrapDoorBlock> trapdoor, RegistryObject<? extends TrapDoorBlock> textureBlock) {
        trapdoorBlockWithRenderType(trapdoor.get(),
                BlockUtils.getBlockResourceLocation(textureBlock.get()),
                true,
                "cutout"
        );
    }

    private void simpleBlock(RegistryObject<Block> block) {
        simpleBlock(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlock(RegistryObject<? extends HorizontalFacingBlock> block) {
        horizontalBlock(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlockWithTexture(RegistryObject<? extends HorizontalFacingBlock> block, RegistryObject<? extends HorizontalFacingBlock> textureBlock) {
        ModelFile cubeAll = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get()));
        horizontalBlock(block.get(), cubeAll);
    }

    private void pillarBlock(RegistryObject<Block> block) {
        ResourceLocation side = BlockUtils.getBlockResourceLocation(block.get());
        ResourceLocation end = BlockUtils.getBlockResourceLocation(block.get(), "top");
        simpleBlock(block.get(), this.models().cubeColumn(BlockUtils.getName(block.get()), side, end));
    }

    private void pillarBlockWithTexture(RegistryObject<Block> block, RegistryObject<Block> textureBlock) {
        ResourceLocation side = BlockUtils.getBlockResourceLocation(textureBlock.get());
        ResourceLocation end = BlockUtils.getBlockResourceLocation(textureBlock.get(), "top");
        simpleBlock(block.get(), this.models().cubeColumn(BlockUtils.getName(block.get()), side, end));
    }

    private void blockWithTexture(RegistryObject<Block> block, RegistryObject<Block> textureBlock) {
        ModelFile cubeAll = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get()));
        simpleBlock(block.get(), cubeAll);
    }

    private void moonPhaseDetector(RegistryObject<? extends MoonPhaseDetectorBlock> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            if (state.getValue(MoonPhaseDetectorBlock.INVERTED)) {
                ModelFile invertedModel = this.models().withExistingParent(BlockUtils.getName(block.get()) + "_inverted", new ResourceLocation("block/template_daylight_detector"))
                        .texture("side", BlockUtils.getBlockResourceLocation(block.get(), "side"))
                        .texture("top", BlockUtils.getBlockResourceLocation(block.get(), "inverted_top"));

                return ConfiguredModel.builder().modelFile(invertedModel).build();
            } else {
                ModelFile model = this.models().withExistingParent(BlockUtils.getName(block.get()), new ResourceLocation("block/template_daylight_detector"))
                        .texture("side", BlockUtils.getBlockResourceLocation(block.get(), "side"))
                        .texture("top", BlockUtils.getBlockResourceLocation(block.get(), "top"));

                return ConfiguredModel.builder().modelFile(model).build();
            }
        });
    }

    private void bulb(RegistryObject<Block> block) {
        this.bulb(block, block);
    }

    private void bulb(RegistryObject<Block> block, RegistryObject<Block> textureBlock) {
        Block b = block.get();
        Block tb = textureBlock.get();
        String name = BlockUtils.getName(b);
        String texName = BlockUtils.getName(tb);

        getVariantBuilder(b)
                .partialState().with(SilverBulbBlock.LIT, false).with(SilverBulbBlock.POWERED, false)
                .modelForState().modelFile(models().cubeAll(name, new ResourceLocation(TheSilverAge.MOD_ID, "block/" + texName))).addModel()
                .partialState().with(SilverBulbBlock.LIT, false).with(SilverBulbBlock.POWERED, true)
                .modelForState().modelFile(models().cubeAll(name + "_powered", new ResourceLocation(TheSilverAge.MOD_ID, "block/" + texName + "_powered"))).addModel()
                .partialState().with(SilverBulbBlock.LIT, true).with(SilverBulbBlock.POWERED, false)
                .modelForState().modelFile(models().cubeAll(name + "_lit", new ResourceLocation(TheSilverAge.MOD_ID, "block/" + texName + "_lit"))).addModel()
                .partialState().with(SilverBulbBlock.LIT, true).with(SilverBulbBlock.POWERED, true)
                .modelForState().modelFile(models().cubeAll(name + "_lit_powered", new ResourceLocation(TheSilverAge.MOD_ID, "block/" + texName + "_lit_powered"))).addModel();
    }

    private void blockItem(RegistryObject<?> deferredBlock, String appendix) {
        simpleBlockItem(((RegistryObject<Block>) deferredBlock).get(), new ModelFile.UncheckedModelFile("tutorialmod:block/" + deferredBlock.getId().getPath() + appendix));
    }

    private void translucentBlock(RegistryObject<Block> block) {
        this.translucentBlock(block, block);
    }

    private void translucentBlock(RegistryObject<Block> block, RegistryObject<Block> textureBlock) {
        ModelFile translucentCube = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get())).renderType("translucent");
        this.getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(translucentCube));
    }
}
