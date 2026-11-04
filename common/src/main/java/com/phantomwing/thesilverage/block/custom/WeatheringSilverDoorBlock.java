package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.block.SilverOxidation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

/**
 * The unwaxed Silver Doors, 1.21's copper door: they tarnish as copper does, unless the {@code enable_silver_oxidation}
 * config is off. Only the lower half ticks; the upper takes its new block through {@link SilverDoorBlock#updateShape}.
 */
public class WeatheringSilverDoorBlock extends SilverDoorBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState weatherState;

    public WeatheringSilverDoorBlock(BlockSetType blockSetType, WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(blockSetType, properties);
        this.weatherState = weatherState;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (SilverOxidation.enabled() && state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            this.onRandomTick(state, level, pos, random);
        }
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public @NotNull WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }
}
