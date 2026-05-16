package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.neoforge.condition.ModConditions;
import com.phantomwing.thesilverage.neoforge.firework.ModFireworks;
import com.phantomwing.thesilverage.neoforge.loot.ModLootModifiers;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * NeoForge entrypoint for The Silver Age.
 *
 * <p>Performs the loader-agnostic bootstrap via {@link TheSilverAgeCommon#init()}
 * (Architectury registries + cross-loader events), then layers on the NeoForge-only
 * pieces that have no Architectury equivalent in Phase 1: the {@code COMMON} config,
 * the config screen factory, the NeoForge GLM / recipe-condition registries, the
 * firework-star recipe patch (timed to {@code FMLCommonSetupEvent}), and the
 * client-side item property registration.</p>
 */
@Mod(TheSilverAgeCommon.MOD_ID)
public final class TheSilverAgeNeoForge {
    public TheSilverAgeNeoForge(IEventBus modEventBus, ModContainer container) {
        // Loader-agnostic registration + events (Architectury DeferredRegisters,
        // MonsterArmorHandler via @ExpectPlatform). Architectury's DeferredRegister
        // wires itself into NeoForge registration internally.
        TheSilverAgeCommon.init();

        // NeoForge-only deferred registries (GLM serializers + recipe condition
        // codecs). These stay NeoForge-specific until Phase 2/4.
        ModConditions.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        // Config (kept on NeoForge for now; common reaches values via @ExpectPlatform).
        container.registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        // This will use NeoForge's ConfigurationScreen to display this mod's configs (client only).
        if (FMLEnvironment.dist.isClient()) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

        modEventBus.addListener(this::commonSetup);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(this::clientSetup);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Firework Star Recipes (mutates the AT-exposed static fields).
            ModFireworks.register();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientPlatform::registerItemProperties);
    }
}
