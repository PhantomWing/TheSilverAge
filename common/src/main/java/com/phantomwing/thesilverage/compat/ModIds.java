package com.phantomwing.thesilverage.compat;

/** Mod IDs for optional dependencies used in compat checks and recipe conditions. */
public final class ModIds {
    public static final String CREATE = "create";
    /** Create: Aquatic Ambitions, whose fan channeling through a conduit weathers copper. */
    public static final String CREATE_AQUATIC_AMBITIONS = "create_aquatic_ambitions";
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
    /** Backported Spears: 1.21.11's spears on older versions, and an API other mods register their own through. */
    public static final String BACKPORTED_SPEARS = "spears";
    /** Yori3o's Grappling Hooks, whose hook tiers are read from every jar's {@code yo_hooks/hooks.json}. */
    public static final String GRAPPLING_HOOKS = "yo_hooks";

    private ModIds() {}
}
