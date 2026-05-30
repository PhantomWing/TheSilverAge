package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

/**
 * {@code @ExpectPlatform} bridge that builds the Silver Knife item.
 *
 * <p>Farmer's Delight is an <em>optional</em> dependency, but when present we
 * want the knife to be a real FD {@code KnifeItem} (Cutting Board support, knife
 * ItemAbilities, etc.). FD's {@code KnifeItem} class is loader-specific and only
 * on the classpath when FD is installed, so the choice is made per loader:</p>
 * <ul>
 *   <li>FD loaded → an instance of the loader's {@code SilverKnifeItem}
 *       (extends {@code vectorwing.farmersdelight.common.item.KnifeItem}).</li>
 *   <li>FD absent → a plain {@link net.minecraft.world.item.SwordItem} fallback,
 *       so the item still exists (registry-consistent / multiplayer-safe) and is
 *       a usable weapon, and the mod loads standalone.</li>
 * </ul>
 *
 * <p>The item is always <em>registered</em>; only its concrete class is
 * conditional. The {@code Item.Properties} passed in already carry the
 * knife attack attributes (set in common via the vanilla
 * {@code DiggerItem.createAttributes}, matching FD's own knives).</p>
 *
 * <p>Implemented per loader at
 * {@code com.phantomwing.thesilverage.platform.<loader>.KnifePlatformImpl}.</p>
 */
public final class KnifePlatform {
    private KnifePlatform() {
    }

    @ExpectPlatform
    public static Item createSilverKnife(Item.Properties properties, Tier tier) {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
