package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fabric parity for the NeoForge monster-armor disk-load gating. Hooks the one vanilla path
 * that carries the authoritative loaded-from-storage boolean (NeoForge's loadedFromDisk()):
 * addEntity(EntityAccess, boolean) — true means deserialized from saved chunk storage.
 */
@Mixin(PersistentEntitySectionManager.class)
public abstract class PersistentEntitySectionManagerMixin {
    @Inject(
            method = "addEntity(Lnet/minecraft/world/level/entity/EntityAccess;Z)Z",
            at = @At("HEAD")
    )
    private void thesilverage$equipSilverArmor(EntityAccess entityAccess,
                                               boolean loadedFromStorage,
                                               CallbackInfoReturnable<Boolean> cir) {
        if (entityAccess instanceof Entity entity) {
            MonsterArmorHandler.tryEquipSilverArmor(entity, entity.level(), loadedFromStorage);
        }
    }
}
