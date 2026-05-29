package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.neoforge.client.RecipeOverridePackHandler;
import com.phantomwing.thesilverage.neoforge.condition.ModConditions;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.neoforge.loot.ModLootModifiers;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.platform.ClientPlatform;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

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
        // codecs). Permanently NeoForge-side by design: loot uses the "shared
        // spec, per-loader apply" model — the common SilverLootSpec /
        // SilverLootAlgorithms drive these GLMs here and a loot mixin on Fabric.
        ModConditions.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        // Config (kept on NeoForge for now; common reaches values via @ExpectPlatform).
        container.registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        // This will use NeoForge's ConfigurationScreen to display this mod's configs (client only).
        if (FMLEnvironment.dist.isClient()) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
            modEventBus.addListener(this::clientSetup);

            // Recipe-override server-sync client receiver. MUST be registered
            // here during mod construction, NOT in clientSetup: Architectury's
            // NeoForge adaptor defers the actual payload binding to
            // RegisterPayloadHandlersEvent, which fires before FMLClientSetupEvent
            // — registering from clientSetup adds the listener too late and the
            // receiver never binds. On receipt the value is cached in
            // ServerOverrideState and RecipeOverridePackHandler re-applies the
            // pack's SELECTION (it can't re-register the pack — AddPackFindersEvent
            // fires only once). On disconnect (game-bus LoggingOut) the value is
            // cleared and the pack reverts to the local config.
            ModNetworking.registerClientReceiver(RecipeOverridePackHandler::syncFromState);
            NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut e) -> {
                ServerOverrideState.clear();
                Minecraft mc = Minecraft.getInstance();
                if (mc != null) mc.execute(RecipeOverridePackHandler::syncFromState);
            });
        }

        modEventBus.addListener(this::commonSetup);
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
