package com.phantomwing.thesilverage.platform.forge;

/**
 * Forge implementation of {@link com.phantomwing.thesilverage.platform.SmitePlatform}.
 *
 * <p>No-op: the bonus is added at the head of {@code LivingEntity#hurt} by
 * {@code com.phantomwing.thesilverage.forge.mixin.LivingEntityMixin}, where Fabric's twin adds it, rather than in
 * {@code LivingHurtEvent}, which comes after the damage cooldown and so counted different hits.</p>
 */
public final class SmitePlatformImpl {
    private SmitePlatformImpl() {
    }

    public static void registerDamageHandler() {
        // Intentionally empty - see the class javadoc.
    }
}
