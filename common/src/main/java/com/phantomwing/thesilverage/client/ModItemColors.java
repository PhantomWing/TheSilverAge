package com.phantomwing.thesilverage.client;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.LunarEvents;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * The Moon Dial's moon tint.
 *
 * <p>The Moon Dial's model is two layers: an untinted dial base (layer0) and the
 * moon itself (layer1). From 1.21.4 a tint is data: the dial's {@code items/}
 * definition gives layer1 the {@link LunarEventTint} tint source
 * ({@code thesilverage:lunar_event}), so only the moon is recoloured. With Enhanced
 * Celestials installed the moon takes the colour of the active lunar event, matching
 * the moon in the sky and the tooltip line above the phase name. Enhanced Celestials
 * has no 1.21.10 build, so on this line {@link LunarEvents} answers as for an ordinary
 * night and the moon stays as drawn.</p>
 *
 * <p>The tint source's type goes into vanilla's {@code ItemTintSources} mapper before
 * item models load: through NeoForge's {@code RegisterColorHandlersEvent.ItemTintSources},
 * and on Fabric into the mapper itself, widened by the access widener, from the client
 * entrypoint.</p>
 *
 * <p>Client-only by its call sites, like {@link ModItemProperties}, and not marked {@code @Environment(CLIENT)}:
 * Architectury turns that into NeoForge's {@code @OnlyIn}, which NeoForge only warns about.</p>
 */
public final class ModItemColors {
    /** {@code thesilverage:lunar_event}, the tint source's type in an item definition. */
    public static final Identifier LUNAR_EVENT = TheSilverAge.resourceLocation("lunar_event");

    /** Vanilla's "no tint": white, i.e. the texture is drawn as authored. */
    private static final int NO_TINT = -1;

    /**
     * How bright a tint has to be before it is used as-is, as a fraction of
     * white. Raise it for a paler, more washed-out moon during a lunar event;
     * lower it for a deeper, more saturated one.
     */
    private static final float MIN_TINT_LUMINANCE = 0.4f;

    /** Rec. 709 luma coefficients — how much each channel contributes to brightness. */
    private static final float R_LUMA = 0.2126f;
    private static final float G_LUMA = 0.7152f;
    private static final float B_LUMA = 0.0722f;

    private ModItemColors() {
    }

    /** The moon's tint: the active lunar event's colour, or none. Stateless, so its codec is a unit. */
    public record LunarEventTint() implements ItemTintSource {
        public static final MapCodec<LunarEventTint> MAP_CODEC = MapCodec.unit(new LunarEventTint());

        @Override
        public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
            int colour = LunarEvents.activeEventColor(level);
            return colour == NO_TINT ? NO_TINT : asTint(colour);
        }

        @Override
        public MapCodec<? extends ItemTintSource> type() {
            return MAP_CODEC;
        }
    }

    /**
     * Conditions a lunar event's colour for use as a multiply tint.
     *
     * <p>Enhanced Celestials picks its colours for the sky, where the moon is
     * drawn against black and a dim colour still reads clearly. A tint multiplies
     * the texture instead, so it can only ever darken the dial's moon — a Blood
     * Moon's {@code #990000} is 13% as bright as white, and turns the moon almost
     * black. Both steps below are derived from the colour itself, so add-on events
     * are handled without knowing anything about them.</p>
     */
    private static int asTint(int colour) {
        float r = (colour >> 16 & 0xFF) / 255f;
        float g = (colour >> 8 & 0xFF) / 255f;
        float b = (colour & 0xFF) / 255f;

        // Scale the brightest channel up to full. This undoes the dimming without
        // shifting the hue at all — Harvest Moon's #665828 lands on #ffdc64, which
        // is all but exactly the #ffdb63 EC itself uses for its Super variant.
        float peak = Math.max(r, Math.max(g, b));
        if (peak > 0f) {
            r /= peak;
            g /= peak;
            b /= peak;
        }

        // A fully saturated hue still reads dark even at full channel (pure red is
        // only 21% as bright as white), so lift it toward white — but only by the
        // amount that reaches the floor, so colours that are already bright enough
        // keep every bit of their saturation. Blending toward white moves luminance
        // linearly, which is what makes the exact amount a closed form.
        float luminance = R_LUMA * r + G_LUMA * g + B_LUMA * b;
        if (luminance < MIN_TINT_LUMINANCE) {
            float lift = (MIN_TINT_LUMINANCE - luminance) / (1f - luminance);
            r += (1f - r) * lift;
            g += (1f - g) * lift;
            b += (1f - b) * lift;
        }

        return 0xFF000000 | channel(r) << 16 | channel(g) << 8 | channel(b);
    }

    private static int channel(float value) {
        return Mth.clamp(Math.round(value * 255f), 0, 255);
    }
}
