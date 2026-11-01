package com.phantomwing.thesilverage.client;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.LunarEvents;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Loader-agnostic client registration of the Moon Dial's {@code thesilverage:moon_phase}
 * range-select item-model property.
 *
 * <p>1.21.4 removed {@code ItemProperties.register} and the whole item-{@code overrides}
 * system, replacing them with data-driven <b>item model definitions</b>
 * ({@code assets/<ns>/items/<id>.json}). A {@code range_dispatch} definition selects the
 * Moon Dial frame from a numeric property; this class registers that property's type.</p>
 *
 * <p>Custom range-select properties live in the vanilla (private) late-bound
 * {@link RangeSelectItemModelProperties#ID_MAPPER}; we widen it via the shared access
 * widener (+ NeoForge access-transformer mirror) and {@code put} our property in, so the
 * registration is the same on both loaders. NeoForge also registers the property, and the Moon Dial's
 * {@code thesilverage:lunar_event} tint source ({@link ModItemColors}), through its own events, which come before
 * item models load; putting the same codec under the same id again changes nothing.
 * Both {@code ClientPlatform.registerItemProperties()} impls delegate to {@link #register()}.</p>
 *
 * <p><b>Client-only by call-site isolation</b> — only referenced from client-only call
 * sites (each loader's {@code ClientPlatformImpl} + the dist-guarded client bootstrap),
 * never from server-reachable code, so it is never loaded on a dedicated server. It is
 * deliberately <em>not</em> marked {@code @Environment(CLIENT)}: Architectury rewrites that
 * to NeoForge's {@code @OnlyIn(Dist.CLIENT)} in the production jar, and NeoForge (1.21.x)
 * dropped {@code @OnlyIn}'s runtime member-stripping — so the annotation no longer does
 * anything except emit an ERROR-level warning in every user's log. Call-site isolation is
 * the actual safety mechanism, so the annotation is pure noise and is omitted.</p>
 */
public final class ModItemProperties {
    public static final Identifier MOON_PHASE =
            Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    /** The two {@link LevelUtils#getMoonPhaseSignal} values whose overlay is the full moon. */
    private static final int FULL_MOON_NIGHT = 0;
    private static final int FULL_MOON_UPCOMING = 15;

    /** Signals past the 0-15 phase range, reserved for the super moon overlay. */
    public static final int SUPER_MOON_NIGHT = 16;
    public static final int SUPER_MOON_UPCOMING = 17;

    private ModItemProperties() {
    }

    /**
     * Client-setup registrations shared by both loaders: the {@code thesilverage:moon_phase} range-select property
     * type and the Moon Dial's tint source. 26.1 has no programmatic block render layers (Architectury's
     * RenderTypeRegistry is gone): a block's cutout comes from its textures' alpha, so the doors, lanterns, chains,
     * bars and torch need nothing here.
     */
    public static void register() {
        RangeSelectItemModelProperties.ID_MAPPER.put(MOON_PHASE, MoonPhaseProperty.MAP_CODEC);
        ItemTintSources.ID_MAPPER.put(ModItemColors.LUNAR_EVENT, ModItemColors.LunarEventTint.MAP_CODEC);
    }

    /**
     * 16-state moon-phase property: the settled phase at night plus a transition
     * frame during the day (see {@link LevelUtils#getMoonPhaseSignal}). Stateless,
     * so its codec is a {@code MapCodec.unit}.
     */
    public record MoonPhaseProperty() implements RangeSelectItemModelProperty {
        public static final MapCodec<MoonPhaseProperty> MAP_CODEC = MapCodec.unit(new MoonPhaseProperty());

        @Override
        public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner entity, int seed) {
            // 0..17 → 0..1.0625 in exact 1/16 steps (16 and 17 are the super moon frames). Powers-of-two denominators are
            // exactly representable as floats, so the range_dispatch thresholds match
            // the returned value precisely (no float/double rounding ambiguity).
            return superMoonSignal(level, LevelUtils.getMoonPhaseSignal(level)) / 16f;
        }

        @Override
        public MapCodec<? extends RangeSelectItemModelProperty> type() {
            return MAP_CODEC;
        }
    }

    /**
     * Swaps in the reserved super-moon signals when Enhanced Celestials is
     * running one of its enlarged events, so the model can replace the full-moon
     * overlay with the dedicated super moon texture.
     *
     * <p>Only signals 0 and 15 are remapped — the two frames whose overlay is the
     * full moon (the full-moon night, and the day leading up to it). Super events
     * only roll on a full moon, but a pack could widen that; restricting the swap
     * keeps a crescent frame from being handed a full moon's art.</p>
     */
    private static int superMoonSignal(Level world, int moonPhaseSignal) {
        if (moonPhaseSignal != FULL_MOON_NIGHT && moonPhaseSignal != FULL_MOON_UPCOMING) {
            return moonPhaseSignal;
        }

        if (!LunarEvents.isSuperMoonActive(world)) {
            return moonPhaseSignal;
        }

        return moonPhaseSignal == FULL_MOON_NIGHT ? SUPER_MOON_NIGHT : SUPER_MOON_UPCOMING;
    }
}
