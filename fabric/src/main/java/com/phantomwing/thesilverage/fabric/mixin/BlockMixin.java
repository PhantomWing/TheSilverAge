package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.combat.SilverFloor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mob stepping on silver ({@link SilverFloor#stepOn}), where magma hurts one too. No block silver is made of
 * overrides {@code stepOn}, so this one call reaches every one, waxed or not.
 * The NeoForge twin is the identically-named mixin in the {@code neoforge} source set.
 */
@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V",
            at = @At("HEAD"))
    private void thesilverage$silverFloorHurts(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        SilverFloor.stepOn(level, state, entity);
    }
}
