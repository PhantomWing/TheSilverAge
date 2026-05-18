package com.phantomwing.thesilverage.fabric.world;

import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Fabric parity for the NeoForge {@code ModBiomeModifiers}.
 *
 * <p>The configured/placed features and biome tags are loader-agnostic
 * (generated JSON under {@code worldgen/} + {@code tags/worldgen/biome/}, read
 * by both loaders). The biome → placed-feature <em>link</em>, however, is
 * loader-specific: NeoForge emits {@code neoforge:add_features} biome-modifier
 * JSON (built by {@code com.phantomwing.thesilverage.neoforge.world.ModBiomeModifiers}),
 * which Fabric does not parse. Without an equivalent the placed features load
 * but are never attached to any biome, so silver ore would never generate on
 * Fabric.</p>
 *
 * <p>This reproduces the three NeoForge {@code AddFeaturesBiomeModifier}
 * registrations 1:1 via the Fabric {@link BiomeModifications} API — same placed
 * features, same biome tags, same {@link GenerationStep.Decoration#UNDERGROUND_ORES}
 * step — so world gen is byte-identical across loaders.</p>
 */
public final class ModWorldGen {
    private ModWorldGen() {
    }

    public static void register() {
        // Mirrors ModBiomeModifiers#registerWildCrops:
        //   ORE_SILVER       -> #thesilverage:has_silver_ore
        //   ORE_SILVER_LOWER -> #thesilverage:has_silver_ore
        //   ORE_SILVER_EXTRA -> #thesilverage:has_extra_silver_ore
        // all at GenerationStep.Decoration.UNDERGROUND_ORES.
        BiomeModifications.addFeature(
                BiomeSelectors.tag(ModTags.Biomes.HAS_SILVER_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.ORE_SILVER);

        BiomeModifications.addFeature(
                BiomeSelectors.tag(ModTags.Biomes.HAS_SILVER_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.ORE_SILVER_LOWER);

        BiomeModifications.addFeature(
                BiomeSelectors.tag(ModTags.Biomes.HAS_EXTRA_SILVER_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.ORE_SILVER_EXTRA);
    }
}
