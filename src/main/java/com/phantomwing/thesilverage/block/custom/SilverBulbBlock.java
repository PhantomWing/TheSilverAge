package com.phantomwing.thesilverage.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class SilverBulbBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public SilverBulbBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, false)
                .setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, POWERED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        if (oldState.getBlock() != state.getBlock() && level instanceof ServerLevel) {
            this.checkAndToggle(state, level, pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel) {
            this.checkAndToggle(state, level, pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.checkAndToggle(state, level, pos);
    }

    private void checkAndToggle(BlockState state, Level level, BlockPos pos) {
        boolean hasSignal = level.hasNeighborSignal(pos);
        boolean wasPowered = state.getValue(POWERED);

        if (hasSignal != wasPowered) {
            BlockState newState = state;
            if (hasSignal && !wasPowered) {
                // Rising edge: toggle lit state
                boolean newLit = !state.getValue(LIT);
                newState = state.setValue(LIT, newLit).setValue(POWERED, true);
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS);
            } else {
                // Falling edge: just update powered
                newState = state.setValue(POWERED, false);
            }
            level.setBlock(pos, newState, 3);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }
}
