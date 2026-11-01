package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.entity.SilverArrow;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/** The mod's entity renderers, through Architectury's registry on both loaders. Called from each client entrypoint. */
public final class ModEntityRenderers {
    private static final ResourceLocation SILVER_ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "textures/entity/projectiles/silver_arrow.png");

    private ModEntityRenderers() {
    }

    public static void register() {
        EntityRendererRegistry.register(ModEntityTypes.SILVER_ARROW, SilverArrowRenderer::new);
    }

    // A renderer draws from a render state from 1.21.2; the arrow's plain one carries all it needs.
    private static final class SilverArrowRenderer extends ArrowRenderer<SilverArrow, ArrowRenderState> {
        SilverArrowRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public @NotNull ArrowRenderState createRenderState() {
            return new ArrowRenderState();
        }

        @Override
        protected @NotNull ResourceLocation getTextureLocation(@NotNull ArrowRenderState state) {
            return SILVER_ARROW_TEXTURE;
        }
    }
}
