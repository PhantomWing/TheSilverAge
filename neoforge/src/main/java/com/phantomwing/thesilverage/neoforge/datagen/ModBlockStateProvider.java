package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.HorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.neoforge.utils.BlockUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import dev.architectury.registry.registries.RegistrySupplier;

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

        // Silver Bricks
        simpleBlock(ModBlocks.SILVER_BRICKS);
        simpleBlock(ModBlocks.EXPOSED_SILVER_BRICKS);
        simpleBlock(ModBlocks.WEATHERED_SILVER_BRICKS);
        simpleBlock(ModBlocks.OXIDIZED_SILVER_BRICKS);
        blockWithTexture(ModBlocks.WAXED_SILVER_BRICKS, ModBlocks.SILVER_BRICKS);
        blockWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_BRICKS, ModBlocks.EXPOSED_SILVER_BRICKS);
        blockWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_BRICKS, ModBlocks.WEATHERED_SILVER_BRICKS);
        blockWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS, ModBlocks.OXIDIZED_SILVER_BRICKS);

        // Silver Brick Slab
        slab(ModBlocks.SILVER_BRICK_SLAB, ModBlocks.SILVER_BRICKS);
        slab(ModBlocks.EXPOSED_SILVER_BRICK_SLAB, ModBlocks.EXPOSED_SILVER_BRICKS);
        slab(ModBlocks.WEATHERED_SILVER_BRICK_SLAB, ModBlocks.WEATHERED_SILVER_BRICKS);
        slab(ModBlocks.OXIDIZED_SILVER_BRICK_SLAB, ModBlocks.OXIDIZED_SILVER_BRICKS);
        slab(ModBlocks.WAXED_SILVER_BRICK_SLAB, ModBlocks.SILVER_BRICKS);
        slab(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB, ModBlocks.EXPOSED_SILVER_BRICKS);
        slab(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB, ModBlocks.WEATHERED_SILVER_BRICKS);
        slab(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB, ModBlocks.OXIDIZED_SILVER_BRICKS);

        // Silver Brick Stairs
        stairs(ModBlocks.SILVER_BRICK_STAIRS, ModBlocks.SILVER_BRICKS);
        stairs(ModBlocks.EXPOSED_SILVER_BRICK_STAIRS, ModBlocks.EXPOSED_SILVER_BRICKS);
        stairs(ModBlocks.WEATHERED_SILVER_BRICK_STAIRS, ModBlocks.WEATHERED_SILVER_BRICKS);
        stairs(ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS, ModBlocks.OXIDIZED_SILVER_BRICKS);
        stairs(ModBlocks.WAXED_SILVER_BRICK_STAIRS, ModBlocks.SILVER_BRICKS);
        stairs(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS, ModBlocks.EXPOSED_SILVER_BRICKS);
        stairs(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS, ModBlocks.WEATHERED_SILVER_BRICKS);
        stairs(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS, ModBlocks.OXIDIZED_SILVER_BRICKS);

        // Silver Brick Wall — textured from the bricks of the matching weather stage.
        wall(ModBlocks.SILVER_BRICK_WALL, ModBlocks.SILVER_BRICKS);
        wall(ModBlocks.EXPOSED_SILVER_BRICK_WALL, ModBlocks.EXPOSED_SILVER_BRICKS);
        wall(ModBlocks.WEATHERED_SILVER_BRICK_WALL, ModBlocks.WEATHERED_SILVER_BRICKS);
        wall(ModBlocks.OXIDIZED_SILVER_BRICK_WALL, ModBlocks.OXIDIZED_SILVER_BRICKS);
        wall(ModBlocks.WAXED_SILVER_BRICK_WALL, ModBlocks.SILVER_BRICKS);
        wall(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_WALL, ModBlocks.EXPOSED_SILVER_BRICKS);
        wall(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_WALL, ModBlocks.WEATHERED_SILVER_BRICKS);
        wall(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_WALL, ModBlocks.OXIDIZED_SILVER_BRICKS);

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

        // Silver Pillar — RotatedPillarBlock axis-aware (vertical + horizontal column models).
        // Waxed variants reuse their unwaxed counterpart's textures (matches vanilla copper).
        pillar(ModBlocks.SILVER_PILLAR);
        pillar(ModBlocks.EXPOSED_SILVER_PILLAR);
        pillar(ModBlocks.WEATHERED_SILVER_PILLAR);
        pillar(ModBlocks.OXIDIZED_SILVER_PILLAR);
        pillarWithTexture(ModBlocks.WAXED_SILVER_PILLAR, ModBlocks.SILVER_PILLAR);
        pillarWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_PILLAR, ModBlocks.EXPOSED_SILVER_PILLAR);
        pillarWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_PILLAR, ModBlocks.WEATHERED_SILVER_PILLAR);
        pillarWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR, ModBlocks.OXIDIZED_SILVER_PILLAR);

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
        bulbWithTexture(ModBlocks.WAXED_SILVER_BULB, ModBlocks.SILVER_BULB);
        bulbWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_BULB, ModBlocks.EXPOSED_SILVER_BULB);
        bulbWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_BULB, ModBlocks.WEATHERED_SILVER_BULB);
        bulbWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_BULB, ModBlocks.OXIDIZED_SILVER_BULB);

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

        // Silver Lantern
        lantern(ModBlocks.SILVER_LANTERN);
        lantern(ModBlocks.EXPOSED_SILVER_LANTERN);
        lantern(ModBlocks.WEATHERED_SILVER_LANTERN);
        lantern(ModBlocks.OXIDIZED_SILVER_LANTERN);
        lanternWithTexture(ModBlocks.WAXED_SILVER_LANTERN, ModBlocks.SILVER_LANTERN);
        lanternWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_LANTERN, ModBlocks.EXPOSED_SILVER_LANTERN);
        lanternWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_LANTERN, ModBlocks.WEATHERED_SILVER_LANTERN);
        lanternWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN, ModBlocks.OXIDIZED_SILVER_LANTERN);

        // Silver Chain
        chain(ModBlocks.SILVER_CHAIN);
        chain(ModBlocks.EXPOSED_SILVER_CHAIN);
        chain(ModBlocks.WEATHERED_SILVER_CHAIN);
        chain(ModBlocks.OXIDIZED_SILVER_CHAIN);
        chainWithTexture(ModBlocks.WAXED_SILVER_CHAIN, ModBlocks.SILVER_CHAIN);
        chainWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN, ModBlocks.EXPOSED_SILVER_CHAIN);
        chainWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN, ModBlocks.WEATHERED_SILVER_CHAIN);
        chainWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN, ModBlocks.OXIDIZED_SILVER_CHAIN);

        // Silver Bars
        bars(ModBlocks.SILVER_BARS);
        bars(ModBlocks.EXPOSED_SILVER_BARS);
        bars(ModBlocks.WEATHERED_SILVER_BARS);
        bars(ModBlocks.OXIDIZED_SILVER_BARS);
        barsWithTexture(ModBlocks.WAXED_SILVER_BARS, ModBlocks.SILVER_BARS);
        barsWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_BARS, ModBlocks.EXPOSED_SILVER_BARS);
        barsWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_BARS, ModBlocks.WEATHERED_SILVER_BARS);
        barsWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_BARS, ModBlocks.OXIDIZED_SILVER_BARS);

        // Silver Torch (floor + wall)
        torch(ModBlocks.SILVER_TORCH, ModBlocks.SILVER_WALL_TORCH);
    }

    /** Lantern: standing + hanging models off the vanilla templates, cutout render type. */
    private void lantern(RegistrySupplier<LanternBlock> block) {
        lanternWithTexture(block, block);
    }

    private void lanternWithTexture(RegistrySupplier<LanternBlock> block, RegistrySupplier<LanternBlock> textureBlock) {
        ResourceLocation texture = BlockUtils.getBlockResourceLocation(textureBlock.get());
        ModelFile standing = this.models()
                .withExistingParent(BlockUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("block/template_lantern"))
                .texture("lantern", texture)
                .renderType(RenderType.cutout().name);
        ModelFile hanging = this.models()
                .withExistingParent(BlockUtils.getName(block.get(), "hanging"), ResourceLocation.withDefaultNamespace("block/template_hanging_lantern"))
                .texture("lantern", texture)
                .renderType(RenderType.cutout().name);

        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(LanternBlock.HANGING) ? hanging : standing)
                .build());
    }

    /** Chain: single model rotated per axis (matches vanilla's chain blockstate). */
    private void chain(RegistrySupplier<ChainBlock> block) {
        chainWithTexture(block, block);
    }

    private void chainWithTexture(RegistrySupplier<ChainBlock> block, RegistrySupplier<ChainBlock> textureBlock) {
        // 1.21.1 has no block/template_chain (added in 1.21.9 alongside copper_chain), so
        // inherit the geometry from vanilla block/chain and override its textures.
        ResourceLocation texture = BlockUtils.getBlockResourceLocation(textureBlock.get());
        ModelFile model = this.models()
                .withExistingParent(BlockUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("block/chain"))
                .texture("all", texture)
                .texture("particle", texture)
                .renderType(RenderType.cutout().name);

        getVariantBuilder(block.get()).forAllStates(state -> switch (state.getValue(ChainBlock.AXIS)) {
            case X -> ConfiguredModel.builder().modelFile(model).rotationX(90).rotationY(90).build();
            case Z -> ConfiguredModel.builder().modelFile(model).rotationX(90).build();
            default -> ConfiguredModel.builder().modelFile(model).build();
        });
    }

    /** Bars: vanilla pane multipart (post/post_ends/cap/side), cutout render type. */
    private void bars(RegistrySupplier<IronBarsBlock> block) {
        barsWithTexture(block, block);
    }

    private void barsWithTexture(RegistrySupplier<IronBarsBlock> block, RegistrySupplier<IronBarsBlock> textureBlock) {
        ResourceLocation texture = BlockUtils.getBlockResourceLocation(textureBlock.get());
        paneBlockWithRenderType(block.get(), texture, texture, RenderType.cutout().name);
    }

    /** Torch: floor model + wall model, both cutout. The wall torch has no item. */
    private void torch(RegistrySupplier<TorchBlock> block, RegistrySupplier<WallTorchBlock> wallBlock) {
        ResourceLocation texture = BlockUtils.getBlockResourceLocation(block.get());
        ModelFile standing = this.models()
                .withExistingParent(BlockUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("block/template_torch"))
                .texture("torch", texture)
                .renderType(RenderType.cutout().name);
        ModelFile wall = this.models()
                .withExistingParent(BlockUtils.getName(wallBlock.get()), ResourceLocation.withDefaultNamespace("block/template_torch_wall"))
                .texture("torch", texture)
                .renderType(RenderType.cutout().name);

        getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(standing));
        // Wall torch faces away from the block it is attached to; vanilla offsets by 90°.
        horizontalBlock(wallBlock.get(), state -> wall, 90);
    }

    private void stairs(RegistrySupplier<StairBlock> stairs, RegistrySupplier<Block> parentBlock) {
        stairsBlock(stairs.get(), blockTexture(parentBlock.get()));
    }

    /** Wall multipart (post / side / side_tall) plus the inventory model, off the parent's texture. */
    private void wall(RegistrySupplier<WallBlock> wall, RegistrySupplier<Block> parentBlock) {
        wallBlock(wall.get(), blockTexture(parentBlock.get()));
    }

    private void slab(RegistrySupplier<SlabBlock> slab, RegistrySupplier<Block> parentBlock) {
        ResourceLocation texture = blockTexture(parentBlock.get());
        slabBlock(slab.get(), texture, texture);
    }

    private void bulb(RegistrySupplier<Block> block) {
        bulbWithTexture(block, block);
    }

    private void bulbWithTexture(RegistrySupplier<Block> block, RegistrySupplier<Block> parentBlock) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            boolean lit = state.getValue(CopperBulbBlock.LIT);
            boolean powered = state.getValue(CopperBulbBlock.POWERED);

            String suffix = lit && powered ? "lit_powered" : lit ? "lit" : powered ? "powered" : "";
            String name = BlockUtils.getName(block.get(), suffix);

            ResourceLocation textureResource = BlockUtils.getBlockResourceLocation(parentBlock.get(), suffix);

            ModelFile model = this.models().cubeAll(name, textureResource);

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void door(RegistrySupplier<DoorBlock> doorBlock) {
        this.doorWithTexture(doorBlock, doorBlock);
    }

    private void doorWithTexture(RegistrySupplier<DoorBlock> doorBlock, RegistrySupplier<DoorBlock> textureBlock) {
        doorBlockWithRenderType(doorBlock.get(),
                BlockUtils.getBlockResourceLocation(textureBlock.get(), "bottom"),
                BlockUtils.getBlockResourceLocation(textureBlock.get(), "top"),
                RenderType.cutout().name
        );
    }

    private void trapdoor(RegistrySupplier<TrapDoorBlock> trapdoor) {
        this.trapdoorWithTexture(trapdoor, trapdoor);
    }

    private void trapdoorWithTexture(RegistrySupplier<TrapDoorBlock> trapdoor, RegistrySupplier<TrapDoorBlock> textureBlock) {
        trapdoorBlockWithRenderType(trapdoor.get(),
                BlockUtils.getBlockResourceLocation(textureBlock.get()),
                true,
                RenderType.cutout().name
        );
    }

    private void simpleBlock(RegistrySupplier<Block> block) {
        simpleBlock(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlock(RegistrySupplier<HorizontalFacingBlock> block) {
        horizontalBlock(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlockWithTexture(RegistrySupplier<HorizontalFacingBlock> block, RegistrySupplier<HorizontalFacingBlock> textureBlock) {
        ModelFile cubeAll = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get()));
        horizontalBlock(block.get(), cubeAll);
    }

    private void pillar(RegistrySupplier<RotatedPillarBlock> block) {
        ResourceLocation side = BlockUtils.getBlockResourceLocation(block.get());
        ResourceLocation end = BlockUtils.getBlockResourceLocation(block.get(), "top");
        axisBlock(block.get(), side, end);
    }

    private void pillarWithTexture(RegistrySupplier<RotatedPillarBlock> block, RegistrySupplier<RotatedPillarBlock> textureBlock) {
        ResourceLocation side = BlockUtils.getBlockResourceLocation(textureBlock.get());
        ResourceLocation end = BlockUtils.getBlockResourceLocation(textureBlock.get(), "top");
        axisBlock(block.get(), side, end);
    }

    private void blockWithTexture(RegistrySupplier<Block> block, RegistrySupplier<Block> textureBlock) {
        ModelFile cubeAll = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get()));
        simpleBlock(block.get(), cubeAll);
    }

    private void moonPhaseDetector(RegistrySupplier<MoonPhaseDetectorBlock> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            if (state.getValue(MoonPhaseDetectorBlock.INVERTED)) {
                ModelFile invertedModel = this.models().withExistingParent(BlockUtils.getName(block.get()) + "_inverted", ResourceLocation.withDefaultNamespace("block/template_daylight_detector"))
                        .texture("side", BlockUtils.getBlockResourceLocation(block.get(), "side"))
                        .texture("top", BlockUtils.getBlockResourceLocation(block.get(), "inverted_top"));

                return ConfiguredModel.builder().modelFile(invertedModel).build();
            } else {
                ModelFile model = this.models().withExistingParent(BlockUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("block/template_daylight_detector"))
                        .texture("side", BlockUtils.getBlockResourceLocation(block.get(), "side"))
                        .texture("top", BlockUtils.getBlockResourceLocation(block.get(), "top"));

                return ConfiguredModel.builder().modelFile(model).build();
            }
        });
    }

    private void blockItem(RegistrySupplier<? extends Block> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("tutorialmod:block/" + deferredBlock.getId().getPath() + appendix));
    }

    private void translucentBlock(RegistrySupplier<Block> block) {
        this.translucentBlock(block, block);
    }

    private void translucentBlock(RegistrySupplier<Block> block, RegistrySupplier<Block> textureBlock) {
        ModelFile translucentCube = this.models().cubeAll(BlockUtils.getName(block.get()), this.blockTexture(textureBlock.get())).renderType(RenderType.translucent().name);
        this.getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(translucentCube));
    }
}
