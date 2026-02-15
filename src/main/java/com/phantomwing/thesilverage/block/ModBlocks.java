package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.custom.HorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringCopperHorizontalFacingBlock;
import com.phantomwing.thesilverage.sound.ModSoundTypes;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheSilverAge.MOD_ID);

    // Ores
    public static final DeferredBlock<Block> SILVER_ORE = registerSilverBlock("silver_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE),
            (props) -> new DropExperienceBlock(ConstantInt.of(0), props)
    );
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerSilverBlock("deepslate_silver_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE),
            (props) -> new DropExperienceBlock(ConstantInt.of(0), props)
    );

    // Storage blocks
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerSilverBlock("raw_silver_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).mapColor(MapColor.METAL),
            Block::new
    );

    // Redstone blocks
    public static final DeferredBlock<MoonPhaseDetectorBlock> MOON_PHASE_DETECTOR = registerSilverBlock("moon_phase_detector",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BELL).strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(ModSoundTypes.SILVER).lightLevel((blockState) -> 3),
            MoonPhaseDetectorBlock::new
    );

    // Silver block
    public static final DeferredBlock<Block> SILVER_BLOCK = registerWeatheringSilverBlock("silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_SILVER = registerWeatheringSilverBlock("exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_SILVER = registerWeatheringSilverBlock("weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_SILVER = registerWeatheringSilverBlock("oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_SILVER_BLOCK = registerSilverBlock("waxed_silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> WAXED_EXPOSED_SILVER = registerSilverBlock("waxed_exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WAXED_WEATHERED_SILVER = registerSilverBlock("waxed_weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_SILVER = registerSilverBlock("waxed_oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    // Decorative silver blocks
    public static final DeferredBlock<Block> CUT_SILVER = registerWeatheringSilverBlock("cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_CUT_SILVER = registerWeatheringSilverBlock("exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_CUT_SILVER = registerWeatheringSilverBlock("weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_CUT_SILVER = registerWeatheringSilverBlock("oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_CUT_SILVER = registerSilverBlock("waxed_cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> WAXED_EXPOSED_CUT_SILVER = registerSilverBlock("waxed_exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WAXED_WEATHERED_CUT_SILVER = registerSilverBlock("waxed_weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_CUT_SILVER = registerSilverBlock("waxed_oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<SlabBlock> CUT_SILVER_SLAB = registerWeatheringSilverSlab("cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<SlabBlock> EXPOSED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<SlabBlock> WEATHERED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<SlabBlock> OXIDIZED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<SlabBlock> WAXED_CUT_SILVER_SLAB = registerSilverSlab("waxed_cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<SlabBlock> WAXED_EXPOSED_CUT_SILVER_SLAB = registerSilverSlab("waxed_exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<SlabBlock> WAXED_WEATHERED_CUT_SILVER_SLAB = registerSilverSlab("waxed_weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<SlabBlock> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerSilverSlab("waxed_oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<StairBlock> CUT_SILVER_STAIRS = registerWeatheringSilverStairs("cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<StairBlock> EXPOSED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<StairBlock> WEATHERED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<StairBlock> OXIDIZED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<StairBlock> WAXED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<StairBlock> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<StairBlock> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<StairBlock> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<HorizontalFacingBlock> CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<HorizontalFacingBlock> EXPOSED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<HorizontalFacingBlock> WEATHERED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<HorizontalFacingBlock> OXIDIZED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<HorizontalFacingBlock> WAXED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<HorizontalFacingBlock> WAXED_EXPOSED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<HorizontalFacingBlock> WAXED_WEATHERED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<HorizontalFacingBlock> WAXED_OXIDIZED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> SILVER_GRATE = registerWeatheringSilverGrate("silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_SILVER_GRATE = registerWeatheringSilverGrate("exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_SILVER_GRATE = registerWeatheringSilverGrate("weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_SILVER_GRATE = registerWeatheringSilverGrate("oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_SILVER_GRATE = registerSilverGrate("waxed_silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> WAXED_EXPOSED_SILVER_GRATE = registerSilverGrate("waxed_exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WAXED_WEATHERED_SILVER_GRATE = registerSilverGrate("waxed_weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_SILVER_GRATE = registerSilverGrate("waxed_oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<TrapDoorBlock> SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("silver_trapdoor", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<TrapDoorBlock> EXPOSED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("exposed_silver_trapdoor", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<TrapDoorBlock> WEATHERED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("weathered_silver_trapdoor", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<TrapDoorBlock> OXIDIZED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("oxidized_silver_trapdoor", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<TrapDoorBlock> WAXED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_silver_trapdoor", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<TrapDoorBlock> WAXED_EXPOSED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_exposed_silver_trapdoor", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<TrapDoorBlock> WAXED_WEATHERED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_weathered_silver_trapdoor", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<TrapDoorBlock> WAXED_OXIDIZED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_oxidized_silver_trapdoor", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<DoorBlock> SILVER_DOOR = registerWeatheringSilverDoor("silver_door", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<DoorBlock> EXPOSED_SILVER_DOOR = registerWeatheringSilverDoor("exposed_silver_door", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<DoorBlock> WEATHERED_SILVER_DOOR = registerWeatheringSilverDoor("weathered_silver_door", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<DoorBlock> OXIDIZED_SILVER_DOOR = registerWeatheringSilverDoor("oxidized_silver_door", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<DoorBlock> WAXED_SILVER_DOOR = registerSilverDoor("waxed_silver_door", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<DoorBlock> WAXED_EXPOSED_SILVER_DOOR = registerSilverDoor("waxed_exposed_silver_door", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<DoorBlock> WAXED_WEATHERED_SILVER_DOOR = registerSilverDoor("waxed_weathered_silver_door", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<DoorBlock> WAXED_OXIDIZED_SILVER_DOOR = registerSilverDoor("waxed_oxidized_silver_door", WeatheringCopper.WeatherState.OXIDIZED);

    private static DeferredBlock<Block> registerWeatheringSilverBlock(String name, WeatheringCopper.WeatherState weatherState) {
        return registerSilverBlock(name, getSilverProps(weatherState), (props) -> new WeatheringCopperFullBlock(weatherState, props));
    }

    private static DeferredBlock<HorizontalFacingBlock> registerWeatheringSilverHorizontalFacingBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperHorizontalFacingBlock(weatherState, props));
    }

    private static DeferredBlock<HorizontalFacingBlock> registerSilverHorizontalFacingBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, HorizontalFacingBlock::new);
    }

    private static DeferredBlock<Block> registerWeatheringSilverGrate(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE)).sound(ModSoundTypes.SILVER_GRATE);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperGrateBlock(weatherState, props));
    }

    private static DeferredBlock<Block> registerSilverGrate(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE)).sound(ModSoundTypes.SILVER_GRATE);
        return registerSilverBlock(name, baseProps, WaterloggedTransparentBlock::new);
    }

    private static DeferredBlock<SlabBlock> registerWeatheringSilverSlab(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperSlabBlock(weatherState, props));
    }

    private static DeferredBlock<SlabBlock> registerSilverSlab(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, SlabBlock::new);
    }

    private static DeferredBlock<StairBlock> registerWeatheringSilverStairs(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperStairBlock(weatherState, Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    private static DeferredBlock<StairBlock> registerSilverStairs(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new StairBlock(Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    private static DeferredBlock<TrapDoorBlock> registerWeatheringSilverTrapdoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperTrapDoorBlock(ModBlockSetTypes.SILVER, weatherState, props));
    }

    private static DeferredBlock<TrapDoorBlock> registerSilverTrapdoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR));
        return registerSilverBlock(name, baseProps, (props) -> new TrapDoorBlock(ModBlockSetTypes.SILVER, props));
    }

    private static DeferredBlock<DoorBlock> registerWeatheringSilverDoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperDoorBlock(ModBlockSetTypes.SILVER, weatherState, props));
    }

    private static DeferredBlock<DoorBlock> registerSilverDoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR));
        return registerSilverBlock(name, baseProps, (props) -> new DoorBlock(ModBlockSetTypes.SILVER, props));
    }

    private static DeferredBlock<Block> registerSilverBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, Block::new);
    }

    private static <T extends Block> DeferredBlock<T> registerSilverBlock(String name, BlockBehaviour.Properties baseProps, Function<Block.Properties, T> function) {
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

    private static BlockBehaviour.Properties getSilverProps() {
        return getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}