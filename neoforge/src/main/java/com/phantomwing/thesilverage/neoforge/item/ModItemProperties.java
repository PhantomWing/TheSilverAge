package com.phantomwing.thesilverage.neoforge.item;

import net.minecraft.resources.Identifier;

// NeoForge-side delegate to the shared client ModItemProperties for the Moon Dial.
public final class ModItemProperties {
    public static final Identifier MOON_PHASE =
            com.phantomwing.thesilverage.client.ModItemProperties.MOON_PHASE;

    private ModItemProperties() {
    }

    public static void register() {
        com.phantomwing.thesilverage.client.ModItemProperties.register();
    }
}
