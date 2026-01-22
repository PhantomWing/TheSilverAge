package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheSilverAge.MOD_ID);

    // Ores
    public static final DeferredBlock<Block> SILVER_ORE = registerBlock("silver_ore", getBaseSilverProps(Blocks.GOLD_ORE), (props) -> new DropExperienceBlock(ConstantInt.of(0), props));
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", getBaseSilverProps(Blocks.DEEPSLATE_GOLD_ORE), (props) -> new DropExperienceBlock(ConstantInt.of(0), props));

    // Storage blocks
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", getBaseSilverProps(Blocks.RAW_GOLD_BLOCK), Block::new);

    // Silver block
    public static final DeferredBlock<Block> SILVER_BLOCK = registerWeatheringBlock("silver_block", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_SILVER = registerWeatheringBlock("exposed_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_SILVER = registerWeatheringBlock("weathered_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_SILVER = registerWeatheringBlock("oxidized_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_SILVER_BLOCK = registerBlock("waxed_silver_block", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), Block::new);
    public static final DeferredBlock<Block> WAXED_EXPOSED_SILVER = registerBlock("waxed_exposed_silver", getSilverProps(WeatheringCopper.WeatherState.EXPOSED), Block::new);
    public static final DeferredBlock<Block> WAXED_WEATHERED_SILVER = registerBlock("waxed_weathered_silver", getSilverProps(WeatheringCopper.WeatherState.WEATHERED), Block::new);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_SILVER = registerBlock("waxed_oxidized_silver", getSilverProps(WeatheringCopper.WeatherState.OXIDIZED), Block::new);

    // Decorative silver blocks
    public static final DeferredBlock<Block> CUT_SILVER = registerWeatheringBlock("cut_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_CUT_SILVER = registerWeatheringBlock("exposed_cut_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_CUT_SILVER = registerWeatheringBlock("weathered_cut_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_CUT_SILVER = registerWeatheringBlock("oxidized_cut_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_CUT_SILVER = registerBlock("waxed_cut_silver", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), Block::new);
    public static final DeferredBlock<Block> WAXED_EXPOSED_CUT_SILVER = registerBlock("waxed_exposed_cut_silver", getSilverProps(WeatheringCopper.WeatherState.EXPOSED), Block::new);
    public static final DeferredBlock<Block> WAXED_WEATHERED_CUT_SILVER = registerBlock("waxed_weathered_cut_silver", getSilverProps(WeatheringCopper.WeatherState.WEATHERED), Block::new);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_CUT_SILVER = registerBlock("waxed_oxidized_cut_silver", getSilverProps(WeatheringCopper.WeatherState.OXIDIZED), Block::new);

    public static final DeferredBlock<SlabBlock> CUT_SILVER_SLAB = registerWeatheringSlab("cut_silver_slab", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<SlabBlock> EXPOSED_CUT_SILVER_SLAB = registerWeatheringSlab("exposed_cut_silver_slab", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<SlabBlock> WEATHERED_CUT_SILVER_SLAB = registerWeatheringSlab("weathered_cut_silver_slab", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<SlabBlock> OXIDIZED_CUT_SILVER_SLAB = registerWeatheringSlab("oxidized_cut_silver_slab", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<SlabBlock> WAXED_CUT_SILVER_SLAB = registerSlab("waxed_cut_silver_slab", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<SlabBlock> WAXED_EXPOSED_CUT_SILVER_SLAB = registerSlab("waxed_exposed_cut_silver_slab", getSilverProps(WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<SlabBlock> WAXED_WEATHERED_CUT_SILVER_SLAB = registerSlab("waxed_weathered_cut_silver_slab", getSilverProps(WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<SlabBlock> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerSlab("waxed_oxidized_cut_silver_slab", getSilverProps(WeatheringCopper.WeatherState.OXIDIZED));

    public static final DeferredBlock<StairBlock> CUT_SILVER_STAIRS = registerWeatheringStairs("cut_silver_stairs", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<StairBlock> EXPOSED_CUT_SILVER_STAIRS = registerWeatheringStairs("exposed_cut_silver_stairs", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<StairBlock> WEATHERED_CUT_SILVER_STAIRS = registerWeatheringStairs("weathered_cut_silver_stairs", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<StairBlock> OXIDIZED_CUT_SILVER_STAIRS = registerWeatheringStairs("oxidized_cut_silver_stairs", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<StairBlock> WAXED_CUT_SILVER_STAIRS = registerStairs("waxed_cut_silver_stairs", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<StairBlock> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerStairs("waxed_exposed_cut_silver_stairs", getSilverProps(WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<StairBlock> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerStairs("waxed_weathered_cut_silver_stairs", getSilverProps(WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<StairBlock> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerStairs("waxed_oxidized_cut_silver_stairs", getSilverProps(WeatheringCopper.WeatherState.OXIDIZED));

    public static final DeferredBlock<Block> CHISELED_SILVER = registerWeatheringBlock("chiseled_silver", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_CHISELED_SILVER = registerWeatheringBlock("exposed_chiseled_silver", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_CHISELED_SILVER = registerWeatheringBlock("weathered_chiseled_silver", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_CHISELED_SILVER = registerWeatheringBlock("oxidized_chiseled_silver", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_CHISELED_SILVER = registerBlock("waxed_chiseled_silver", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), Block::new);
    public static final DeferredBlock<Block> WAXED_EXPOSED_CHISELED_SILVER = registerBlock("waxed_exposed_chiseled_silver", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), Block::new);
    public static final DeferredBlock<Block> WAXED_WEATHERED_CHISELED_SILVER = registerBlock("waxed_weathered_chiseled_silver", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), Block::new);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_CHISELED_SILVER = registerBlock("waxed_oxidized_chiseled_silver", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), Block::new);

    public static final DeferredBlock<Block> SILVER_GRATE = registerWeatheringGrate("silver_grate", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final DeferredBlock<Block> EXPOSED_SILVER_GRATE = registerWeatheringGrate("exposed_silver_grate", WeatheringCopper.WeatherState.EXPOSED);
    public static final DeferredBlock<Block> WEATHERED_SILVER_GRATE = registerWeatheringGrate("weathered_silver_grate", WeatheringCopper.WeatherState.WEATHERED);
    public static final DeferredBlock<Block> OXIDIZED_SILVER_GRATE = registerWeatheringGrate("oxidized_silver_grate", WeatheringCopper.WeatherState.OXIDIZED);

    public static final DeferredBlock<Block> WAXED_SILVER_GRATE = registerBlock("waxed_silver_grate", getSilverProps(WeatheringCopper.WeatherState.UNAFFECTED), WaterloggedTransparentBlock::new);
    public static final DeferredBlock<Block> WAXED_EXPOSED_SILVER_GRATE = registerBlock("waxed_exposed_silver_grate", getSilverProps(WeatheringCopper.WeatherState.EXPOSED), WaterloggedTransparentBlock::new);
    public static final DeferredBlock<Block> WAXED_WEATHERED_SILVER_GRATE = registerBlock("waxed_weathered_silver_grate", getSilverProps(WeatheringCopper.WeatherState.WEATHERED), WaterloggedTransparentBlock::new);
    public static final DeferredBlock<Block> WAXED_OXIDIZED_SILVER_GRATE = registerBlock("waxed_oxidized_silver_grate", getSilverProps(WeatheringCopper.WeatherState.OXIDIZED), WaterloggedTransparentBlock::new);

    public static final DeferredBlock<DoorBlock> SILVER_DOOR = registerDoor("silver_door", Blocks.IRON_DOOR);
    public static final DeferredBlock<TrapDoorBlock> SILVER_TRAPDOOR = registerTrapdoor("silver_trapdoor", Blocks.IRON_TRAPDOOR);


    private static DeferredBlock<Block> registerWeatheringBlock(String name, WeatheringCopper.WeatherState weatherState) {
        return registerBlock(name, getSilverProps(weatherState), (props) -> new WeatheringCopperFullBlock(weatherState, props));
    }

    private static DeferredBlock<Block> registerWeatheringGrate(String name, WeatheringCopper.WeatherState weatherState) {
        return registerBlock(name, getSilverProps(weatherState, Blocks.COPPER_GRATE), (props) -> new WeatheringCopperGrateBlock(weatherState, props));
    }

    private static DeferredBlock<SlabBlock> registerWeatheringSlab(String name, WeatheringCopper.WeatherState weatherState) {
        return registerBlock(name, getSilverProps(weatherState), (props) -> new WeatheringCopperSlabBlock(weatherState, props));
    }

    private static DeferredBlock<StairBlock> registerWeatheringStairs(String name, WeatheringCopper.WeatherState weatherState) {
        return registerBlock(name, getSilverProps(weatherState), (props) -> new WeatheringCopperStairBlock(weatherState, Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    private static DeferredBlock<SlabBlock> registerSlab(String name, BlockBehaviour.Properties baseProps) {
        return registerBlock(name, baseProps, SlabBlock::new);
    }

    private static DeferredBlock<StairBlock> registerStairs(String name, BlockBehaviour.Properties baseProps) {
        return registerBlock(name, baseProps, (props) -> new StairBlock(Blocks.IRON_BLOCK.defaultBlockState(), props));
    }

    private static DeferredBlock<DoorBlock> registerDoor(String name, Block baseBlock) {
        return registerBlock(name, Block.Properties.ofFullCopy(baseBlock), (props) -> new DoorBlock(BlockSetType.IRON, props));
    }

    private static DeferredBlock<TrapDoorBlock> registerTrapdoor(String name, Block baseBlock) {
        return registerBlock(name, Block.Properties.ofFullCopy(baseBlock), (props) -> new TrapDoorBlock(BlockSetType.IRON, props));
    }

    private static DeferredBlock<Block> registerBlock(String name, Block copy) {
        return registerBlock(name, Block.Properties.ofFullCopy(copy), Block::new);
    }

    private static DeferredBlock<Block> registerBlock(String name, Function<Block.Properties, Block> function) {
        return registerBlock(name, Block.Properties.of(), function);
    }

    private static DeferredBlock<Block> registerBlock(String name, Block copy, Function<Block.Properties, Block> function) {
        return registerBlock(name, Block.Properties.ofFullCopy(copy), function);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, BlockBehaviour.Properties baseProps, Function<Block.Properties, T> function) {
        return BLOCKS.register(name, () ->  function.apply(baseProps));
    }

    private static BlockBehaviour.Properties getSilverProps(WeatheringCopper.WeatherState weatherState) {
        return getSilverProps(weatherState, Blocks.IRON_BLOCK);
    }

    private static BlockBehaviour.Properties getSilverProps(WeatheringCopper.WeatherState weatherState, Block baseBlock) {
        BlockBehaviour.Properties baseProps = getBaseSilverProps(baseBlock);

        switch (weatherState) {
            case UNAFFECTED -> {
                baseProps = baseProps.mapColor(MapColor.TERRACOTTA_WHITE);
            }
            case EXPOSED -> {
                baseProps = baseProps.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY);
            }
            case WEATHERED -> {
                baseProps = baseProps.mapColor(MapColor.TERRACOTTA_GRAY);
            }
            case OXIDIZED -> {
                baseProps = baseProps.mapColor(MapColor.TERRACOTTA_BLACK);
            }
        }

        return baseProps;
    }

    private static BlockBehaviour.Properties getBaseSilverProps(Block baseBlock) {
        return BlockBehaviour.Properties.ofFullCopy(baseBlock).mapColor(MapColor.TERRACOTTA_WHITE);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}