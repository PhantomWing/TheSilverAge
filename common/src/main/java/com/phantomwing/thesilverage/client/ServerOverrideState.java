package com.phantomwing.thesilverage.client;

import java.util.Optional;

/**
 * Client-side holder for the {@code override_vanilla_recipes} value the current
 * server pushed on join (see
 * {@link com.phantomwing.thesilverage.network.RecipeOverrideSyncPayload}).
 *
 * <p>While connected to a server that sent the value, {@link #effective(boolean)}
 * returns the server's value, so the recipe-override texture pack matches the
 * server regardless of the player's local config. When empty (main menu, a
 * server without the mod, or a pre-sync version) it falls back to the local
 * config value — preserving the original single-player / standalone behaviour.</p>
 *
 * <p>Mutated only from the render thread (the network receiver queues onto it and
 * the disconnect listeners run there too); {@code volatile} guards the
 * publication so any incidental off-thread read sees a consistent reference.</p>
 */
public final class ServerOverrideState {
    private static volatile Optional<Boolean> serverValue = Optional.empty();

    private ServerOverrideState() {
    }

    /** Record the value a server sent on join. */
    public static void setFromServer(boolean enabled) {
        serverValue = Optional.of(enabled);
    }

    /** Forget any server value (on disconnect / returning to the main menu). */
    public static void clear() {
        serverValue = Optional.empty();
    }

    /** @return the server's value if connected to one that sent it, else {@code localFallback}. */
    public static boolean effective(boolean localFallback) {
        return serverValue.orElse(localFallback);
    }
}
