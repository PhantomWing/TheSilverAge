package com.phantomwing.thesilverage.neoforge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.phantomwing.thesilverage.combat.SilverFloor;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A walking mob's step onto silver costs it as much as one onto magma where silver harms it ({@link SilverFloor#costed}),
 * on top of whatever the step cost already; the drowned's amphibious evaluator is the walker's too. Without the
 * method, as where a mod has rewritten it, it does nothing.
 * The Fabric twin is the identically-named mixin in the {@code fabric} source set.
 */
@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvaluatorMixin extends NodeEvaluator {
    @ModifyReturnValue(method = "findAcceptedNode(IIIIDLnet/minecraft/core/Direction;Lnet/minecraft/world/level/pathfinder/PathType;)Lnet/minecraft/world/level/pathfinder/Node;",
            at = @At("RETURN"), require = 0)
    private Node thesilverage$silverCostsMore(Node node) {
        return SilverFloor.costed(node, this.mob, this.currentContext);
    }
}
