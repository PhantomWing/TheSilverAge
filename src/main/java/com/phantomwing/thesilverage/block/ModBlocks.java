package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.custom.weathering.SilverWeatherState;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverFullBlock;
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
    public static final DeferredBlock<Block> SILVER_ORE = registerBlock("silver_ore", Blocks.IRON_ORE);
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", Blocks.DEEPSLATE_IRON_ORE);

    // Storage blocks
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", Blocks.RAW_IRON_BLOCK);

    // Silver block
    public static final DeferredBlock<Block> SILVER_BLOCK = registerBlock("silver_block", Blocks.IRON_BLOCK, (props) -> new WeatheringSilverFullBlock(SilverWeatherState.UNAFFECTED, props.mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> EXPOSED_SILVER = registerBlock("exposed_silver", Blocks.IRON_BLOCK, (props) -> new WeatheringSilverFullBlock(SilverWeatherState.EXPOSED, props.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final DeferredBlock<Block> WEATHERED_SILVER = registerBlock("weathered_silver", Blocks.IRON_BLOCK, (props) -> new WeatheringSilverFullBlock(SilverWeatherState.WEATHERED, props.mapColor(MapColor.TERRACOTTA_GRAY)));
    public static final DeferredBlock<Block> OXIDIZED_SILVER = registerBlock("oxidized_silver", Blocks.IRON_BLOCK, (props) -> new WeatheringSilverFullBlock(SilverWeatherState.OXIDIZED, props.mapColor(MapColor.TERRACOTTA_BLACK)));

    // Decorative silver blocks
    public static final DeferredBlock<Block> CUT_SILVER = registerBlock("cut_silver", Blocks.IRON_BLOCK, (props) -> new Block(props.mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final DeferredBlock<SlabBlock> CUT_SILVER_SLAB = registerSlab("cut_silver_slab", Blocks.IRON_BLOCK);
    public static final DeferredBlock<StairBlock> CUT_SILVER_STAIRS = registerStairs("cut_silver_stairs", Blocks.IRON_BLOCK);
    public static final DeferredBlock<Block> CHISELED_SILVER = registerBlock("chiseled_silver", Blocks.IRON_BLOCK);
    public static final DeferredBlock<Block> SILVER_GRATE = registerBlock("silver_grate", Blocks.COPPER_GRATE, (props) -> new Block(props.mapColor(MapColor.COLOR_LIGHT_GRAY)));


    public static final DeferredBlock<DoorBlock> SILVER_DOOR = registerDoor("silver_door", Blocks.IRON_DOOR);
    public static final DeferredBlock<TrapDoorBlock> SILVER_TRAPDOOR = registerTrapdoor("silver_trapdoor", Blocks.IRON_TRAPDOOR);

    private static DeferredBlock<SlabBlock> registerSlab(String name, Block baseBlock) {
        return registerBlock(name, Block.Properties.ofFullCopy(baseBlock), (props) -> new SlabBlock(props));
    }

    private static DeferredBlock<StairBlock> registerStairs(String name, Block baseBlock) {
        return registerBlock(name, Block.Properties.ofFullCopy(baseBlock), (props) -> new StairBlock(baseBlock.defaultBlockState(), props));
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}