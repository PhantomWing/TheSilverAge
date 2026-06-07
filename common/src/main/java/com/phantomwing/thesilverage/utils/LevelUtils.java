package com.phantomwing.thesilverage.utils;

import net.minecraft.world.level.Level;

public class LevelUtils {
    /**
     * Ticks per Minecraft day. 1.21.11 removed the {@code Level.TICKS_PER_DAY} constant (and
     * {@code Level.getMoonPhase()}); the value is stable, so it is reproduced locally.
     */
    private static final long TICKS_PER_DAY = 24000L;

    /**
     * Canonical moon phase (0-7), reproducing the removed {@code Level.getMoonPhase()}:
     * day index ({@code dayTime / 24000}) modulo 8. 0 = Full Moon, 4 = New Moon.
     */
    private static int moonPhaseOf(Level level) {
        return (int) (level.getDayTime() / TICKS_PER_DAY % 8L);
    }

    public static int getMoonPhaseSignal(Level level) {
        if (level != null && !level.dimensionType().hasFixedTime()) {
            // If it is day, show a "transition" between the current and next moon phase.
            // If it is night, show the current moon phase.
            double progressionInDay = ((double) level.getDayTime() / TICKS_PER_DAY) % 1;
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
     * Canonical moon phase (0-7), i.e. 0 = Full Moon, 4 = New Moon (the vanilla brightness
     * ordering used everywhere, including the wiki's lunar-phase table). Unlike
     * {@link #getMoonPhaseSignal(Level)} this is NOT doubled for the day/night transition —
     * there are only eight named phases, so the text tooltip maps to the canonical phase the
     * dial settles on.
     *
     * <p>Same guards as the signal helper: dimensions with a fixed time (Nether/End) and a
     * {@code null} level fall back to {@code 0} (Full Moon), matching the full-moon frame the
     * dial's texture shows there.</p>
     */
    public static int getMoonPhase(Level level) {
        if (level != null && !level.dimensionType().hasFixedTime()) {
            return moonPhaseOf(level);
        }

        return 0;
    }
}
