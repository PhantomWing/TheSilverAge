package com.phantomwing.thesilverage;

import com.mojang.logging.LogUtils;
import com.phantomwing.thesilverage.block.ModBlockEntityTypes;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.condition.ModConditions;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.item.ModItemProperties;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.loot.ModLootModifiers;
import com.phantomwing.thesilverage.block.ModOxidizables;
import com.phantomwing.thesilverage.block.ModWaxables;
import com.phantomwing.thesilverage.ui.ModCreativeModeTab;
import com.phantomwing.thesilverage.world.ModPlacementModifiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(TheSilverAge.MOD_ID)
public class TheSilverAge
{
    public static final String MOD_ID = "thesilverage";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TheSilverAge()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
        registerManagers(eventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Firework Star Recipes.
            ModFireworks.register();

            // Register oxidizables and waxables.
            ModOxidizables.register();
            ModWaxables.register();
        });
    }

    // Register all managers to the event bus.
    private void registerManagers(IEventBus eventBus) {
        ModConditions.register();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntityTypes.register(eventBus);
        ModCreativeModeTab.register(eventBus);
        ModLootModifiers.register(eventBus);
        ModPlacementModifiers.register(eventBus);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.register();
        }
    }

    public static ResourceLocation resourceLocation(String path) {
        return new ResourceLocation(TheSilverAge.MOD_ID, path);
    }
}
