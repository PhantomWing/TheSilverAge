package com.phantomwing.thesilverage.gametest;

import java.util.function.Supplier;

/**
 * A test's way to turn an option off for a moment. Tests in a batch run side by side, so the option is set, the body
 * run and the option put back in one go, before any other test's next tick could read it.
 */
public final class TestSettings {
    private TestSettings() {
    }

    /** What {@code body} gives back with option {@code id} set to {@code value}. */
    public static <T> T with(String id, boolean value, Supplier<T> body) {
        boolean was = TestLoader.setBooleanSetting(id, value);
        try {
            return body.get();
        } finally {
            TestLoader.setBooleanSetting(id, was);
        }
    }
}
