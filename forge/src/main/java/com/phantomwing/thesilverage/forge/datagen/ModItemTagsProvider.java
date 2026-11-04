package com.phantomwing.thesilverage.forge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.BackportTags;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.FabricTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    /** Farmer's Delight's knife tag — the Cutting Board's accepted tool. Adding to it is
     *  harmless when FD is absent (the tag is simply never consulted). */
    private static final TagKey<Item> FARMERS_DELIGHT_KNIVES =
            TagKey.create(Registries.ITEM, new ResourceLocation("farmersdelight", "tools/knives"));
    private static final TagKey<Item> WEREWOLVES_SILVER_TOOLS =
            TagKey.create(Registries.ITEM, new ResourceLocation("werewolves", "tools/silver"));
    private static final TagKey<Item> WEREWOLVES_SILVER_ARMOR =
            TagKey.create(Registries.ITEM, new ResourceLocation("werewolves", "armor/silver"));

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addModTags(provider);
        addCommonTags(provider);
        addMinecraftTags(provider);
    }

    protected void addModTags(HolderLookup.@NotNull Provider provider) {
        // Glistering nuggets (used for e.g. the Glistering Melon override recipe): the gold nugget, and other mods'
        // gold through each loader's tag, optional since the other loader hasn't got it.
        tag(ModTags.Items.GLISTERING_NUGGETS)
                .add(Items.GOLD_NUGGET)
                .addOptionalTag(Tags.Items.NUGGETS_GOLD.location())
                .addOptionalTag(new ResourceLocation("c", "gold_nuggets"))
                .addTag(CommonTags.Items.NUGGETS_SILVER);

        // Redstone silver components — all silver forms accepted as the "S" ingredient in the
        // Redstone Repeater / Comparator override recipes. Silver sheet is Create-only: it
        // exists as an item either way, but is only obtainable with Create installed.
        tag(ModTags.Items.REDSTONE_SILVER_COMPONENTS)
                .addTag(CommonTags.Items.INGOTS_SILVER)
                .addTag(CommonTags.Items.PLATES_SILVER);

        // Silver tools carrying the innate anti-undead damage bonus. The Silver
        // Knife is included: it is registered on both loaders (a Farmer's Delight
        // KnifeItem when FD is present, a plain fallback otherwise).
        tag(ModTags.Items.SILVER_SMITE_TOOLS)
                .add(ModItems.SILVER_SWORD.get())
                .add(ModItems.SILVER_AXE.get())
                .add(ModItems.SILVER_PICKAXE.get())
                .add(ModItems.SILVER_SHOVEL.get())
                .add(ModItems.SILVER_HOE.get())
                .add(ModItems.SILVER_KNIFE.get())
                .add(ModItems.SILVER_SPEAR.get());

        // Silver armour burning the undead that strike its wearer: a piece's chance each, and a full set's for
        // Silver Horse Armor, all the silver a horse wears.
        tag(ModTags.Items.SILVER_THORNS_ARMOR)
                .add(ModItems.SILVER_HELMET.get())
                .add(ModItems.SILVER_CHESTPLATE.get())
                .add(ModItems.SILVER_LEGGINGS.get())
                .add(ModItems.SILVER_BOOTS.get())
                .add(ModItems.SILVER_HORSE_ARMOR.get())
                .add(ModItems.SILVER_NAUTILUS_ARMOR.get());
    }

    protected void addCommonTags(HolderLookup.@NotNull Provider provider) {
        // Storage blocks
        tag(CommonTags.Items.STORAGE_BLOCKS_SILVER).add(ModItems.SILVER_BLOCK.get()).addOptionalTag(FabricTags.Items.SILVER_BLOCKS.location());
        tag(FabricTags.Items.SILVER_BLOCKS).add(ModItems.SILVER_BLOCK.get());
        tag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER).add(ModItems.RAW_SILVER_BLOCK.get()).addOptionalTag(FabricTags.Items.RAW_SILVER_BLOCKS.location());
        tag(FabricTags.Items.RAW_SILVER_BLOCKS).add(ModItems.RAW_SILVER_BLOCK.get());
        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER);

        // Raw materials
        tag(CommonTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get()).addOptionalTag(FabricTags.Items.RAW_SILVER_ORES.location());
        tag(FabricTags.Items.RAW_SILVER_ORES).add(ModItems.RAW_SILVER.get());
        tag(Tags.Items.RAW_MATERIALS).addTag(CommonTags.Items.RAW_MATERIALS_SILVER);

        // Materials
        tag(CommonTags.Items.TOOL_MATERIALS_SILVER).addTag(CommonTags.Items.INGOTS_SILVER);

        // Nuggets
        tag(CommonTags.Items.NUGGETS_SILVER).add(ModItems.SILVER_NUGGET.get()).addOptionalTag(FabricTags.Items.SILVER_NUGGETS.location());
        tag(FabricTags.Items.SILVER_NUGGETS).add(ModItems.SILVER_NUGGET.get());
        tag(Tags.Items.NUGGETS).addTag(CommonTags.Items.NUGGETS_SILVER);

        // Ingots
        tag(CommonTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get()).addOptionalTag(FabricTags.Items.SILVER_INGOTS.location());
        tag(FabricTags.Items.SILVER_INGOTS).add(ModItems.SILVER_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(CommonTags.Items.INGOTS_SILVER);

        // Plates (Create compat — matches com.simibubi.create.foundation.data.recipe.CommonMetal.SILVER.plates)
        tag(CommonTags.Items.PLATES_SILVER).add(ModItems.SILVER_SHEET.get()).addOptionalTag(FabricTags.Items.SILVER_PLATES.location());
        tag(FabricTags.Items.SILVER_PLATES).add(ModItems.SILVER_SHEET.get());
        tag(CommonTags.Items.PLATES).addTag(CommonTags.Items.PLATES_SILVER);

        // Ores
        tag(CommonTags.Items.ORES_SILVER).add(ModItems.SILVER_ORE.get(), ModItems.DEEPSLATE_SILVER_ORE.get()).addOptionalTag(FabricTags.Items.SILVER_ORES.location());
        tag(FabricTags.Items.SILVER_ORES).add(ModItems.SILVER_ORE.get(), ModItems.DEEPSLATE_SILVER_ORE.get());
        tag(Tags.Items.ORES).addTag(CommonTags.Items.ORES_SILVER);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ModItems.SILVER_ORE.get());
        tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(ModItems.DEEPSLATE_SILVER_ORE.get());

        tag(CommonTags.Items.ARMORS_NAUTILUS).add(ModItems.SILVER_NAUTILUS_ARMOR.get());

        // Dusts (Mekanism compat; the item exists either way)
        tag(CommonTags.Items.DUSTS_SILVER).add(ModItems.SILVER_DUST.get()).addOptionalTag(FabricTags.Items.SILVER_DUSTS.location());
        tag(FabricTags.Items.SILVER_DUSTS).add(ModItems.SILVER_DUST.get());
        tag(CommonTags.Items.DUSTS).addTag(CommonTags.Items.DUSTS_SILVER);
        tag(CommonTags.Items.DIRTY_DUSTS_SILVER).add(ModItems.DIRTY_SILVER_DUST.get());
        tag(CommonTags.Items.CLUMPS_SILVER).add(ModItems.SILVER_CLUMP.get());
        tag(CommonTags.Items.SHARDS_SILVER).add(ModItems.SILVER_SHARD.get());
        tag(CommonTags.Items.CRYSTALS_SILVER).add(ModItems.SILVER_CRYSTAL.get());

        // 1.20.1 has no conventional chains tag; minecraft:chains (BackportTags) has them.
    }

    protected void addMinecraftTags(HolderLookup.@NotNull Provider provider) {
        // The item tags of 1.21.9's bars, chains, lanterns and lightning rods, which Vanilla Backport and Copper Age
        // Backport fill on this version, as the 1.21.9 lines do.
        tag(BackportTags.Items.BARS)
                .add(ModItems.SILVER_BARS.get())
                .add(ModItems.EXPOSED_SILVER_BARS.get())
                .add(ModItems.WEATHERED_SILVER_BARS.get())
                .add(ModItems.OXIDIZED_SILVER_BARS.get())
                .add(ModItems.WAXED_SILVER_BARS.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_BARS.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_BARS.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_BARS.get());
        tag(BackportTags.Items.CHAINS)
                .add(ModItems.SILVER_CHAIN.get())
                .add(ModItems.EXPOSED_SILVER_CHAIN.get())
                .add(ModItems.WEATHERED_SILVER_CHAIN.get())
                .add(ModItems.OXIDIZED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_CHAIN.get());
        tag(BackportTags.Items.LANTERNS)
                .add(ModItems.SILVER_LANTERN.get())
                .add(ModItems.EXPOSED_SILVER_LANTERN.get())
                .add(ModItems.WEATHERED_SILVER_LANTERN.get())
                .add(ModItems.OXIDIZED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_LANTERN.get());
        tag(BackportTags.Items.LIGHTNING_RODS)
                .add(ModItems.SILVER_LIGHTNING_ROD.get())
                .add(ModItems.EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.OXIDIZED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_LIGHTNING_ROD.get());
        // Both spear backports put minecraft:spears into the enchantable tags, so the Silver Spear takes Lunge and
        // the melee enchantments through it.
        tag(BackportTags.Items.SPEARS).add(ModItems.SILVER_SPEAR.get());
        // What a Sulfur Cube swallowing it turns into, as vanilla's copper blocks, ores and bulbs do (26.2, and Vanilla
        // Backport here).
        tag(BackportTags.Items.SULFUR_CUBE_SLOW_FLAT)
                .add(ModItems.SILVER_ORE.get(), ModItems.DEEPSLATE_SILVER_ORE.get(), ModItems.RAW_SILVER_BLOCK.get())
                .add(ModItems.SILVER_BLOCK.get(), ModItems.EXPOSED_SILVER.get(), ModItems.WEATHERED_SILVER.get(),
                        ModItems.OXIDIZED_SILVER.get(), ModItems.WAXED_SILVER_BLOCK.get(), ModItems.WAXED_EXPOSED_SILVER.get(),
                        ModItems.WAXED_WEATHERED_SILVER.get(), ModItems.WAXED_OXIDIZED_SILVER.get())
                .add(ModItems.CUT_SILVER.get(), ModItems.EXPOSED_CUT_SILVER.get(), ModItems.WEATHERED_CUT_SILVER.get(),
                        ModItems.OXIDIZED_CUT_SILVER.get(), ModItems.WAXED_CUT_SILVER.get(), ModItems.WAXED_EXPOSED_CUT_SILVER.get(),
                        ModItems.WAXED_WEATHERED_CUT_SILVER.get(), ModItems.WAXED_OXIDIZED_CUT_SILVER.get())
                .add(ModItems.CHISELED_SILVER.get(), ModItems.EXPOSED_CHISELED_SILVER.get(),
                        ModItems.WEATHERED_CHISELED_SILVER.get(), ModItems.OXIDIZED_CHISELED_SILVER.get(),
                        ModItems.WAXED_CHISELED_SILVER.get(), ModItems.WAXED_EXPOSED_CHISELED_SILVER.get(),
                        ModItems.WAXED_WEATHERED_CHISELED_SILVER.get(), ModItems.WAXED_OXIDIZED_CHISELED_SILVER.get())
                .add(ModItems.SILVER_BULB.get(), ModItems.EXPOSED_SILVER_BULB.get(), ModItems.WEATHERED_SILVER_BULB.get(),
                        ModItems.OXIDIZED_SILVER_BULB.get(), ModItems.WAXED_SILVER_BULB.get(), ModItems.WAXED_EXPOSED_SILVER_BULB.get(),
                        ModItems.WAXED_WEATHERED_SILVER_BULB.get(), ModItems.WAXED_OXIDIZED_SILVER_BULB.get());

        // Tools
        tag(ItemTags.SWORDS).add(ModItems.SILVER_SWORD.get());
        // What bows and crossbows fire.
        tag(ItemTags.ARROWS).add(ModItems.SILVER_ARROW.get());
        tag(ItemTags.SHOVELS).add(ModItems.SILVER_SHOVEL.get());
        tag(ItemTags.PICKAXES).add(ModItems.SILVER_PICKAXE.get());
        tag(ItemTags.AXES).add(ModItems.SILVER_AXE.get());
        tag(ItemTags.HOES).add(ModItems.SILVER_HOE.get());

        // Every pickaxe gets an amethyst cluster's full four shards.
        tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(ModItems.SILVER_PICKAXE.get());

        // Farmer's Delight compat — the Silver Knife. Added to FD's knife tag (the
        // Cutting Board's accepted tool) and the reserved c: convention tag. Both
        // are unconditional: ignored when FD isn't installed.
        tag(FARMERS_DELIGHT_KNIVES).add(ModItems.SILVER_KNIFE.get());
        tag(CommonTags.Items.TOOLS_KNIFE).add(ModItems.SILVER_KNIFE.get());
        // Farmer's Delight Refabricated reads its knives from c:tools/knives on 1.20.1.
        tag(FabricTags.Items.KNIVES).add(ModItems.SILVER_KNIFE.get());

        // Werewolves: Become a Beast compat. A weapon in its silver tools tag gives a werewolf it hits the Silver
        // effect, and a werewolf player holding or wearing anything in its silver type tag (which takes these and the
        // c: silver tags) is weakened. Ignored when Werewolves isn't installed.
        tag(WEREWOLVES_SILVER_TOOLS).addTag(ModTags.Items.SILVER_SMITE_TOOLS);
        tag(WEREWOLVES_SILVER_ARMOR)
                .add(ModItems.SILVER_HELMET.get())
                .add(ModItems.SILVER_CHESTPLATE.get())
                .add(ModItems.SILVER_LEGGINGS.get())
                .add(ModItems.SILVER_BOOTS.get());

        // Armor
        // 1.21's enchantable armour tags (head_armor and the rest) aren't on 1.20.1, where an armour item is enchanted by its slot.
        tag(ItemTags.TRIMMABLE_ARMOR).add(
                ModItems.SILVER_HELMET.get(),
                ModItems.SILVER_CHESTPLATE.get(),
                ModItems.SILVER_LEGGINGS.get(),
                ModItems.SILVER_BOOTS.get()
        );

        // Armor trims
        tag(ItemTags.TRIM_MATERIALS).add(ModItems.SILVER_INGOT.get());

        // Beacon
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.SILVER_INGOT.get());

        // Walls
        tag(ItemTags.WALLS)
                .add(ModItems.SILVER_BRICK_WALL.get())
                .add(ModItems.EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModItems.WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModItems.OXIDIZED_SILVER_BRICK_WALL.get())
                .add(ModItems.WAXED_SILVER_BRICK_WALL.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_BRICK_WALL.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_BRICK_WALL.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_BRICK_WALL.get());

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
