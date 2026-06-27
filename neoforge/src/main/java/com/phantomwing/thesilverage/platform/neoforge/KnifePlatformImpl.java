package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.neoforge.compat.farmersdelight.SilverKnifeItem;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/**
 * NeoForge implementation of {@link com.phantomwing.thesilverage.platform.KnifePlatform}
 * (resolved by Architectury's {@code @ExpectPlatform} transformer).
 */
public final class KnifePlatformImpl {
    private KnifePlatformImpl() {
    }

    public static Item createSilverKnife(Item.Properties properties, ToolMaterial material) {
        // FD's KnifeItem takes only Properties now, so bake the knife attack stats
        // (0.5 damage, -2.0 speed, matching FD's knives) into the properties up front.
        // 1.21.5: SwordItem removed — plain Item + Item.Properties#sword.
        Item.Properties knifeProps = properties.sword(material, 0.5F, -2.0F);
        // Only touch SilverKnifeItem (→ FD's KnifeItem) when FD is actually
        // loaded, so the mod still loads standalone with the plain-Item fallback.
        // Call the static factory (invokestatic) rather than `new SilverKnifeItem`
        // here: an inline `new` makes the verifier load SilverKnifeItem's FD-only
        // superclass when THIS method is verified, crashing without FD.
        if (CommonPlatform.isModLoaded(ModIds.FARMERS_DELIGHT)) {
            return SilverKnifeItem.create(knifeProps);
        }
        return new Item(knifeProps);
    }
}
