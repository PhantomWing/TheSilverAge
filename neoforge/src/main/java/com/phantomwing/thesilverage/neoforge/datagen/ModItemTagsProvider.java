package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    /** Farmer's Delight knife tag; inert when FD is absent. */
    private static final TagKey<Item> FARMERS_DELIGHT_KNIVES =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("farmersdelight", "tools/knives"));


    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addModTags(provider);
        addCommonTags(provider);
        addMinecraftTags(provider);
    }

    protected void addModTags(HolderLookup.@NotNull Provider provider) {
        // Glistering nuggets (used for e.g. the Glistering Melon override recipe)
        tag(ModTags.Items.GLISTERING_NUGGETS)
                .add(Items.GOLD_NUGGET.builtInRegistryHolder().key())
                .add(ModItems.SILVER_NUGGET.get().builtInRegistryHolder().key());

        // Redstone silver components ("S" ingredient in Repeater/Comparator override recipes).
        tag(ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .add(ModItems.SILVER_INGOT.get().builtInRegistryHolder().key())
                .add(ModItems.SILVER_SHEET.get().builtInRegistryHolder().key());
    }

    protected void addCommonTags(HolderLookup.@NotNull Provider provider) {
        // Storage blocks
        tag(CommonTags.Items.STORAGE_BLOCKS_SILVER).add(ModItems.SILVER_BLOCK.get().builtInRegistryHolder().key());
        tag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER).add(ModItems.RAW_SILVER_BLOCK.get().builtInRegistryHolder().key());
        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER);

        // Raw materials
        tag(CommonTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get().builtInRegistryHolder().key());
        tag(Tags.Items.RAW_MATERIALS).addTag(CommonTags.Items.RAW_MATERIALS_SILVER);

        // Materials
        tag(CommonTags.Items.TOOL_MATERIALS_SILVER).add(ModItems.SILVER_INGOT.get().builtInRegistryHolder().key());

        // Nuggets
        tag(CommonTags.Items.NUGGETS_SILVER).add(ModItems.SILVER_NUGGET.get().builtInRegistryHolder().key());
        tag(Tags.Items.NUGGETS).addTag(CommonTags.Items.NUGGETS_SILVER);

        // Ingots
        tag(CommonTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get().builtInRegistryHolder().key());
        tag(Tags.Items.INGOTS).addTag(CommonTags.Items.INGOTS_SILVER);

        // Plates (Create compat)
        tag(CommonTags.Items.PLATES_SILVER).add(ModItems.SILVER_SHEET.get().builtInRegistryHolder().key());
        tag(CommonTags.Items.PLATES).addTag(CommonTags.Items.PLATES_SILVER);

        // Ores
        tag(CommonTags.Items.ORES_SILVER).add(ModItems.SILVER_ORE.get().builtInRegistryHolder().key(), ModItems.DEEPSLATE_SILVER_ORE.get().builtInRegistryHolder().key());
        tag(Tags.Items.ORES).addTag(CommonTags.Items.ORES_SILVER);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ModItems.SILVER_ORE.get().builtInRegistryHolder().key());
        tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(ModItems.DEEPSLATE_SILVER_ORE.get().builtInRegistryHolder().key());
    }

    protected void addMinecraftTags(HolderLookup.@NotNull Provider provider) {

        // Tools
        tag(ItemTags.SWORDS).add(ModItems.SILVER_SWORD.get().builtInRegistryHolder().key());
        tag(ItemTags.SHOVELS).add(ModItems.SILVER_SHOVEL.get().builtInRegistryHolder().key());
        tag(ItemTags.PICKAXES).add(ModItems.SILVER_PICKAXE.get().builtInRegistryHolder().key());
        tag(ItemTags.AXES).add(ModItems.SILVER_AXE.get().builtInRegistryHolder().key());
        tag(ItemTags.HOES).add(ModItems.SILVER_HOE.get().builtInRegistryHolder().key());
        tag(ItemTags.SPEARS).add(ModItems.SILVER_SPEAR.get().builtInRegistryHolder().key());

        // Silver Knife (Farmer's Delight + c: convention tag)
        tag(FARMERS_DELIGHT_KNIVES).add(ModItems.SILVER_KNIFE.get().builtInRegistryHolder().key());
        tag(CommonTags.Items.TOOLS_KNIFE).add(ModItems.SILVER_KNIFE.get().builtInRegistryHolder().key());

        // Armor
        tag(ItemTags.HEAD_ARMOR).add(ModItems.SILVER_HELMET.get().builtInRegistryHolder().key());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.SILVER_CHESTPLATE.get().builtInRegistryHolder().key());
        tag(ItemTags.LEG_ARMOR).add(ModItems.SILVER_LEGGINGS.get().builtInRegistryHolder().key());
        tag(ItemTags.FOOT_ARMOR).add(ModItems.SILVER_BOOTS.get().builtInRegistryHolder().key());
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                ModItems.SILVER_HELMET.get().builtInRegistryHolder().key(),
                ModItems.SILVER_CHESTPLATE.get().builtInRegistryHolder().key(),
                ModItems.SILVER_LEGGINGS.get().builtInRegistryHolder().key(),
                ModItems.SILVER_BOOTS.get().builtInRegistryHolder().key()
        );
        // Nautilus Armor
        tag(CommonTags.Items.ARMORS_NAUTILUS).add(ModItems.SILVER_NAUTILUS_ARMOR.get().builtInRegistryHolder().key());

        // Armor trims
        tag(ItemTags.TRIM_MATERIALS).add(ModItems.SILVER_INGOT.get().builtInRegistryHolder().key());

        // Beacon
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.SILVER_INGOT.get().builtInRegistryHolder().key());
    }
}
