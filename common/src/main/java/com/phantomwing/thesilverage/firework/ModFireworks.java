package com.phantomwing.thesilverage.firework;

/**
 * Silver Nugget firework-star parity: lets the Silver Nugget act as a
 * {@link net.minecraft.world.item.component.FireworkExplosion.Shape#STAR} shape ingredient in
 * the {@code minecraft:firework_star} recipe, exactly like a Gold Nugget.
 *
 * <p><b>26.1 change.</b> Earlier versions kept a {@code private static final Map<Item, Shape>
 * SHAPE_BY_ITEM} on {@code FireworkStarRecipe} that {@code matches()} consulted directly, so the
 * mod merged the Silver Nugget into it (widened via the access widener / access transformer).
 * 26.1 removed that static map: the shape&rarr;ingredient association is now <b>per-recipe data</b>
 * (the recipe carries a {@code Map<Shape, Ingredient> shapes} built from its JSON). There is no
 * longer any static field to mutate, so the programmatic patch is gone.</p>
 *
 * <p>The parity is instead provided by OVERRIDING the vanilla {@code minecraft:firework_star}
 * recipe to add the Silver Nugget to the STAR shape's ingredient (emitted in datagen, shipped in
 * the {@code silver_recipe_overrides} built-in pack alongside the other vanilla-recipe overrides).
 * The obsolete {@code SHAPE_BY_ITEM} access-widener / access-transformer entries were removed.</p>
 *
 * <p>{@link #register()} is retained as a no-op so the existing loader setup call sites
 * (NeoForge {@code FMLCommonSetupEvent}, Fabric {@code ModInitializer}) need no change.</p>
 */
public final class ModFireworks {
    private ModFireworks() {
    }

    /** No-op on 26.1+: firework-star parity is now data-driven (see class doc). */
    public static void register() {
    }
}
