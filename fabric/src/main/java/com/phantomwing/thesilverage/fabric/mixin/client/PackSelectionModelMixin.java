package com.phantomwing.thesilverage.fabric.mixin.client;

import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

/**
 * Hides the silver recipe-override pack from {@code Options → Resource Packs}.
 *
 * <p>The pack must stay in the {@link net.minecraft.server.packs.repository.PackRepository}
 * so it can be toggled in sync with {@code override_vanilla_recipes} (config /
 * server-sync), but it should not be a user-facing entry: it's mod-managed, and
 * a manual toggle would just desync from the config. We filter it out of the two
 * streams the selection screen renders from. Selection state is unaffected —
 * {@link PackSelectionModel#commit()} operates on the model's internal pack
 * list, not these display streams, so a hidden-but-selected pack stays selected.</p>
 */
@Mixin(PackSelectionModel.class)
public class PackSelectionModelMixin {
    @Inject(method = "getUnselected", at = @At("RETURN"), cancellable = true)
    private void thesilverage$hideUnselected(CallbackInfoReturnable<Stream<PackSelectionModel.Entry>> cir) {
        cir.setReturnValue(cir.getReturnValue().filter(e -> !thesilverage$isHidden(e.getId())));
    }

    @Inject(method = "getSelected", at = @At("RETURN"), cancellable = true)
    private void thesilverage$hideSelected(CallbackInfoReturnable<Stream<PackSelectionModel.Entry>> cir) {
        cir.setReturnValue(cir.getReturnValue().filter(e -> !thesilverage$isHidden(e.getId())));
    }

    /**
     * Matches the recipe-override pack id on both loaders: Fabric
     * {@code thesilverage/silver_recipe_overrides}, NeoForge
     * {@code builtin/thesilverage/recipe_overrides}.
     */
    @Unique
    private static boolean thesilverage$isHidden(String id) {
        return id != null && id.contains("thesilverage") && id.endsWith("recipe_overrides");
    }
}
