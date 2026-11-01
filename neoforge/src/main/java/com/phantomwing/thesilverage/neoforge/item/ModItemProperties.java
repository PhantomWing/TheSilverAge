package com.phantomwing.thesilverage.neoforge.item;

import net.minecraft.resources.Identifier;

// NeoForge-side delegate to the shared client ModItemProperties for the Moon Dial.
public final class ModItemProperties {
    /** Re-exported for source compatibility; identical values to the shared class. */
    public static final Identifier MOON_PHASE =
            com.phantomwing.thesilverage.client.ModItemProperties.MOON_PHASE;
    public static final int SUPER_MOON_NIGHT =
            com.phantomwing.thesilverage.client.ModItemProperties.SUPER_MOON_NIGHT;
    public static final int SUPER_MOON_UPCOMING =
            com.phantomwing.thesilverage.client.ModItemProperties.SUPER_MOON_UPCOMING;

    private ModItemProperties() {
    }

    public static void register() {
        com.phantomwing.thesilverage.client.ModItemProperties.register();
    }
}
