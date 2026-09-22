package com.phantomwing.thesilverage.world;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

// 26.3 folded ConfiguredFeature into Feature: a Feature instance carries its own
// configuration and is registered directly in Registries.FEATURE.
public class ModFeatures {
    public static final ResourceKey<Feature> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<Feature> ORE_SILVER_BURIED = registerKey("ore_silver_buried");
    public static final ResourceKey<Feature> ORE_SILVER_SMALL = registerKey("ore_silver_small");

    public static void bootstrap(BootstrapContext<Feature> context){
        registerOverworldOre(context, ORE_SILVER, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 10);
        registerOverworldOre(context, ORE_SILVER_BURIED, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 10, 0.5f);
        registerOverworldOre(context, ORE_SILVER_SMALL, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, 5);
    }

    /** Registers an overworld ore feature with both stone and deepslate variants. */
    private static <T extends Block> void registerOverworldOre(BootstrapContext<Feature> context, ResourceKey<Feature> key, RegistrySupplier<T> stoneOre, RegistrySupplier<T> deepslateOre, int veinSize) {
        registerOverworldOre(context, key, stoneOre, deepslateOre, veinSize, 0.0f);
    }

    /** As above, with an {@code airDiscardChance} (0.0 to 1.0) for ore exposed to air. */
    private static <T extends Block> void registerOverworldOre(BootstrapContext<Feature> context, ResourceKey<Feature> key, RegistrySupplier<T> stoneOre, RegistrySupplier<T> deepslateOre, int veinSize, float airDiscardChance) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<BlockReplacement> overworldOres = List.of(
                BlockReplacement.replace(stoneReplaceables, stoneOre.get().defaultBlockState()),
                BlockReplacement.replace(deepslateReplaceables, deepslateOre.get().defaultBlockState()));

        context.register(key, new OreFeature(overworldOres, veinSize, airDiscardChance));
    }

    private static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
    }
}
