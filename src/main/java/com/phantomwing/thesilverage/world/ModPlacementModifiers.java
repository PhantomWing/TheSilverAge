package com.phantomwing.thesilverage.world;

import com.mojang.serialization.Codec;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, TheSilverAge.MOD_ID);

    private static <P extends PlacementModifier> Supplier<PlacementModifierType<P>> register(String name, Codec<P> codec) {
        return PLACEMENT_MODIFIERS.register(name, () -> () -> codec);
    }

    public static void register(IEventBus eventBus) {
        PLACEMENT_MODIFIERS.register(eventBus);
    }
}
