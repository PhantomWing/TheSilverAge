package com.phantomwing.thesilverage.neoforge.compat.spears;

import com.notunanancyowen.spears.Spears;
import com.phantomwing.thesilverage.tool.ModTiers;
import net.minecraft.world.item.Item;

/** The Silver Spear as Backported Spears makes a spear. Only loaded when it is installed. */
public final class SilverSpear {
    private SilverSpear() {
    }

    /** The numbers of 1.21.11's Silver Spear, the arguments of its {@code Item.Properties#spear} in the same order. */
    public static Item create() {
        return Spears.registerSpearRaw(ModTiers.SILVER, 1.0f, 0.82f, 0.65f, 4.0f, 9.0f, 8.25f, 5.1f, 12.5f, 4.6f);
    }
}
