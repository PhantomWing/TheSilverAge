package com.phantomwing.thesilverage.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * What the Moon Dial knows about lunar events, independent of which mod supplies them.
 *
 * <p>On 1.21.1 this asks Enhanced Celestials and Enhanced Celestials 2. Neither has a build for 1.21.2 or 1.21.3, so
 * on this line every night is an ordinary one: the dial shows the vanilla phase, untinted. The calls stay so the
 * Moon Dial's code reads the same as on the lines that have the compat.</p>
 */
public final class LunarEvents {
    /** Returned by the colour accessors when nothing should be tinted. */
    public static final int NO_COLOR = -1;

    private LunarEvents() {
    }

    /** The styled name of the active lunar event, or {@code null} for an ordinary night. */
    @Nullable
    public static Component activeEventName(@Nullable Level level) {
        return null;
    }

    /** The active lunar event's moon colour as opaque {@code 0xFFRRGGBB}, or {@link #NO_COLOR}. */
    public static int activeEventColor(@Nullable Level level) {
        return NO_COLOR;
    }

    /** Whether an enlarged "super" moon is up. */
    public static boolean isSuperMoonActive(@Nullable Level level) {
        return false;
    }
}
