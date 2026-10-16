package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * {@code @ExpectPlatform} bridge for registering Silver oxidation / waxing relationships.
 * NeoForge is a no-op (driven by the committed data maps); Fabric registers the pair at
 * runtime via {@code OxidizableBlocksRegistry}.
 */
public final class WeatheringPlatform {
    private WeatheringPlatform() {
    }

    /**
     * Registers a single oxidation step {@code less -> more}. Blocks are {@link Supplier}s so the
     * call during mod construction never resolves them before NeoForge flushes its deferred registries.
     */
    @ExpectPlatform
    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /** Registers a single waxable pair {@code unwaxed -> waxed}. */
    @ExpectPlatform
    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
