package com.phantomwing.thesilverage.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

/**
 * The waxed Silver Doors, and the parent of {@link WeatheringSilverDoorBlock}. A half takes the block of its other half
 * when that one changes, as 1.21's doors do: 1.20.1's {@code DoorBlock} keeps only a half of its own block, so waxing,
 * scraping or tarnishing one half of a silver door broke the door.
 */
public class SilverDoorBlock extends DoorBlock {
    public SilverDoorBlock(BlockSetType blockSetType, Properties properties) {
        super(properties, blockSetType);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                           @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.getBlock() instanceof SilverDoorBlock && neighborState.getValue(HALF) != half
                    ? neighborState.setValue(HALF, half)
                    : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
}
