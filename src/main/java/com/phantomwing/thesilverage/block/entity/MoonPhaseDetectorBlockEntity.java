package com.phantomwing.thesilverage.block.entity;

import com.phantomwing.thesilverage.block.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MoonPhaseDetectorBlockEntity extends BlockEntity {
    public MoonPhaseDetectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.MOON_PHASE_DETECTOR.get(), pos, blockState);
    }
}
