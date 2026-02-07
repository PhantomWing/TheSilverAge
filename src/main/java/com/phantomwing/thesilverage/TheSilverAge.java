package com.phantomwing.thesilverage;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.condition.ModConditions;
import com.phantomwing.thesilverage.firework.ModFireworks;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.loot.LootModifierManager;
import com.phantomwing.thesilverage.ui.ModCreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(TheSilverAge.MOD_ID)
public class TheSilverAge
{
    public static final String MOD_ID = "thesilverage";

    public TheSilverAge(IEventBus eventBus, ModContainer container)
    {
        eventBus.addListener(this::commonSetup);

        container.registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        // This will use NeoForge's ConfigurationScreen to display this mod's configs (Client only)
        if (FMLEnvironment.dist.isClient()) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

        NeoForge.EVENT_BUS.register(this);
        registerManagers(eventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Firework Star Recipes.
            ModFireworks.register();
        });
    }

    // Register all managers to the event bus.
    private void registerManagers(IEventBus eventBus) {
        ModConditions.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModCreativeModeTab.register(eventBus);
        ModArmorMaterials.register(eventBus);
        LootModifierManager.register(eventBus);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, path);
    }
}
