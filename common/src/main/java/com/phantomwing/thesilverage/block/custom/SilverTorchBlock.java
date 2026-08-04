package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A torch burning silver salts: the flame is violet rather than orange. Standing variant;
 * {@link SilverWallTorchBlock} is the wall-mounted twin.
 *
 * <p>The flame cannot be handed to the {@link TorchBlock} constructor the way a vanilla
 * particle can. {@code BLOCK} is registered before {@code PARTICLE_TYPE}, so
 * {@code SILVER_FLAME.get()} would resolve while the particle registry is still empty.
 * Overriding {@code animateTick} defers the lookup to render time, which is why the
 * constructor is passed a throwaway particle.</p>
 */
public class SilverTorchBlock extends TorchBlock {
    public SilverTorchBlock(Properties properties) {
        super(ParticleTypes.SMOKE, properties); // the ctor particle is unused — animateTick is overridden
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        emitFlame(level, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5);
    }

    /** Vanilla torch ambience with only the flame swapped, so the smoke stays ordinary. */
    static void emitFlame(Level level, double x, double y, double z) {
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
        level.addParticle(ModParticles.SILVER_FLAME.get(), x, y, z, 0.0, 0.0, 0.0);
    }
}
