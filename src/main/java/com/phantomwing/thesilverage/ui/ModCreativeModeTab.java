package com.phantomwing.thesilverage.ui;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheSilverAge.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register(TheSilverAge.MOD_ID + "_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SILVER_INGOT.get()))
                    .title(Component.translatable(("item_group." + TheSilverAge.MOD_ID)))
                    .displayItems((parameters, output) -> {
                        // Add items to this tab.
                        ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get()));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}