package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.neoforge.Configuration;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Locale;

/**
 * The calls a test makes that each modloader answers its own way, each behind a signature both share. NeoForge's
 * and Fabric's test source sets each have this class, so a test body compiles on both unchanged.
 */
public final class TestLoader {
    private TestLoader() {
    }

    /**
     * Sets a boolean option by its id, in memory only, and gives back what it was. Each option's value is the
     * {@link Configuration} field named as its id in capitals.
     */
    public static boolean setBooleanSetting(String id, boolean value) {
        try {
            ModConfigSpec.BooleanValue option = (ModConfigSpec.BooleanValue) Configuration.class
                    .getField(id.toUpperCase(Locale.ROOT)).get(null);
            boolean was = option.get();
            option.set(value);
            return was;
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("No option " + id, e);
        }
    }
}
