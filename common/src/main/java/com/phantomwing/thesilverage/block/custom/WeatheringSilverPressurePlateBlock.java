package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.block.SilverOxidation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * The unwaxed {@link SilverPressurePlateBlock}, which tarnishes over time as copper does, unless the
 * {@code enable_silver_oxidation} config is off. Its count changes with each stage.
 */
public class WeatheringSilverPressurePlateBlock extends SilverPressurePlateBlock implements WeatheringCopper {
    public static final MapCodec<WeightedPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(block -> ((WeatheringSilverPressurePlateBlock) block).getAge()),
            propertiesCodec()
    ).apply(instance, WeatheringSilverPressurePlateBlock::new));

    public WeatheringSilverPressurePlateBlock(WeatheringCopper.WeatherState weatherState, Properties properties) {
        super(weatherState, properties);
    }

    @Override
    public @NotNull MapCodec<WeightedPressurePlateBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull WeatheringCopper.WeatherState getAge() {
        return weatherState;
    }

    /**
     * Ticks while it has a stage left to reach. Vanilla asks WeatheringCopper.getNext, which on NeoForge 21.3 reads the
     * oxidizables data map; a state caches this at startup, before data maps load, so it read false and silver never tarnished.
     */
    @Override
    protected boolean isRandomlyTicking(@NotNull BlockState state) {
        return getAge() != WeatheringCopper.WeatherState.OXIDIZED;
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (SilverOxidation.enabled()) {
            changeOverTime(state, level, pos, random);
        }
    }
}
