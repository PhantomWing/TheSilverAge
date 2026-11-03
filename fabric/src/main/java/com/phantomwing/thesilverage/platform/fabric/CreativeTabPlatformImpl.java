package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.platform.CreativeTabPlatform;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public final class CreativeTabPlatformImpl {
    /** After the default phase, so the items other mods add (Farmer's Delight's knives, Create's sheets) are there to insert after. */
    private static final Identifier AFTER_MODS = TheSilverAge.resourceLocation("after_mods");

    private CreativeTabPlatformImpl() {
    }

    public static void modify(ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabPlatform.Inserter> contents) {
        Event<CreativeModeTabEvents.ModifyOutput> event = CreativeModeTabEvents.modifyOutputEvent(tab);
        event.addPhaseOrdering(Event.DEFAULT_PHASE, AFTER_MODS);
        event.register(AFTER_MODS, output -> contents.accept((anchor, stack) -> insertAfter(output, anchor, stack)));
    }

    // Fabric appends a stack whose anchor the tab doesn't list; throwing as NeoForge does lets the next anchor be tried.
    private static void insertAfter(FabricCreativeModeTabOutput output, ItemStack anchor, ItemStack stack) {
        boolean listed = output.getDisplayStacks().stream().anyMatch(shown -> ItemStack.isSameItemSameComponents(shown, anchor))
                || output.getSearchTabStacks().stream().anyMatch(shown -> ItemStack.isSameItemSameComponents(shown, anchor));
        if (!listed) {
            throw new IllegalArgumentException(anchor + " isn't in the tab");
        }
        output.insertAfter(anchor, List.of(stack));
    }
}
