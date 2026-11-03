package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.neoforge.compat.spears.SilverSpear;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.world.item.Item;

/** NeoForge implementation of {@link com.phantomwing.thesilverage.platform.BackportPlatform}. */
public final class BackportPlatformImpl {
    private BackportPlatformImpl() {
    }

    public static Item createSilverSpear(Item.Properties fallback) {
        // A static factory, not an inline `new`, so verifying this method never loads the other mod's classes.
        if (CommonPlatform.isModLoaded(ModIds.BACKPORTED_SPEARS)) {
            return SilverSpear.create();
        }
        return new Item(fallback);
    }
}
