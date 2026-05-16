package com.phantomwing.thesilverage.platform.fabric;

/**
 * Fabric implementation of {@link ClientPlatform}.
 *
 * <p>Phase 1 shell: Moon Dial item-property override registration on Fabric is
 * TODO(phase 4). No-op for now (and never invoked yet — the Fabric entrypoint
 * does not call client setup in Phase 1).</p>
 */
public final class ClientPlatformImpl {
    private ClientPlatformImpl() {
    }

    public static void registerItemProperties() {
        // TODO(phase 4): Fabric ModelPredicateProviderRegistry equivalent.
    }
}
