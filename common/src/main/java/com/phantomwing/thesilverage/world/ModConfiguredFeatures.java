package com.phantomwing.thesilverage.world;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import dev.architectury.registry.registries.RegistrySupplier;

import java.util.List;

// To see Minecraft's built-in configured features, check out the OreFeatures class in the Minecraft codebase

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_BURIED = registerKey("ore_silver_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL = registerKey("ore_silver_small");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        registerOverworldOre(context, ORE_SILVER, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 10);
        registerOverworldOre(context, ORE_SILVER_BURIED, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 10, 0.5f);
        registerOverworldOre(context, ORE_SILVER_SMALL, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 5);
    }

    /** Registers an overworld ore configured feature with both stone and deepslate variants. */
    private static <T extends Block> void registerOverworldOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, RegistrySupplier<T> stoneOre, RegistrySupplier<T> deepslateOre, int veinSize) {
        registerOverworldOre(context, key, stoneOre, deepslateOre, veinSize, 0.0f);
    }

    /** As above, with an {@code airDiscardChance} (0.0–1.0) for ore exposed to air. */
    private static <T extends Block> void registerOverworldOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, RegistrySupplier<T> stoneOre, RegistrySupplier<T> deepslateOre, int veinSize, float airDiscardChance) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldOres = List.of(
                OreConfiguration.target(stoneReplaceables, stoneOre.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, deepslateOre.get().defaultBlockState()));

        register(context, key, Feature.ORE, new OreConfiguration(overworldOres, veinSize, airDiscardChance));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
