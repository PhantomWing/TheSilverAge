package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

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
            int moonPhaseSignal = LevelUtils.getMoonPhaseSignal(world);
            return moonPhaseSignal / 100f; // Normalize between [0, 1] for texture selection
        });
    }
}
