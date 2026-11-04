package com.phantomwing.thesilverage.platform.forge;

import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.forge.compat.farmersdelight.SilverKnifeItem;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * Forge implementation of {@link com.phantomwing.thesilverage.platform.KnifePlatform}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 */
public final class KnifePlatformImpl {
    private KnifePlatformImpl() {
    }

    public static Item createSilverKnife(Item.Properties properties, Tier tier) {
        // Only touch SilverKnifeItem (→ FD's KnifeItem) when FD is actually
        // loaded, so the mod still loads standalone with the SwordItem fallback.
        // Call the static factory (invokestatic) rather than `new SilverKnifeItem`
        // here: an inline `new` makes the verifier load SilverKnifeItem's FD-only
        // superclass when THIS method is verified, crashing without FD.
        if (CommonPlatform.isModLoaded(ModIds.FARMERS_DELIGHT)) {
            return SilverKnifeItem.create(tier, 0.5f, -2.0f, properties);
        }
        // SwordItem takes a whole number, so the fallback rounds the knives' 0.5 to 0.
        return new SwordItem(tier, 0, -2.0f, properties);
    }
}
