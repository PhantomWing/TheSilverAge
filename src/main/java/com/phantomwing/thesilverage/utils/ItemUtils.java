package com.phantomwing.thesilverage.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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

    public static ResourceLocation getPrefixedResourceLocation(ItemLike item, String prefix, String suffix) {
        String namespace = getNamespace(item);
        return ResourceLocation.fromNamespaceAndPath(namespace, prefix + "/" + getName(item) + (suffix != null && !suffix.isEmpty() ? ("_" + suffix) : ""));
    }

    public static ResourceLocation getItemResourceLocation(ItemLike item) {
        return getPrefixedResourceLocation(item, "item", "");
    }

    public static ResourceLocation getItemResourceLocation(ItemLike item, String suffix) {
        return getPrefixedResourceLocation(item, "item", suffix);
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

    /** Change the item type of an ItemStack while preserving the count, durability, and enchantments (if applicable). */
    public static ItemStack tryTransmuteStack(ItemStack from, ItemLike item) {
        int count = Math.min(new ItemStack(item.asItem()).getMaxStackSize(), from.getCount());
        ItemStack to = new ItemStack(item.asItem(), count);

        // Carry over the durability of the replaced item if both items are damageable.
        if (from.isDamaged() && to.isDamageableItem()) {
            int durability = Math.min(from.getDamageValue(), to.getMaxDamage());
            to.setDamageValue(durability);
        }

        // Carry over enchantments from the replaced item to the new item (if supported).
        if (from.isEnchanted() && to.isEnchantable()) {
            ItemEnchantments enchantments = from.getTagEnchantments();
            enchantments.keySet().forEach(enchantment -> {
                if (to.supportsEnchantment(enchantment)) {
                    to.enchant(enchantment, from.getEnchantmentLevel(enchantment));
                }
            });
        }

        return to;
    }
}
