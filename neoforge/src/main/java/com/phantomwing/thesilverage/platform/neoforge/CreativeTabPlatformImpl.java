package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.platform.CreativeTabPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class CreativeTabPlatformImpl {
    private record Modification(ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabPlatform.Inserter> contents) {
    }

    private static final List<Modification> MODIFICATIONS = new ArrayList<>();

    private CreativeTabPlatformImpl() {
    }

    public static void modify(ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabPlatform.Inserter> contents) {
        MODIFICATIONS.add(new Modification(tab, contents));
    }

    /** On the mod's own bus, so it runs after the mods {@code neoforge.mods.toml} orders before it. */
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        for (Modification modification : MODIFICATIONS) {
            if (event.getTabKey().equals(modification.tab())) {
                modification.contents().accept((anchor, stack) -> event.insertAfter(anchor, stack,
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
            }
        }
    }
}
