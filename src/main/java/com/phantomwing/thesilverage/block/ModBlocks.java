package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.world.level.block.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheSilverAge.MOD_ID);

    // Pancake blocks
    public static final DeferredBlock<Block> SILVER_BLOCK = BLOCKS.register("silver_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}