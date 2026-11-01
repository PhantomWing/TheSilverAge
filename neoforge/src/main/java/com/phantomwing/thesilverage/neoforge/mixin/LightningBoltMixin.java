package com.phantomwing.thesilverage.neoforge.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A struck Silver Lightning Rod powers, and scrapes the block it stands on, as vanilla's does. Vanilla asks
 * for {@code Blocks.LIGHTNING_ROD} itself, so any other rod took the strike and did nothing. NeoForge 21.1
 * patched both checks to {@code instanceof LightningRodBlock}; 21.3 to 21.7 don't, so both loaders have this mixin.
 *
 * <p>This line's jar runs on NeoForge 21.7 (Minecraft 1.21.7) and 21.8, and NeoForge's 1.21.8 builds patch both checks to
 * {@code instanceof LightningRodBlock} again, leaving no {@code is(Block)} call to wrap. So each wrap is {@code require = 0}:
 * it applies on 21.7 and finds nothing on 21.8, where NeoForge's own check already takes any rod. With the config's
 * {@code defaultRequire} of 1, the missing target stopped NeoForge 21.8 at startup.</p>
 */
@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {
    @WrapOperation(method = "powerLightningRod", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"), require = 0)
    private boolean thesilverage$powerAnyLightningRod(BlockState state, Block block, Operation<Boolean> original) {
        return original.call(state, block) || state.getBlock() instanceof LightningRodBlock;
    }

    @WrapOperation(method = "clearCopperOnLightningStrike", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"), require = 0)
    private static boolean thesilverage$scrapeUnderAnyLightningRod(BlockState state, Block block, Operation<Boolean> original) {
        return original.call(state, block) || state.getBlock() instanceof LightningRodBlock;
    }
}
