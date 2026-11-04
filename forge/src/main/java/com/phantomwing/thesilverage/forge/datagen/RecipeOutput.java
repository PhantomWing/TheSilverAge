package com.phantomwing.thesilverage.forge.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Where recipes are saved: 1.20.1's {@code Consumer<FinishedRecipe>}, with 1.21's {@code withConditions}, which writes
 * Forge's top-level {@code conditions} into each recipe and its unlocking advancement. {@link FabricConditionsProvider}
 * adds Fabric's beside them.
 */
@FunctionalInterface
public interface RecipeOutput extends Consumer<FinishedRecipe> {
    static RecipeOutput of(Consumer<FinishedRecipe> consumer) {
        return consumer::accept;
    }

    default RecipeOutput withConditions(ICondition... conditions) {
        return recipe -> accept(new Conditional(recipe, conditions));
    }

    record Conditional(FinishedRecipe recipe, ICondition[] conditions) implements FinishedRecipe {
        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            recipe.serializeRecipeData(json);
        }

        @Override
        public @NotNull JsonObject serializeRecipe() {
            return withConditions(recipe.serializeRecipe());
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return recipe.getId();
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return recipe.getType();
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            JsonObject advancement = recipe.serializeAdvancement();
            return advancement == null ? null : withConditions(advancement);
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return recipe.getAdvancementId();
        }

        private JsonObject withConditions(JsonObject json) {
            JsonArray array = json.has("conditions") ? json.getAsJsonArray("conditions") : new JsonArray();
            for (ICondition condition : conditions) {
                array.add(CraftingHelper.serialize(condition));
            }
            json.add("conditions", array);
            return json;
        }
    }
}
