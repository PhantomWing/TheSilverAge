package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.platform.CommonConfig;

import java.util.Optional;

/**
 * Client-side holder for the server's {@code enable_silver_arrows} and {@code enable_silver_gear}, sent on join and
 * whenever its config changes, which the creative tabs and the recipe viewers' info pages read: with the local
 * config's, a player on a server set otherwise saw items they couldn't get, or none they could. Empty (main menu, a
 * server without the mod) each falls back to the local config, as in single-player. Oxidation's is
 * {@link com.phantomwing.thesilverage.block.SilverOxidation}'s.
 */
public final class ServerContentSettings {
    private static volatile Optional<Boolean> arrowsFromServer = Optional.empty();
    private static volatile Optional<Boolean> gearFromServer = Optional.empty();

    private ServerContentSettings() {
    }

    public static void setFromServer(Boolean arrows, Boolean gear) {
        arrowsFromServer = Optional.ofNullable(arrows);
        gearFromServer = Optional.ofNullable(gear);
    }

    /** Whether Silver Arrows can be had where the client plays. */
    public static boolean arrows() {
        return arrowsFromServer.orElseGet(CommonConfig::silverArrows);
    }

    /** Whether silver gear can be had where the client plays. */
    public static boolean gear() {
        return gearFromServer.orElseGet(CommonConfig::silverGear);
    }
}
