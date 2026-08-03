package com.phantomwing.thesilverage.compat.enhancedcelestials;

import dev.corgitaco.enhancedcelestials.EnhancedCelestials;
import dev.corgitaco.enhancedcelestials.api.EnhancedCelestialsRegistry;
import dev.corgitaco.enhancedcelestials.api.lunarevent.DefaultLunarEvents;
import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import dev.corgitaco.enhancedcelestials.util.CustomTranslationTextComponent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Enhanced Celestials compatibility for the Moon Dial.
 *
 * <p>Exposes the lunar event currently in progress: its name, so the dial's
 * tooltip can read "Blood Moon" above the phase, and its moon colour, so the
 * item texture's moon layer can be tinted to match the sky. The name Component
 * comes straight from the event definition, so Enhanced Celestials' own translations
 * AND those of its many add-ons (Horde Moon, Miner's Moon, ...) are picked up
 * with no per-add-on code. "Bigger" moons are separate registered events with
 * their own names ({@code super_blood_moon} = "Super Blood Moon"), so they need
 * no special handling either.</p>
 *
 * <p><b>Only ever classloaded when Enhanced Celestials is installed.</b> Every
 * reference to an EC type lives in this class, and callers reach it through an
 * {@code isModLoaded} guard via {@code invokestatic} — the same trick used by
 * {@code SilverKnifeItem}, so the JVM verifier never resolves EC classes when
 * the mod is absent.</p>
 */
public final class EnhancedCelestialsCompat {
    /** Enhanced Celestials' stock {@code moon_size}, used if {@code default} can't be read. */
    private static final float FALLBACK_DEFAULT_MOON_SIZE = 20.0f;

    private EnhancedCelestialsCompat() {
    }

    /**
     * The styled name of the active lunar event, or {@code null} when nothing
     * special is happening (Enhanced Celestials reports its {@code DEFAULT}
     * event, whose name is just "Moon" — the caller should show the vanilla
     * phase name in that case).
     */
    @Nullable
    public static Component getActiveLunarEventName(@Nullable Level level) {
        LunarEvent event = activeEvent(level);
        return event == null ? null : name(event);
    }

    /**
     * The colour Enhanced Celestials tints the sky moon with during the active
     * lunar event, or {@code -1} (no tint) when nothing special is happening.
     * Used to tint the moon layer of the Moon Dial's item texture so the icon
     * matches the moon in the sky.
     *
     * <p>Returned as opaque {@code 0xFFRRGGBB}: Enhanced Celestials stores the
     * colour as bare {@code 0xRRGGBB} (it feeds {@code Style#withColor}, which
     * rejects an alpha byte), and NeoForge patches {@code ItemRenderer} to honour
     * the alpha of an item tint where vanilla forces it opaque — so handing the
     * raw value straight through renders the moon fully transparent.</p>
     */
    public static int getActiveLunarEventColor(@Nullable Level level) {
        LunarEvent event = activeEvent(level);
        if (event == null) {
            return -1;
        }

        return 0xFF000000 | event.getClientSettings().colorSettings().getMoonTextureColor();
    }

    /**
     * Whether the active lunar event is a "super" moon — Enhanced Celestials'
     * enlarged variants (Super Blood Moon, Super Blue Moon, ...), which the dial
     * shows with a dedicated overlay texture.
     *
     * <p>Detected by moon size rather than by event id, so add-on events that
     * enlarge the moon are picked up too: every stock event including
     * {@code default} uses {@code moon_size: 20.0} and all four super variants
     * use {@code 40.0}. Compared against the {@code default} event's own size so
     * packs that rescale every moon still resolve correctly.</p>
     */
    public static boolean isSuperMoonActive(@Nullable Level level) {
        LunarEvent event = activeEvent(level);
        return event != null && event.getClientSettings().moonSize() > defaultMoonSize(level);
    }

    /** {@code default}'s moon size, falling back to the value Enhanced Celestials ships. */
    private static float defaultMoonSize(Level level) {
        return level.registryAccess()
                .registry(EnhancedCelestialsRegistry.LUNAR_EVENT_KEY)
                .map(registry -> registry.get(DefaultLunarEvents.DEFAULT))
                .map(event -> event.getClientSettings().moonSize())
                .orElse(FALLBACK_DEFAULT_MOON_SIZE);
    }

    /**
     * The lunar event currently in progress, or {@code null} for an ordinary
     * night (Enhanced Celestials reports its {@code DEFAULT} event then, whose
     * name is just "Moon").
     */
    @Nullable
    private static LunarEvent activeEvent(@Nullable Level level) {
        if (level == null) {
            return null;
        }

        return EnhancedCelestials.lunarForecastWorldData(level)
                .map(data -> {
                    Holder<LunarEvent> holder = data.currentLunarEventHolder();
                    if (holder == null || holder.is(DefaultLunarEvents.DEFAULT)) {
                        return null;
                    }

                    return holder.value();
                })
                .orElse(null);
    }

    /**
     * Builds the display name. Event definitions carry their own style (the
     * Blood Moon's name is red), so that is used when present; otherwise the
     * moon's texture colour stands in, which keeps add-on events coloured too.
     */
    private static Component name(LunarEvent event) {
        CustomTranslationTextComponent name = event.getTextComponents().name();
        Style style = name.getStyle();
        if (style == null || style.getColor() == null) {
            int colour = event.getClientSettings().colorSettings().getMoonTextureColor();
            style = (style == null ? Style.EMPTY : style).withColor(colour);
        }

        return Component.translatable(name.getKey()).withStyle(style);
    }
}
