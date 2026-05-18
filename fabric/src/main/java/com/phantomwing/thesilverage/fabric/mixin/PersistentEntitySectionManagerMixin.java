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
 * Fabric parity for the NeoForge monster-armor disk-load gating.
 *
 * <p>NeoForge subscribes {@code EntityJoinLevelEvent} and forwards the real
 * {@code event.loadedFromDisk()} flag to
 * {@link MonsterArmorHandler#tryEquipSilverArmor(Entity, net.minecraft.world.level.Level, boolean)}.
 * Architectury's {@code EntityEvent.ADD} (the Phase 1 Fabric shell) carries no
 * loaded-from-disk distinction, so disk-loaded mobs would re-roll silver armor
 * on every chunk load — a behavioural divergence from NeoForge.</p>
 *
 * <p>This mixin restores exact parity by hooking the single vanilla path that
 * carries the authoritative "loaded from storage" boolean:</p>
 *
 * <pre>{@code
 * net.minecraft.world.level.entity.PersistentEntitySectionManager
 *     #addEntity(T extends EntityAccess, boolean)   // 1.21.1 Mojmap
 *     // descriptor: (Lnet/minecraft/world/level/entity/EntityAccess;Z)Z
 * }</pre>
 *
 * <p>The boolean parameter is vanilla's "loaded from storage" flag, verified
 * against the 1.21.1 Mojmap bytecode:</p>
 * <ul>
 *   <li>{@code addNewEntity(T)} calls {@code addEntity(e, false)} — a freshly
 *       created/spawned entity.</li>
 *   <li>The world-gen chunk consumer ({@code addWorldGenChunkEntities}) calls
 *       {@code addEntity(e, false)} — world-gen entities count as new (this
 *       matches NeoForge: {@code loadedFromDisk()} is {@code false} for
 *       world-gen spawns).</li>
 *   <li>The legacy / pending chunk-deserialization paths call
 *       {@code addEntity(e, true)} — entities read back from saved chunk
 *       storage.</li>
 *   <li>Inside {@code addEntity}, when the boolean is {@code true} the
 *       {@code LevelCallback.onCreated(entity)} call is skipped — i.e. the
 *       engine itself treats {@code true} as "not a new entity, loaded from
 *       disk".</li>
 * </ul>
 *
 * <p>That is precisely the semantics of NeoForge's
 * {@code EntityJoinLevelEvent#loadedFromDisk()}, so passing this boolean through
 * unchanged makes the Fabric behaviour byte-for-byte equivalent to NeoForge.
 * {@code PersistentEntitySectionManager} is the <em>server</em> entity manager
 * (the client uses {@code TransientEntitySectionManager}), so this never fires
 * client-side; the existing {@code level.isClientSide()} guard inside
 * {@link MonsterArmorHandler} is preserved regardless. All other gating in
 * {@link MonsterArmorHandler} is untouched — only the {@code loadedFromDisk}
 * argument is now correct.</p>
 *
 * <p>This replaces the Architectury {@code EntityEvent.ADD} registration that
 * previously lived in the Fabric {@code MonsterArmorPlatformImpl}; the platform
 * impl is now a documented no-op (the mixin self-registers via
 * {@code thesilverage.mixins.json}).</p>
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
            MonsterArmorHandler.tryEquipSilverArmor(entity, entity.level(), loadedFromStorage);
        }
    }
}
