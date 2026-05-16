package com.phantomwing.thesilverage;

import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import com.phantomwing.thesilverage.block.ModBlockEntityTypes;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import com.phantomwing.thesilverage.ui.ModCreativeModeTab;
import com.phantomwing.thesilverage.world.ModPlacementModifiers;

/**
 * Common (loader-agnostic) entrypoint for The Silver Age.
 *
 * <p>Registers every Architectury {@code DeferredRegister} and wires the
 * loader-agnostic gameplay event hooks. Loader-specific bootstrap (NeoForge
 * config registration, the config screen factory, GLM/condition registries,
 * firework recipe patching, data generation, Create/EMI compat) is performed by
 * the per-loader entrypoints.</p>
 */
public final class TheSilverAgeCommon {
    public static final String MOD_ID = TheSilverAge.MOD_ID;

    private TheSilverAgeCommon() {
    }

    public static void init() {
        // Architectury deferred registries. Building the static fields of these
        // classes enqueues every entry; register() flushes them to the platform
        // registries at the right time on each loader.
        ModItems.register();
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModCreativeModeTab.register();
        ModArmorMaterials.register();
        ModPlacementModifiers.register();

        // Loader-agnostic gameplay events.
        MonsterArmorHandler.register();

        // Loader-specific setup that has no Architectury equivalent yet
        // (NeoForge: firework recipe patch via AT-exposed fields + config;
        // Fabric: Phase 4 no-ops). Bridged through @ExpectPlatform.
        CommonPlatform.onCommonSetup();
    }
}
