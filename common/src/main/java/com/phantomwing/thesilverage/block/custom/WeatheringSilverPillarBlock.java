package com.phantomwing.thesilverage.block.custom;

import net.minecraft.world.level.block.WeatheringCopper;
import com.phantomwing.thesilverage.block.SilverOxidation;
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
 * copper. Mirrors this project's existing {@code WeatheringSilverHorizontalFacingBlock}
 * line-for-line, swapping the {@link RotatedPillarBlock} parent in: random tick
 * advances the weather stage, and {@code isRandomlyTicking} stops at the final
 * (most-oxidized) state.
 */
public class WeatheringSilverPillarBlock extends RotatedPillarBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringSilverPillarBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec())
            .apply(instance, WeatheringSilverPillarBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    public @NotNull MapCodec<WeatheringSilverPillarBlock> codec() {
        return CODEC;
    }

    public WeatheringSilverPillarBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties);

        this.weatherState = weatherState;
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
