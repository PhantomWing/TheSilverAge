package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.neoforge.compat.farmersdelight.SilverKnifeItem;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

// NeoForge implementation of KnifePlatform.
public final class KnifePlatformImpl {
    private KnifePlatformImpl() {
    }

    public static Item createSilverKnife(Item.Properties properties, ToolMaterial material) {
        // Use the static factory, not `new SilverKnifeItem` here: an inline `new` would make the
        // verifier load FD's KnifeItem superclass when this method is verified, crashing without FD.
        if (CommonPlatform.isModLoaded(ModIds.FARMERS_DELIGHT)) {
            return SilverKnifeItem.create(material, properties);
        }
        // Attack stats (0.5 damage, -2.0 speed) match FD's own knives.
        return new Item(properties.sword(material, 0.5F, -2.0F));
    }
}
