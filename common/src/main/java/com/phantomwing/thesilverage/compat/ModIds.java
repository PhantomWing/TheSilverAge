package com.phantomwing.thesilverage.compat;

/** Mod IDs for optional dependencies used in compat checks and recipe conditions. */
public final class ModIds {
    public static final String CREATE = "create";
    /** Farmer's Delight — same mod id on NeoForge (vectorwing) and Fabric (Refabricated). */
    public static final String FARMERS_DELIGHT = "farmersdelight";
    /** Adds lunar events (Blood Moon, Harvest Moon, ...) that the Moon Dial names. */
    public static final String ENHANCED_CELESTIALS = "enhancedcelestials";
    /**
     * Enhanced Celestials 2 — a separate mod with its own id, not an update to the
     * above, so both can be installed at once. This is the library half; the events
     * themselves ship in {@code enhancedcelestials2defaultlunarevents}, which we
     * never need to name because everything is read through the Core's API.
     */
    public static final String ENHANCED_CELESTIALS_2 = "enhancedcelestials2core";

    private ModIds() {}
}
