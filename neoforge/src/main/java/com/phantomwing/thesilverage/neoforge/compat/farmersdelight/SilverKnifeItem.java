package com.phantomwing.thesilverage.neoforge.compat.farmersdelight;

import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.item.KnifeItem;

/**
 * Silver Knife when Farmer's Delight is present: a real FD {@link KnifeItem}, so
 * it gains the Cutting Board {@code KNIFE_DIG}/{@code KNIFE_HARVEST} item
 * abilities and the {@code MINEABLE_WITH_KNIFE} mining tag for free.
 *
 * <p>This class references FD types, so it is only ever classloaded when FD is
 * installed — {@code KnifePlatformImpl} guards construction behind
 * {@code isModLoaded("farmersdelight")} and otherwise builds a plain SwordItem.</p>
 */
public class SilverKnifeItem extends KnifeItem {
    public SilverKnifeItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Factory used by {@code KnifePlatformImpl} behind the {@code isModLoaded}
     * guard. The {@code new SilverKnifeItem} (which the verifier resolves against
     * the FD-only {@code KnifeItem} superclass) MUST live here, not inline in the
     * caller: the JVM verifier loads classes named by a {@code new} instruction
     * when it verifies the enclosing method, so an inline {@code new
     * SilverKnifeItem} would force-load {@code KnifeItem} (and crash) even when FD
     * is absent. Reached via {@code invokestatic}, this class is only loaded when
     * the call actually executes (i.e. FD is present). Returns {@link Item} so the
     * caller's descriptor never names this class either.
     */
    public static Item create(Item.Properties properties) {
        return new SilverKnifeItem(properties);
    }
}
