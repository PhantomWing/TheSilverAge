package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.fabric.compat.spears.SilverSpear;
import com.phantomwing.thesilverage.fabric.compat.vanillabackport.SilverNautilusArmor;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/** Fabric implementation of {@link com.phantomwing.thesilverage.platform.BackportPlatform}. */
public final class BackportPlatformImpl {
    private BackportPlatformImpl() {
    }

    public static RegistrySupplier<Item> registerSilverSpear(DeferredRegister<Item> items, String name, Supplier<Item> spear) {
        if (CommonPlatform.isModLoaded(ModIds.BACKPORTED_SPEARS)) {
            return SilverSpear.register(items, name, spear);
        }
        return items.register(name, spear);
    }

    public static Item createSilverSpear(Item.Properties fallback) {
        // Static factories, not an inline `new`, so verifying this method never loads the other mod's classes.
        if (CommonPlatform.isModLoaded(ModIds.BACKPORTED_SPEARS)) {
            return SilverSpear.create();
        }
        if (CommonPlatform.isModLoaded(ModIds.VANILLA_BACKPORT)) {
            return com.phantomwing.thesilverage.fabric.compat.vanillabackport.SilverSpear.create(fallback);
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
