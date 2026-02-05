package com.phantomwing.thesilverage.world;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_BURIED = registerKey("ore_silver_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL = registerKey("ore_silver_small");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        registerOverworldOre(context, ORE_SILVER, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 10);
        registerOverworldOre(context, ORE_SILVER_BURIED, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 12, 0.25f);
        registerOverworldOre(context, ORE_SILVER_SMALL, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 3);
    }

    /** Registers an overworld ore configured feature with both stone and deepslate variants.
     *
     * @param context The bootstrap context to register the feature in.
     * @param key The resource key for the configured feature.
     * @param stoneOre The block representing the stone variant of the ore.
     * @param deepslateOre The block representing the deepslate variant of the ore.
     * @param veinSize The size of the ore vein (number of blocks per vein).
     * @param <T> The type of block being registered as an ore.
     */
    private static <T extends Block> void registerOverworldOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredBlock<T> stoneOre, DeferredBlock<T> deepslateOre, int veinSize) {
        registerOverworldOre(context, key, stoneOre, deepslateOre, veinSize, 0.0f);
    }

    /** Registers an overworld ore configured feature with both stone and deepslate variants.
     *
     * @param context The bootstrap context to register the feature in.
     * @param key The resource key for the configured feature.
     * @param stoneOre The block representing the stone variant of the ore.
     * @param deepslateOre The block representing the deepslate variant of the ore.
     * @param veinSize The size of the ore vein (number of blocks per vein).
     * @param airDiscardChance The chance (0.0 to 1.0) that an ore block will be discarded if exposed to air.
     * @param <T> The type of block being registered as an ore.
     */
    private static <T extends Block> void registerOverworldOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredBlock<T> stoneOre, DeferredBlock<T> deepslateOre, int veinSize, float airDiscardChance) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldOres = List.of(
                OreConfiguration.target(stoneReplaceables, stoneOre.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, deepslateOre.get().defaultBlockState()));

        register(context, key, Feature.ORE, new OreConfiguration(overworldOres, veinSize, airDiscardChance));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
