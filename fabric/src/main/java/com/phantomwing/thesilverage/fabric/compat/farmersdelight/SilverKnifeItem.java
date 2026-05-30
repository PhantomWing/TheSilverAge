package com.phantomwing.thesilverage.fabric.compat.farmersdelight;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

/**
 * Silver Knife when Farmer's Delight Refabricated is present: a real FD
 * {@link KnifeItem}. FDR keeps the {@code vectorwing.farmersdelight.common}
 * package and the same {@code (Tier, Item.Properties)} constructor as the
 * NeoForge build, so this is the same shape as the NeoForge counterpart.
 *
 * <p>Only classloaded when FDR is installed — {@code KnifePlatformImpl} guards
 * construction behind {@code isModLoaded("farmersdelight")}.</p>
 */
public class SilverKnifeItem extends KnifeItem {
    public SilverKnifeItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    /**
     * Factory used by {@code KnifePlatformImpl} behind the {@code isModLoaded}
     * guard. The {@code new SilverKnifeItem} MUST live here, not inline in the
     * caller: the JVM verifier loads classes named by a {@code new} instruction
     * when verifying the enclosing method, so an inline {@code new SilverKnifeItem}
     * would force-load the {@code KnifeItem} superclass and crash when FDR is
     * absent (standalone — the compile-time stub is stripped from the jar).
     * Reached via {@code invokestatic}, this loads only when FDR is present.
     */
    public static Item create(Tier tier, Item.Properties properties) {
        return new SilverKnifeItem(tier, properties);
    }
}
