package com.phantomwing.thesilverage.forge;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.advancement.ModCriteriaTriggers;
import com.phantomwing.thesilverage.combat.SilverUndeadGoals;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.forge.client.TheSilverAgeForgeClient;
import com.phantomwing.thesilverage.forge.compat.mekanism.SilverSlurries;
import com.phantomwing.thesilverage.forge.condition.ModConditions;
import com.phantomwing.thesilverage.forge.loot.ModLootModifiers;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.forge.CreativeTabPlatformImpl;
import com.phantomwing.thesilverage.platform.forge.WeatheringPlatformImpl;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Forge entrypoint for The Silver Age.
 *
 * <p>Performs the loader-agnostic bootstrap via {@link TheSilverAgeCommon#init()}
 * (Architectury registries + cross-loader events), then layers on the Forge-only
 * pieces: the {@code COMMON} config, the GLM / recipe-condition registries, the
 * firework-star recipe patch (timed to {@code FMLCommonSetupEvent}), and the
 * client-side bootstrap.</p>
 */
@Mod(TheSilverAgeCommon.MOD_ID)
public final class TheSilverAgeForge {
    public TheSilverAgeForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Architectury's registries and events on Forge need the mod's bus before anything registers.
        EventBuses.registerModEventBus(TheSilverAge.MOD_ID, modEventBus);

        // Config first: common code reads it as soon as it registers.
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        TheSilverAgeCommon.init();

        // Forge-only registries (GLM serializers + recipe condition serializers). Loot uses the "shared spec,
        // per-loader apply" model: the common SilverLootSpec / SilverLootAlgorithms drive these GLMs here and a loot
        // mixin on Fabric.
        ModConditions.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        // Silver's slurries go in Mekanism's slurry registry, which exists only with Mekanism.
        if (ModList.get().isLoaded(ModIds.MEKANISM)) {
            SilverSlurries.register(modEventBus);
        }

        if (FMLEnvironment.dist.isClient()) {
            TheSilverAgeForgeClient.init(modEventBus);
        }

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) {
                ModNetworking.configChanged();
            }
        });
        modEventBus.addListener(CreativeTabPlatformImpl::buildContents);
        MinecraftForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) -> SilverUndeadGoals.addTo(event.getEntity()));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Vanilla's maps aren't thread-safe and this event runs in parallel with other mods', so on the main thread.
        event.enqueueWork(() -> {
            ModCriteriaTriggers.register();
            TheSilverAgeCommon.setup();
            // Register Firework Star Recipes (mutates the AT-exposed static fields).
            ModFireworks.register();
            // Silver's oxidation and waxing, into vanilla's maps.
            WeatheringPlatformImpl.apply();
        });
    }
}
