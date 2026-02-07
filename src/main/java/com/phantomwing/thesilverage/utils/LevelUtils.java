package com.phantomwing.thesilverage.utils;

import net.minecraft.world.level.Level;

public class LevelUtils {
    public static int getMoonPhaseSignal(Level level) {
        if (level != null && !level.dimensionType().hasFixedTime()) {
            // If it is day, show a "transition" between the current and next moon phase.
            // If it is night, show the current moon phase.
            double progressionInDay = ((double)level.getDayTime() / Level.TICKS_PER_DAY) % 1;
            boolean isDay = progressionInDay < 0.52 || progressionInDay > 0.95; // Consider it day for a short period of time to show the transition.

            int moonPhase = level.getMoonPhase() * 2 - (isDay ? 1 : 0);

            // Wrap around to ensure the texture ID is between 0 and 15 (inclusive).
            if (moonPhase < 0) {
                moonPhase += 16;
            }

            return moonPhase;
        }

        return 0;
    }
}
