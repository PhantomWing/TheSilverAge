package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.fabric.compat.farmersdelight.SilverKnifeItem;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * Fabric implementation of {@link com.phantomwing.thesilverage.platform.KnifePlatform}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 */
public final class KnifePlatformImpl {
    private KnifePlatformImpl() {
    }

    public static Item createSilverKnife(Item.Properties properties, Tier tier) {
        // Only touch SilverKnifeItem (→ FDR's KnifeItem) when FDR is actually
        // loaded, so the mod still loads standalone with the SwordItem fallback.
        // Call the static factory (invokestatic) rather than `new SilverKnifeItem`
        // here: an inline `new` makes the verifier load SilverKnifeItem's FDR-only
        // superclass when THIS method is verified, crashing without FDR.
        if (CommonPlatform.isModLoaded(ModIds.FARMERS_DELIGHT)) {
            return SilverKnifeItem.create(tier, properties);
        }
        return new SwordItem(tier, properties);
    }
}
