package com.phantomwing.thesilverage.forge.mixin;

import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Lightning finds a rod by its point of interest, which 1.20.1 gives the vanilla rod alone. Forge 1.20.1 has no event
 * to add blocks to a point of interest, and rebuilds the map of them from the registry whenever it is reloaded, so the
 * Silver Lightning Rods are answered for here instead. Fabric adds them to vanilla's map once.
 */
@Mixin(PoiTypes.class)
public abstract class PoiTypesMixin {
    @Inject(method = "forState", at = @At("HEAD"), cancellable = true)
    private static void thesilverage$silverLightningRods(BlockState state, CallbackInfoReturnable<Optional<Holder<PoiType>>> cir) {
        if (ModBlocks.isSilverLightningRod(state.getBlock())) {
            cir.setReturnValue(BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolder(PoiTypes.LIGHTNING_ROD).map(holder -> holder));
        }
    }

    @Inject(method = "hasPoi", at = @At("HEAD"), cancellable = true)
    private static void thesilverage$silverLightningRodsHavePoi(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (ModBlocks.isSilverLightningRod(state.getBlock())) {
            cir.setReturnValue(true);
        }
    }
}
