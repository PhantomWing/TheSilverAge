package com.phantomwing.thesilverage.ui;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverOxidation;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.item.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register(TheSilverAge.MOD_ID + "_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ModItems.SILVER_INGOT.get()))
                    .title(Component.translatable(("item_group." + TheSilverAge.MOD_ID)))
                    .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach(item -> {
                        if (isShown(item.get())) {
                            output.accept(item.get());
                        }
                    }))
                    .build());

    /**
     * Whether the creative tabs list {@code item}: one of the mod's tab items, but not the weathered and waxed variants
     * while oxidation is off, which are unobtainable in survival then, nor Silver Arrows while they can't be got.
     */
    public static boolean isShown(Item item) {
        if (ModItems.CREATIVE_TAB_ITEMS.stream().noneMatch(entry -> entry.get() == item)) {
            return false;
        }
        if (item == ModItems.SILVER_ARROW.get() && !CommonConfig.silverArrows()) {
            return false;
        }
        return SilverOxidation.enabledOnClient() || !(item instanceof BlockItem blockItem)
                || !SilverOxidation.nonBaseVariants().contains(blockItem.getBlock());
    }

    public static void register() {
        CREATIVE_MODE_TABS.register();
    }
}
