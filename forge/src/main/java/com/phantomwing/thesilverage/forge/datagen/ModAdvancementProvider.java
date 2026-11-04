package com.phantomwing.thesilverage.forge.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.advancement.SilverArmorHurtTrigger;
import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.forge.Configuration;
import com.phantomwing.thesilverage.forge.condition.ConfigBooleanCondition;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    static final String KILL_UNDEAD_WITH_SILVER_ARROW = "kill_undead_with_silver_arrow";
    static final String HURT_UNDEAD_WITH_SILVER_ARMOR = "hurt_undead_with_silver_armor";

    /**
     * The advancements that load only while a config option is on, so none is left that nothing can grant.
     * {@link AdvancementConditionsProvider} writes them in, since an advancement generator can't.
     */
    static final Map<ResourceLocation, List<ICondition>> CONDITIONS = Map.of(
            new ResourceLocation(getNameId(KILL_UNDEAD_WITH_SILVER_ARROW)), List.of(new ConfigBooleanCondition(Configuration.ENABLE_SILVER_ARROWS_ID)),
            new ResourceLocation(getNameId(HURT_UNDEAD_WITH_SILVER_ARMOR)), List.of(
                    new ConfigBooleanCondition(Configuration.ENABLE_SILVER_GEAR_ID),
                    new ConfigBooleanCondition(Configuration.SILVER_AFFECTS_UNDEAD_ID),
                    new ConfigBooleanCondition(Configuration.ENABLE_SILVER_THORNS_ID)));

    @Override
    public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
        // Root tab
        Advancement theSilverAge = Advancement.Builder.advancement()
                .display(ModItems.RAW_SILVER.get(),
                        getAdvancementTitle("root"),
                        getAdvancementDesc("root"),
                        new ResourceLocation("thesilverage:textures/block/oxidized_cut_silver.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("root"));

        // Obtain Silver
        Advancement obtainSilverIngot = obtainItemAdvancement(consumer, theSilverAge, ModItems.SILVER_INGOT.get());
        Advancement obtainMoonDial = obtainItemAdvancement(consumer, obtainSilverIngot, ModItems.MOON_DIAL.get());

        // Silver against the undead
        getAdvancement(consumer, obtainSilverIngot, KILL_UNDEAD_WITH_SILVER_ARROW, ModItems.SILVER_ARROW.get(), FrameType.TASK,
                builder -> builder.addCriterion("killed_undead", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().of(ModTags.EntityTypes.SENSITIVE_TO_SILVER),
                        DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(ModEntityTypes.SILVER_ARROW.get())))));
        getAdvancement(consumer, obtainSilverIngot, HURT_UNDEAD_WITH_SILVER_ARMOR, ModItems.SILVER_CHESTPLATE.get(), FrameType.TASK,
                builder -> builder.addCriterion("hurt_undead", SilverArmorHurtTrigger.TriggerInstance.hurtUndead()));
    }

    protected static Advancement obtainItemAdvancement(Consumer<Advancement> consumer, Advancement parent, ItemLike item) {
        String itemName = ItemUtils.getName(item);
        return getAdvancement(consumer, parent, "obtain_" + itemName, item, FrameType.TASK,
                builder -> builder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(item.asItem())));
    }

    protected static Advancement getAdvancement(Consumer<Advancement> consumer, Advancement parent, String name, ItemLike display, FrameType frame, Function<Advancement.Builder, Advancement.Builder> function) {
        Advancement.Builder builder = getAdvancement(parent, display, name, frame, true, true, false);
        return function.apply(builder).save(consumer, getNameId(name));
    }

    protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
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
