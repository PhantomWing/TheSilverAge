package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.recipe.RecipeOverride;

import java.util.Optional;
import java.util.Set;

/**
 * Client-side holder for the recipe overrides the current server has on, pushed on join (see
 * {@link com.phantomwing.thesilverage.network.RecipeOverrideSyncPayload}).
 *
 * <p>While connected to a server that sent them, {@link #isOn(RecipeOverride)} answers with the
 * server's, so each recipe-override texture pack matches the server regardless of the player's
 * local config. When empty (main menu, a server without the mod, or an older version) it falls
 * back to the local config, as in single-player.</p>
 *
 * <p>Mutated only from the render thread (the network receiver queues onto it and the disconnect
 * listeners run there too); {@code volatile} guards the publication so any incidental off-thread
 * read sees a consistent reference.</p>
 */
public final class ServerOverrideState {
    private static volatile Optional<Set<String>> serverEnabled = Optional.empty();

    private ServerOverrideState() {
    }

    /** Record the setting ids of the overrides a server has on, sent on join. */
    public static void setFromServer(Set<String> enabledSettingIds) {
        serverEnabled = Optional.of(Set.copyOf(enabledSettingIds));
    }

    /** Forget the server's overrides (on disconnect / returning to the main menu). */
    public static void clear() {
        serverEnabled = Optional.empty();
    }

    /**
     * Whether an override applies here: the server's when connected to one that sent them, else the
     * local config. Defaults to on while the config can't be read yet, as it is by default.
     */
    public static boolean isOn(RecipeOverride override) {
        Optional<Set<String>> fromServer = serverEnabled;
        if (fromServer.isPresent()) {
            return fromServer.get().contains(override.settingId());
        }
        try {
            return CommonConfig.recipeOverride(override);
        } catch (RuntimeException notLoadedYet) {
            return true;
        }
    }
}
