package com.phantomwing.thesilverage;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

/**
 * Shared constants and helpers for The Silver Age.
 *
 * <p>This is the loader-agnostic constants holder retained (rather than folding
 * everything into {@link TheSilverAgeCommon}) so the large number of existing
 * {@code TheSilverAge.MOD_ID} / {@code TheSilverAge.resourceLocation(...)} /
 * {@code TheSilverAge.LOGGER} references across both the common and NeoForge
 * source sets keep compiling unchanged. The actual mod bootstrap lives in
 * {@link TheSilverAgeCommon#init()} (called from each loader entrypoint).</p>
 */
public final class TheSilverAge {
    public static final String MOD_ID = "thesilverage";
    public static final Logger LOGGER = LogUtils.getLogger();

    private TheSilverAge() {
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
