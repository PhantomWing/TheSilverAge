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
 * Pillar counterpart to vanilla's {@code WeatheringCopperSlabBlock} /
 * {@code WeatheringCopperStairBlock} — the shape vanilla never shipped for
 * copper. Mirrors this project's existing {@code WeatheringCopperHorizontalFacingBlock}
 * line-for-line, swapping the {@link RotatedPillarBlock} parent in: random tick
 * advances the weather stage, and {@code isRandomlyTicking} stops at the final
 * (most-oxidized) state.
 */
public class WeatheringCopperPillarBlock extends RotatedPillarBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringCopperPillarBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec())
            .apply(instance, WeatheringCopperPillarBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    public @NotNull MapCodec<WeatheringCopperPillarBlock> codec() {
        return CODEC;
    }

    public WeatheringCopperPillarBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
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
