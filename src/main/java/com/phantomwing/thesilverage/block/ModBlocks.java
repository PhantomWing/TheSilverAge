package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.custom.HorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.block.custom.SilverBulbBlock;
import com.phantomwing.thesilverage.block.custom.SilverGrateBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringCopperHorizontalFacingBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverBulbBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverDoorBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverGrateBlock;
import com.phantomwing.thesilverage.block.custom.WaxedSilverDoorBlock;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverTrapDoorBlock;
import com.phantomwing.thesilverage.sound.ModSoundTypes;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TheSilverAge.MOD_ID);

    // Ores
    public static final RegistryObject<Block> SILVER_ORE = registerSilverBlock("silver_ore",
            BlockBehaviour.Properties.copy(Blocks.GOLD_ORE),
            (props) -> new DropExperienceBlock(props, ConstantInt.of(0))
    );
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = registerSilverBlock("deepslate_silver_ore",
            BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE),
            (props) -> new DropExperienceBlock(props, ConstantInt.of(0))
    );

    // Storage blocks
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerSilverBlock("raw_silver_block",
            BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).mapColor(MapColor.METAL),
            Block::new
    );

    // Redstone blocks
    public static final RegistryObject<MoonPhaseDetectorBlock> MOON_PHASE_DETECTOR = registerSilverBlock("moon_phase_detector",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BELL).strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(ModSoundTypes.SILVER).lightLevel((blockState) -> 3),
            MoonPhaseDetectorBlock::new
    );

    // Silver block
    public static final RegistryObject<Block> SILVER_BLOCK = registerWeatheringSilverBlock("silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<Block> EXPOSED_SILVER = registerWeatheringSilverBlock("exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<Block> WEATHERED_SILVER = registerWeatheringSilverBlock("weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<Block> OXIDIZED_SILVER = registerWeatheringSilverBlock("oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<Block> WAXED_SILVER_BLOCK = registerSilverBlock("waxed_silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<Block> WAXED_EXPOSED_SILVER = registerSilverBlock("waxed_exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<Block> WAXED_WEATHERED_SILVER = registerSilverBlock("waxed_weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<Block> WAXED_OXIDIZED_SILVER = registerSilverBlock("waxed_oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    // Decorative silver blocks
    public static final RegistryObject<Block> CUT_SILVER = registerWeatheringSilverBlock("cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<Block> EXPOSED_CUT_SILVER = registerWeatheringSilverBlock("exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<Block> WEATHERED_CUT_SILVER = registerWeatheringSilverBlock("weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<Block> OXIDIZED_CUT_SILVER = registerWeatheringSilverBlock("oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<Block> WAXED_CUT_SILVER = registerSilverBlock("waxed_cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_SILVER = registerSilverBlock("waxed_exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_SILVER = registerSilverBlock("waxed_weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<Block> WAXED_OXIDIZED_CUT_SILVER = registerSilverBlock("waxed_oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<SlabBlock> CUT_SILVER_SLAB = registerWeatheringSilverSlab("cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<SlabBlock> EXPOSED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<SlabBlock> WEATHERED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<SlabBlock> OXIDIZED_CUT_SILVER_SLAB = registerWeatheringSilverSlab("oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<SlabBlock> WAXED_CUT_SILVER_SLAB = registerSilverSlab("waxed_cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<SlabBlock> WAXED_EXPOSED_CUT_SILVER_SLAB = registerSilverSlab("waxed_exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<SlabBlock> WAXED_WEATHERED_CUT_SILVER_SLAB = registerSilverSlab("waxed_weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<SlabBlock> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerSilverSlab("waxed_oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<StairBlock> CUT_SILVER_STAIRS = registerWeatheringSilverStairs("cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<StairBlock> EXPOSED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<StairBlock> WEATHERED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<StairBlock> OXIDIZED_CUT_SILVER_STAIRS = registerWeatheringSilverStairs("oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<StairBlock> WAXED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<StairBlock> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<StairBlock> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<StairBlock> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerSilverStairs("waxed_oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<HorizontalFacingBlock> CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<HorizontalFacingBlock> EXPOSED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<HorizontalFacingBlock> WEATHERED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<HorizontalFacingBlock> OXIDIZED_CHISELED_SILVER = registerWeatheringSilverHorizontalFacingBlock("oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<HorizontalFacingBlock> WAXED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<HorizontalFacingBlock> WAXED_EXPOSED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<HorizontalFacingBlock> WAXED_WEATHERED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<HorizontalFacingBlock> WAXED_OXIDIZED_CHISELED_SILVER = registerSilverHorizontalFacingBlock("waxed_oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<TrapDoorBlock> SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("silver_trapdoor", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<TrapDoorBlock> EXPOSED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("exposed_silver_trapdoor", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<TrapDoorBlock> WEATHERED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("weathered_silver_trapdoor", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<TrapDoorBlock> OXIDIZED_SILVER_TRAPDOOR = registerWeatheringSilverTrapdoor("oxidized_silver_trapdoor", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<TrapDoorBlock> WAXED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_silver_trapdoor", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<TrapDoorBlock> WAXED_EXPOSED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_exposed_silver_trapdoor", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<TrapDoorBlock> WAXED_WEATHERED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_weathered_silver_trapdoor", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<TrapDoorBlock> WAXED_OXIDIZED_SILVER_TRAPDOOR = registerSilverTrapdoor("waxed_oxidized_silver_trapdoor", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<DoorBlock> SILVER_DOOR = registerWeatheringSilverDoor("silver_door", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<DoorBlock> EXPOSED_SILVER_DOOR = registerWeatheringSilverDoor("exposed_silver_door", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<DoorBlock> WEATHERED_SILVER_DOOR = registerWeatheringSilverDoor("weathered_silver_door", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<DoorBlock> OXIDIZED_SILVER_DOOR = registerWeatheringSilverDoor("oxidized_silver_door", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<DoorBlock> WAXED_SILVER_DOOR = registerSilverDoor("waxed_silver_door", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<DoorBlock> WAXED_EXPOSED_SILVER_DOOR = registerSilverDoor("waxed_exposed_silver_door", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<DoorBlock> WAXED_WEATHERED_SILVER_DOOR = registerSilverDoor("waxed_weathered_silver_door", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<DoorBlock> WAXED_OXIDIZED_SILVER_DOOR = registerSilverDoor("waxed_oxidized_silver_door", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Grate
    public static final RegistryObject<Block> SILVER_GRATE = registerWeatheringSilverGrate("silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<Block> EXPOSED_SILVER_GRATE = registerWeatheringSilverGrate("exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<Block> WEATHERED_SILVER_GRATE = registerWeatheringSilverGrate("weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<Block> OXIDIZED_SILVER_GRATE = registerWeatheringSilverGrate("oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    public static final RegistryObject<Block> WAXED_SILVER_GRATE = registerSilverGrate("waxed_silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final RegistryObject<Block> WAXED_EXPOSED_SILVER_GRATE = registerSilverGrate("waxed_exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final RegistryObject<Block> WAXED_WEATHERED_SILVER_GRATE = registerSilverGrate("waxed_weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final RegistryObject<Block> WAXED_OXIDIZED_SILVER_GRATE = registerSilverGrate("waxed_oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    // Silver Bulb
    public static final RegistryObject<Block> SILVER_BULB = registerWeatheringSilverBulb("silver_bulb", WeatheringCopper.WeatherState.UNAFFECTED, 15);
    public static final RegistryObject<Block> EXPOSED_SILVER_BULB = registerWeatheringSilverBulb("exposed_silver_bulb", WeatheringCopper.WeatherState.EXPOSED, 12);
    public static final RegistryObject<Block> WEATHERED_SILVER_BULB = registerWeatheringSilverBulb("weathered_silver_bulb", WeatheringCopper.WeatherState.WEATHERED, 8);
    public static final RegistryObject<Block> OXIDIZED_SILVER_BULB = registerWeatheringSilverBulb("oxidized_silver_bulb", WeatheringCopper.WeatherState.OXIDIZED, 4);

    public static final RegistryObject<Block> WAXED_SILVER_BULB = registerSilverBulb("waxed_silver_bulb", WeatheringCopper.WeatherState.UNAFFECTED, 15);
    public static final RegistryObject<Block> WAXED_EXPOSED_SILVER_BULB = registerSilverBulb("waxed_exposed_silver_bulb", WeatheringCopper.WeatherState.EXPOSED, 12);
    public static final RegistryObject<Block> WAXED_WEATHERED_SILVER_BULB = registerSilverBulb("waxed_weathered_silver_bulb", WeatheringCopper.WeatherState.WEATHERED, 8);
    public static final RegistryObject<Block> WAXED_OXIDIZED_SILVER_BULB = registerSilverBulb("waxed_oxidized_silver_bulb", WeatheringCopper.WeatherState.OXIDIZED, 4);

    private static RegistryObject<Block> registerWeatheringSilverBlock(String name, WeatheringCopper.WeatherState weatherState) {
        return registerSilverBlock(name, getSilverProps(weatherState), (props) -> new WeatheringCopperFullBlock(weatherState, props));
    }

    private static RegistryObject<HorizontalFacingBlock> registerWeatheringSilverHorizontalFacingBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperHorizontalFacingBlock(weatherState, props));
    }

    private static RegistryObject<HorizontalFacingBlock> registerSilverHorizontalFacingBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, HorizontalFacingBlock::new);
    }

    private static RegistryObject<SlabBlock> registerWeatheringSilverSlab(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperSlabBlock(weatherState, props));
    }

    private static RegistryObject<SlabBlock> registerSilverSlab(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, SlabBlock::new);
    }

    private static RegistryObject<StairBlock> registerWeatheringSilverStairs(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringCopperStairBlock(weatherState, Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    private static RegistryObject<StairBlock> registerSilverStairs(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, (props) -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState, props));
    }

    private static RegistryObject<TrapDoorBlock> registerWeatheringSilverTrapdoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverTrapDoorBlock(ModBlockSetTypes.SILVER, weatherState, props));
    }

    private static RegistryObject<TrapDoorBlock> registerSilverTrapdoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR));
        return registerSilverBlock(name, baseProps, (props) -> new TrapDoorBlock(props, ModBlockSetTypes.SILVER));
    }

    private static RegistryObject<DoorBlock> registerWeatheringSilverDoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.copy(Blocks.IRON_DOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WeatheringSilverDoorBlock(ModBlockSetTypes.SILVER, weatherState, props));
    }

    private static RegistryObject<DoorBlock> registerSilverDoor(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState, BlockBehaviour.Properties.copy(Blocks.IRON_DOOR));
        return registerSilverBlock(name, baseProps, (props) -> new WaxedSilverDoorBlock(props, ModBlockSetTypes.SILVER));
    }

    private static RegistryObject<Block> registerWeatheringSilverGrate(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties props = getSilverGrateProps(weatherState);
        return registerSilverBlock(name, props, (p) -> new WeatheringSilverGrateBlock(weatherState, p));
    }

    private static RegistryObject<Block> registerSilverGrate(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties props = getSilverGrateProps(weatherState);
        return registerSilverBlock(name, props, SilverGrateBlock::new);
    }

    private static RegistryObject<Block> registerWeatheringSilverBulb(String name, WeatheringCopper.WeatherState weatherState, int litLightLevel) {
        BlockBehaviour.Properties props = getSilverBulbProps(weatherState, litLightLevel);
        return registerSilverBlock(name, props, (p) -> new WeatheringSilverBulbBlock(weatherState, p));
    }

    private static RegistryObject<Block> registerSilverBulb(String name, WeatheringCopper.WeatherState weatherState, int litLightLevel) {
        BlockBehaviour.Properties props = getSilverBulbProps(weatherState, litLightLevel);
        return registerSilverBlock(name, props, SilverBulbBlock::new);
    }

    private static RegistryObject<Block> registerSilverBlock(String name, WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties baseProps = getSilverProps(weatherState);
        return registerSilverBlock(name, baseProps, Block::new);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Block> RegistryObject<T> registerSilverBlock(String name, BlockBehaviour.Properties baseProps, Function<BlockBehaviour.Properties, T> function) {
        return (RegistryObject<T>) BLOCKS.register(name, () -> function.apply(baseProps));
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

    private static BlockBehaviour.Properties getSilverGrateProps(WeatheringCopper.WeatherState weatherState) {
        BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .sound(ModSoundTypes.SILVER_GRATE)
                .noOcclusion()
                .isSuffocating((state, level, pos) -> false)
                .isValidSpawn((state, level, pos, type) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .instrument(NoteBlockInstrument.BELL);

        switch (weatherState) {
            case UNAFFECTED -> props = props.mapColor(MapColor.METAL);
            case EXPOSED -> props = props.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY);
            case WEATHERED -> props = props.mapColor(MapColor.TERRACOTTA_GRAY);
            case OXIDIZED -> props = props.mapColor(MapColor.TERRACOTTA_BLACK);
        }

        return props;
    }

    private static BlockBehaviour.Properties getSilverBulbProps(WeatheringCopper.WeatherState weatherState, int litLightLevel) {
        BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .sound(ModSoundTypes.SILVER_BULB)
                .instrument(NoteBlockInstrument.BELL)
                .lightLevel((state) -> state.getValue(SilverBulbBlock.LIT) ? litLightLevel : 0);

        switch (weatherState) {
            case UNAFFECTED -> props = props.mapColor(MapColor.METAL);
            case EXPOSED -> props = props.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY);
            case WEATHERED -> props = props.mapColor(MapColor.TERRACOTTA_GRAY);
            case OXIDIZED -> props = props.mapColor(MapColor.TERRACOTTA_BLACK);
        }

        return props;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
