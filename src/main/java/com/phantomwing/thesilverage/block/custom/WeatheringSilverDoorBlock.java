package com.phantomwing.thesilverage.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

/**
 * A weathering silver door block. Overrides updateShape to accept any DoorBlock as a valid
 * partner half (not just the same block instance), so that waxing one half doesn't break the door.
 */
public class WeatheringSilverDoorBlock extends DoorBlock implements WeatheringCopper {
    private final WeatherState weatherState;

    public WeatheringSilverDoorBlock(BlockSetType blockSetType, WeatherState weatherState, Properties properties) {
        super(properties, blockSetType);
        this.weatherState = weatherState;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        // Only weather on the lower half (like vanilla 1.21 WeatheringCopperDoorBlock).
        if (state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
            this.onRandomTick(state, level, pos, random);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public @NotNull WeatherState getAge() {
        return this.weatherState;
    }

    /**
     * In vanilla 1.20.1, DoorBlock.updateShape uses is(this) to check if the other half
     * is the same block. When honeycomb waxes one half (replacing it with a different DoorBlock),
     * the other half sees a non-matching block and turns to AIR, breaking the door.
     *
     * This override accepts any DoorBlock as a valid partner, matching the 1.21 behavior
     * (which uses instanceof DoorBlock).
     */
    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                  @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y
                && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            // The neighbor is our other half (above for LOWER, below for UPPER).
            // Accept any DoorBlock as a valid partner, not just is(this).
            if (neighborState.getBlock() instanceof DoorBlock
                    && neighborState.getValue(HALF) != half) {
                return state
                        .setValue(FACING, neighborState.getValue(FACING))
                        .setValue(OPEN, neighborState.getValue(OPEN))
                        .setValue(HINGE, neighborState.getValue(HINGE))
                        .setValue(POWERED, neighborState.getValue(POWERED));
            } else {
                return Blocks.AIR.defaultBlockState();
            }
        } else {
            return half == DoubleBlockHalf.LOWER
                    && direction == Direction.DOWN
                    && !state.canSurvive(level, pos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }
    }
}
