package com.phantomwing.thesilverage.block.custom;

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
 * Wall counterpart to vanilla's {@code WeatheringCopperSlabBlock} /
 * {@code WeatheringCopperStairBlock} — a shape vanilla never shipped for copper.
 * Mirrors this project's {@link WeatheringSilverPillarBlock} line-for-line,
 * swapping the {@link WallBlock} parent in.
 */
public class WeatheringSilverWallBlock extends WallBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringSilverWallBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec())
            .apply(instance, WeatheringSilverWallBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    // WallBlock is a leaf class in 1.21.1 (exact MapCodec<WallBlock> return, not the
    // subclassable wildcard RotatedPillarBlock uses), so match its return type and
    // cast — only this mod's weathering walls ever use CODEC.
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public @NotNull MapCodec<WallBlock> codec() {
        return (MapCodec) CODEC;
    }

    public WeatheringSilverWallBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties);

        this.weatherState = weatherState;
    }

    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!SilverOxidation.enabled()) {
            return;
        }

        this.changeOverTime(state, level, pos, random);
    }

    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    public WeatheringCopper.@NotNull WeatherState getAge() {
        return this.weatherState;
    }
}
