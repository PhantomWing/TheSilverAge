package com.phantomwing.thesilverage.platform.fabric;

/**
 * Fabric implementation of {@link com.phantomwing.thesilverage.platform.SmitePlatform}.
 *
 * <p>No-op: Fabric API has no event that can modify an incoming damage amount
 * ({@code ServerLivingEntityEvents.ALLOW_DAMAGE} is allow/deny only), so the
 * hook is the always-active
 * {@code com.phantomwing.thesilverage.fabric.mixin.LivingEntityMixin}, applied
 * via {@code thesilverage.mixins.json}. Nothing to subscribe at runtime.</p>
 */
public final class SmitePlatformImpl {
    private SmitePlatformImpl() {
    }

    public static void registerDamageHandler() {
        // Intentionally empty — see the class javadoc.
    }
}
