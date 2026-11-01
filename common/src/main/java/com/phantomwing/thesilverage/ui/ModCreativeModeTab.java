package com.phantomwing.thesilverage.ui;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register(TheSilverAge.MOD_ID + "_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ModItems.SILVER_INGOT.get()))
                    .title(Component.translatable(("item_group." + TheSilverAge.MOD_ID)))
                    .displayItems((parameters, output) -> {
                        ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get()));
                    })
                    .build());

    public static void register() {
        CREATIVE_MODE_TABS.register();
    }
}
