package com.phantomwing.thesilverage.platform.forge;

import com.phantomwing.thesilverage.forge.compat.vanillabackport.SilverNautilusArmor;
import com.phantomwing.thesilverage.forge.compat.vanillabackport.SilverSpear;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;

/**
 * Forge implementation of {@link com.phantomwing.thesilverage.platform.BackportPlatform}. On 1.20.1 only Vanilla
 * Backport makes the Silver Spear: Backported Spears' 1.20.1 builds have no way for another mod to make a spear.
 */
public final class BackportPlatformImpl {
    private BackportPlatformImpl() {
    }

    public static Item createSilverSpear(Item.Properties fallback) {
        // Static factories, not an inline `new`, so verifying this method never loads the other mod's classes.
        if (CommonPlatform.isModLoaded(ModIds.VANILLA_BACKPORT)) {
            return SilverSpear.create(fallback);
        }
        return new Item(fallback);
    }

    public static Item createSilverNautilusArmor(Item.Properties properties) {
        if (CommonPlatform.isModLoaded(ModIds.VANILLA_BACKPORT)) {
            return SilverNautilusArmor.create(properties);
        }
        return new Item(properties);
    }
}
