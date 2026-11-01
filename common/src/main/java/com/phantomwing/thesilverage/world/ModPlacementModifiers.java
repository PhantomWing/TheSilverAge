package com.phantomwing.thesilverage.world;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ModPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.PLACEMENT_MODIFIER_TYPE);

    @SuppressWarnings("unused")
    private static <P extends PlacementModifier> RegistrySupplier<PlacementModifierType<P>> register(String name, MapCodec<P> codec) {
        return PLACEMENT_MODIFIERS.register(name, () -> typeConvert(codec));
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> typeConvert(MapCodec<P> codec) {
        return () -> codec;
    }

    public static void register() {
        PLACEMENT_MODIFIERS.register();
    }
}
