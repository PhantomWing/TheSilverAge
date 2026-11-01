package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge for the incoming-damage hook backing
 * {@link com.phantomwing.thesilverage.combat.SilverSmiteHandler}.
 *
 * <p>Architectury's {@code EntityEvent.LIVING_HURT} only returns an
 * {@code EventResult} (allow/deny) and cannot change the damage amount, so the
 * hook has to be loader-specific.</p>
 *
 * <p>NeoForge: subscribes {@code LivingIncomingDamageEvent} and adds to
 * {@code event.getAmount()}.<br>
 * Fabric: a no-op — the equivalent hook is the always-active
 * {@code LivingEntityMixin}, registered through {@code thesilverage.mixins.json}.</p>
 */
public final class SmitePlatform {
    private SmitePlatform() {
    }

    @ExpectPlatform
    public static void registerDamageHandler() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
