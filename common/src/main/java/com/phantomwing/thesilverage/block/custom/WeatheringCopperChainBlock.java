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
 * Weathering counterpart to {@link ChainBlock} — the oxidizing chain shape
 * (mirrors {@code WeatheringCopperChainBlock} added to vanilla in 1.21.9).
 * Line-for-line identical to {@link WeatheringCopperPillarBlock}, swapping the
 * {@link ChainBlock} parent in.
 */
public class WeatheringCopperChainBlock extends ChainBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringCopperChainBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec())
            .apply(instance, WeatheringCopperChainBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    // ChainBlock is a leaf class in 1.21.1 (exact MapCodec<ChainBlock> return, not the
    // subclassable wildcard IronBarsBlock/RotatedPillarBlock use), so match its return
    // type and cast — only this mod's weathering chains ever use CODEC.
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public @NotNull MapCodec<ChainBlock> codec() {
        return (MapCodec) CODEC;
    }

    public WeatheringCopperChainBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
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
