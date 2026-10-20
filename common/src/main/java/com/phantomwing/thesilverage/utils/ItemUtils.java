package com.phantomwing.thesilverage.utils;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Objects;

public class ItemUtils {
    /**
     * What the stack a swap replaces keeps beyond its wear and enchantments: what a player, a structure or a trial
     * spawner gave it - a name, lore, a trim. Not its attribute modifiers, which would take the new item's own away.
     */
    private static final List<DataComponentType<?>> KEPT = List.of(DataComponents.CUSTOM_NAME, DataComponents.LORE,
            DataComponents.TRIM, DataComponents.UNBREAKABLE, DataComponents.CUSTOM_DATA);

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

    /**
     * Change the item type of an ItemStack while preserving the count, how worn it is as a share of its durability, the
     * enchantments the new item supports, and its name, lore and trim.
     */
    public static ItemStack tryTransmuteStack(ItemStack from, ItemLike item) {
        int count = Math.min(new ItemStack(item.asItem()).getMaxStackSize(), from.getCount());
        ItemStack to = new ItemStack(item.asItem(), count);

        // The same share of durability used, not the same damage: loot wears iron by a share of its 250, and as much on
        // a silver tool (208) leaves it nearer breaking. One use is always left, as loot's wear leaves one.
        if (from.isDamaged() && from.isDamageableItem() && to.isDamageableItem()) {
            float worn = from.getDamageValue() / (float) from.getMaxDamage();
            to.setDamageValue(Math.min(Math.round(worn * to.getMaxDamage()), to.getMaxDamage() - 1));
        }

        // Carry over enchantments from the replaced item to the new item (if supported).
        // (Uses vanilla ItemStack/Enchantment APIs rather than the NeoForge
        // convenience helpers so this stays loader-agnostic; behaviour is the same.)
        if (from.isEnchanted() && to.isEnchantable()) {
            ItemEnchantments enchantments = from.getEnchantments();
            enchantments.keySet().forEach(enchantment -> {
                if (enchantment.value().canEnchant(to)) {
                    to.enchant(enchantment, enchantments.getLevel(enchantment));
                }
            });
        }

        for (DataComponentType<?> type : KEPT) {
            copy(from, to, type);
        }
        return to;
    }

    private static <T> void copy(ItemStack from, ItemStack to, DataComponentType<T> type) {
        T value = from.get(type);
        if (value != null) {
            to.set(type, value);
        }
    }
}
