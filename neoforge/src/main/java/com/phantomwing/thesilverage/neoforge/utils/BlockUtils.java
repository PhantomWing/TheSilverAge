package com.phantomwing.thesilverage.neoforge.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class BlockUtils {
    public static Identifier getResourceLocation(Block block) {
        return Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block));
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
        Identifier rl = getResourceLocation(block);
        return rl.getNamespace() + ":" + rl.getPath();
    }

    public static Identifier getPrefixedResourceLocation(Block block, String prefix) {
        String namespace = getNamespace(block);
        return Identifier.fromNamespaceAndPath(namespace, prefix + "/" + getName(block));
    }

    public static Identifier getPrefixedResourceLocationWithSuffix(Block block, String prefix, String suffix) {
        String namespace = getNamespace(block);
        return Identifier.fromNamespaceAndPath(namespace, prefix + "/" + getName(block) + (!suffix.isEmpty() ? ("_" + suffix) : ""));
    }

    public static Identifier getBlockResourceLocation(Block block) {
        return getPrefixedResourceLocation(block, "block");
    }

    public static Identifier getBlockResourceLocation(Block block, String suffix) {
        return getPrefixedResourceLocationWithSuffix(block, "block", suffix);
    }
}
