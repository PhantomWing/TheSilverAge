package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import com.phantomwing.thesilverage.combat.SilverUndeadGoals;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fabric's twin of Forge's {@code EntityJoinLevelEvent} for the mod's mobs: silver gear and the undead's goals, given
 * as each entity joins the server's level.
 *
 * <p>Architectury's {@code EntityEvent.ADD} misses the entities a chunk loads with, and says nothing of whether one
 * was loaded from disk, which decides whether a mob may roll for silver gear again. 1.20.1's
 * {@code PersistentEntitySectionManager#addEntity(EntityAccess, boolean)} is the one path every entity joins by:
 * {@code addNewEntity} and the world-gen chunk entities pass {@code false}, the entities read back from a saved chunk
 * {@code true}, and with {@code true} the manager itself skips {@code onCreated} - the meaning of Forge's
 * {@code loadedFromDisk()}. It is the server's manager alone (the client's is {@code TransientEntitySectionManager}),
 * and {@link MonsterArmorHandler} still checks the side.</p>
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
        // PersistentEntitySectionManager<T> is parameterised with
        // net.minecraft.world.entity.Entity on the server level, and Entity
        // implements EntityAccess, so this cast is always safe at runtime.
        if (entityAccess instanceof Entity entity) {
            MonsterArmorHandler.tryEquipSilverGear(entity, entity.level(), loadedFromStorage);
        }
    }

    /** Every mob silver harms keeps away from silver when idle, those loaded with their chunk too, which Architectury's ADD misses. */
    @Inject(
            method = "addEntity(Lnet/minecraft/world/level/entity/EntityAccess;Z)Z",
            at = @At("HEAD")
    )
    private void thesilverage$leaveSilverFloors(EntityAccess entityAccess,
                                                boolean loadedFromStorage,
                                                CallbackInfoReturnable<Boolean> cir) {
        if (entityAccess instanceof Entity entity) {
            SilverUndeadGoals.addTo(entity);
        }
    }
}
