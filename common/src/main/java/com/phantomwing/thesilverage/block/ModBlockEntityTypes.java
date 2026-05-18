package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.entity.MoonPhaseDetectorBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> TILES =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<MoonPhaseDetectorBlockEntity>> MOON_PHASE_DETECTOR =
            TILES.register("moon_phase_detector", () -> BlockEntityType.Builder.of(MoonPhaseDetectorBlockEntity::new,
                ModBlocks.MOON_PHASE_DETECTOR.get()
            ).build(null));

    public static void register() {
        TILES.register();
    }
}
