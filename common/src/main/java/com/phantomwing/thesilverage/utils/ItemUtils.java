package com.phantomwing.thesilverage.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Objects;

public class ItemUtils {
    /**
     * The parts of a stack's tag a swap doesn't carry over as they are: its wear and enchantments, which are carried
     * over as they suit the new item, and its attribute modifiers, which would take the new item's own away. The rest -
     * what a player or a structure gave it, a name, lore, a trim - is kept.
     */
    private static final List<String> NOT_KEPT = List.of(ItemStack.TAG_DAMAGE, ItemStack.TAG_ENCH, "AttributeModifiers");

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
        return new ResourceLocation(namespace, prefix + "/" + getName(item) + (suffix != null && !suffix.isEmpty() ? ("_" + suffix) : ""));
    }

    public static ResourceLocation getItemResourceLocation(ItemLike item) {
        return getPrefixedResourceLocation(item, "item", "");
    }

    public static ResourceLocation getItemResourceLocation(ItemLike item, String suffix) {
        return getPrefixedResourceLocation(item, "item", suffix);
    }

    public static String getTrimNameForArmor(ItemLike item, ResourceKey<TrimMaterial> trimMaterial) {
        String itemName = getNameWithNamespace(item);
        String trimName = trimMaterial.location().getPath();

        if (itemName.contains(trimName)) {
            return trimName + "_darker";
        }

        return trimName;
    }

    public static String getArmorTrimModelName(ItemLike item, ResourceKey<TrimMaterial> trimMaterial) {
        return getNameWithNamespace(item) + "_" + getTrimNameForArmor(item, trimMaterial) + "_trim";
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

        CompoundTag tag = from.getTag();
        if (tag != null) {
            CompoundTag kept = tag.copy();
            NOT_KEPT.forEach(kept::remove);
            if (!kept.isEmpty()) {
                to.getOrCreateTag().merge(kept);
            }
        }

        // Carry over enchantments from the replaced item to the new item (if supported).
        if (from.isEnchanted() && to.isEnchantable()) {
            EnchantmentHelper.getEnchantments(from).forEach((enchantment, level) -> {
                if (enchantment.canEnchant(to)) {
                    to.enchant(enchantment, level);
                }
            });
        }
        return to;
    }
}
