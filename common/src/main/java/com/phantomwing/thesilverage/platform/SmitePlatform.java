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
 * <p>Both loaders on this line: a no-op - the hook is each loader's {@code LivingEntityMixin}, adding the bonus
 * at the head of {@code LivingEntity#hurt}, where neither Fabric API nor Forge's {@code LivingHurtEvent} (which comes after
 * the damage cooldown) can.</p>
 */
public final class SmitePlatform {
    private SmitePlatform() {
    }

    @ExpectPlatform
    public static void registerDamageHandler() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
