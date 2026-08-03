package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.compat.enhancedcelestials.EnhancedCelestialsCompat;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Loader-agnostic item colour handlers.
 *
 * <p>The Moon Dial's model is two layers: an untinted dial base (layer0) and the
 * moon itself (layer1). {@code item/generated} gives every layer a tint index
 * equal to its layer number, so returning a colour for tint index
 * {@link #MOON_TINT_INDEX} recolours only the moon, leaving the silver dial
 * alone. With Enhanced Celestials installed the moon takes the colour of the
 * active lunar event, matching the moon in the sky and the tooltip line above
 * the phase name.</p>
 *
 * <p>{@link ColorHandlerRegistry} is Architectury's cross-loader wrapper (Fabric's
 * {@code ColorProviderRegistry.ITEM} / NeoForge's
 * {@code RegisterColorHandlersEvent.Item}), so both client entrypoints call
 * {@link #register()} directly — no {@code @ExpectPlatform} bridge needed.</p>
 *
 * <p>{@code @Environment(CLIENT)} for the same reason as
 * {@link ModItemProperties}: it is only ever referenced from client-only call
 * sites and must never load on a dedicated server.</p>
 */
@Environment(EnvType.CLIENT)
public final class ModItemColors {
    /** Layer 1 of the Moon Dial model — the moon. Layer 0 (the dial) stays untinted. */
    private static final int MOON_TINT_INDEX = 1;

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

    public static void register() {
        ColorHandlerRegistry.registerItemColors(ModItemColors::moonDialTint, ModItems.MOON_DIAL);
    }

    private static int moonDialTint(ItemStack stack, int tintIndex) {
        if (tintIndex != MOON_TINT_INDEX
                || !CommonPlatform.isModLoaded(ModIds.ENHANCED_CELESTIALS)) {
            return NO_TINT;
        }

        // Guarded by isModLoaded above so EnhancedCelestialsCompat (and every EC
        // class it names) is only ever classloaded when the mod is present.
        Level level = Minecraft.getInstance().level;
        int colour = EnhancedCelestialsCompat.getActiveLunarEventColor(level);

        return colour == NO_TINT ? NO_TINT : asTint(colour);
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
