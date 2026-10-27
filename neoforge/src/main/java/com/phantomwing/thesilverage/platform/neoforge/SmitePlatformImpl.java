package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/**
 * NeoForge implementation of {@link com.phantomwing.thesilverage.platform.SmitePlatform}.
 *
 * <p>{@code LivingIncomingDamageEvent} fires before armour and resistance are
 * applied, matching where vanilla adds the Smite enchantment's damage.</p>
 */
public final class SmitePlatformImpl {
    private SmitePlatformImpl() {
    }

    public static void registerDamageHandler() {
        NeoForge.EVENT_BUS.addListener((LivingIncomingDamageEvent event) -> {
            float bonus = SilverSmiteHandler.handleIncomingDamage(event.getEntity(), event.getSource());
            if (bonus > 0.0f) {
                event.setAmount(event.getAmount() + bonus);
            }
        });
    }
}
