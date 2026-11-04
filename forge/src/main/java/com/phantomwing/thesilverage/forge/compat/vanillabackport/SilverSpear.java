package com.phantomwing.thesilverage.forge.compat.vanillabackport;

import com.blackgear.platform.client.GameRendering;
import com.blackgear.vanillabackport.common.level.items.spear.SpearItem;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tool.ModTiers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;

/**
 * The Silver Spear as Vanilla Backport makes a spear. Only loaded when Vanilla Backport is.
 */
public final class SilverSpear {
    private SilverSpear() {
    }

    /** The numbers of 1.21.11's Silver Spear, which Vanilla Backport's spears take in vanilla's order. */
    public static Item create(Item.Properties properties) {
        return new SpearItem(ModTiers.SILVER, 1.0f, 0.82f, 0.65f, 4.0f, 9.0f, 8.25f, 5.1f, 12.5f, 4.6f, properties);
    }

    /**
     * Vanilla Backport draws only its own spears held as spears; its Platform library draws the Silver Spear's
     * {@code _in_hand} model in the hand and its own one elsewhere.
     */
    public static void registerHeldModel() {
        GameRendering.registerHandHeldModels(event ->
                event.register(ModItems.SILVER_SPEAR.get(), new ModelResourceLocation(TheSilverAge.resourceLocation("silver_spear_in_hand"), "inventory")));
    }
}
