package com.phantomwing.thesilverage.neoforge.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.ModIds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.jarcontents.JarContents;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.resource.JarContentsPackResources;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The Silver Grappling Hook's {@code items/} definition names Grappling Hooks' own condition property, which fails to
 * parse, with an error in the log, wherever that mod isn't installed; so it is in a pack of its own, provided only
 * with Grappling Hooks.
 */
@EventBusSubscriber(modid = TheSilverAge.MOD_ID, value = Dist.CLIENT)
public final class GrapplingHookPack {
    private static final String ROOT = "resourcepacks/silver_grappling_hook";

    private GrapplingHookPack() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES || !ModList.get().isLoaded(ModIds.GRAPPLING_HOOKS)) return;
        JarContents contents = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile().getContents();
        if (!contents.containsFile(ROOT + "/pack.mcmeta")) {
            TheSilverAge.LOGGER.warn("Grappling Hooks pack '{}' not found in the mod file; skipping it.", ROOT);
            return;
        }
        event.addRepositorySource(consumer -> {
            PackLocationInfo location = new PackLocationInfo("builtin/" + TheSilverAge.MOD_ID + "/silver_grappling_hook",
                    Component.literal("The Silver Age: Silver Grappling Hook"), PackSource.BUILT_IN, Optional.empty());
            PackSelectionConfig selection = new PackSelectionConfig(true, Pack.Position.TOP, true);
            Pack pack = Pack.readMetaAndCreate(location,
                    new JarContentsPackResources.JarContentsResourcesSupplier(contents, ROOT), PackType.CLIENT_RESOURCES, selection);
            if (pack != null) {
                consumer.accept(pack);
            }
        });
    }
}
