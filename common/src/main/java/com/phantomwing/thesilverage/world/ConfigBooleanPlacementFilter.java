package com.phantomwing.thesilverage.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.platform.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

/**
 * Places a feature only while a boolean config option is on: {@code thesilverage:config_boolean} naming the option's
 * id, as the recipe condition of that name does. It is read as each chunk generates, so a change applies to the chunks
 * generated after it, and what has generated stays.
 */
public class ConfigBooleanPlacementFilter extends PlacementFilter {
    public static final MapCodec<ConfigBooleanPlacementFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("settingId").forGetter(ConfigBooleanPlacementFilter::settingId)
    ).apply(instance, ConfigBooleanPlacementFilter::new));

    private final String settingId;

    public ConfigBooleanPlacementFilter(String settingId) {
        this.settingId = settingId;
    }

    public String settingId() {
        return settingId;
    }

    @Override
    protected boolean shouldPlace(@NotNull PlacementContext context, @NotNull RandomSource random, @NotNull BlockPos pos) {
        return CommonConfig.booleanSetting(settingId);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return ModPlacementModifiers.CONFIG_BOOLEAN.get();
    }
}
