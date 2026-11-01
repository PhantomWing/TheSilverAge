package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CopperBulbBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * The waxed Silver Bulbs: vanilla's copper bulb, which is itself the class that doesn't weather, toggled with sounds of
 * its own ("Silver Bulb turns on") where vanilla's name copper. {@link WeatheringSilverBulbBlock} toggles the same way.
 */
public class SilverBulbBlock extends CopperBulbBlock {
    public static final MapCodec<SilverBulbBlock> CODEC = simpleCodec(SilverBulbBlock::new);

    public SilverBulbBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends CopperBulbBlock> codec() {
        return CODEC;
    }

    @Override
    public void checkAndFlip(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos) {
        flip(state, level, pos);
    }

    /** Vanilla's toggle, with the mod's sounds. */
    static void flip(BlockState state, ServerLevel level, BlockPos pos) {
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
}
