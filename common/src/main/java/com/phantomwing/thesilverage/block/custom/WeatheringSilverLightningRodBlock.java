package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.block.SilverOxidation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Weathering counterpart to {@link LightningRodBlock} (mirrors the copper lightning rods vanilla made
 * oxidize in 1.21.9). A strike scrapes the rod back to bare silver, as it does copper; the block the rod
 * stands on is vanilla's to scrape.
 */
public class WeatheringSilverLightningRodBlock extends LightningRodBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState weatherState;

    public WeatheringSilverLightningRodBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties);

        this.weatherState = weatherState;
    }

    @Override
    public void onLightningStrike(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        // The bare rod takes the strike itself: the unpowering tick is scheduled for the block that schedules it.
        BlockState bare = WeatheringCopper.getFirst(state);
        if (bare.getBlock() != this && bare.getBlock() instanceof LightningRodBlock rod) {
            rod.onLightningStrike(bare, level, pos);
        } else {
            super.onLightningStrike(state, level, pos);
        }
    }

    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!SilverOxidation.enabled()) {
            return;
        }

        this.changeOverTime(state, level, pos, random);
    }

    /**
     * Ticks while it has a stage left to reach. Vanilla asks WeatheringCopper.getNext, which on NeoForge 21.3 reads the
     * oxidizables data map; a state caches this at startup, before data maps load, so it read false and silver never tarnished.
     */
    @Override
    protected boolean isRandomlyTicking(@NotNull BlockState state) {
        return getAge() != WeatheringCopper.WeatherState.OXIDIZED;
    }

    public WeatheringCopper.@NotNull WeatherState getAge() {
        return this.weatherState;
    }
}
