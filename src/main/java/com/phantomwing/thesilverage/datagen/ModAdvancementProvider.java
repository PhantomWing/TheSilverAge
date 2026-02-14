package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModAdvancementProvider implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper existingFileHelper) {
        // Root tab
        AdvancementHolder theSilverAge = Advancement.Builder.advancement()
                .display(ModItems.RAW_SILVER.get(),
                        getAdvancementTitle("root"),
                        getAdvancementDesc("root"),
                        ResourceLocation.parse("thesilverage:textures/block/oxidized_cut_silver.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("root"));

        // Obtain Silver
        AdvancementHolder obtainSilverIngot = obtainItemAdvancement(consumer, theSilverAge, ModItems.SILVER_INGOT);
        AdvancementHolder obtainMoonDial = obtainItemAdvancement(consumer, obtainSilverIngot, ModItems.MOON_DIAL);
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
