package com.phantomwing.thesilverage.fabric.mixin.client;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Worn armour of a material named with its mod's namespace, {@code thesilverage:silver}, draws from that namespace:
 * {@code thesilverage:textures/models/armor/silver_layer_1.png}. Vanilla 1.20.1 puts the whole name into a path of its
 * own namespace, which the name's colon makes no valid id, and the game fails to draw it. Forge resolves the
 * namespace itself; Fabric doesn't.
 */
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {
    @Unique
    private static final Map<String, ResourceLocation> THESILVERAGE$LOCATIONS = new HashMap<>();

    @Inject(method = "getArmorLocation", at = @At("HEAD"), cancellable = true)
    private void thesilverage$namespacedArmorLocation(ArmorItem armorItem, boolean innerModel, @Nullable String suffix,
                                                      CallbackInfoReturnable<ResourceLocation> cir) {
        String name = armorItem.getMaterial().getName();
        int colon = name.indexOf(':');
        if (colon < 0) {
            return;
        }
        String path = "textures/models/armor/" + name.substring(colon + 1) + "_layer_" + (innerModel ? 2 : 1)
                + (suffix == null ? "" : "_" + suffix) + ".png";
        String namespace = name.substring(0, colon);
        cir.setReturnValue(THESILVERAGE$LOCATIONS.computeIfAbsent(namespace + ":" + path, key -> new ResourceLocation(namespace, path)));
    }
}
