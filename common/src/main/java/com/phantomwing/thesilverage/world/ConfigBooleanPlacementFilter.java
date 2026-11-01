package com.phantomwing.thesilverage.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.platform.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Places a feature only while a boolean config option is on: {@code thesilverage:config_boolean} naming the option's
 * id, as the recipe condition of that name does. It is read as each chunk generates, so a change applies to the chunks
 * generated after it, and what has generated stays.
 */
public record ConfigBooleanPlacementFilter(String settingId) implements PlacementFilter {
    public static final MapCodec<ConfigBooleanPlacementFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("settingId").forGetter(ConfigBooleanPlacementFilter::settingId)
    ).apply(instance, ConfigBooleanPlacementFilter::new));

    @Override
    public boolean shouldPlace(@NotNull PlacementContext context, @NotNull RandomSource random, @NotNull BlockPos pos) {
        return CommonConfig.booleanSetting(settingId);
    }

    /** The positions it lets through from {@code pos}: {@code pos} itself or none, as a placement modifier's stream was before 26.3. */
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        return shouldPlace(context, random, pos) ? Stream.of(pos) : Stream.empty();
    }

    @Override
    public @NotNull MapCodec<ConfigBooleanPlacementFilter> codec() {
        return CODEC;
    }
}
