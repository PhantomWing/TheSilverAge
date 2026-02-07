package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.entity.MoonPhaseDetectorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TheSilverAge.MOD_ID);

    public static final Supplier<BlockEntityType<MoonPhaseDetectorBlockEntity>> MOON_PHASE_DETECTOR = TILES.register("moon_phase_detector", () -> BlockEntityType.Builder.of(MoonPhaseDetectorBlockEntity::new,
        ModBlocks.MOON_PHASE_DETECTOR.get()
    ).build(null));

    public static void register(IEventBus eventBus) {
        TILES.register(eventBus);
    }
}