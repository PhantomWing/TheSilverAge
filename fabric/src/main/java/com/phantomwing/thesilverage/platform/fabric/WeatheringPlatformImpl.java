package com.phantomwing.thesilverage.platform.fabric;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/** Fabric impl of WeatheringPlatform (@ExpectPlatform); registers oxidation/waxing pairs at runtime (Fabric has no data map). */
public final class WeatheringPlatformImpl {
    private WeatheringPlatformImpl() {
    }

    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
        OxidizableBlocksRegistry.registerNextStage(less.get(), more.get());
    }

    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        OxidizableBlocksRegistry.registerWaxable(unwaxed.get(), waxed.get());
    }
}
