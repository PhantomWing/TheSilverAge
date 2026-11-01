package com.phantomwing.thesilverage.client;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.compat.LunarEvents;
import com.phantomwing.thesilverage.utils.LevelUtils;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
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
 * registration is the same on both loaders. NeoForge also registers the property, and the Moon Dial's
 * {@code thesilverage:lunar_event} tint source ({@link ModItemColors}), through its own events, which come before
 * item models load; putting the same codec under the same id again changes nothing.
 * Both {@code ClientPlatform.registerItemProperties()} impls delegate to {@link #register()}.</p>
 *
 * <p>{@code @Environment(CLIENT)} — only referenced from client-only call sites, never
 * loaded on a dedicated server.</p>
 */
@Environment(EnvType.CLIENT)
public final class ModItemProperties {
    /** {@code thesilverage:moon_phase} — id of the custom range-select property + the items/ range_dispatch. */
    public static final ResourceLocation MOON_PHASE =
            ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    /** The two {@link LevelUtils#getMoonPhaseSignal} values whose overlay is the full moon. */
    private static final int FULL_MOON_NIGHT = 0;
    private static final int FULL_MOON_UPCOMING = 15;

    /** Signals past the 0-15 phase range, reserved for the super moon overlay. */
    public static final int SUPER_MOON_NIGHT = 16;
    public static final int SUPER_MOON_UPCOMING = 17;

    private ModItemProperties() {
    }

    /**
     * Client-setup registrations shared by both loaders: the {@code thesilverage:moon_phase}
     * range-select property type, plus the transparent-block render layers. 1.21.4 no longer
     * carries render type in the model JSON, so doors, trapdoors, lanterns and the torch (cutout), chains and bars
     * (cutout_mipped, as vanilla's chain and iron bars) and grates (translucent) register their layer here via
     * Architectury's cross-loader RenderTypeRegistry.
     */
    public static void register() {
        RangeSelectItemModelProperties.ID_MAPPER.put(MOON_PHASE, MoonPhaseProperty.MAP_CODEC);
        ItemTintSources.ID_MAPPER.put(ModItemColors.LUNAR_EVENT, ModItemColors.LunarEventTint.MAP_CODEC);

        RenderTypeRegistry.register(RenderType.cutout(), CUTOUT_BLOCKS);
        RenderTypeRegistry.register(RenderType.cutoutMipped(), CUTOUT_MIPPED_BLOCKS);
        RenderTypeRegistry.register(RenderType.translucent(), TRANSLUCENT_BLOCKS);
    }

    /** Doors + trapdoors (all weather/waxed states) — cutout render layer. */
    private static final Block[] CUTOUT_BLOCKS = Stream.of(
            ModBlocks.SILVER_DOOR, ModBlocks.EXPOSED_SILVER_DOOR, ModBlocks.WEATHERED_SILVER_DOOR, ModBlocks.OXIDIZED_SILVER_DOOR,
            ModBlocks.WAXED_SILVER_DOOR, ModBlocks.WAXED_EXPOSED_SILVER_DOOR, ModBlocks.WAXED_WEATHERED_SILVER_DOOR, ModBlocks.WAXED_OXIDIZED_SILVER_DOOR,
            ModBlocks.SILVER_TRAPDOOR, ModBlocks.EXPOSED_SILVER_TRAPDOOR, ModBlocks.WEATHERED_SILVER_TRAPDOOR, ModBlocks.OXIDIZED_SILVER_TRAPDOOR,
            ModBlocks.WAXED_SILVER_TRAPDOOR, ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR, ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR, ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR,
            ModBlocks.SILVER_LANTERN, ModBlocks.EXPOSED_SILVER_LANTERN, ModBlocks.WEATHERED_SILVER_LANTERN, ModBlocks.OXIDIZED_SILVER_LANTERN,
            ModBlocks.WAXED_SILVER_LANTERN, ModBlocks.WAXED_EXPOSED_SILVER_LANTERN, ModBlocks.WAXED_WEATHERED_SILVER_LANTERN, ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN,
            ModBlocks.SILVER_TORCH, ModBlocks.SILVER_WALL_TORCH
    ).map(s -> (Block) s.get()).toArray(Block[]::new);

    /** Chains + bars (all weather/waxed states) — cutout_mipped, as vanilla's chain and iron bars. */
    private static final Block[] CUTOUT_MIPPED_BLOCKS = Stream.of(
            ModBlocks.SILVER_CHAIN, ModBlocks.EXPOSED_SILVER_CHAIN, ModBlocks.WEATHERED_SILVER_CHAIN, ModBlocks.OXIDIZED_SILVER_CHAIN,
            ModBlocks.WAXED_SILVER_CHAIN, ModBlocks.WAXED_EXPOSED_SILVER_CHAIN, ModBlocks.WAXED_WEATHERED_SILVER_CHAIN, ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN,
            ModBlocks.SILVER_BARS, ModBlocks.EXPOSED_SILVER_BARS, ModBlocks.WEATHERED_SILVER_BARS, ModBlocks.OXIDIZED_SILVER_BARS,
            ModBlocks.WAXED_SILVER_BARS, ModBlocks.WAXED_EXPOSED_SILVER_BARS, ModBlocks.WAXED_WEATHERED_SILVER_BARS, ModBlocks.WAXED_OXIDIZED_SILVER_BARS
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
