package com.phantomwing.thesilverage.block.custom.weathering;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.Optional;

import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.DataMapHooks;
import org.spongepowered.include.com.google.common.collect.BiMap;
import org.spongepowered.include.com.google.common.collect.ImmutableBiMap;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

public interface WeatheringSilver extends ChangeOverTimeBlock<SilverWeatherState> {
    /** @deprecated */
    @Deprecated
    Supplier<ImmutableMap<Object, Object>> NEXT_BY_BLOCK = Suppliers.memoize(() -> ImmutableBiMap.builder()
            .put(ModBlocks.SILVER_BLOCK, ModBlocks.EXPOSED_SILVER)
            .put(ModBlocks.EXPOSED_SILVER, ModBlocks.WEATHERED_SILVER)
            .put(ModBlocks.WEATHERED_SILVER, ModBlocks.OXIDIZED_SILVER)
            .build());

    /** @deprecated */
    @Deprecated
    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> ((BiMap)NEXT_BY_BLOCK.get()).inverse());

    static Optional<Block> getPrevious(Block block) {
        return Optional.ofNullable(DataMapHooks.getPreviousOxidizedStage(block));
    }

    static Block getFirst(Block p_block) {
        Block block = p_block;

        for(Block block1 = DataMapHooks.getPreviousOxidizedStage(p_block); block1 != null; block1 = DataMapHooks.getPreviousOxidizedStage(block1)) {
            block = block1;
        }

        return block;
    }

    static Optional<BlockState> getPrevious(BlockState state) {
        return getPrevious(state.getBlock()).map((p_154903_) -> p_154903_.withPropertiesOf(state));
    }

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(DataMapHooks.getNextOxidizedStage(block));
    }

    static BlockState getFirst(BlockState state) {
        return getFirst(state.getBlock()).withPropertiesOf(state);
    }

    default Optional<BlockState> getNext(BlockState state) {
        return getNext(state.getBlock()).map((p_154896_) -> p_154896_.withPropertiesOf(state));
    }

    default float getChanceModifier() {
        return this.getAge() == SilverWeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }
}