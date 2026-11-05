package com.phantomwing.thesilverage.forge.compat.vanillabackport;

import com.blackgear.vanillabackport.common.level.items.spear.SpearItem;
import com.phantomwing.thesilverage.tool.ModTiers;
import net.minecraft.world.item.Item;

/**
 * The Silver Spear as Vanilla Backport makes a spear. Only loaded when Vanilla Backport is; its held model is
 * {@code SilverSpearModels}'s, on the client alone.
 */
public final class SilverSpear {
    private SilverSpear() {
    }

    /** The numbers of 1.21.11's Silver Spear, which Vanilla Backport's spears take in vanilla's order. */
    public static Item create(Item.Properties properties) {
        return new SpearItem(ModTiers.SILVER, 1.0f, 0.82f, 0.65f, 4.0f, 9.0f, 8.25f, 5.1f, 12.5f, 4.6f, properties);
    }
}
