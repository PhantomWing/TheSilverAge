package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.entity.MoonPhaseDetectorBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> TILES =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    // 1.21.2 removed BlockEntityType.Builder; construct via the (now widened —
    // see thesilverage.accesswidener / accesstransformer.cfg) ctor directly.
    public static final RegistrySupplier<BlockEntityType<MoonPhaseDetectorBlockEntity>> MOON_PHASE_DETECTOR =
            TILES.register("moon_phase_detector", () -> new BlockEntityType<>(
                MoonPhaseDetectorBlockEntity::new,
                Set.of(ModBlocks.MOON_PHASE_DETECTOR.get())
            ));

    public static void register() {
        TILES.register();
    }
}
