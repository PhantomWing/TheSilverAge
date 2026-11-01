package com.phantomwing.thesilverage.fabric.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/** Which point of interest a block state makes. Fabric API can register a new type but not extend a vanilla one. */
@Mixin(PoiTypes.class)
public interface PoiTypesAccessor {
    @Accessor("TYPE_BY_STATE")
    static Map<BlockState, Holder<PoiType>> thesilverage$typeByState() {
        throw new AssertionError();
    }
}
