package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * {@code @ExpectPlatform} bridge that builds the Silver Spear, 1.21.11's, which Backported Spears backports to this
 * version.
 *
 * <p>It is always registered, so a world keeps it when the mod goes, and is the mod's own kind of spear when the mod
 * is installed, a plain item otherwise. As with the Silver Knife, the class that names the mod is only loaded behind
 * an {@code isModLoaded} check, through a static factory.</p>
 */
public final class BackportPlatform {
    private BackportPlatform() {
    }

    /**
     * Registers the spear {@code spear} makes, in {@code items}; on Fabric with Backported Spears, not before that has
     * made its own spears, which a spear is built from.
     */
    @ExpectPlatform
    public static RegistrySupplier<Item> registerSilverSpear(DeferredRegister<Item> items, String name, Supplier<Item> spear) {
        throw new AssertionError();
    }

    /** {@code fallback} carries the item's id, as every item's properties must from 1.21.2. */
    @ExpectPlatform
    public static Item createSilverSpear(Item.Properties fallback) {
        throw new AssertionError();
    }
}
