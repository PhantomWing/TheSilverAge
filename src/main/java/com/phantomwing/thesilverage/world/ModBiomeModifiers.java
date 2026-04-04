package com.phantomwing.thesilverage.world;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<BiomeModifier> ORE_SILVER_LOWER = registerKey("ore_silver_lower");
    public static final ResourceKey<BiomeModifier> ORE_SILVER_EXTRA = registerKey("ore_silver_extra");

    public static void bootstrap(BootstapContext<BiomeModifier> context){
        registerWildCrops(context);
    }

    private static void registerWildCrops(BootstapContext<BiomeModifier> context) {
        registerOre(context, ORE_SILVER, ModPlacedFeatures.ORE_SILVER, ModTags.Biomes.HAS_SILVER_ORE);
        registerOre(context, ORE_SILVER_LOWER, ModPlacedFeatures.ORE_SILVER_LOWER, ModTags.Biomes.HAS_SILVER_ORE);
        registerOre(context, ORE_SILVER_EXTRA, ModPlacedFeatures.ORE_SILVER_EXTRA, ModTags.Biomes.HAS_EXTRA_SILVER_ORE);

    }

    private static void registerOre(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> biomeModifierKey, ResourceKey<PlacedFeature> placedFeatureKey, TagKey<Biome> biomeTag) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(biomeModifierKey, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(biomeTag),
                HolderSet.direct(placedFeatures.getOrThrow(placedFeatureKey)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TheSilverAge.MOD_ID, name));
    }
}
