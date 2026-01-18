package com.phantomwing.thesilverage.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import java.util.Objects;

public class BlockUtils {
    public static ResourceLocation getResourceLocation(Block block) {
        return Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block));
    }

    public static String getName(Block block) {
        return getResourceLocation(block).getPath();
    }

    public static String getNamespace(Block block) {
        return getResourceLocation(block).getNamespace();
    }

    public static String getNameWithNamespace(Block block) {
        ResourceLocation rl = getResourceLocation(block);
        return rl.getNamespace() + ":" + rl.getPath();
    }

    public static ResourceLocation getPrefixedResourceLocation(Block block, String prefix) {
        String namespace = getNamespace(block);
        return ResourceLocation.fromNamespaceAndPath(namespace, prefix + "/" + getName(block));
    }

    public static ResourceLocation getBlockResourceLocation(Block block) {
        return getPrefixedResourceLocation(block, "block");
    }

    public static ModelFile getModel(BlockModelProvider provider, Block block) {
        return new ModelFile.ExistingModelFile(getBlockResourceLocation(block), provider.existingFileHelper);
    }
}
