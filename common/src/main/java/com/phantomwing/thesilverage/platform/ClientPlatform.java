package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge for client-only setup that has no Architectury
 * equivalent (item property override registration).
 *
 * <p>Not called from {@code TheSilverAgeCommon.init()} (which runs on both
 * sides); each loader entrypoint invokes this at the correct client lifecycle
 * point.</p>
 */
public final class ClientPlatform {
    private ClientPlatform() {
    }

    /**
     * Registers the {@code moon_phase} item property override on the Moon Dial.
     *
     * <p>NeoForge: delegates to {@code ModItemProperties.register()} (vanilla
     * {@code ItemProperties.register}), invoked from {@code FMLClientSetupEvent}.<br>
     * Fabric: TODO(phase 4) — no-op shell.</p>
     */
    @ExpectPlatform
    public static void registerItemProperties() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
