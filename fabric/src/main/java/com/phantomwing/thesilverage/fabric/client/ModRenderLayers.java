package com.phantomwing.thesilverage.fabric.client;

import com.phantomwing.thesilverage.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

/**
 * Fabric-side block render-layer registration.
 *
 * <p>NeoForge bakes the render layer into each generated block-model JSON via
 * its {@code "render_type"} field (see {@code ModBlockStateProvider}: doors and
 * trapdoors via {@code doorBlockWithRenderType}/{@code trapdoorBlockWithRenderType}
 * with {@code RenderType.cutout()}; grates via {@code translucentBlock} with
 * {@code RenderType.translucent()}). Fabric does not parse that field, so without
 * an explicit mapping every such block falls back to the solid layer and any
 * transparent pixel renders as opaque black.</p>
 *
 * <p>Registering through {@link BlockRenderLayerMap} here is the Fabric twin of
 * NeoForge's JSON {@code render_type}, keeping the two loaders visually identical.</p>
 */
@Environment(EnvType.CLIENT)
public final class ModRenderLayers {
    private ModRenderLayers() {
    }

    public static void register() {
        // Doors — cutout (transparent pixels around the hinge / panel edges).
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocks.SILVER_DOOR.get(),
                ModBlocks.EXPOSED_SILVER_DOOR.get(),
                ModBlocks.WEATHERED_SILVER_DOOR.get(),
                ModBlocks.OXIDIZED_SILVER_DOOR.get(),
                ModBlocks.WAXED_SILVER_DOOR.get(),
                ModBlocks.WAXED_EXPOSED_SILVER_DOOR.get(),
                ModBlocks.WAXED_WEATHERED_SILVER_DOOR.get(),
                ModBlocks.WAXED_OXIDIZED_SILVER_DOOR.get());

        // Trapdoors — cutout.
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocks.SILVER_TRAPDOOR.get(),
                ModBlocks.EXPOSED_SILVER_TRAPDOOR.get(),
                ModBlocks.WEATHERED_SILVER_TRAPDOOR.get(),
                ModBlocks.OXIDIZED_SILVER_TRAPDOOR.get(),
                ModBlocks.WAXED_SILVER_TRAPDOOR.get(),
                ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR.get(),
                ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR.get(),
                ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());

        // Grates — translucent (matches NeoForge's translucentBlock in ModBlockStateProvider).
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(),
                ModBlocks.SILVER_GRATE.get(),
                ModBlocks.EXPOSED_SILVER_GRATE.get(),
                ModBlocks.WEATHERED_SILVER_GRATE.get(),
                ModBlocks.OXIDIZED_SILVER_GRATE.get(),
                ModBlocks.WAXED_SILVER_GRATE.get(),
                ModBlocks.WAXED_EXPOSED_SILVER_GRATE.get(),
                ModBlocks.WAXED_WEATHERED_SILVER_GRATE.get(),
                ModBlocks.WAXED_OXIDIZED_SILVER_GRATE.get());
    }
}
