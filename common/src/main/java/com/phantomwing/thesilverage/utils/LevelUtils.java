package com.phantomwing.thesilverage.utils;

import net.minecraft.world.level.Level;

public class LevelUtils {
    private static final long TICKS_PER_DAY = 24000L;

    /** Canonical moon phase (0-7): day index modulo 8. 0 = Full Moon, 4 = New Moon. */
    private static int moonPhaseOf(Level level) {
        return (int) (level.getOverworldClockTime() / TICKS_PER_DAY % 8L);
    }

    public static int getMoonPhaseSignal(Level level) {
        if (level != null && !level.dimensionType().hasFixedTime()) {
            // If it is day, show a "transition" between the current and next moon phase.
            // If it is night, show the current moon phase.
            double progressionInDay = ((double) level.getOverworldClockTime() / TICKS_PER_DAY) % 1;
            boolean isDay = progressionInDay < 0.52 || progressionInDay > 0.95; // Consider it day for a short period of time to show the transition.

            int moonPhase = moonPhaseOf(level) * 2 - (isDay ? 1 : 0);

            // Wrap around to ensure the texture ID is between 0 and 15 (inclusive).
            if (moonPhase < 0) {
                moonPhase += 16;
            }

            return moonPhase;
        }

        return 0;
    }

    /**
     * Canonical moon phase (0-7), 0 = Full Moon. Unlike {@link #getMoonPhaseSignal(Level)} this is
     * NOT doubled for the day/night transition. Fixed-time dimensions and a {@code null} level give {@code 0}.
     */
    public static int getMoonPhase(Level level) {
        if (level != null && !level.dimensionType().hasFixedTime()) {
            return moonPhaseOf(level);
        }

        return 0;
    }
}
