package com.phantomwing.thesilverage.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodValues {
    // Pancakes
    public static final FoodProperties PANCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.6F).build();
}