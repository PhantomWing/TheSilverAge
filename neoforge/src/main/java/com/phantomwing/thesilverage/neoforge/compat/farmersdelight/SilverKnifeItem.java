package com.phantomwing.thesilverage.neoforge.compat.farmersdelight;

import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.item.KnifeItem;

// FD KnifeItem subclass, only classloaded when Farmer's Delight is present.
public class SilverKnifeItem extends KnifeItem {
    public SilverKnifeItem(Item.Properties properties) {
        super(properties);
    }

    // The `new SilverKnifeItem` MUST stay here, not inline in the caller: the verifier would otherwise
    // force-load the FD-only KnifeItem superclass even when FD is absent. Returns Item to keep it out of the caller's descriptor.
    public static Item create(Item.Properties properties) {
        return new SilverKnifeItem(properties);
    }
}
