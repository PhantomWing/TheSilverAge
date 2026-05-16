package com.phantomwing.thesilverage.world;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

// To see Minecraft's built-in configured features, check out the OrePlacements class in the Minecraft codebase

// Generally, silver generates similar to gold. Veins are slightly bigger (by 1), and they generate slightly higher. And 1 extra vein per chunk.
// Silver generates more frequently in Ancient cities and the Deep Dark.

public class ModPlacedFeatures {
    private static final int ANCIENT_CITY_LAYER = -51;

    public static final ResourceKey<PlacedFeature> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_LOWER = registerKey("ore_silver_lower");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_EXTRA = registerKey("ore_silver_extra");

    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Main silver generation: Generate 5 times per chunk, with a lower chance when exposed to air.
        register(context, ORE_SILVER, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_SILVER_BURIED),
                commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40))));

        // 50% chance to generate in a chunk, with a lower chance when exposed to air. For thematic purposes, generate around the same level as ancient cities.
        register(context, ORE_SILVER_LOWER, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_SILVER_BURIED),
                orePlacement(CountPlacement.of(UniformInt.of(0, 1)), HeightRangePlacement.uniform(VerticalAnchor.absolute(ANCIENT_CITY_LAYER), VerticalAnchor.absolute(ANCIENT_CITY_LAYER + 16))));

        // In some biomes, distribute a few smaller veins uniformly until a somewhat higher level, no matter air exposure.
        // Gold: 50 times over an area of 224 blocks high.
        // Silver: 25 times over an area of 112 blocks high, for a similar overall frequency but more concentrated around the lower levels.
        register(context, ORE_SILVER_EXTRA, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_SILVER_SMALL),
                commonOrePlacement(25, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(48))));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
