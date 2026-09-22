package com.phantomwing.thesilverage.world;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class ModPlacementModifiers {
    // 26.3 dropped PlacementModifierType: the registry holds the MapCodec directly.
    public static final DeferredRegister<MapCodec<? extends PlacementModifier>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.PLACEMENT_MODIFIER_TYPE);

    @SuppressWarnings("unused")
    private static <P extends PlacementModifier> RegistrySupplier<MapCodec<? extends PlacementModifier>> register(String name, MapCodec<P> codec) {
        return PLACEMENT_MODIFIERS.register(name, () -> codec);
    }

    public static void register() {
        PLACEMENT_MODIFIERS.register();
    }
}
