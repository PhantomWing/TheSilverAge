package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.block.custom.weathering.SilverWeatherState;
import com.phantomwing.thesilverage.block.custom.weathering.WeatheringSilver;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WeatheringSilverFullBlock extends Block implements WeatheringSilver {
    public static final MapCodec<WeatheringSilverFullBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(SilverWeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec()).apply(instance, WeatheringSilverFullBlock::new));
    private final SilverWeatherState weatherState;

    public MapCodec<WeatheringSilverFullBlock> codec() {
        return CODEC;
    }

    public WeatheringSilverFullBlock(SilverWeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringSilver.getNext(state.getBlock()).isPresent();
    }

    public @NotNull SilverWeatherState getAge() {
        return this.weatherState;
    }
}
