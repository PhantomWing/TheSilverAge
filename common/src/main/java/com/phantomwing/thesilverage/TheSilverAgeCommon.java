package com.phantomwing.thesilverage;

import com.phantomwing.thesilverage.advancement.ModCriteriaTriggers;
import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import com.phantomwing.thesilverage.block.ModBlockEntityTypes;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.SilverOxidation;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import com.phantomwing.thesilverage.platform.WeatheringPlatform;
import com.phantomwing.thesilverage.particle.ModParticles;
import com.phantomwing.thesilverage.sound.ModSounds;
import com.phantomwing.thesilverage.ui.ModCreativeModeTab;
import com.phantomwing.thesilverage.ui.VanillaTabs;
import com.phantomwing.thesilverage.villager.SilverArrowsEnabled;
import com.phantomwing.thesilverage.world.ModPlacementModifiers;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.world.level.block.DispenserBlock;

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
        // Sounds first: on Fabric, Architectury makes each entry as it registers, and the silver armour's material
        // needs its equip sound's holder.
        ModSounds.register();
        // Blocks MUST register before items: on Fabric, Architectury resolves ModItems'
        // BlockItem factories eagerly during ITEMS.register(), so the Block supplier must already exist.
        ModBlocks.register();
        ModItems.register();
        ModBlockEntityTypes.register();
        ModEntityTypes.register();
        ModParticles.register();
        ModCriteriaTriggers.register();
        ModCreativeModeTab.register();
        VanillaTabs.register();
        ModArmorMaterials.register();
        ModPlacementModifiers.register();
        SilverArrowsEnabled.register();

        // Loader-agnostic gameplay events.
        MonsterArmorHandler.register();

        // Innate anti-undead damage for silver tools, and the matching damage
        // reduction on silver armour (both built in, not enchantments). They share
        // one loader-specific hook, wired here.
        SilverSmiteHandler.register();

        // A dispenser fires only the projectiles it has a behaviour for, which vanilla registers for its own arrows alone.
        LifecycleEvent.SETUP.register(() -> DispenserBlock.registerProjectileBehavior(ModItems.SILVER_ARROW.get()));

        // Refuses honeycomb on silver while the oxidation config is off.
        SilverOxidation.register();

        // Server→client sync of override_vanilla_recipes on join, so each
        // client's recipe-override texture pack matches the server it joins
        // (the recipes themselves are already server-driven). Registers the
        // server-side payload type + join hook on both sides; the client-side
        // receiver is wired separately from each loader's client entrypoint.
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
