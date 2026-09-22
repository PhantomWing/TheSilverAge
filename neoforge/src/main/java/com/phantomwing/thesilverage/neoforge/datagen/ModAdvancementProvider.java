package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;
import java.util.function.Function;

public class ModAdvancementProvider extends AdvancementSubProvider {
    public ModAdvancementProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        // Root tab. 26.3 dropped the background argument from display(); it lives on
        // DisplayInfo as a ClientAsset now.
        AdvancementHolder theSilverAge = Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStackTemplate(ModItems.RAW_SILVER.get()),
                        getAdvancementTitle("root"),
                        getAdvancementDesc("root"),
                        Optional.of(new ClientAsset.ResourceTexture(
                                Identifier.parse("thesilverage:block/oxidized_cut_silver"))),
                        AdvancementType.TASK, false, false, false))
                .addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(this.output, getNameId("root"));

        // Obtain Silver
        AdvancementHolder obtainSilverIngot = obtainItemAdvancement(this.output, theSilverAge, ModItems.SILVER_INGOT.get());
        obtainItemAdvancement(this.output, obtainSilverIngot, ModItems.MOON_DIAL.get());

        // Recipe-book unlocks for the config-gated override recipes. 26.3 drops the advancement
        // that RecipeBuilder would normally emit for a conditioned recipe, so these are built here
        // and name a recipe TAG, which resolves when the datapack loads rather than at datagen.
        recipeUnlock("brewing_stand", ModTags.Recipes.UNLOCKS_BREWING_STAND, Items.BLAZE_ROD,
                vanillaRecipe("brewing_stand"), vanillaRecipe("brewing_stand_fallback"));
        recipeUnlock("lodestone", ModTags.Recipes.UNLOCKS_LODESTONE, Items.CHISELED_STONE_BRICKS,
                vanillaRecipe("lodestone"), vanillaRecipe("lodestone_fallback"));
        recipeUnlock("comparator", ModTags.Recipes.UNLOCKS_COMPARATOR, Items.REDSTONE_TORCH,
                vanillaRecipe("comparator"), vanillaRecipe("comparator_fallback"));
        recipeUnlock("repeater", ModTags.Recipes.UNLOCKS_REPEATER, Items.REDSTONE_TORCH,
                vanillaRecipe("repeater"), vanillaRecipe("repeater_fallback"));
        recipeUnlock("silver_ingot_from_crushed_raw_silver", ModTags.Recipes.UNLOCKS_SILVER_FROM_CRUSHED,
                ModItems.RAW_SILVER.get(),
                ownRecipe("silver_ingot_from_crushed_raw_silver_smelting"),
                ownRecipe("silver_ingot_from_crushed_raw_silver_blasting"));
    }

    protected static AdvancementHolder obtainItemAdvancement(BootstrapContext<Advancement> output, AdvancementHolder parent, ItemLike item) {
        String itemName = ItemUtils.getName(item);
        return getAdvancement(output, parent, "obtain_" + itemName, item, AdvancementType.TASK,
                builder -> builder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(item.asItem())));
    }

    protected static AdvancementHolder getAdvancement(BootstrapContext<Advancement> output, AdvancementHolder parent, String name, ItemLike display, AdvancementType frame, Function<Advancement.Builder, Advancement.Builder> function) {
        Advancement.Builder builder = getAdvancement(parent, display, name, frame, true, true, false);
        return function.apply(builder).save(output, getNameId(name));
    }

    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display.asItem(),
                getAdvancementTitle(name),
                getAdvancementDesc(name),
                frame, showToast, announceToChat, hidden);
    }

    /**
     * A recipe-book unlock advancement whose has_the_recipe criterion names a tag, so it survives
     * the recipe being conditioned away (the tag is simply empty then).
     */
    @SafeVarargs
    private void recipeUnlock(String name, TagKey<Recipe<?>> tag, net.minecraft.world.level.ItemLike trigger,
                              ResourceKey<Recipe<?>>... rewarded) {
        String hasItem = "has_" + ItemUtils.getName(trigger);
        AdvancementRewards.Builder rewards = new AdvancementRewards.Builder();
        for (ResourceKey<Recipe<?>> recipe : rewarded) {
            rewards.addRecipe(recipe);
        }

        Advancement.Builder.advancement()
                .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                .addCriterion(hasItem, InventoryChangeTrigger.TriggerInstance.hasItems(trigger.asItem()))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(
                        this.output.lookup(Registries.RECIPE).getOrThrow(tag)))
                .requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", hasItem)))
                .rewards(rewards)
                .save(this.output, TheSilverAge.MOD_ID + ":recipes/" + name);
    }

    private static ResourceKey<Recipe<?>> vanillaRecipe(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace(path));
    }

    private static ResourceKey<Recipe<?>> ownRecipe(String path) {
        return ResourceKey.create(Registries.RECIPE, TheSilverAge.resourceLocation(path));
    }

    public static MutableComponent getAdvancementTitle(String key) {
        return Component.translatable(TheSilverAge.MOD_ID + ".advancement." + key);
    }

    public static MutableComponent getAdvancementDesc(String key) {
        return Component.translatable(TheSilverAge.MOD_ID + ".advancement." + key + ".description");
    }

    private static String getNameId(String id) {
        return TheSilverAge.MOD_ID + ":main/" + id;
    }
}
