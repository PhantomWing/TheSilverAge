package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * {@code @ExpectPlatform} bridge for registering Silver oxidation / waxing
 * relationships.
 *
 * <p>The pair lists live in the common
 * {@link com.phantomwing.thesilverage.block.SilverWeatheringSpec} (single source
 * of truth). {@link com.phantomwing.thesilverage.TheSilverAgeCommon#init()}
 * iterates that spec and calls these methods so each loader wires the
 * relationship its own way:</p>
 *
 * <ul>
 *   <li><b>Forge</b>: gathers the pairs and adds them to vanilla's
 *       {@code WeatheringCopper} and {@code HoneycombItem} maps when those are
 *       first read (Forge 1.20.1 has no data maps).</li>
 *   <li><b>Fabric</b>: registers the pair at runtime via Fabric API's
 *       {@code OxidizableBlocksRegistry} (Fabric has no equivalent data map).</li>
 * </ul>
 *
 * <p>Implemented per loader at
 * {@code com.phantomwing.thesilverage.platform.<loader>.WeatheringPlatformImpl}.</p>
 */
public final class WeatheringPlatform {
    private WeatheringPlatform() {
    }

    /**
     * Registers a single oxidation step {@code less -> more} (one weather-state
     * advance, e.g. {@code SILVER_BLOCK -> EXPOSED_SILVER}).
     *
     * <p>Blocks are passed as {@link Supplier} so the call from
     * {@code TheSilverAgeCommon.init()} (which runs during mod construction,
     * before Forge flushes its deferred registries) never resolves them
     * eagerly. The Forge impl resolves them when vanilla's maps are first read;
     * the Fabric impl when it registers the pair.</p>
     */
    @ExpectPlatform
    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Registers a single waxable pair {@code unwaxed -> waxed} (e.g.
     * {@code EXPOSED_SILVER -> WAXED_EXPOSED_SILVER}). See
     * {@link #registerOxidation} for why the blocks are {@link Supplier}s.
     */
    @ExpectPlatform
    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
