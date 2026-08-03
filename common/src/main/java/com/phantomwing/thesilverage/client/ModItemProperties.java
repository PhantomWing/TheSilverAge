package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.LunarEvents;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * Loader-agnostic client item-property registration.
 *
 * <p>The Moon Dial {@code moon_phase} override is pure vanilla client API
 * ({@link net.minecraft.client.renderer.item.ItemProperties#register}), so the
 * registration logic is shared here instead of being duplicated per loader. Both
 * loaders' {@code ClientPlatform.registerItemProperties()} implementations
 * delegate to {@link #register()}:</p>
 *
 * <ul>
 *   <li><b>NeoForge</b>: {@code platform.neoforge.ClientPlatformImpl} →
 *       {@code neoforge.item.ModItemProperties.register()} → here, invoked from
 *       {@code FMLClientSetupEvent} (unchanged lifecycle/behaviour).</li>
 *   <li><b>Fabric</b>: {@code platform.fabric.ClientPlatformImpl} → here,
 *       invoked from the {@code ClientModInitializer}
 *       ({@code fabric.client.TheSilverAgeFabricClient}).</li>
 * </ul>
 *
 * <p>This class is {@code @Environment(CLIENT)} — fabric-loader's
 * {@code @Environment} is the one Fabric annotation explicitly allowed in
 * {@code common} (see {@code common/build.gradle}). It is only ever referenced
 * from client-only call sites, so it is never loaded on a dedicated server.</p>
 */
@Environment(EnvType.CLIENT)
public final class ModItemProperties {
    /** {@code thesilverage:moon_phase} — identical id to the original NeoForge registration. */
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
     * Registers the {@code moon_phase} item-property override on the Moon Dial.
     *
     * <p>Predicate is byte-for-byte the original NeoForge one: read the moon
     * phase signal for the holder's level and normalise to {@code [0, 1]} by
     * dividing by {@code 100f} for texture selection.</p>
     */
    public static void register() {
        ItemProperties.register(ModItems.MOON_DIAL.get(), MOON_PHASE, (stack, world, entity, seed) -> {
            int moonPhaseSignal = superMoonSignal(world, LevelUtils.getMoonPhaseSignal(world));
            return moonPhaseSignal / 100f; // Normalize between [0, 1] for texture selection
        });
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
