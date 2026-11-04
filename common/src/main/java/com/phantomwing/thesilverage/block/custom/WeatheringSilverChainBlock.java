package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.block.SilverOxidation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Weathering counterpart to {@link ChainBlock} — the oxidizing chain shape
 * (mirrors {@code WeatheringSilverChainBlock} added to vanilla in 1.21.9).
 * Line-for-line identical to {@link WeatheringSilverPillarBlock}, swapping the
 * {@link ChainBlock} parent in.
 */
public class WeatheringSilverChainBlock extends ChainBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState weatherState;


    public WeatheringSilverChainBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties);

        this.weatherState = weatherState;
    }

    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!SilverOxidation.enabled()) {
            return;
        }

        this.onRandomTick(state, level, pos, random);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    public WeatheringCopper.@NotNull WeatherState getAge() {
        return this.weatherState;
    }
}
