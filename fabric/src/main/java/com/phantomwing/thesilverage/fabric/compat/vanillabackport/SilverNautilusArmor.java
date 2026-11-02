package com.phantomwing.thesilverage.fabric.compat.vanillabackport;

import com.blackgear.vanillabackport.common.level.items.NautilusArmorItem;
import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import net.minecraft.world.item.Item;

/**
 * Silver Nautilus Armor as Vanilla Backport's nautilus wears it: its renderer draws any {@link NautilusArmorItem}, from
 * {@code thesilverage:textures/entity/nautilus/armor/silver.png}. Only loaded when it is installed.
 */
public final class SilverNautilusArmor {
    private SilverNautilusArmor() {
    }

    public static Item create(Item.Properties properties) {
        return new NautilusArmorItem(ModArmorMaterials.SILVER_ARMOR_MATERIAL, properties);
    }
}
