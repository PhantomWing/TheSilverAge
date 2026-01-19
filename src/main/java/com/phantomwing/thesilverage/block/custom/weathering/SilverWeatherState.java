package com.phantomwing.thesilverage.block.custom.weathering;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum SilverWeatherState implements StringRepresentable {
    UNAFFECTED("unaffected"),
    EXPOSED("exposed"),
    WEATHERED("weathered"),
    OXIDIZED("oxidized");

    public static final Codec<SilverWeatherState> CODEC = StringRepresentable.fromEnum(SilverWeatherState::values);
    private final String name;

    SilverWeatherState(String name) {
        this.name = name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}