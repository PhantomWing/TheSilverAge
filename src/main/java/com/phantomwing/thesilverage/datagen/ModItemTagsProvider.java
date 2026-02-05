package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // Tools
        tag(ItemTags.SWORDS).add(ModItems.SILVER_SWORD.get());
        tag(ItemTags.SHOVELS).add(ModItems.SILVER_SHOVEL.get());
        tag(ItemTags.PICKAXES).add(ModItems.SILVER_PICKAXE.get());
        tag(ItemTags.AXES).add(ModItems.SILVER_AXE.get());
        tag(ItemTags.HOES).add(ModItems.SILVER_HOE.get());
        tag(ModTags.Items.SILVER_TOOL_MATERIALS).add(ModItems.SILVER_INGOT.get());

        // Armor
        tag(ItemTags.HEAD_ARMOR).add(ModItems.SILVER_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.SILVER_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(ModItems.SILVER_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(ModItems.SILVER_BOOTS.get());
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                ModItems.SILVER_HELMET.get(),
                ModItems.SILVER_CHESTPLATE.get(),
                ModItems.SILVER_LEGGINGS.get(),
                ModItems.SILVER_BOOTS.get()
        );

        // Storage blocks
        tag(CommonTags.Items.STORAGE_BLOCKS_SILVER).add(ModItems.SILVER_BLOCK.get());
        tag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER).add(ModItems.RAW_SILVER_BLOCK.get());
        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER);

        // Raw materials
        tag(CommonTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get());
        tag(Tags.Items.RAW_MATERIALS).addTag(CommonTags.Items.RAW_MATERIALS_SILVER);

        // Nuggets
        tag(CommonTags.Items.NUGGETS_SILVER).add(ModItems.SILVER_NUGGET.get());
        tag(Tags.Items.NUGGETS).addTag(CommonTags.Items.NUGGETS_SILVER);

        // Ingots
        tag(CommonTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(CommonTags.Items.INGOTS_SILVER);

        // Ores
        tag(CommonTags.Items.ORES_SILVER).add(ModItems.SILVER_ORE.get(), ModItems.DEEPSLATE_SILVER_ORE.get());
        tag(Tags.Items.ORES).addTag(CommonTags.Items.ORES_SILVER);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ModItems.SILVER_ORE.get());
        tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(ModItems.DEEPSLATE_SILVER_ORE.get());

        // Silver misc
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.SILVER_INGOT.get());
        tag(ItemTags.TRIM_MATERIALS).add(ModItems.SILVER_INGOT.get());

        // Slabs
        tag(ItemTags.SLABS)
                .add(ModItems.CUT_SILVER_SLAB.get())
                .add(ModItems.EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModItems.WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModItems.OXIDIZED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get());

        tag(ItemTags.STAIRS)
                .add(ModItems.CUT_SILVER_STAIRS.get())
                .add(ModItems.EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModItems.OXIDIZED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get());

        tag(ItemTags.TRAPDOORS)
                .add(ModItems.SILVER_TRAPDOOR.get())
                .add(ModItems.EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModItems.WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModItems.OXIDIZED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());

        tag(ItemTags.DOORS)
                .add(ModItems.SILVER_DOOR.get())
                .add(ModItems.EXPOSED_SILVER_DOOR.get())
                .add(ModItems.WEATHERED_SILVER_DOOR.get())
                .add(ModItems.OXIDIZED_SILVER_DOOR.get())
                .add(ModItems.WAXED_SILVER_DOOR.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_DOOR.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_DOOR.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_DOOR.get());
    }
}
