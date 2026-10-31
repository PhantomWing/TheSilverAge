package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.platform.CommonConfig;

import java.util.Optional;

/**
 * Client-side holder for the server's {@code enable_silver_arrows}, sent on join and whenever its config changes, which
 * the creative tabs and the recipe viewers' info pages read: with the local config's, a player on a server set
 * otherwise saw arrows they couldn't get, or none they could. Empty (main menu, a server without the mod) it falls back
 * to the local config, as in single-player. Oxidation's is {@link com.phantomwing.thesilverage.block.SilverOxidation}'s.
 */
public final class ServerContentSettings {
    private static volatile Optional<Boolean> arrowsFromServer = Optional.empty();

    private ServerContentSettings() {
    }

    public static void setArrowsFromServer(Boolean enabled) {
        arrowsFromServer = Optional.ofNullable(enabled);
    }

    /** Whether Silver Arrows can be had where the client plays. */
    public static boolean arrows() {
        return arrowsFromServer.orElseGet(CommonConfig::silverArrows);
    }
}
