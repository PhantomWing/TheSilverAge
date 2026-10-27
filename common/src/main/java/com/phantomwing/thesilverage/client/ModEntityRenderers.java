package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.entity.SilverArrow;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/** The mod's entity renderers, through Architectury's registry on both loaders. Called from each client entrypoint. */
public final class ModEntityRenderers {
    // TODO: thesilverage:textures/entity/projectiles/silver_arrow.png once it is drawn.
    private static final ResourceLocation SILVER_ARROW_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");

    private ModEntityRenderers() {
    }

    public static void register() {
        EntityRendererRegistry.register(ModEntityTypes.SILVER_ARROW, SilverArrowRenderer::new);
    }

    private static final class SilverArrowRenderer extends ArrowRenderer<SilverArrow> {
        SilverArrowRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public @NotNull ResourceLocation getTextureLocation(@NotNull SilverArrow arrow) {
            return SILVER_ARROW_TEXTURE;
        }
    }
}
