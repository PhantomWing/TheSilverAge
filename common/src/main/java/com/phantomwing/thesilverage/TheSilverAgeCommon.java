package com.phantomwing.thesilverage;

import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import com.phantomwing.thesilverage.block.ModBlockEntityTypes;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import com.phantomwing.thesilverage.platform.WeatheringPlatform;
import com.phantomwing.thesilverage.ui.ModCreativeModeTab;
import com.phantomwing.thesilverage.world.ModPlacementModifiers;

/**
 * Common (loader-agnostic) entrypoint for The Silver Age. Registers every Architectury
 * {@code DeferredRegister} and wires the loader-agnostic gameplay event hooks;
 * loader-specific bootstrap is performed by the per-loader entrypoints.
 */
public final class TheSilverAgeCommon {
    public static final String MOD_ID = TheSilverAge.MOD_ID;

    private TheSilverAgeCommon() {
    }

    public static void init() {
        // Blocks MUST register before items: on Fabric, Architectury resolves ModItems'
        // BlockItem factories eagerly during ITEMS.register(), so the Block supplier must already exist.
        ModBlocks.register();
        ModItems.register();
        ModBlockEntityTypes.register();
        ModCreativeModeTab.register();
        ModArmorMaterials.register();
        ModPlacementModifiers.register();

        // Loader-agnostic gameplay events.
        MonsterArmorHandler.register();

        // Server→client sync of override_vanilla_recipes on join (matches the recipe-override
        // texture pack to the server). Client-side receiver is wired from each loader's client entrypoint.
        ModNetworking.register();

        // Silver oxidation / waxing relationships (single common spec), bridged through @ExpectPlatform.
        SilverWeatheringSpec.oxidationPairs().forEach(pair ->
                WeatheringPlatform.registerOxidation(pair.from(), pair.to()));
        SilverWeatheringSpec.waxablePairs().forEach(pair ->
                WeatheringPlatform.registerWaxable(pair.from(), pair.to()));

        // Loader-specific setup that has no Architectury equivalent, bridged through @ExpectPlatform.
        CommonPlatform.onCommonSetup();
    }
}
