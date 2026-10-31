package com.phantomwing.thesilverage.neoforge.mixin;

import com.phantomwing.thesilverage.combat.SilverFloor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * No mob silver harms spawns on silver ({@link SilverFloor#allowsSpawn}): the check magma keeps the mobs fire harms off
 * it with, which natural spawning and trial spawners ask. A monster spawner spawns on anything, as it does on magma.
 * The Fabric twin is the identically-named mixin in the {@code fabric} source set.
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    @Inject(method = "isValidSpawn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/EntityType;)Z",
            at = @At("RETURN"), cancellable = true)
    private void thesilverage$noSpawningOnSilver(BlockGetter level, BlockPos pos, EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && !SilverFloor.allowsSpawn((BlockState) (Object) this, type)) {
            cir.setReturnValue(false);
        }
    }
}
