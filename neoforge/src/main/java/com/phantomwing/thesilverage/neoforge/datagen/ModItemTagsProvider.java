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

// 1.21.6: vanilla net.minecraft.data.tags.ItemTagsProvider was removed. NeoForge's
// net.neoforged.neoforge.common.data.ItemTagsProvider replaces it, and its ctor dropped
// the block-tag TagLookup param (block->item tag copying is now separate; this provider
// only adds item tags directly, so it isn't needed).
public class ModItemTagsProvider extends ItemTagsProvider {
    /** Farmer's Delight's knife tag — the Cutting Board's accepted tool. Adding to it is
     *  harmless when FD is absent (the tag is simply never consulted). */
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
                .add(Items.GOLD_NUGGET)
                .add(ModItems.SILVER_NUGGET.get());

        // Redstone silver components — all silver forms accepted as the "S" ingredient in the
        // Redstone Repeater / Comparator override recipes. Silver sheet is Create-only: it
        // exists as an item either way, but is only obtainable with Create installed.
        tag(ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .add(ModItems.SILVER_INGOT.get())
                .add(ModItems.SILVER_SHEET.get());
    }

    protected void addCommonTags(HolderLookup.@NotNull Provider provider) {
        // Storage blocks
        tag(CommonTags.Items.STORAGE_BLOCKS_SILVER).add(ModItems.SILVER_BLOCK.get());
        tag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER).add(ModItems.RAW_SILVER_BLOCK.get());
        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER);

        // Raw materials
        tag(CommonTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get());
        tag(Tags.Items.RAW_MATERIALS).addTag(CommonTags.Items.RAW_MATERIALS_SILVER);

        // Materials
        tag(CommonTags.Items.TOOL_MATERIALS_SILVER).add(ModItems.SILVER_INGOT.get());

        // Nuggets
        tag(CommonTags.Items.NUGGETS_SILVER).add(ModItems.SILVER_NUGGET.get());
        tag(Tags.Items.NUGGETS).addTag(CommonTags.Items.NUGGETS_SILVER);

        // Ingots
        tag(CommonTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(CommonTags.Items.INGOTS_SILVER);

        // Plates (Create compat — matches com.simibubi.create.foundation.data.recipe.CommonMetal.SILVER.plates)
        tag(CommonTags.Items.PLATES_SILVER).add(ModItems.SILVER_SHEET.get());
        tag(CommonTags.Items.PLATES).addTag(CommonTags.Items.PLATES_SILVER);

        // Ores
        tag(CommonTags.Items.ORES_SILVER).add(ModItems.SILVER_ORE.get(), ModItems.DEEPSLATE_SILVER_ORE.get());
        tag(Tags.Items.ORES).addTag(CommonTags.Items.ORES_SILVER);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ModItems.SILVER_ORE.get());
        tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(ModItems.DEEPSLATE_SILVER_ORE.get());
    }

    protected void addMinecraftTags(HolderLookup.@NotNull Provider provider) {

        // Tools
        tag(ItemTags.SWORDS).add(ModItems.SILVER_SWORD.get());
        tag(ItemTags.SHOVELS).add(ModItems.SILVER_SHOVEL.get());
        tag(ItemTags.PICKAXES).add(ModItems.SILVER_PICKAXE.get());
        tag(ItemTags.AXES).add(ModItems.SILVER_AXE.get());
        tag(ItemTags.HOES).add(ModItems.SILVER_HOE.get());
        tag(ItemTags.SPEARS).add(ModItems.SILVER_SPEAR.get());

        // Farmer's Delight compat — the Silver Knife. Added to FD's knife tag (the
        // Cutting Board's accepted tool) and the reserved c: convention tag. Both
        // are unconditional: ignored when FD isn't installed.
        tag(FARMERS_DELIGHT_KNIVES).add(ModItems.SILVER_KNIFE.get());
        tag(CommonTags.Items.TOOLS_KNIFE).add(ModItems.SILVER_KNIFE.get());

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
        // 1.21.11 Nautilus Armor (BODY-slot animal armor) — the c: convention tag.
        tag(CommonTags.Items.ARMORS_NAUTILUS).add(ModItems.SILVER_NAUTILUS_ARMOR.get());

        // Armor trims
        tag(ItemTags.TRIM_MATERIALS).add(ModItems.SILVER_INGOT.get());

        // Beacon
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.SILVER_INGOT.get());

        // Slabs
        tag(ItemTags.SLABS)
                .add(ModItems.SILVER_BRICK_SLAB.get())
                .add(ModItems.EXPOSED_SILVER_BRICK_SLAB.get())
                .add(ModItems.WEATHERED_SILVER_BRICK_SLAB.get())
                .add(ModItems.OXIDIZED_SILVER_BRICK_SLAB.get())
                .add(ModItems.WAXED_SILVER_BRICK_SLAB.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB.get())
                .add(ModItems.CUT_SILVER_SLAB.get())
                .add(ModItems.EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModItems.WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModItems.OXIDIZED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_EXPOSED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_WEATHERED_CUT_SILVER_SLAB.get())
                .add(ModItems.WAXED_OXIDIZED_CUT_SILVER_SLAB.get());

        // Stairs
        tag(ItemTags.STAIRS)
                .add(ModItems.SILVER_BRICK_STAIRS.get())
                .add(ModItems.EXPOSED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.WEATHERED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.OXIDIZED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.WAXED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS.get())
                .add(ModItems.CUT_SILVER_STAIRS.get())
                .add(ModItems.EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModItems.OXIDIZED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_EXPOSED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_WEATHERED_CUT_SILVER_STAIRS.get())
                .add(ModItems.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get());

        // Trapdoors
        tag(ItemTags.TRAPDOORS)
                .add(ModItems.SILVER_TRAPDOOR.get())
                .add(ModItems.EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModItems.WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModItems.OXIDIZED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());

        // Doors
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
