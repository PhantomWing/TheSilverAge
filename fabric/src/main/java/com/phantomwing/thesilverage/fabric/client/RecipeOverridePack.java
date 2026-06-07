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

/**
 * Fabric counterpart to the NeoForge {@code RecipeOverridePackHandler}:
 * registers the {@code resourcepacks/silver_recipe_overrides} built-in pack
 * and keeps its enabled state in sync with the
 * {@code override_vanilla_recipes} config value.
 *
 * <p>Fabric has no {@code AddPackFindersEvent} that re-fires on resource
 * reload, so we use the static {@code registerBuiltinResourcePack} call at
 * client init (the pack always exists) and manage its enabled state via the
 * client {@link PackRepository}. AutoConfig's save listener triggers an
 * enable/disable + {@code reloadResourcePacks()} when the config value
 * changes — same end-user experience as NeoForge (brief reload flash, no
 * restart).</p>
 *
 * <p>Initial activation: {@code DEFAULT_ENABLED} so a fresh install with
 * the default-{@code true} config has the pack on. The post-init startup tick
 * also forces the pack's state to match the current config, covering the
 * "user has saved config={@code false}" case where they shouldn't see the
 * silver textures on first launch.</p>
 */
public final class RecipeOverridePack {
    /**
     * Fabric pack repository ids are {@code <namespace>/<path>}, and
     * {@link ResourceManagerHelper#registerBuiltinResourcePack} looks the pack
     * up inside the mod jar at {@code resourcepacks/<id-path>/}. The path here
     * must therefore match the actual directory name in
     * {@code common/src/main/resources/resourcepacks/} or the registered pack
     * is empty and no overrides apply.
     */
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

        // Sync once after registration in case the saved config differs from
        // the DEFAULT_ENABLED default (e.g. user previously saved config=false).
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
     * Re-evaluate the desired pack state and apply it. The effective value is the
     * server's {@code override_vanilla_recipes} when connected to a server that
     * synced it ({@link ServerOverrideState}), otherwise the local config value.
     * Called on init, on config save, on server-sync receipt, and on disconnect.
     */
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

        // Resolve the pack's ACTUAL repository id rather than trusting the
        // hardcoded PACK_ID: Fabric derives the id from the Identifier but
        // the exact format is an implementation detail. The previous hardcoded
        // comparison never matched, so isSelected was always false and the
        // toggle silently no-op'd (config-off / server-off left the textures on).
        String packId = resolvePackId(repo);
        if (packId == null) return; // pack not registered yet — nothing to toggle

        boolean isSelected = repo.getSelectedIds().contains(packId);
        if (enabled == isSelected) return; // already in the desired state — no reload

        ArrayList<String> next = new ArrayList<>(repo.getSelectedIds());
        if (enabled) {
            if (!next.contains(packId)) next.add(packId); // append = top priority, overrides vanilla
        } else {
            next.remove(packId);
        }
        repo.setSelected(next);
        mc.reloadResourcePacks();
    }

    /**
     * Find the recipe-override pack's id among the repository's available packs.
     * Matches the hardcoded id first, then falls back to the unique trailing
     * path segment so we are robust to Fabric's id-formatting details.
     */
    private static String resolvePackId(PackRepository repo) {
        for (String id : repo.getAvailableIds()) {
            if (id.equals(PACK_ID) || id.endsWith("/" + PACK_PATH) || id.endsWith(":" + PACK_PATH)) {
                return id;
            }
        }
        return null;
    }
}
