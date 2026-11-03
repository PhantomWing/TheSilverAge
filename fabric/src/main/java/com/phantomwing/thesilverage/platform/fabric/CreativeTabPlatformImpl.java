package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.platform.CreativeTabPlatform;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public final class CreativeTabPlatformImpl {
    /** After the default phase, so the items other mods add (Backported Spears' Golden Spear) are there to insert after. */
    private static final ResourceLocation AFTER_MODS = TheSilverAge.resourceLocation("after_mods");

    private CreativeTabPlatformImpl() {
    }

    public static void modify(ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabPlatform.Inserter> contents) {
        Event<ItemGroupEvents.ModifyEntries> event = ItemGroupEvents.modifyEntriesEvent(tab);
        event.addPhaseOrdering(Event.DEFAULT_PHASE, AFTER_MODS);
        event.register(AFTER_MODS, entries -> contents.accept((anchor, stack) -> insertAfter(entries, anchor, stack)));
    }

    // Fabric appends a stack whose anchor the tab doesn't list; throwing as NeoForge does lets the next anchor be tried.
    private static void insertAfter(FabricItemGroupEntries entries, ItemStack anchor, ItemStack stack) {
        boolean listed = entries.getDisplayStacks().stream().anyMatch(shown -> ItemStack.isSameItemSameComponents(shown, anchor))
                || entries.getSearchTabStacks().stream().anyMatch(shown -> ItemStack.isSameItemSameComponents(shown, anchor));
        if (!listed) {
            throw new IllegalArgumentException(anchor + " isn't in the tab");
        }
        entries.addAfter(anchor, stack);
    }
}
