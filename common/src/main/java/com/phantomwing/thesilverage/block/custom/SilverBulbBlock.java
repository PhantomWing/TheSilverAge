package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

/**
 * The waxed Silver Bulbs, and the parent of {@link WeatheringSilverBulbBlock}: 1.21's copper bulb, which 1.20.1
 * doesn't have, toggled on each rising redstone edge with sounds of its own ("Silver Bulb turns on"). The same
 * {@code lit} and {@code powered} states as vanilla's, and a comparator reads 15 while it is lit.
 */
public class SilverBulbBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public SilverBulbBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, POWERED);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        if (oldState.getBlock() != state.getBlock() && level instanceof ServerLevel serverLevel) {
            checkAndFlip(state, serverLevel, pos);
        }
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel) {
            checkAndFlip(state, serverLevel, pos);
        }
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        checkAndFlip(state, level, pos);
    }

    public void checkAndFlip(BlockState state, ServerLevel level, BlockPos pos) {
        boolean powered = level.hasNeighborSignal(pos);
        if (powered == state.getValue(POWERED)) {
            return;
        }
        BlockState flipped = state;
        if (!state.getValue(POWERED)) {
            flipped = state.cycle(LIT);
            level.playSound(null, pos, flipped.getValue(LIT) ? ModSounds.SILVER_BULB_TURN_ON : ModSounds.SILVER_BULB_TURN_OFF,
                    SoundSource.BLOCKS);
        }
        level.setBlock(pos, flipped.setValue(POWERED, powered), Block.UPDATE_ALL);
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }
}
