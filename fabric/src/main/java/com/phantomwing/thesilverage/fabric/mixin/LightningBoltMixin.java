package com.phantomwing.thesilverage.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A struck Silver Lightning Rod powers, and scrapes the block it stands on, as vanilla's does. Vanilla
 * 1.21.1 asks for {@code Blocks.LIGHTNING_ROD} itself, so any other rod took the strike and did nothing;
 * NeoForge patches both checks to {@code instanceof LightningRodBlock}, which this matches.
 */
@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {
    @WrapOperation(method = "powerLightningRod", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean thesilverage$powerAnyLightningRod(BlockState state, Block block, Operation<Boolean> original) {
        return original.call(state, block) || state.getBlock() instanceof LightningRodBlock;
    }

    @WrapOperation(method = "clearCopperOnLightningStrike", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean thesilverage$scrapeUnderAnyLightningRod(BlockState state, Block block, Operation<Boolean> original) {
        return original.call(state, block) || state.getBlock() instanceof LightningRodBlock;
    }
}
