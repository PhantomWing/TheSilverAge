package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.custom.HorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.block.custom.SilverTorchBlock;
import com.phantomwing.thesilverage.block.custom.SilverWallTorchBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverHorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverFullBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverSlabBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverGrateBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverBulbBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverStairBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverTrapDoorBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverDoorBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverBarsBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverChainBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverLanternBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverPillarBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverWallBlock;
import com.phantomwing.thesilverage.sound.ModSoundTypes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(TheSilverAge.MOD_ID, Registries.BLOCK);

    // Ores
    public static final RegistrySupplier<Block> SILVER_ORE = registerSilverBlock("silver_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE),
            (props) -> new DropExperienceBlock(ConstantInt.of(0), props)
    );
    public static final RegistrySupplier<Block> DEEPSLATE_SILVER_ORE = registerSilverBlock("deepslate_silver_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE),
            (props) -> new DropExperienceBlock(ConstantInt.of(0), props)
    );

    // Storage blocks
    public static final RegistrySupplier<Block> RAW_SILVER_BLOCK = registerSilverBlock("raw_silver_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).mapColor(MapColor.METAL),
            Block::new
    );

    // Redstone blocks
    public static final RegistrySupplier<MoonPhaseDetectorBlock> MOON_PHASE_DETECTOR = registerSilverBlock("moon_phase_detector",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BELL).strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(ModSoundTypes.SILVER).lightLevel((blockState) -> 3),
            MoonPhaseDetectorBlock::new
    );

    // Silver block
    public static final RegistrySupplier<Block> SILVER_BLOCK = registerWeatheringSilverBlock("silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> EXPOSED_SILVER = registerWeatheringSilverBlock("exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WEATHERED_SILVER = registerWeatheringSilverBlock("weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> OXIDIZED_SILVER = registerWeatheringSilverBlock("oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> WAXED_SILVER_BLOCK = registerSilverBlock("waxed_silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> WAXED_EXPOSED_SILVER = registerSilverBlock("waxed_exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WAXED_WEATHERED_SILVER = registerSilverBlock("waxed_weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_SILVER = registerSilverBlock("waxed_oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    // Decorative silver blocks
    public static final RegistrySupplier<Block> CUT_SILVER = registerWeatheringSilverBlock("cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> EXPOSED_CUT_SILVER = registerWeatheringSilverBlock("exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WEATHERED_CUT_SILVER = registerWeatheringSilverBlock("weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> OXIDIZED_CUT_SILVER = registerWeatheringSilverBlock("oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> WAXED_CUT_SILVER = registerSilverBlock("waxed_cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> WAXED_EXPOSED_CUT_SILVER = registerSilverBlock("waxed_exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WAXED_WEATHERED_CUT_SILVER = registerSilverBlock("waxed_weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_CUT_SILVER = registerSilverBlock("waxed_oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Bricks
    public static final RegistrySupplier<Block> SILVER_BRICKS = registerWeatheringSilverBlock("silver_bricks", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> EXPOSED_SILVER_BRICKS = registerWeatheringSilverBlock("exposed_silver_bricks", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WEATHERED_SILVER_BRICKS = registerWeatheringSilverBlock("weathered_silver_bricks", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> OXIDIZED_SILVER_BRICKS = registerWeatheringSilverBlock("oxidized_silver_bricks", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> WAXED_SILVER_BRICKS = registerSilverBlock("waxed_silver_bricks", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> WAXED_EXPOSED_SILVER_BRICKS = registerSilverBlock("waxed_exposed_silver_bricks", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WAXED_WEATHERED_SILVER_BRICKS = registerSilverBlock("waxed_weathered_silver_bricks", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_SILVER_BRICKS = registerSilverBlock("waxed_oxidized_silver_bricks", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Brick Slab
    public static final RegistrySupplier<SlabBlock> SILVER_BRICK_SLAB = registerWeatheringSilverSlab("silver_brick_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<SlabBlock> EXPOSED_SILVER_BRICK_SLAB = registerWeatheringSilverSlab("exposed_silver_brick_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<SlabBlock> WEATHERED_SILVER_BRICK_SLAB = registerWeatheringSilverSlab("weathered_silver_brick_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<SlabBlock> OXIDIZED_SILVER_BRICK_SLAB = registerWeatheringSilverSlab("oxidized_silver_brick_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<SlabBlock> WAXED_SILVER_BRICK_SLAB = registerSilverSlab("waxed_silver_brick_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<SlabBlock> WAXED_EXPOSED_SILVER_BRICK_SLAB = registerSilverSlab("waxed_exposed_silver_brick_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<SlabBlock> WAXED_WEATHERED_SILVER_BRICK_SLAB = registerSilverSlab("waxed_weathered_silver_brick_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<SlabBlock> WAXED_OXIDIZED_SILVER_BRICK_SLAB = registerSilverSlab("waxed_oxidized_silver_brick_slab", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Brick Stairs
    public static final RegistrySupplier<StairBlock> SILVER_BRICK_STAIRS = registerWeatheringSilverStairs("silver_brick_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<StairBlock> EXPOSED_SILVER_BRICK_STAIRS = registerWeatheringSilverStairs("exposed_silver_brick_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<StairBlock> WEATHERED_SILVER_BRICK_STAIRS = registerWeatheringSilverStairs("weathered_silver_brick_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<StairBlock> OXIDIZED_SILVER_BRICK_STAIRS = registerWeatheringSilverStairs("oxidized_silver_brick_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<StairBlock> WAXED_SILVER_BRICK_STAIRS = registerSilverStairs("waxed_silver_brick_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<StairBlock> WAXED_EXPOSED_SILVER_BRICK_STAIRS = registerSilverStairs("waxed_exposed_silver_brick_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<StairBlock> WAXED_WEATHERED_SILVER_BRICK_STAIRS = registerSilverStairs("waxed_weathered_silver_brick_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<StairBlock> WAXED_OXIDIZED_SILVER_BRICK_STAIRS = registerSilverStairs("waxed_oxidized_silver_brick_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Brick Wall
    public static final RegistrySupplier<WallBlock> SILVER_BRICK_WALL = registerWeatheringSilverWall("silver_brick_wall", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<WallBlock> EXPOSED_SILVER_BRICK_WALL = registerWeatheringSilverWall("exposed_silver_brick_wall", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<WallBlock> WEATHERED_SILVER_BRICK_WALL = registerWeatheringSilverWall("weathered_silver_brick_wall", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<WallBlock> OXIDIZED_SILVER_BRICK_WALL = registerWeatheringSilverWall("oxidized_silver_brick_wall", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<WallBlock> WAXED_SILVER_BRICK_WALL = registerSilverWall("waxed_silver_brick_wall", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<WallBlock> WAXED_EXPOSED_SILVER_BRICK_WALL = registerSilverWall("waxed_exposed_silver_brick_wall", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<WallBlock> WAXED_WEATHERED_SILVER_BRICK_WALL = registerSilverWall("waxed_weathered_silver_brick_wall", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<WallBlock> WAXED_OXIDIZED_SILVER_BRICK_WALL = registerSilverWall("waxed_oxidized_silver_brick_wall", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<SlabBlock> CUT_SILVER_SLAB = registerWeatheringSilverSlab("cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<SlabBlock> EXPOSED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<SlabBlock> WEATHERED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<SlabBlock> OXIDIZED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<SlabBlock> WAXED_CUT_SILVER_SLAB = registerSilverSlab("waxed_cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<SlabBlock> WAXED_EXPOSED_CUT_SILVER_SLAB = registerSilverSlab("waxed_exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<SlabBlock> WAXED_WEATHERED_CUT_SILVER_SLAB = registerSilverSlab("waxed_weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<SlabBlock> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerSilverSlab("waxed_oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<StairBlock> CUT_SILVER_STAIRS = registerWeatheringSilverStairs("cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<StairBlock> EXPOSED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<StairBlock> WEATHERED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<StairBlock> OXIDIZED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<StairBlock> WAXED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<StairBlock> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<StairBlock> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<StairBlock> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<HorizontalFacingBlock> CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<HorizontalFacingBlock> EXPOSED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<HorizontalFacingBlock> WEATHERED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<HorizontalFacingBlock> OXIDIZED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<HorizontalFacingBlock> WAXED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<HorizontalFacingBlock> WAXED_EXPOSED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<HorizontalFacingBlock> WAXED_WEATHERED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<HorizontalFacingBlock> WAXED_OXIDIZED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Pillar
    public static final RegistrySupplier<RotatedPillarBlock> SILVER_PILLAR = registerWeatheringSilverPillar("silver_pillar", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<RotatedPillarBlock> EXPOSED_SILVER_PILLAR = registerWeatheringSilverPillar("exposed_silver_pillar", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<RotatedPillarBlock> WEATHERED_SILVER_PILLAR = registerWeatheringSilverPillar("weathered_silver_pillar", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<RotatedPillarBlock> OXIDIZED_SILVER_PILLAR = registerWeatheringSilverPillar("oxidized_silver_pillar", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<RotatedPillarBlock> WAXED_SILVER_PILLAR = registerSilverPillar("waxed_silver_pillar", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<RotatedPillarBlock> WAXED_EXPOSED_SILVER_PILLAR = registerSilverPillar("waxed_exposed_silver_pillar", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<RotatedPillarBlock> WAXED_WEATHERED_SILVER_PILLAR = registerSilverPillar("waxed_weathered_silver_pillar", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<RotatedPillarBlock> WAXED_OXIDIZED_SILVER_PILLAR = registerSilverPillar("waxed_oxidized_silver_pillar", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> SILVER_GRATE = registerWeatheringSilverGrate("silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> EXPOSED_SILVER_GRATE = registerWeatheringSilverGrate("exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WEATHERED_SILVER_GRATE = registerWeatheringSilverGrate("weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> OXIDIZED_SILVER_GRATE = registerWeatheringSilverGrate("oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> WAXED_SILVER_GRATE = registerSilverGrate("waxed_silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> WAXED_EXPOSED_SILVER_GRATE = registerSilverGrate("waxed_exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WAXED_WEATHERED_SILVER_GRATE = registerSilverGrate("waxed_weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_SILVER_GRATE = registerSilverGrate("waxed_oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> SILVER_BULB = registerWeatheringSilverBulb("silver_bulb", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> EXPOSED_SILVER_BULB = registerWeatheringSilverBulb("exposed_silver_bulb", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WEATHERED_SILVER_BULB = registerWeatheringSilverBulb("weathered_silver_bulb", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> OXIDIZED_SILVER_BULB = registerWeatheringSilverBulb("oxidized_silver_bulb", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<Block> WAXED_SILVER_BULB = registerSilverBulb("waxed_silver_bulb", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<Block> WAXED_EXPOSED_SILVER_BULB = registerSilverBulb("waxed_exposed_silver_bulb", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<Block> WAXED_WEATHERED_SILVER_BULB = registerSilverBulb("waxed_weathered_silver_bulb", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_SILVER_BULB = registerSilverBulb("waxed_oxidized_silver_bulb", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<TrapDoorBlock> SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("silver_trapdoor", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<TrapDoorBlock> EXPOSED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("exposed_silver_trapdoor", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<TrapDoorBlock> WEATHERED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("weathered_silver_trapdoor", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<TrapDoorBlock> OXIDIZED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("oxidized_silver_trapdoor", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<TrapDoorBlock> WAXED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_silver_trapdoor", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<TrapDoorBlock> WAXED_EXPOSED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_exposed_silver_trapdoor", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<TrapDoorBlock> WAXED_WEATHERED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_weathered_silver_trapdoor", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<TrapDoorBlock> WAXED_OXIDIZED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_oxidized_silver_trapdoor", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<DoorBlock> SILVER_DOOR = registerWeatheringSilverDoor("silver_door", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<DoorBlock> EXPOSED_SILVER_DOOR = registerWeatheringSilverDoor("exposed_silver_door", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<DoorBlock> WEATHERED_SILVER_DOOR = registerWeatheringSilverDoor("weathered_silver_door", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<DoorBlock> OXIDIZED_SILVER_DOOR = registerWeatheringSilverDoor("oxidized_silver_door", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<DoorBlock> WAXED_SILVER_DOOR = registerSilverDoor("waxed_silver_door", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<DoorBlock> WAXED_EXPOSED_SILVER_DOOR = registerSilverDoor("waxed_exposed_silver_door", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<DoorBlock> WAXED_WEATHERED_SILVER_DOOR = registerSilverDoor("waxed_weathered_silver_door", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<DoorBlock> WAXED_OXIDIZED_SILVER_DOOR = registerSilverDoor("waxed_oxidized_silver_door", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Lantern
    public static final RegistrySupplier<LanternBlock> SILVER_LANTERN = registerWeatheringSilverLantern("silver_lantern", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<LanternBlock> EXPOSED_SILVER_LANTERN = registerWeatheringSilverLantern("exposed_silver_lantern", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<LanternBlock> WEATHERED_SILVER_LANTERN = registerWeatheringSilverLantern("weathered_silver_lantern", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<LanternBlock> OXIDIZED_SILVER_LANTERN = registerWeatheringSilverLantern("oxidized_silver_lantern", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<LanternBlock> WAXED_SILVER_LANTERN = registerSilverLantern("waxed_silver_lantern", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<LanternBlock> WAXED_EXPOSED_SILVER_LANTERN = registerSilverLantern("waxed_exposed_silver_lantern", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<LanternBlock> WAXED_WEATHERED_SILVER_LANTERN = registerSilverLantern("waxed_weathered_silver_lantern", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<LanternBlock> WAXED_OXIDIZED_SILVER_LANTERN = registerSilverLantern("waxed_oxidized_silver_lantern", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Chain
    public static final RegistrySupplier<ChainBlock> SILVER_CHAIN = registerWeatheringSilverChain("silver_chain", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<ChainBlock> EXPOSED_SILVER_CHAIN = registerWeatheringSilverChain("exposed_silver_chain", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<ChainBlock> WEATHERED_SILVER_CHAIN = registerWeatheringSilverChain("weathered_silver_chain", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<ChainBlock> OXIDIZED_SILVER_CHAIN = registerWeatheringSilverChain("oxidized_silver_chain", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<ChainBlock> WAXED_SILVER_CHAIN = registerSilverChain("waxed_silver_chain", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<ChainBlock> WAXED_EXPOSED_SILVER_CHAIN = registerSilverChain("waxed_exposed_silver_chain", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<ChainBlock> WAXED_WEATHERED_SILVER_CHAIN = registerSilverChain("waxed_weathered_silver_chain", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<ChainBlock> WAXED_OXIDIZED_SILVER_CHAIN = registerSilverChain("waxed_oxidized_silver_chain", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Bars
    public static final RegistrySupplier<IronBarsBlock> SILVER_BARS = registerWeatheringSilverBars("silver_bars", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<IronBarsBlock> EXPOSED_SILVER_BARS = registerWeatheringSilverBars("exposed_silver_bars", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<IronBarsBlock> WEATHERED_SILVER_BARS = registerWeatheringSilverBars("weathered_silver_bars", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<IronBarsBlock> OXIDIZED_SILVER_BARS = registerWeatheringSilverBars("oxidized_silver_bars", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistrySupplier<IronBarsBlock> WAXED_SILVER_BARS = registerSilverBars("waxed_silver_bars", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistrySupplier<IronBarsBlock> WAXED_EXPOSED_SILVER_BARS = registerSilverBars("waxed_exposed_silver_bars", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistrySupplier<IronBarsBlock> WAXED_WEATHERED_SILVER_BARS = registerSilverBars("waxed_weathered_silver_bars", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistrySupplier<IronBarsBlock> WAXED_OXIDIZED_SILVER_BARS = registerSilverBars("waxed_oxidized_silver_bars", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Torch (non-oxidizable; violet flame via the custom silver_flame particle)
    public static final RegistrySupplier<TorchBlock> SILVER_TORCH = registerSilverBlock("silver_torch",
            BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH),
            SilverTorchBlock::new);
    public static final RegistrySupplier<WallTorchBlock> SILVER_WALL_TORCH = registerSilverBlock("silver_wall_torch",
            BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH),
            SilverWallTorchBlock::new);

    private static RegistrySupplier<Block> registerWeatheringSilverBlock(String name, WeatheringCopper.WeatherState weatherState) {
        return registerSilverBlock(name, getSilverProps(weatherState), (props) -> new WeatheringSilverFullBlock(weatherState, props));
    }

    private static RegistrySupplier<HorizontalFacingBlock> registerWeatheringSilverHorizontalFacingBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverHorizontalFacingBlock(weatherState, props));
    }

    private static RegistrySupplier<HorizontalFacingBlock> registerSilverHorizontalFacingBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, HorizontalFacingBlock::new);
    }

    private static RegistrySupplier<Block> registerWeatheringSilverGrate(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE)).sound(ModSoundTypes.SILVER_GRATE);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverGrateBlock(weatherState, props));
    }

    private static RegistrySupplier<Block> registerSilverGrate(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE)).sound(ModSoundTypes.SILVER_GRATE);
        return registerSilverBlock(name, baseProps, WaterloggedTransparentBlock::new);
    }

    private static RegistrySupplier<Block> registerWeatheringSilverBulb(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB)).sound(ModSoundTypes.SILVER_BULB);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverBulbBlock(weatherState, props));
    }

    private static RegistrySupplier<Block> registerSilverBulb(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB)).sound(ModSoundTypes.SILVER_BULB);
        return registerSilverBlock(name, baseProps, CopperBulbBlock::new);
    }

    private static RegistrySupplier<SlabBlock> registerWeatheringSilverSlab(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverSlabBlock(weatherState, props));
    }

    private static RegistrySupplier<SlabBlock> registerSilverSlab(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, SlabBlock::new);
    }

    private static RegistrySupplier<StairBlock> registerWeatheringSilverStairs(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverStairBlock(weatherState, Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    private static RegistrySupplier<StairBlock> registerSilverStairs(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new StairBlock(Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    // forceSolidOn() matches how vanilla builds its walls, so blocks can still be
    // placed/attached on top of them.
    private static RegistrySupplier<WallBlock> registerWeatheringSilverWall(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState).forceSolidOn();
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverWallBlock(weatherState, props));
    }

    private static RegistrySupplier<WallBlock> registerSilverWall(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState).forceSolidOn();
        return registerSilverBlock(name, baseProps, WallBlock::new);
    }

    private static RegistrySupplier<RotatedPillarBlock> registerWeatheringSilverPillar(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverPillarBlock(weatherState, props));
    }

    private static RegistrySupplier<RotatedPillarBlock> registerSilverPillar(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, RotatedPillarBlock::new);
    }

    private static RegistrySupplier<TrapDoorBlock> registerWeatheringSilverTrapdoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverTrapDoorBlock(ModBlockSetTypes.SILVER, weatherState, props));
    }

    private static RegistrySupplier<TrapDoorBlock> registerSilverTrapdoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR));
        return registerSilverBlock(name, baseProps, (props) -> new TrapDoorBlock(ModBlockSetTypes.SILVER, props));
    }

    private static RegistrySupplier<DoorBlock> registerWeatheringSilverDoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverDoorBlock(ModBlockSetTypes.SILVER, weatherState, props));
    }

    private static RegistrySupplier<DoorBlock> registerSilverDoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR));
        return registerSilverBlock(name, baseProps, (props) -> new DoorBlock(ModBlockSetTypes.SILVER, props));
    }

    private static RegistrySupplier<LanternBlock> registerWeatheringSilverLantern(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverLanternBlock(weatherState, props));
    }

    private static RegistrySupplier<LanternBlock> registerSilverLantern(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN));
        return registerSilverBlock(name, baseProps, LanternBlock::new);
    }

    private static RegistrySupplier<ChainBlock> registerWeatheringSilverChain(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverChainBlock(weatherState, props));
    }

    private static RegistrySupplier<ChainBlock> registerSilverChain(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));
        return registerSilverBlock(name, baseProps, ChainBlock::new);
    }

    private static RegistrySupplier<IronBarsBlock> registerWeatheringSilverBars(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverBarsBlock(weatherState, props));
    }

    private static RegistrySupplier<IronBarsBlock> registerSilverBars(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS));
        return registerSilverBlock(name, baseProps, IronBarsBlock::new);
    }

    @SuppressWarnings("unused")
    private static RegistrySupplier<Block> registerSilverBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, Block::new);
    }

    private static <T extends Block> RegistrySupplier<T> registerSilverBlock(String name, BlockBehaviour.Properties baseProps, Function<Block.Properties, T> function) {
        return BLOCKS.register(name, () ->  function.apply(baseProps));
    }

    private static BlockBehaviour.Properties getSilverProps(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties baseProps) {
        BlockBehaviour.Properties props = baseProps
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .sound(ModSoundTypes.SILVER)
                .mapColor(MapColor.METAL)
                .instrument(NoteBlockInstrument.BELL);

        switch (weatherState) {
            case UNAFFECTED -> {
                props = props.mapColor(MapColor.METAL);
            }
            case EXPOSED -> {
                props = props.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY);
            }
            case WEATHERED -> {
                props = props.mapColor(MapColor.TERRACOTTA_GRAY);
            }
            case OXIDIZED -> {
                props = props.mapColor(MapColor.TERRACOTTA_BLACK);
            }
        }

        return props;
    }

    private static BlockBehaviour.Properties getSilverProps(WeatheringCopper.WeatherState weatherState) {
        return getSilverProps(weatherState, BlockBehaviour.Properties.of());
    }

    @SuppressWarnings("unused")
    private static BlockBehaviour.Properties getSilverProps() {
        return getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED);
    }

    public static void register() {
        BLOCKS.register();
    }
}
