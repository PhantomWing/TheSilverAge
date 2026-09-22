package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Tags over our own recipes, so advancements can name a recipe by tag.
 *
 * <p>Entries are optional on purpose: the override recipes are config-gated, so whichever of the
 * main/fallback pair is conditioned away is simply absent from the tag instead of erroring.</p>
 */
public class ModRecipeTagsProvider extends TagsProvider<Recipe<?>> {
    public ModRecipeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.RECIPE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // Each pair is mutually exclusive: override_vanilla_recipes picks one.
        this.tag(ModTags.Recipes.UNLOCKS_BREWING_STAND)
                .addOptional(vanilla("brewing_stand"))
                .addOptional(vanilla("brewing_stand_fallback"));
        this.tag(ModTags.Recipes.UNLOCKS_LODESTONE)
                .addOptional(vanilla("lodestone"))
                .addOptional(vanilla("lodestone_fallback"));
        this.tag(ModTags.Recipes.UNLOCKS_COMPARATOR)
                .addOptional(vanilla("comparator"))
                .addOptional(vanilla("comparator_fallback"));
        this.tag(ModTags.Recipes.UNLOCKS_REPEATER)
                .addOptional(vanilla("repeater"))
                .addOptional(vanilla("repeater_fallback"));
        // Create-gated: absent entirely when Create is not installed.
        this.tag(ModTags.Recipes.UNLOCKS_SILVER_FROM_CRUSHED)
                .addOptional(own("silver_ingot_from_crushed_raw_silver_smelting"))
                .addOptional(own("silver_ingot_from_crushed_raw_silver_blasting"));
    }

    private static ResourceKey<Recipe<?>> vanilla(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace(path));
    }

    private static ResourceKey<Recipe<?>> own(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, path));
    }
}
