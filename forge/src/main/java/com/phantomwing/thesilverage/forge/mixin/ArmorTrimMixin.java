package com.phantomwing.thesilverage.forge.mixin;

import com.phantomwing.thesilverage.armor.TrimPalettes;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Without it a silver trim on worn silver armour draws in silver's own palette rather than its darker one. */
@Mixin(ArmorTrim.class)
public abstract class ArmorTrimMixin {
    @Inject(method = "getColorPaletteSuffix", at = @At("HEAD"), cancellable = true)
    private void thesilverage$darkerOnOwnMetal(ArmorMaterial armor, CallbackInfoReturnable<String> cir) {
        String palette = TrimPalettes.overrideFor(((ArmorTrim) (Object) this).material(), armor);
        if (palette != null) {
            cir.setReturnValue(palette);
        }
    }
}
