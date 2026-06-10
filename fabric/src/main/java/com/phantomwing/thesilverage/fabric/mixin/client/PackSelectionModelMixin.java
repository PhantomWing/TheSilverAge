package com.phantomwing.thesilverage.fabric.mixin.client;

import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

/** Hides the mod-managed silver recipe-override pack from the Resource Packs screen (it stays toggleable via config). */
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

    @Unique
    private static boolean thesilverage$isHidden(String id) {
        return id != null && id.contains("thesilverage") && id.endsWith("recipe_overrides");
    }
}
