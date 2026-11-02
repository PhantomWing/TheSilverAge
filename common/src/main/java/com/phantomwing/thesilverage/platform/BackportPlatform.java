package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;

/**
 * {@code @ExpectPlatform} bridge that builds the items 1.21.11 added, which mods backport to this version: the Silver
 * Spear through Backported Spears, and Silver Nautilus Armor through Vanilla Backport.
 *
 * <p>Each is always registered, so a world keeps it when the mod goes, and is the mod's own kind of item when the mod
 * is installed, a plain item otherwise. As with the Silver Knife, the class that names the mod is only loaded behind
 * an {@code isModLoaded} check, through a static factory.</p>
 */
public final class BackportPlatform {
    private BackportPlatform() {
    }

    @ExpectPlatform
    public static Item createSilverSpear(Item.Properties fallback) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item createSilverNautilusArmor(Item.Properties properties) {
        throw new AssertionError();
    }
}
