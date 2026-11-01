package com.phantomwing.thesilverage.neoforge;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.network.ModNetworking;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.combat.SilverUndeadGoals;
import com.phantomwing.thesilverage.neoforge.client.TheSilverAgeNeoForgeClient;
import com.phantomwing.thesilverage.neoforge.condition.ModConditions;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.neoforge.loot.ModLootModifiers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.GameData;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

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
        modEventBus.addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getModId().equals(TheSilverAge.MOD_ID)) {
                ModNetworking.configChanged();
            }
        });
        NeoForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) -> SilverUndeadGoals.addTo(event.getEntity()));
    }

    /**
     * Lightning finds a rod by its point of interest, which vanilla gives its own rod alone. NeoForge 21.3 to 21.7 have no
     * ExtendPoiTypesEvent, so the Silver Lightning Rods' states go in the map it looks a state's point of interest up in.
     * NeoForge's 1.21.8 builds have it again, but this jar runs on 21.7 too, where naming it fails to load the mod; the map
     * is in both.
     */
    private static void extendLightningRodPoi() {
        Holder<PoiType> lightningRod = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getOrThrow(PoiTypes.LIGHTNING_ROD);
        ModBlocks.silverLightningRods().forEach(rod -> rod.getStateDefinition().getPossibleStates()
                .forEach(state -> GameData.getBlockStatePointOfInterestTypeMap().put(state, lightningRod)));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Firework Star Recipes (mutates the AT-exposed static fields).
            ModFireworks.register();
            extendLightningRodPoi();
        });
    }
}
