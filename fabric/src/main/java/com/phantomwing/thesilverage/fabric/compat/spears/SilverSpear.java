package com.phantomwing.thesilverage.fabric.compat.spears;

import com.notunanancyowen.spears.Spears;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tool.ModTiers;
import net.minecraft.world.item.Item;

/** The Silver Spear as Backported Spears makes a spear. Only loaded when it is installed. */
public final class SilverSpear {
    private SilverSpear() {
    }

    /**
     * The numbers of 1.21.11's Silver Spear, the arguments of its {@code Item.Properties#spear} in the same order.
     * Backported Spears registers the components a spear is made of in its own initializer, so on Fabric they exist
     * only when it ran first; otherwise the spear is the plain fallback rather than a crash.
     */
    public static Item create(Item.Properties fallback) {
        if (Spears.USE_EFFECTS == null) {
            TheSilverAge.LOGGER.warn("Backported Spears hasn't registered its spear components yet; the Silver Spear is a plain item");
            return new Item(fallback);
        }
        return Spears.registerSpearRaw(TheSilverAge.resourceLocation("silver_spear"), ModTiers.SILVER,
                1.0f, 0.82f, 0.65f, 4.0f, 9.0f, 8.25f, 5.1f, 12.5f, 4.6f);
    }
}
