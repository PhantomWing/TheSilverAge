package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
        // Root tab
        Advancement theSilverAge = Advancement.Builder.advancement()
                .display(ModItems.RAW_SILVER.get(),
                        getAdvancementTitle("root"),
                        getAdvancementDesc("root"),
                        new ResourceLocation(TheSilverAge.MOD_ID, "textures/block/oxidized_cut_silver.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("root"));

        // Obtain Silver
        Advancement obtainSilverIngot = obtainItemAdvancement(consumer, theSilverAge, ModItems.SILVER_INGOT.get());
        Advancement obtainMoonDial = obtainItemAdvancement(consumer, obtainSilverIngot, ModItems.MOON_DIAL.get());
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
