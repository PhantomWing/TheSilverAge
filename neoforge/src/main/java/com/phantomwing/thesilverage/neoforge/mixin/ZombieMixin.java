package com.phantomwing.thesilverage.neoforge.mixin;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Rolls a villager-made zombie villager's silver gear once it has its gear ({@link MonsterArmorHandler#rollAfterConversion}):
 * a conversion's {@code finalizeSpawn} comes after the mob has joined the level. The other loader's {@code ZombieMixin} is
 * the same.
 */
@Mixin(Zombie.class)
public abstract class ZombieMixin {
    @Inject(method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;",
            at = @At("RETURN"))
    private void thesilverage$rollSilverGear(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType type,
                                             SpawnGroupData data, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (type == MobSpawnType.CONVERSION) {
            MonsterArmorHandler.rollAfterConversion((Zombie) (Object) this);
        }
    }
}
