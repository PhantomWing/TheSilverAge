package com.phantomwing.thesilverage.client;

import java.util.Optional;

/** Client-side holder for the {@code override_vanilla_recipes} value the current server pushed on join. */
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
