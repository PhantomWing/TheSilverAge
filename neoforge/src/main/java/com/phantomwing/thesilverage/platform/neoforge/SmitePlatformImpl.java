package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.combat.UndeadProtectionHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/**
 * NeoForge implementation of {@link com.phantomwing.thesilverage.platform.SmitePlatform}.
 *
 * <p>{@code LivingIncomingDamageEvent} fires before armour and resistance are
 * applied, matching where vanilla adds the Smite enchantment's damage.</p>
 *
 * <p>The same event also carries the silver armour reduction, but that one is NOT
 * applied to {@code getAmount()}: it is registered as a reduction modifier on the
 * {@code ENCHANTMENTS} stage, which runs after armour, where vanilla applies the
 * Protection enchantment. See {@link UndeadProtectionHandler} for why the stage
 * matters.</p>
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

            event.addReductionModifier(DamageContainer.Reduction.ENCHANTMENTS,
                    (container, damage) -> UndeadProtectionHandler.applyReduction(
                            event.getEntity(), event.getSource(), damage));
        });
    }
}
