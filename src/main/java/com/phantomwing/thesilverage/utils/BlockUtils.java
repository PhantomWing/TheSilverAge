package com.phantomwing.thesilverage.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class BlockUtils {
    public static ResourceLocation getResourceLocation(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
    }

    public static String getName(Block block) {
        return getResourceLocation(block).getPath();
    }

    public static String getName(Block block, String suffix) {
        return getName(block) + (!suffix.isEmpty() ? ("_" + suffix) : "");
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
        return new ResourceLocation(namespace, prefix + "/" + getName(block));
    }

    public static ResourceLocation getPrefixedResourceLocationWithSuffix(Block block, String prefix, String suffix) {
        String namespace = getNamespace(block);
        return new ResourceLocation(namespace, prefix + "/" + getName(block) + (!suffix.isEmpty() ? ("_" + suffix) : ""));
    }

    public static ResourceLocation getBlockResourceLocation(Block block) {
        return getPrefixedResourceLocation(block, "block");
    }

    public static ResourceLocation getBlockResourceLocation(Block block, String suffix) {
        return getPrefixedResourceLocationWithSuffix(block, "block", suffix);
    }

    public static ModelFile getModel(BlockModelProvider provider, Block block) {
        return new ModelFile.ExistingModelFile(getBlockResourceLocation(block), provider.existingFileHelper);
    }
}
