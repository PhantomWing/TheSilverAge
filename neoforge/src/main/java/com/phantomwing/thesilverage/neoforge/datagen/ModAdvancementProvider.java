package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.advancement.SilverArmorHurtTrigger;
import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModAdvancementProvider implements AdvancementSubProvider {
    static final String KILL_UNDEAD_WITH_SILVER_ARROW = "kill_undead_with_silver_arrow";
    static final String HURT_UNDEAD_WITH_SILVER_ARMOR = "hurt_undead_with_silver_armor";

    /**
     * The advancements that load only while a config option is on, so none is left that nothing can grant.
     * {@link AdvancementConditionsProvider} writes them in, since an advancement generator can't.
     */
    static final Map<Identifier, List<ICondition>> CONDITIONS = Map.of(
            Identifier.parse(getNameId(KILL_UNDEAD_WITH_SILVER_ARROW)), List.of(new ConfigBooleanCondition(Configuration.ENABLE_SILVER_ARROWS_ID)),
            Identifier.parse(getNameId(HURT_UNDEAD_WITH_SILVER_ARMOR)), List.of(
                    new ConfigBooleanCondition(Configuration.SILVER_AFFECTS_UNDEAD_ID),
                    new ConfigBooleanCondition(Configuration.ENABLE_SILVER_THORNS_ID)));

    @Override
    public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer) {
        // Root tab
        AdvancementHolder theSilverAge = Advancement.Builder.advancement()
                .display(ModItems.RAW_SILVER.get(),
                        getAdvancementTitle("root"),
                        getAdvancementDesc("root"),
                        Identifier.parse("thesilverage:block/oxidized_cut_silver"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("root"));

        // Obtain Silver
        AdvancementHolder obtainSilverIngot = obtainItemAdvancement(consumer, theSilverAge, ModItems.SILVER_INGOT.get());
        AdvancementHolder obtainMoonDial = obtainItemAdvancement(consumer, obtainSilverIngot, ModItems.MOON_DIAL.get());

        // Silver against the undead. From 1.21.2 an entity predicate takes the entity types' lookup.
        HolderGetter<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        getAdvancement(consumer, obtainSilverIngot, KILL_UNDEAD_WITH_SILVER_ARROW, ModItems.SILVER_ARROW.get(), AdvancementType.TASK,
                builder -> builder.addCriterion("killed_undead", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().of(entityTypes, ModTags.EntityTypes.SENSITIVE_TO_SILVER),
                        DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(entityTypes, ModEntityTypes.SILVER_ARROW.get())))));
        getAdvancement(consumer, obtainSilverIngot, HURT_UNDEAD_WITH_SILVER_ARMOR, ModItems.SILVER_CHESTPLATE.get(), AdvancementType.TASK,
                builder -> builder.addCriterion("hurt_undead", SilverArmorHurtTrigger.TriggerInstance.hurtUndead()));
    }

    protected static AdvancementHolder obtainItemAdvancement(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, ItemLike item) {
        String itemName = ItemUtils.getName(item);
        return getAdvancement(consumer, parent, "obtain_" + itemName, item, AdvancementType.TASK,
                builder -> builder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(item.asItem())));
    }

    protected static AdvancementHolder getAdvancement(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String name, ItemLike display, AdvancementType frame, Function<Advancement.Builder, Advancement.Builder> function) {
        Advancement.Builder builder = getAdvancement(parent, display, name, frame, true, true, false);
        return function.apply(builder).save(consumer, getNameId(name));
    }

    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                getAdvancementTitle(name),
                getAdvancementDesc(name),
                null, frame, showToast, announceToChat, hidden);
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
