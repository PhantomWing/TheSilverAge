package com.phantomwing.thesilverage.neoforge.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.phantomwing.thesilverage.combat.SilverFloor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/**
 * Where a mob silver harms picks to go next, a place on silver scores worst ({@link SilverFloor#scorer}): the mob's own
 * liking for each place still counts first. Without the call to wrap, as where a mod has rewritten the method, it does
 * nothing.
 * The Fabric twin is the identically-named mixin in the {@code fabric} source set.
 */
@Mixin(RandomPos.class)
public class RandomPosMixin {
    @WrapOperation(method = "generateRandomPos(Lnet/minecraft/world/entity/PathfinderMob;Ljava/util/function/Supplier;)Lnet/minecraft/world/phys/Vec3;",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/util/RandomPos;generateRandomPos(Ljava/util/function/Supplier;Ljava/util/function/ToDoubleFunction;)Lnet/minecraft/world/phys/Vec3;"),
            require = 0)
    private static Vec3 thesilverage$silverScoresWorst(Supplier<BlockPos> places, ToDoubleFunction<BlockPos> scorer,
                                                       Operation<Vec3> original, @Local(argsOnly = true) PathfinderMob mob) {
        return original.call(places, SilverFloor.scorer(mob, scorer));
    }
}
