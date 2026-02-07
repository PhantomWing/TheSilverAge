package com.phantomwing.thesilverage.firework;

import com.google.common.collect.Maps;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;

import java.util.HashMap;
import java.util.Map;

public class ModFireworks {
    private static final Map<Item, FireworkExplosion.Shape> SHAPE_ITEMS = Util.make(Maps.newHashMap(), (map) -> {
        map.put(ModItems.SILVER_NUGGET.get(), FireworkExplosion.Shape.STAR);
    });

    public static void register() {
        // Update firework shape ingredient to include mod items.
        FireworkStarRecipe.SHAPE_INGREDIENT = CompoundIngredient.of(FireworkStarRecipe.SHAPE_INGREDIENT, Ingredient.of(SHAPE_ITEMS.keySet().toArray(new Item[0])));

        // Update firework shape by item map to include mod items.
        Map<Item, FireworkExplosion.Shape> items = new HashMap<>(Map.copyOf(SHAPE_ITEMS));
        items.putAll(FireworkStarRecipe.SHAPE_BY_ITEM);
        FireworkStarRecipe.SHAPE_BY_ITEM = items;
    }
}
