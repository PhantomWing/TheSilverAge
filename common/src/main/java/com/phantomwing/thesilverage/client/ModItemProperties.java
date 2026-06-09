package com.phantomwing.thesilverage.client;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;
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
 * registration is byte-identical on both loaders — no loader-specific event needed.
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
    /** {@code thesilverage:moon_phase} — id of the custom range-select property + the items/ range_dispatch. */
    public static final Identifier MOON_PHASE =
            Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    private ModItemProperties() {
    }

    /**
     * Client-setup registration shared by both loaders: the {@code thesilverage:moon_phase}
     * range-select property type.
     *
     * <p>26.1: programmatic block render layers are gone — Architectury removed
     * {@code RenderTypeRegistry}. 26.1 derives the render layer AUTOMATICALLY: blocks with
     * binary-alpha textures (doors, trapdoors, grates — all holes-with-opaque-metal, like vanilla
     * copper_grate / copper_door whose models carry no {@code render_type}) render as cutout
     * without any declaration. True translucency is the only opt-in, via {@code force_translucent}
     * on a texture {@code Material}. The mod's transparent blocks are all cutout, so nothing needs
     * to be registered or emitted — removing the old RenderTypeRegistry calls is sufficient.</p>
     */
    public static void register() {
        RangeSelectItemModelProperties.ID_MAPPER.put(MOON_PHASE, MoonPhaseProperty.MAP_CODEC);
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
            // 0..15 → 0..0.9375 in exact 1/16 steps. Powers-of-two denominators are
            // exactly representable as floats, so the range_dispatch thresholds match
            // the returned value precisely (no float/double rounding ambiguity).
            return LevelUtils.getMoonPhaseSignal(level) / 16f;
        }

        @Override
        public MapCodec<? extends RangeSelectItemModelProperty> type() {
            return MAP_CODEC;
        }
    }
}
