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

// NeoForge entrypoint. Client-only wiring is isolated in TheSilverAgeNeoForgeClient
// so the dedicated server never classloads the client types it references.
@Mod(TheSilverAgeCommon.MOD_ID)
public final class TheSilverAgeNeoForge {
    public TheSilverAgeNeoForge(IEventBus modEventBus, ModContainer container) {
        TheSilverAgeCommon.init();

        ModConditions.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        container.registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        if (FMLEnvironment.getDist().isClient()) {
            TheSilverAgeNeoForgeClient.init(modEventBus, container);
        }

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Mutates the AT-exposed static fields.
            ModFireworks.register();
        });
    }
}
