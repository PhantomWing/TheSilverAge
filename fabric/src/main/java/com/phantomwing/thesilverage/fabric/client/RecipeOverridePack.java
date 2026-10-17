package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric counterpart to the NeoForge {@code RecipeOverridePackHandler}: registers one built-in pack
 * per {@link RecipeOverride} with a texture pack ({@code resourcepacks/<texturePack>}) and keeps each
 * one's selection in step with its override - the server's when connected to one that synced them,
 * else the local config.
 *
 * <p>Fabric has no {@code AddPackFindersEvent} that re-fires on resource reload, so the packs are
 * registered once with {@code registerBuiltinResourcePack} at client init and switched by selecting
 * them in the client {@link PackRepository}. {@code DEFAULT_ENABLED} has a fresh install's packs on
 * with the default config; the sync right after registration covers a config that has some off.</p>
 */
public final class RecipeOverridePack {
    private RecipeOverridePack() {
    }

    public static void register() {
        FabricLoader.getInstance().getModContainer(TheSilverAge.MOD_ID).ifPresent(container -> {
            for (RecipeOverride override : RecipeOverride.values()) {
                if (!override.hasTexturePack()) continue;
                // The path is looked up in the mod jar as resourcepacks/<path>, so it must be the folder's name.
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, override.texturePack()),
                        container,
                        Component.literal("The Silver Age: " + override.texturePack()),
                        ResourcePackActivationType.DEFAULT_ENABLED);
            }
        });

        // Sync once after registration in case the saved config differs from DEFAULT_ENABLED.
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) mc.execute(RecipeOverridePack::refresh);

        // React to config saves at runtime.
        AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).registerSaveListener((holder, config) -> {
            Minecraft client = Minecraft.getInstance();
            if (client != null) client.execute(RecipeOverridePack::refresh);
            return InteractionResult.SUCCESS;
        });
    }

    /**
     * Select each pack whose override is on and deselect the rest, then reload once if anything
     * changed. A pack that isn't in the repository is left out, so it can't cause a reload. Called
     * on init, on config save, on server-sync receipt and on disconnect.
     */
    public static void refresh() {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        PackRepository repo = mc.getResourcePackRepository();

        List<String> selected = new ArrayList<>(repo.getSelectedIds());
        boolean changed = false;
        for (RecipeOverride override : RecipeOverride.values()) {
            if (!override.hasTexturePack()) continue;
            String packId = resolvePackId(repo, override.texturePack());
            if (packId == null) continue;

            boolean wanted = ServerOverrideState.isOn(override);
            if (wanted && !selected.contains(packId)) {
                selected.add(packId); // last = top priority, over vanilla
                changed = true;
            } else if (!wanted && selected.remove(packId)) {
                changed = true;
            }
        }
        if (!changed) return;

        repo.setSelected(selected);
        mc.reloadResourcePacks();
    }

    /**
     * A pack's actual repository id. Fabric derives it from the ResourceLocation, and the exact format
     * is an implementation detail, so it is matched by its trailing path.
     */
    @Nullable
    private static String resolvePackId(PackRepository repo, String texturePack) {
        for (String id : repo.getAvailableIds()) {
            if (id.contains(TheSilverAge.MOD_ID) && (id.endsWith("/" + texturePack) || id.endsWith(":" + texturePack))) {
                return id;
            }
        }
        return null;
    }
}
