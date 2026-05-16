package com.phantomwing.thesilverage.fabric;

import com.phantomwing.thesilverage.TheSilverAgeCommon;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric entrypoint for The Silver Age.
 *
 * <p>Phase 0 scaffold: simply delegates to the common initializer.</p>
 */
public final class TheSilverAgeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TheSilverAgeCommon.init();
    }
}
