package com.phantomwing.thesilverage.world;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.key(), TheSilverAge.MOD_ID);

    private static <P extends PlacementModifier> Supplier<PlacementModifierType<P>> register(String name, MapCodec<P> codec) {
        return PLACEMENT_MODIFIERS.register(name, () -> typeConvert(codec));
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> typeConvert(MapCodec<P> codec) {
        return () -> codec;
    }

    public static void register(IEventBus eventBus) {
        PLACEMENT_MODIFIERS.register(eventBus);
    }
}
