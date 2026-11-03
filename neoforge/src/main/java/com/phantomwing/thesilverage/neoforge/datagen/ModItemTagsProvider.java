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
    /** 26.2: TagAppender#add takes a ResourceKey, so the chains below go through a wrapper that still takes items. */
    @Override
    protected ValueAppender<Item> tag(TagKey<Item> tag) {
        return new ValueAppender<>(super.tag(tag), value -> value.builtInRegistryHolder().key());
    }

    // 26.2 dropped the ItemTags constants of the tags that mirror a block family; vanilla's tags are still there.
    private static final TagKey<Item> VANILLA_BARS = vanillaItemTag("bars");
    private static final TagKey<Item> VANILLA_CHAINS = vanillaItemTag("chains");
    private static final TagKey<Item> VANILLA_LANTERNS = vanillaItemTag("lanterns");
    private static final TagKey<Item> VANILLA_LIGHTNING_RODS = vanillaItemTag("lightning_rods");
    private static final TagKey<Item> VANILLA_SLABS = vanillaItemTag("slabs");
    private static final TagKey<Item> VANILLA_STAIRS = vanillaItemTag("stairs");
    private static final TagKey<Item> VANILLA_TRAPDOORS = vanillaItemTag("trapdoors");
    private static final TagKey<Item> VANILLA_DOORS = vanillaItemTag("doors");

    private static TagKey<Item> vanillaItemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace(path));
    }

    /** Farmer's Delight knife tag; inert when FD is absent. */
    private static final TagKey<Item> FARMERS_DELIGHT_KNIVES =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("farmersdelight", "tools/knives"));
    private static final TagKey<Item> WEREWOLVES_SILVER_TOOLS =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("werewolves", "tools/silver"));
    private static final TagKey<Item> WEREWOLVES_SILVER_ARMOR =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("werewolves", "armor/silver"));

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
        // Glistering nuggets (used for e.g. the Glistering Melon override recipe). c:nuggets/gold rather than
        // the gold nugget alone, as NeoForge's own copy of that recipe takes it, so other mods' gold works too.
        tag(ModTags.Items.GLISTERING_NUGGETS)
                .addTag(Tags.Items.NUGGETS_GOLD)
                .addTag(CommonTags.Items.NUGGETS_SILVER);

        // Redstone silver components ("S" ingredient in Repeater/Comparator override recipes).
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
        tag(CommonTags.Items.STORAGE_BLOCKS_SILVER).add(ModItems.SILVER_BLOCK.get());
        tag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER).add(ModItems.RAW_SILVER_BLOCK.get());
        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_SILVER)
                .addTag(CommonTags.Items.STORAGE_BLOCKS_RAW_SILVER);

        // Raw materials
        tag(CommonTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get());
        tag(Tags.Items.RAW_MATERIALS).addTag(CommonTags.Items.RAW_MATERIALS_SILVER);

        // Materials
        tag(CommonTags.Items.TOOL_MATERIALS_SILVER).addTag(CommonTags.Items.INGOTS_SILVER);

        // Nuggets
        tag(CommonTags.Items.NUGGETS_SILVER).add(ModItems.SILVER_NUGGET.get());
        tag(Tags.Items.NUGGETS).addTag(CommonTags.Items.NUGGETS_SILVER);
        // 26.1: vanilla crafts the Name Tag from paper and #minecraft:metal_nuggets.
        tag(ItemTags.METAL_NUGGETS).add(ModItems.SILVER_NUGGET.get());

        // Ingots
        tag(CommonTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(CommonTags.Items.INGOTS_SILVER);

        // Plates (Create compat)
        tag(CommonTags.Items.PLATES_SILVER).add(ModItems.SILVER_SHEET.get());
        tag(CommonTags.Items.PLATES).addTag(CommonTags.Items.PLATES_SILVER);

        // Ores
        tag(CommonTags.Items.ORES_SILVER).add(ModItems.SILVER_ORE.get(), ModItems.DEEPSLATE_SILVER_ORE.get());
        tag(Tags.Items.ORES).addTag(CommonTags.Items.ORES_SILVER);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ModItems.SILVER_ORE.get());
        tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(ModItems.DEEPSLATE_SILVER_ORE.get());

        // Chains
        tag(Tags.Items.CHAINS)
                .add(ModItems.SILVER_CHAIN.get())
                .add(ModItems.EXPOSED_SILVER_CHAIN.get())
                .add(ModItems.WEATHERED_SILVER_CHAIN.get())
                .add(ModItems.OXIDIZED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_CHAIN.get());
    }

    protected void addMinecraftTags(HolderLookup.@NotNull Provider provider) {
        // The item tags of 1.21.9's bars, chains, lanterns and lightning rods, as vanilla's copper ones are in.
        tag(VANILLA_BARS)
                .add(ModItems.SILVER_BARS.get())
                .add(ModItems.EXPOSED_SILVER_BARS.get())
                .add(ModItems.WEATHERED_SILVER_BARS.get())
                .add(ModItems.OXIDIZED_SILVER_BARS.get())
                .add(ModItems.WAXED_SILVER_BARS.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_BARS.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_BARS.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_BARS.get());
        tag(VANILLA_CHAINS)
                .add(ModItems.SILVER_CHAIN.get())
                .add(ModItems.EXPOSED_SILVER_CHAIN.get())
                .add(ModItems.WEATHERED_SILVER_CHAIN.get())
                .add(ModItems.OXIDIZED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_CHAIN.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_CHAIN.get());
        tag(VANILLA_LANTERNS)
                .add(ModItems.SILVER_LANTERN.get())
                .add(ModItems.EXPOSED_SILVER_LANTERN.get())
                .add(ModItems.WEATHERED_SILVER_LANTERN.get())
                .add(ModItems.OXIDIZED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_LANTERN.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_LANTERN.get());
        tag(VANILLA_LIGHTNING_RODS)
                .add(ModItems.SILVER_LIGHTNING_ROD.get())
                .add(ModItems.EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.OXIDIZED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_LIGHTNING_ROD.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_LIGHTNING_ROD.get());


        // Tools
        tag(ItemTags.SWORDS).add(ModItems.SILVER_SWORD.get());
        // What bows and crossbows fire.
        tag(ItemTags.ARROWS).add(ModItems.SILVER_ARROW.get());
        tag(ItemTags.SHOVELS).add(ModItems.SILVER_SHOVEL.get());
        tag(ItemTags.PICKAXES).add(ModItems.SILVER_PICKAXE.get());
        tag(ItemTags.AXES).add(ModItems.SILVER_AXE.get());
        tag(ItemTags.HOES).add(ModItems.SILVER_HOE.get());
        tag(ItemTags.SPEARS).add(ModItems.SILVER_SPEAR.get());
        // What a Sulfur Cube swallowing it turns into, as vanilla's copper blocks, ores and bulbs do.
        tag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_FLAT)
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

        // Every pickaxe gets an amethyst cluster's full four shards.
        tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(ModItems.SILVER_PICKAXE.get());

        // Farmer's Delight compat — the Silver Knife. Added to FD's knife tag (the
        // Cutting Board's accepted tool) and the reserved c: convention tag. Both
        // are unconditional: ignored when FD isn't installed.
        tag(FARMERS_DELIGHT_KNIVES).add(ModItems.SILVER_KNIFE.get());
        tag(CommonTags.Items.TOOLS_KNIFE).add(ModItems.SILVER_KNIFE.get());

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
        // Nautilus Armor
        // Horse Armor - the c: convention tag, which every loader has from 1.21.11.
        tag(CommonTags.Items.ARMORS_HORSE).add(ModItems.SILVER_HORSE_ARMOR.get());
        tag(CommonTags.Items.ARMORS_NAUTILUS).add(ModItems.SILVER_NAUTILUS_ARMOR.get());

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
        tag(VANILLA_SLABS)
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
        tag(VANILLA_STAIRS)
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
        tag(VANILLA_TRAPDOORS)
                .add(ModItems.SILVER_TRAPDOOR.get())
                .add(ModItems.EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModItems.WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModItems.OXIDIZED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_EXPOSED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_WEATHERED_SILVER_TRAPDOOR.get())
                .add(ModItems.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());

        // Doors
        tag(VANILLA_DOORS)
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
