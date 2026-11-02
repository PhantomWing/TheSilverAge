package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.platform.CreativeTabPlatform;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Consumer;

public final class CreativeTabPlatformImpl {
    private CreativeTabPlatformImpl() {
    }

    public static void modify(ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabPlatform.Inserter> contents) {
        CreativeTabRegistry.modify(CreativeTabRegistry.defer(tab), (flags, output, op) -> contents.accept(output::acceptAfter));
    }
}
