package com.phantomwing.thesilverage.platform.fabric;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * Fabric implementation of {@link com.phantomwing.thesilverage.platform.WeatheringPlatform}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 *
 * <p>Fabric has no oxidation/waxing data map, so the relationships are
 * registered at runtime via Fabric API's {@code OxidizableBlocksRegistry}
 * (fabric-content-registries-v0). The pair lists come from the common
 * {@link com.phantomwing.thesilverage.block.SilverWeatheringSpec}, iterated by
 * {@code TheSilverAgeCommon.init()} — the same source of truth that drives the
 * NeoForge data-map datagen, so both loaders advance the silver weathering chain
 * and accept honeycomb waxing identically.</p>
 *
 * <p>Blocks arrive as {@link Supplier}s (see {@code WeatheringPlatform}); on
 * Fabric, Architectury registers blocks during {@code init()} before these run,
 * so resolving them here is safe.</p>
 */
public final class WeatheringPlatformImpl {
    private WeatheringPlatformImpl() {
    }

    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(less.get(), more.get());
    }

    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        OxidizableBlocksRegistry.registerWaxableBlockPair(unwaxed.get(), waxed.get());
    }
}
