package com.phantomwing.thesilverage.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Wall-mounted twin of {@link SilverTorchBlock} — same violet flame. */
public class SilverWallTorchBlock extends WallTorchBlock {
    public SilverWallTorchBlock(Properties properties) {
        super(ParticleTypes.SMOKE, properties); // the ctor particle is unused — animateTick is overridden
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // Vanilla wall-torch flame position: nudged out of the wall toward the facing.
        Direction opposite = state.getValue(FACING).getOpposite();
        SilverTorchBlock.emitFlame(level,
                pos.getX() + 0.5 + 0.27 * opposite.getStepX(),
                pos.getY() + 0.7 + 0.22,
                pos.getZ() + 0.5 + 0.27 * opposite.getStepZ());
    }
}
