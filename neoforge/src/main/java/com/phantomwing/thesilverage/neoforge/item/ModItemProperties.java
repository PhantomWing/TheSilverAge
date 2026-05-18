package com.phantomwing.thesilverage.neoforge.item;

import net.minecraft.resources.ResourceLocation;

/**
 * NeoForge-side delegate for the Moon Dial item-property override.
 *
 * <p>The registration logic is now shared in
 * {@link com.phantomwing.thesilverage.client.ModItemProperties} (pure vanilla
 * {@code ItemProperties.register} — see that class). This delegate is retained
 * so the existing call site
 * ({@code platform.neoforge.ClientPlatformImpl#registerItemProperties()},
 * invoked from {@code FMLClientSetupEvent}) keeps compiling and the NeoForge
 * lifecycle/behaviour is byte-identical: same {@code thesilverage:moon_phase}
 * id, same predicate.</p>
 */
public final class ModItemProperties {
    /** Re-exported for source compatibility; identical value to the shared class. */
    public static final ResourceLocation MOON_PHASE =
            com.phantomwing.thesilverage.client.ModItemProperties.MOON_PHASE;

    private ModItemProperties() {
    }

    public static void register() {
        com.phantomwing.thesilverage.client.ModItemProperties.register();
    }
}
