package com.phantomwing.thesilverage.compat.farmersdelight;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

/**
 * Silver Knife when Farmer's Delight is installed. Only construct it through {@link #create}: an
 * inline {@code new} at the call site would load FD's superclass while verifying the caller.
 */
public class SilverKnifeItem extends KnifeItem {
    public SilverKnifeItem(Tier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    public static Item create(Tier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        return new SilverKnifeItem(tier, attackDamage, attackSpeed, properties);
    }
}
