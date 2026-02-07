package com.phantomwing.thesilverage.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class ItemUtils {
    public static ResourceLocation getResourceLocation(ItemLike item) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }

    public static String getName(ItemLike item) {
        return getResourceLocation(item).getPath();
    }

    public static String getNamespace(ItemLike item) {
        return getResourceLocation(item).getNamespace();
    }

    public static String getNameWithNamespace(ItemLike item) {
        ResourceLocation rl = getResourceLocation(item);
        return rl.getNamespace() + ":" + rl.getPath();
    }

    public static ResourceLocation getPrefixedResourceLocation(ItemLike item, String prefix) {
        String namespace = getNamespace(item);
        return ResourceLocation.fromNamespaceAndPath(namespace, prefix + "/" + getName(item));
    }

    public static ResourceLocation getItemResourceLocation(ItemLike item) {
        return getPrefixedResourceLocation(item, "item");
    }

    public static String getTrimNameForArmor(ItemLike item, ResourceKey<TrimMaterial> trimMaterial) {
        String itemName = item.toString();
        String trimName = trimMaterial.location().getPath();

        if (itemName.contains(trimName)) {
            return trimName + "_darker";
        }

        return trimName;
    }

    public static String getArmorTrimModelName(ItemLike item, ResourceKey<TrimMaterial> trimMaterial) {
        return item.toString() + "_" + getTrimNameForArmor(item, trimMaterial) + "_trim";
    }
}
