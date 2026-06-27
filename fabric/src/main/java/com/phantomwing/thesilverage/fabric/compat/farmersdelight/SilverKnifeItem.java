package com.phantomwing.thesilverage.fabric.compat.farmersdelight;

import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.item.KnifeItem;

/** Silver Knife as a real FD KnifeItem; only classloaded when FDR is installed. */
public class SilverKnifeItem extends KnifeItem {
    public SilverKnifeItem(Item.Properties properties) {
        super(properties);
    }

    // The `new` MUST stay in this factory, not inline in the caller: an inline new would make
    // the JVM verifier force-load the KnifeItem superclass and crash when FDR is absent.
    public static Item create(Item.Properties properties) {
        return new SilverKnifeItem(properties);
    }
}
