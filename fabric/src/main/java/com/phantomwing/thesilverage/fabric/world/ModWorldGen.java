package com.phantomwing.thesilverage.fabric.world;

import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;

/** Attaches the silver-ore placed features to biomes via the Fabric API (Fabric ignores NeoForge's add_features JSON). */
public final class ModWorldGen {
    private ModWorldGen() {
    }

    public static void register() {
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
