package com.phantomwing.thesilverage.firework;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.Map;

/**
 * Loader-agnostic firework-star recipe patch: lets the Silver Nugget act as a
 * {@link FireworkExplosion.Shape#STAR} shape ingredient in the vanilla
 * {@link FireworkStarRecipe}, exactly like a Gold Nugget.
 *
 * <p>Vanilla keeps two {@code private static final} fields in sync:
 * {@code SHAPE_BY_ITEM} (item → shape) and {@code SHAPE_INGREDIENT} (the recipe's
 * accepted shape items, equal to {@code SHAPE_BY_ITEM.keySet()}). The original
 * single-loader NeoForge build appended via NeoForge's {@code CompoundIngredient};
 * that type is loader-specific, so here we instead rebuild
 * {@code SHAPE_INGREDIENT} from the merged key set — pure vanilla, an identical
 * accepted-item set, and working on both loaders.</p>
 *
 * <p>Both fields are exposed (accessible + mutable) via the shared
 * {@code thesilverage.accesswidener}; NeoForge production additionally ships the
 * mirrored {@code public-f} access-transformer entries (the AW is not converted
 * to an AT in {@code remapJar}).</p>
 *
 * <p>Call once during mod setup, after item registration: NeoForge runs it in
 * {@code FMLCommonSetupEvent} (enqueued onto the main thread); Fabric from the
 * {@code ModInitializer} after the common bootstrap.</p>
 */
public final class ModFireworks {
    private static final Map<Item, FireworkExplosion.Shape> SILVER_SHAPES = Map.of(
            ModItems.SILVER_NUGGET.get(), FireworkExplosion.Shape.STAR);

    private ModFireworks() {
    }

    public static void register() {
        // Merge the Silver shapes into vanilla's item→shape map. Start from the
        // Silver entries and putAll vanilla so vanilla wins on the off chance of
        // an overlap — identical ordering to the original NeoForge doApply.
        Map<Item, FireworkExplosion.Shape> merged = new HashMap<>(SILVER_SHAPES);
        merged.putAll(FireworkStarRecipe.SHAPE_BY_ITEM);
        FireworkStarRecipe.SHAPE_BY_ITEM = merged;

        // Rebuild the accepted-shape Ingredient from the merged key set. Vanilla
        // keeps SHAPE_INGREDIENT == SHAPE_BY_ITEM.keySet(), so this is equivalent
        // to CompoundIngredient.of(<old>, Ingredient.of(SILVER_NUGGET)) without
        // any loader-specific ingredient type.
        FireworkStarRecipe.SHAPE_INGREDIENT = Ingredient.of(
                merged.keySet().toArray(new Item[0]));
    }
}
