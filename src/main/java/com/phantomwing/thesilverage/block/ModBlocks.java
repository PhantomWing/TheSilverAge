package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheSilverAge.MOD_ID);

    // Storage blocks
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", Blocks.RAW_IRON_BLOCK, Block::new);
    public static final DeferredBlock<Block> SILVER_BLOCK = registerBlock("silver_block", Blocks.IRON_BLOCK, Block::new);

    // Ores
    public static final DeferredBlock<Block> SILVER_ORE = registerBlock("silver_ore", Blocks.IRON_ORE, Block::new);
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", Blocks.DEEPSLATE_IRON_ORE, Block::new);

    private static DeferredBlock<Block> registerBlock(String name, Function<Block.Properties, Block> function) {
        return registerBlock(name, Block.Properties.of(), function);
    }

    private static DeferredBlock<Block> registerBlock(String name, Block copy, Function<Block.Properties, Block> function) {
        return registerBlock(name, Block.Properties.ofFullCopy(copy), function);
    }

    private static DeferredBlock<Block> registerBlock(String name, BlockBehaviour.Properties baseProps, Function<Block.Properties, Block> function) {
        return BLOCKS.register(name, () ->  function.apply(baseProps));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}