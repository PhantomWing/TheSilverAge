package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/**
 * {@code @ExpectPlatform} bridge that builds the Silver Knife item. The item is always registered;
 * only its class is conditional — an FD {@code KnifeItem} when Farmer's Delight is present (FD's
 * class is only on the classpath then), else a plain {@link net.minecraft.world.item.SwordItem}.
 */
public final class KnifePlatform {
    private KnifePlatform() {
    }

    @ExpectPlatform
    public static Item createSilverKnife(Item.Properties properties, ToolMaterial material) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
