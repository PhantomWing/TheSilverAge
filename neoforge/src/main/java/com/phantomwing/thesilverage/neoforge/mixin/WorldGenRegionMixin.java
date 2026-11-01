package com.phantomwing.thesilverage.neoforge.mixin;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Rolls the silver gear of a mob a structure places as world generation adds it
 * ({@link MonsterArmorHandler#rollForStructure}). The other loader's {@code WorldGenRegionMixin} is the same.
 */
@Mixin(WorldGenRegion.class)
public abstract class WorldGenRegionMixin {
    @Inject(method = "addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"))
    private void thesilverage$rollSilverGear(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        MonsterArmorHandler.rollForStructure(entity);
    }
}
