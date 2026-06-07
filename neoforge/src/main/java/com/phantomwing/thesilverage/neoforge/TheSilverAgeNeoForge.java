package com.phantomwing.thesilverage.neoforge;

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
 * pieces: the {@code COMMON} config, the NeoForge GLM / recipe-condition registries,
 * and the firework-star recipe patch (timed to {@code FMLCommonSetupEvent}).</p>
 *
 * <p><b>Client-only wiring</b> (config screen, recipe-override sync, item
 * properties) lives in {@link TheSilverAgeNeoForgeClient}, reached only through a
 * guarded {@code invokestatic}. This class deliberately names <em>no</em>
 * client-only types: NeoForge 1.21.2+'s dedicated-server class loader rejects
 * loading any client class (it would crash during mod construction when the JVM
 * verifies lambda methods), so the client references must be isolated in a class
 * the server never loads.</p>
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

        // Client-only wiring, isolated in a separate class so the server class
        // loader never sees the client types it references (see class javadoc).
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
