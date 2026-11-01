package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.combat.SilverUndeadGoals;
import com.phantomwing.thesilverage.neoforge.client.TheSilverAgeNeoForgeClient;
import com.phantomwing.thesilverage.neoforge.condition.ModConditions;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.neoforge.loot.ModLootModifiers;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.poi.ExtendPoiTypesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

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
        modEventBus.addListener(TheSilverAgeNeoForge::extendLightningRodPoi);
        modEventBus.addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) {
                ModNetworking.configChanged();
            }
        });
        NeoForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) -> SilverUndeadGoals.addTo(event.getEntity()));
    }

    /**
     * Lightning finds a rod by its point of interest, which vanilla gives its own copper rods alone.
     */
    private static void extendLightningRodPoi(ExtendPoiTypesEvent event) {
        ModBlocks.silverLightningRods().forEach(rod -> event.addBlockToPoi(PoiTypes.LIGHTNING_ROD, rod));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Mutates the AT-exposed static fields.
            ModFireworks.register();
        });
    }
}
