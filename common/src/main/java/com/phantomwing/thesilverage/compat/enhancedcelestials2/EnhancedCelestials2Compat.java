package com.phantomwing.thesilverage.compat.enhancedcelestials2;

import dev.corgitaco.enhancedcelestials2core.EnhancedCelestials;
import dev.corgitaco.enhancedcelestials2core.api.EnhancedCelestialsRegistry;
import dev.corgitaco.enhancedcelestials2core.api.lunarevent.LunarEvent;
import dev.corgitaco.enhancedcelestials2core.core.lunarevent.DefaultLunarEvents;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/**
 * Enhanced Celestials <b>2</b> compatibility for the Moon Dial.
 *
 * <p>EC2 is a separate mod rather than an update — different id, different package,
 * and it can be installed alongside the original — so this sits next to
 * {@code enhancedcelestials.EnhancedCelestialsCompat} rather than replacing it, and
 * {@link com.phantomwing.thesilverage.compat.LunarEvents} picks between them.</p>
 *
 * <p>EC2 rebuilt lunar events around a list of composable modifiers, but exposes
 * flattened accessors over them, so the three things the dial needs map across
 * cleanly. Two are actually nicer than in EC1: names carry a dedicated
 * {@code name_color} (a readable {@code #ff5555} rather than the moon's dark
 * {@code #990000}), and the moon colour is absent rather than white when an event
 * does not tint the moon.</p>
 *
 * <p><b>Only ever classloaded when Enhanced Celestials 2 is installed</b>, by the
 * same {@code isModLoaded} + {@code invokestatic} guard the EC1 compat uses.</p>
 */
public final class EnhancedCelestials2Compat {
    /** Enhanced Celestials 2's stock {@code moon_size}, used if {@code default} can't be read. */
    private static final float FALLBACK_DEFAULT_MOON_SIZE = 20.0f;

    private EnhancedCelestials2Compat() {
    }

    /**
     * The styled name of the active lunar event, or {@code null} for an ordinary
     * night. The key is derived from the event's registry id, so add-on events are
     * translated by their own mod with no per-add-on code.
     */
    @Nullable
    public static Component getActiveLunarEventName(@Nullable Level level) {
        Holder<LunarEvent> event = activeEvent(level);
        if (event == null) {
            return null;
        }

        Style style = Style.EMPTY;
        TextColor nameColor = event.value().getNameColor().orElse(null);
        if (nameColor != null) {
            style = style.withColor(nameColor);
        }

        return Component.translatable(LunarEvent.getTranslationKey(event)).withStyle(style);
    }

    /**
     * The colour the sky moon is tinted with during the active lunar event, as
     * opaque {@code 0xFFRRGGBB}, or {@code -1} (no tint) when there is no event or
     * the event leaves the moon alone. See
     * {@code EnhancedCelestialsCompat#getActiveLunarEventColor} for why the alpha
     * byte has to be set.
     */
    public static int getActiveLunarEventColor(@Nullable Level level) {
        Holder<LunarEvent> event = activeEvent(level);
        if (event == null) {
            return -1;
        }

        return event.value().getMoonTextureColor()
                .map(EnhancedCelestials2Compat::pack)
                .orElse(-1);
    }

    /**
     * Whether the active lunar event is a "super" moon, detected by moon size
     * exactly as on EC1 — every stock EC2 event leaves {@code moon_size} at its
     * default and only the four super variants raise it to {@code 40.0}.
     */
    public static boolean isSuperMoonActive(@Nullable Level level) {
        Holder<LunarEvent> event = activeEvent(level);
        return event != null && event.value().getMoonSize() > defaultMoonSize(level);
    }

    /**
     * The lunar event currently in progress, or {@code null} for an ordinary night
     * (EC2 reports its {@code DEFAULT} event then, whose name is just "Moon").
     */
    @Nullable
    private static Holder<LunarEvent> activeEvent(@Nullable Level level) {
        if (level == null) {
            return null;
        }

        return EnhancedCelestials.lunarForecastWorldData(level)
                .map(forecast -> {
                    Holder<LunarEvent> event = forecast.currentLunarEventHolder();
                    if (event == null || event.is(DefaultLunarEvents.DEFAULT)) {
                        return null;
                    }

                    return event;
                })
                .orElse(null);
    }

    /** {@code default}'s moon size, falling back to the value Enhanced Celestials 2 ships. */
    private static float defaultMoonSize(Level level) {
        return level.registryAccess()
                .registry(EnhancedCelestialsRegistry.LUNAR_EVENT_KEY)
                .map(registry -> registry.get(DefaultLunarEvents.DEFAULT))
                .map(LunarEvent::getMoonSize)
                .orElse(FALLBACK_DEFAULT_MOON_SIZE);
    }

    /**
     * EC2 hands out the moon colour as GL components. They are a plain
     * {@code /255} of the authored hex with no gamma applied, so scaling back up
     * reproduces it exactly.
     */
    private static int pack(Vector3f colour) {
        return 0xFF000000
                | channel(colour.x()) << 16
                | channel(colour.y()) << 8
                | channel(colour.z());
    }

    private static int channel(float value) {
        return Mth.clamp(Math.round(value * 255f), 0, 255);
    }
}
