package com.phantomwing.thesilverage.platform.neoforge;

import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * NeoForge implementation of {@link com.phantomwing.thesilverage.platform.WeatheringPlatform}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 *
 * <p><b>Intentional no-op.</b> On NeoForge, oxidation/waxing continue to come
 * from the committed, datagen-produced {@code oxidizables.json} /
 * {@code waxables.json} data maps (loaded by NeoForge's built-in
 * {@code NeoForgeDataMaps.OXIDIZABLES}/{@code WAXABLES}). The datagen providers
 * ({@code ModOxidizables}/{@code ModWaxables}) now iterate the common
 * {@code SilverWeatheringSpec}, so there is a single source of truth and the
 * regenerated data-map JSON is byte-identical to what is committed.</p>
 *
 * <p>Keeping the data-map path (rather than switching to a NeoForge runtime
 * oxidation-registration API) guarantees NeoForge behaviour is unchanged — the
 * data maps already drive the {@code WeatheringSilverBlock}/waxing interactions
 * exactly as before this refactor.</p>
 */
public final class WeatheringPlatformImpl {
    private WeatheringPlatformImpl() {
    }

    /** No-op: NeoForge oxidation comes from the {@code oxidizables} data map. */
    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
    }

    /** No-op: NeoForge waxing comes from the {@code waxables} data map. */
    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
    }
}
