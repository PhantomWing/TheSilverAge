package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.advancement.SilverArmorHurtTrigger;
import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
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
import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.Optional;
import java.util.function.Function;

public class ModAdvancementProvider extends AdvancementSubProvider {
    static final String KILL_UNDEAD_WITH_SILVER_ARROW = "kill_undead_with_silver_arrow";
    static final String HURT_UNDEAD_WITH_SILVER_ARMOR = "hurt_undead_with_silver_armor";

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

        // Recipe-book unlocks for the config-gated override recipes, at vanilla's own advancement ids
        // so they replace vanilla's. Vanilla's name the recipe outright, and 26.3 binds a recipe an
        // advancement names: with override_vanilla_recipes off that recipe is gone, and the server
        // refused to start. These name a tag of optional entries instead, empty for the half that's off.
        recipeUnlock("minecraft:recipes/brewing/brewing_stand", ModTags.Recipes.UNLOCKS_BREWING_STAND, Items.BLAZE_ROD,
                vanillaRecipe("brewing_stand"), vanillaRecipe("brewing_stand_fallback"));
        recipeUnlock("minecraft:recipes/decorations/lodestone", ModTags.Recipes.UNLOCKS_LODESTONE, Items.CHISELED_STONE_BRICKS,
                vanillaRecipe("lodestone"), vanillaRecipe("lodestone_fallback"));
        recipeUnlock("minecraft:recipes/redstone/comparator", ModTags.Recipes.UNLOCKS_COMPARATOR, Items.REDSTONE_TORCH,
                vanillaRecipe("comparator"), vanillaRecipe("comparator_fallback"));
        recipeUnlock("minecraft:recipes/redstone/repeater", ModTags.Recipes.UNLOCKS_REPEATER, Items.REDSTONE_TORCH,
                vanillaRecipe("repeater"), vanillaRecipe("repeater_fallback"));
        recipeUnlock("minecraft:recipes/brewing/glistering_melon_slice", ModTags.Recipes.UNLOCKS_GLISTERING_MELON_SLICE, Items.MELON_SLICE,
                vanillaRecipe("glistering_melon_slice"), vanillaRecipe("glistering_melon_slice_fallback"));
        recipeUnlock(TheSilverAge.MOD_ID + ":recipes/silver_ingot_from_crushed_raw_silver", ModTags.Recipes.UNLOCKS_SILVER_FROM_CRUSHED,
                ModItems.RAW_SILVER.get(),
                ownRecipe("silver_ingot_from_crushed_raw_silver_smelting"),
                ownRecipe("silver_ingot_from_crushed_raw_silver_blasting"));

        // Silver against the undead, each loading only while its config option is on, so none is left that nothing
        // can grant. From 1.21.2 an entity predicate takes the entity types' lookup.
        HolderGetter<EntityType<?>> entityTypes = this.output.lookup(Registries.ENTITY_TYPE);
        getAdvancement(this.output, obtainSilverIngot, KILL_UNDEAD_WITH_SILVER_ARROW, ModItems.SILVER_ARROW.get(), AdvancementType.TASK,
                builder -> builder.addCriterion("killed_undead", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().of(entityTypes, ModTags.EntityTypes.SENSITIVE_TO_SILVER),
                        DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(entityTypes, ModEntityTypes.SILVER_ARROW.get())))),
                new ConfigBooleanCondition(Configuration.ENABLE_SILVER_ARROWS_ID));
        getAdvancement(this.output, obtainSilverIngot, HURT_UNDEAD_WITH_SILVER_ARMOR, ModItems.SILVER_CHESTPLATE.get(), AdvancementType.TASK,
                builder -> builder.addCriterion("hurt_undead", SilverArmorHurtTrigger.TriggerInstance.hurtUndead()),
                new ConfigBooleanCondition(Configuration.ENABLE_SILVER_GEAR_ID),
                new ConfigBooleanCondition(Configuration.SILVER_AFFECTS_UNDEAD_ID),
                new ConfigBooleanCondition(Configuration.ENABLE_SILVER_THORNS_ID));
    }

    protected static AdvancementHolder obtainItemAdvancement(BootstrapContext<Advancement> output, AdvancementHolder parent, ItemLike item) {
        String itemName = ItemUtils.getName(item);
        return getAdvancement(output, parent, "obtain_" + itemName, item, AdvancementType.TASK,
                builder -> builder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(item.asItem())));
    }

    protected static AdvancementHolder getAdvancement(BootstrapContext<Advancement> output, AdvancementHolder parent, String name, ItemLike display, AdvancementType frame, Function<Advancement.Builder, Advancement.Builder> function, ICondition... conditions) {
        Advancement.Builder builder = getAdvancement(parent, display, name, frame, true, true, false);
        return function.apply(builder).save(output, getNameId(name), conditions);
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
    private void recipeUnlock(String id, TagKey<Recipe<?>> tag, net.minecraft.world.level.ItemLike trigger,
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
                .save(this.output, id);
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
