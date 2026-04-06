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
 * A waxed silver door block. Overrides updateShape to accept any DoorBlock as a valid
 * partner half (not just the same block instance), so that unwaxing or weathering of the
 * other half doesn't break the door.
 */
public class WaxedSilverDoorBlock extends DoorBlock {
    public WaxedSilverDoorBlock(Properties properties, BlockSetType blockSetType) {
        super(properties, blockSetType);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                  @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y
                && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
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
