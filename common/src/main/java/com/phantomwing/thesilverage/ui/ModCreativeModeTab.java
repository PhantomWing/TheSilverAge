package com.phantomwing.thesilverage.ui;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverOxidation;
import com.phantomwing.thesilverage.item.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
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
                        // Add items to this tab. While oxidation is off the weathered and
                        // waxed variants are unobtainable in survival, so they are hidden
                        // and only the base silver blocks are listed.
                        boolean hideVariants = !SilverOxidation.enabled();
                        ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> {
                            if (hideVariants && item.get() instanceof BlockItem blockItem
                                    && SilverOxidation.nonBaseVariants().contains(blockItem.getBlock())) {
                                return;
                            }

                            output.accept(item.get());
                        });
                    })
                    .build());

    public static void register() {
        CREATIVE_MODE_TABS.register();
    }
}
