package com.phantomwing.thesilverage.firework;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.FireworkStarRecipe;

import java.util.HashMap;
import java.util.Map;

/**
 * Loader-agnostic firework-star recipe patch: lets the Silver Nugget act as a
 * {@link FireworkExplosion.Shape#STAR} shape ingredient in the vanilla
 * {@link FireworkStarRecipe}, exactly like a Gold Nugget.
 *
 * <p>Vanilla's {@code private static final Map<Item, FireworkExplosion.Shape>
 * SHAPE_BY_ITEM} maps shape items to their shape; {@code FireworkStarRecipe#matches}
 * checks {@code SHAPE_BY_ITEM.containsKey(...)} directly. (1.21.2 removed the cached
 * {@code SHAPE_INGREDIENT} field that older versions kept equal to
 * {@code SHAPE_BY_ITEM.keySet()}, so there is nothing extra to rebuild — mutating
 * the map alone is enough.) The original single-loader NeoForge build appended via
 * NeoForge's {@code CompoundIngredient}; that type is loader-specific, so here we
 * just merge into the map — pure vanilla, working on both loaders.</p>
 *
 * <p>{@code SHAPE_BY_ITEM} is exposed (accessible + mutable) via the shared
 * {@code thesilverage.accesswidener}; NeoForge production additionally ships the
 * mirrored {@code public-f} access-transformer entry (the AW is not converted
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
        // FireworkStarRecipe#matches reads SHAPE_BY_ITEM.containsKey(...) directly,
        // so adding the Silver Nugget entry above is all that's needed (1.21.2
        // removed the separate cached SHAPE_INGREDIENT field).
    }
}
