package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Weathering counterpart to {@link IronBarsBlock} — the oxidizing bars shape
 * (mirrors {@code WeatheringCopperBarsBlock} added to vanilla in 1.21.9).
 * Line-for-line identical to {@link WeatheringCopperPillarBlock}, swapping the
 * {@link IronBarsBlock} parent in.
 */
public class WeatheringCopperBarsBlock extends IronBarsBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringCopperBarsBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec())
            .apply(instance, WeatheringCopperBarsBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    public @NotNull MapCodec<WeatheringCopperBarsBlock> codec() {
        return CODEC;
    }

    public WeatheringCopperBarsBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties);

        this.weatherState = weatherState;
    }

    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    public WeatheringCopper.@NotNull WeatherState getAge() {
        return this.weatherState;
    }
}
