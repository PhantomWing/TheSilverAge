package com.phantomwing.thesilverage.platform.neoforge;

import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

// NeoForge implementation of WeatheringPlatform. No-op: oxidation/waxing come from
// the datagen-produced oxidizables/waxables data maps, not runtime registration.
public final class WeatheringPlatformImpl {
    private WeatheringPlatformImpl() {
    }

    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
    }

    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
    }
}
