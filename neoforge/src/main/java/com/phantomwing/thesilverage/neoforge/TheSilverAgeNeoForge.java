package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.neoforge.client.TheSilverAgeNeoForgeClient;
import com.phantomwing.thesilverage.neoforge.condition.ModConditions;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.neoforge.loot.ModLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

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

        // Client-only bootstrap (config screen factory, item properties, recipe-
        // override sync). Isolated in TheSilverAgeNeoForgeClient and reached only
        // via invokestatic behind this guard: the referenced client types (Screen,
        // ConfigurationScreen, ClientTickEvent, ...) must never be loaded/verified
        // on a dedicated server, or NeoForge's server class loader throws
        // "invalid dist DEDICATED_SERVER". A runtime guard alone is not enough —
        // verification of any method/lambda naming those types happens before the
        // branch runs, so the code must live in a class the server never touches.
        if (FMLEnvironment.dist.isClient()) {
            TheSilverAgeNeoForgeClient.init(modEventBus, container);
        }

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Firework Star Recipes (mutates the AT-exposed static fields).
            ModFireworks.register();
        });
    }
}
