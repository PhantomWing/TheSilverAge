package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.entity.MoonPhaseDetectorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TheSilverAge.MOD_ID);

    public static final RegistryObject<BlockEntityType<MoonPhaseDetectorBlockEntity>> MOON_PHASE_DETECTOR = TILES.register("moon_phase_detector", () -> BlockEntityType.Builder.of(MoonPhaseDetectorBlockEntity::new,
        ModBlocks.MOON_PHASE_DETECTOR.get()
    ).build(null));

    public static void register(IEventBus eventBus) {
        TILES.register(eventBus);
    }
}
