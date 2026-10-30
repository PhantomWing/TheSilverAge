package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.combat.SilverUndeadSettings;

import java.util.Optional;

/**
 * Client-side holder for the server's {@link SilverUndeadSettings}, sent on join and after each {@code /reload}, which
 * the tooltips read: with the local config's numbers a player on a server set otherwise saw a bonus they weren't
 * dealing. Empty (main menu, a server without the mod) it falls back to the local config, as in single-player.
 */
public final class ServerUndeadSettings {
    private static volatile Optional<SilverUndeadSettings> fromServer = Optional.empty();

    private ServerUndeadSettings() {
    }

    public static void setFromServer(SilverUndeadSettings settings) {
        fromServer = Optional.of(settings);
    }

    public static void clear() {
        fromServer = Optional.empty();
    }

    /** The server's settings when connected to one that sent them, else the local config's. */
    public static SilverUndeadSettings current() {
        return fromServer.orElseGet(SilverUndeadSettings::fromConfig);
    }
}
