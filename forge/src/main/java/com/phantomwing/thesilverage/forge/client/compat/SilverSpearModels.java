package com.phantomwing.thesilverage.forge.client.compat;

import com.blackgear.platform.client.GameRendering;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

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
     * {@code _in_hand} model in the hand and its own one elsewhere. Called at client setup, once the spear exists.
     */
    public static void registerHeldModel() {
        GameRendering.registerHandHeldModels(event -> event.register(ModItems.SILVER_SPEAR.get(), IN_HAND));
    }

    /**
     * Platform adds a hand-held model to those to bake as the model bakery is made, which on Forge happens during the
     * first resource load, alongside mod loading: client setup can come after it, and the spear would be held as the
     * missing model until the next reload. So the model is added here too, which Forge asks for on every load.
     */
    public static void registerAdditional(ModelEvent.RegisterAdditional event) {
        event.register(IN_HAND);
    }
}
