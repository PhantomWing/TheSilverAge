package com.phantomwing.thesilverage;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

/** Shared constants and helpers for The Silver Age. Mod bootstrap lives in {@link TheSilverAgeCommon#init()}. */
public final class TheSilverAge {
    public static final String MOD_ID = "thesilverage";
    public static final Logger LOGGER = LogUtils.getLogger();

    private TheSilverAge() {
    }

    public static Identifier resourceLocation(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
