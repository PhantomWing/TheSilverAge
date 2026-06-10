package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.fabric.compat.farmersdelight.SilverKnifeItem;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/** Fabric impl of KnifePlatform (@ExpectPlatform). */
public final class KnifePlatformImpl {
    private KnifePlatformImpl() {
    }

    public static Item createSilverKnife(Item.Properties properties, ToolMaterial material) {
        // Call the static factory, not `new SilverKnifeItem`: an inline new would make the verifier
        // load the FDR-only superclass here, crashing without FDR.
        if (CommonPlatform.isModLoaded(ModIds.FARMERS_DELIGHT)) {
            return SilverKnifeItem.create(material, properties);
        }
        // Knife attack stats (0.5 damage, -2.0 speed) match FD's own knives.
        return new Item(properties.sword(material, 0.5F, -2.0F));
    }
}
