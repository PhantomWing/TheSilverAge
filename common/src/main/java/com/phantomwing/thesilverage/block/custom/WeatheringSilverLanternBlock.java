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
 * Weathering counterpart to {@link LanternBlock} — the oxidizing lantern shape
 * (mirrors {@code WeatheringSilverLanternBlock} added to vanilla in 1.21.9).
 * Line-for-line identical to {@link WeatheringSilverPillarBlock}, swapping the
 * {@link LanternBlock} parent in: random tick advances the weather stage, and
 * {@code isRandomlyTicking} stops at the final (most-oxidized) state.
 */
public class WeatheringSilverLanternBlock extends LanternBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringSilverLanternBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec())
            .apply(instance, WeatheringSilverLanternBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    // LanternBlock is a leaf class in 1.21.1 (exact MapCodec<LanternBlock> return, not the
    // subclassable wildcard IronBarsBlock/RotatedPillarBlock use), so match its return
    // type and cast — only this mod's weathering lanterns ever use CODEC.
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public @NotNull MapCodec<LanternBlock> codec() {
        return (MapCodec) CODEC;
    }

    public WeatheringSilverLanternBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
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
