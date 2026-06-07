package com.phantomwing.thesilverage.client;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.utils.LevelUtils;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

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
    public static final ResourceLocation MOON_PHASE =
            ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    private ModItemProperties() {
    }

    /**
     * Client-setup registrations shared by both loaders: the {@code thesilverage:moon_phase}
     * range-select property type, plus the transparent-block render layers. 1.21.4 no longer
     * carries render type in the model JSON, so doors/trapdoors (cutout) and grates
     * (translucent) register their layer here via Architectury's cross-loader RenderTypeRegistry.
     *
     * <p>1.21.6: block render layers moved from {@code RenderType} (cutout()/translucent())
     * to the {@link ChunkSectionLayer} enum; Architectury's RenderTypeRegistry.register now
     * takes a {@code ChunkSectionLayer}.</p>
     */
    public static void register() {
        RangeSelectItemModelProperties.ID_MAPPER.put(MOON_PHASE, MoonPhaseProperty.MAP_CODEC);

        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, CUTOUT_BLOCKS);
        RenderTypeRegistry.register(ChunkSectionLayer.TRANSLUCENT, TRANSLUCENT_BLOCKS);
    }

    /** Doors + trapdoors (all weather/waxed states) — cutout render layer. */
    private static final Block[] CUTOUT_BLOCKS = Stream.of(
            ModBlocks.SILVER_DOOR, ModBlocks.EXPOSED_SILVER_DOOR, ModBlocks.WEATHERED_SILVER_DOOR, ModBlocks.OXIDIZED_SILVER_DOOR,
            ModBlocks.WAXED_SILVER_DOOR, ModBlocks.WAXED_EXPOSED_SILVER_DOOR, ModBlocks.WAXED_WEATHERED_SILVER_DOOR, ModBlocks.WAXED_OXIDIZED_SILVER_DOOR,
            ModBlocks.SILVER_TRAPDOOR, ModBlocks.EXPOSED_SILVER_TRAPDOOR, ModBlocks.WEATHERED_SILVER_TRAPDOOR, ModBlocks.OXIDIZED_SILVER_TRAPDOOR,
            ModBlocks.WAXED_SILVER_TRAPDOOR, ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR, ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR, ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR
    ).map(s -> (Block) s.get()).toArray(Block[]::new);

    /** Grates (all weather/waxed states) — translucent render layer. */
    private static final Block[] TRANSLUCENT_BLOCKS = Stream.of(
            ModBlocks.SILVER_GRATE, ModBlocks.EXPOSED_SILVER_GRATE, ModBlocks.WEATHERED_SILVER_GRATE, ModBlocks.OXIDIZED_SILVER_GRATE,
            ModBlocks.WAXED_SILVER_GRATE, ModBlocks.WAXED_EXPOSED_SILVER_GRATE, ModBlocks.WAXED_WEATHERED_SILVER_GRATE, ModBlocks.WAXED_OXIDIZED_SILVER_GRATE
    ).map(s -> (Block) s.get()).toArray(Block[]::new);

    /**
     * 16-state moon-phase property: the settled phase at night plus a transition
     * frame during the day (see {@link LevelUtils#getMoonPhaseSignal}). Stateless,
     * so its codec is a {@code MapCodec.unit}.
     */
    public record MoonPhaseProperty() implements RangeSelectItemModelProperty {
        public static final MapCodec<MoonPhaseProperty> MAP_CODEC = MapCodec.unit(new MoonPhaseProperty());

        @Override
        public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
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
