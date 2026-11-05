package com.phantomwing.thesilverage.fabric.client.compat;

import com.blackgear.platform.client.GameRendering;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.client.resources.model.ModelResourceLocation;

/**
 * The Silver Spear's held model where Vanilla Backport makes it a spear. Client-only, apart from the spear itself, so a
 * dedicated server never resolves a client type.
 */
public final class SilverSpearModels {
    /** The model the spear is drawn with in the hand. */
    public static final ModelResourceLocation IN_HAND = new ModelResourceLocation(TheSilverAge.resourceLocation("silver_spear_in_hand"), "inventory");

    private SilverSpearModels() {
    }

    /**
     * Vanilla Backport draws only its own spears held as spears; its Platform library draws the Silver Spear's
     * {@code _in_hand} model in the hand and its own one elsewhere. Called from the client entrypoint, before the first
     * resource load, where Platform adds the model to those to bake.
     */
    public static void registerHeldModel() {
        GameRendering.registerHandHeldModels(event -> event.register(ModItems.SILVER_SPEAR.get(), IN_HAND));
    }
}
