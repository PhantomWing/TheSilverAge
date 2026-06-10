package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge for client-only setup that has no Architectury
 * equivalent. Invoked by each loader entrypoint at the correct client lifecycle point.
 */
public final class ClientPlatform {
    private ClientPlatform() {
    }

    /** Registers the {@code moon_phase} item property override on the Moon Dial. */
    @ExpectPlatform
    public static void registerItemProperties() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
