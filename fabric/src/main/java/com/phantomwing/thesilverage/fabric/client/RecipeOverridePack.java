package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.InteractionResult;

import java.util.ArrayList;

/** Registers the silver_recipe_overrides built-in pack and syncs its enabled state with the override_vanilla_recipes config. */
public final class RecipeOverridePack {
    // PACK_PATH must match the directory name in common resourcepacks/ or the registered pack is empty.
    private static final String PACK_ID = TheSilverAge.MOD_ID + "/silver_recipe_overrides";
    private static final String PACK_PATH = "silver_recipe_overrides";

    private RecipeOverridePack() {
    }

    public static void register() {
        FabricLoader.getInstance().getModContainer(TheSilverAge.MOD_ID).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, PACK_PATH),
                        container,
                        Component.literal("The Silver Age: Recipe-Override Textures"),
                        ResourcePackActivationType.DEFAULT_ENABLED));

        // Sync once after registration in case the saved config differs from DEFAULT_ENABLED.
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) mc.execute(RecipeOverridePack::refresh);

        AutoConfig.getConfigHolder(TheSilverAgeFabricConfig.class).registerSaveListener((holder, config) -> {
            Minecraft client = Minecraft.getInstance();
            if (client != null) client.execute(RecipeOverridePack::refresh);
            return InteractionResult.SUCCESS;
        });
    }

    /** Uses the server's synced override_vanilla_recipes value when connected, otherwise the local config. */
    public static void refresh() {
        applyPackState(ServerOverrideState.effective(currentConfigEnabled()));
    }

    private static boolean currentConfigEnabled() {
        try {
            return TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                    TheSilverAgeFabricConfig.OVERRIDE_VANILLA_RECIPES_ID);
        } catch (Exception ignored) {
            return true;
        }
    }

    /** Add/remove the pack from the selected list to match {@code enabled}, then reload if anything changed. */
    private static void applyPackState(boolean enabled) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        PackRepository repo = mc.getResourcePackRepository();

        // Resolve the actual repository id; Fabric's id format is an implementation detail.
        String packId = resolvePackId(repo);
        if (packId == null) return;

        boolean isSelected = repo.getSelectedIds().contains(packId);
        if (enabled == isSelected) return;

        ArrayList<String> next = new ArrayList<>(repo.getSelectedIds());
        if (enabled) {
            if (!next.contains(packId)) next.add(packId); // append = top priority, overrides vanilla
        } else {
            next.remove(packId);
        }
        repo.setSelected(next);
        mc.reloadResourcePacks();
    }

    private static String resolvePackId(PackRepository repo) {
        for (String id : repo.getAvailableIds()) {
            if (id.equals(PACK_ID) || id.endsWith("/" + PACK_PATH) || id.endsWith(":" + PACK_PATH)) {
                return id;
            }
        }
        return null;
    }
}
