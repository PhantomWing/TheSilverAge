package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;

/**
 * The calls a test makes that each modloader answers its own way, each behind a signature both share. NeoForge's
 * and Fabric's test source sets each have this class, so a test body compiles on both unchanged.
 */
public final class TestLoader {
    private TestLoader() {
    }

    /**
     * Sets a boolean option by its id, in memory only, and gives back what it was. Each option is the
     * {@link TheSilverAgeFabricConfig} field named as its id.
     */
    public static boolean setBooleanSetting(String id, boolean value) {
        try {
            var field = TheSilverAgeFabricConfig.class.getField(id);
            TheSilverAgeFabricConfig config = TheSilverAgeFabricConfig.get();
            boolean was = field.getBoolean(config);
            field.setBoolean(config, value);
            return was;
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("No option " + id, e);
        }
    }
}
