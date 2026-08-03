package com.phantomwing.thesilverage.compat;

import com.phantomwing.thesilverage.compat.enhancedcelestials.EnhancedCelestialsCompat;
import com.phantomwing.thesilverage.compat.enhancedcelestials2.EnhancedCelestials2Compat;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * What the Moon Dial knows about lunar events, independent of which mod supplies
 * them.
 *
 * <p>Enhanced Celestials and Enhanced Celestials 2 are separate mods with separate
 * ids that can both be installed at once, so this asks EC2 first and falls back to
 * EC1 when EC2 has nothing to report. Preferring the newer one is not enough on its
 * own: EC2's Core is a library that ships no events, so someone running it without
 * the Default Lunar Events mod would otherwise silently lose the EC1 events they
 * still have.</p>
 *
 * <p>Each branch is behind an {@code isModLoaded} check, which is what keeps the
 * per-mod compat classes (and every Enhanced Celestials type they name) from being
 * classloaded when that mod is absent.</p>
 */
public final class LunarEvents {
    /** Returned by the colour accessors when nothing should be tinted. */
    public static final int NO_COLOR = -1;

    private LunarEvents() {
    }

    /** The styled name of the active lunar event, or {@code null} for an ordinary night. */
    @Nullable
    public static Component activeEventName(@Nullable Level level) {
        if (CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS_2)) {
            Component name = EnhancedCelestials2Compat.getActiveLunarEventName(level);
            if (name != null) {
                return name;
            }
        }

        if (CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS)) {
            return EnhancedCelestialsCompat.getActiveLunarEventName(level);
        }

        return null;
    }

    /**
     * The active lunar event's moon colour as opaque {@code 0xFFRRGGBB}, or
     * {@link #NO_COLOR} when there is no event to tint for.
     */
    public static int activeEventColor(@Nullable Level level) {
        if (CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS_2)) {
            int colour = EnhancedCelestials2Compat.getActiveLunarEventColor(level);
            if (colour != NO_COLOR) {
                return colour;
            }
        }

        if (CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS)) {
            return EnhancedCelestialsCompat.getActiveLunarEventColor(level);
        }

        return NO_COLOR;
    }

    /** Whether an enlarged "super" moon is up, in either mod. */
    public static boolean isSuperMoonActive(@Nullable Level level) {
        return (CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS_2)
                && EnhancedCelestials2Compat.isSuperMoonActive(level))
                || (CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS)
                && EnhancedCelestialsCompat.isSuperMoonActive(level));
    }
}
