package com.phantomwing.thesilverage.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * The tags other mods share silver through. On 1.20.1 those are Forge's ({@code forge:ingots/silver}), which recipes and
 * code read on both loaders, since the mod ships them; Fabric's mods share theirs under Fabric API's own names
 * ({@code c:silver_ingots}, {@link FabricTags}), which each Forge tag takes in too, so their silver counts here as well.
 */
public class CommonTags {
    private static final String COMMON_MOD_ID = "forge";

    // Block tags
    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = tag("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = tag("storage_blocks/raw_silver");
        public static final TagKey<Block> ORES_SILVER = tag("ores/silver");

        private static TagKey<Block> tag(String path) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(COMMON_MOD_ID, path));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = tag("storage_blocks/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = tag("storage_blocks/raw_silver");
        public static final TagKey<Item> ORES_SILVER = tag("ores/silver");
        public static final TagKey<Item> RAW_MATERIALS_SILVER = tag("raw_materials/silver");
        public static final TagKey<Item> NUGGETS_SILVER = tag("nuggets/silver");
        public static final TagKey<Item> INGOTS_SILVER = tag("ingots/silver");
        public static final TagKey<Item> TOOL_MATERIALS_SILVER = tag("tool_materials/silver");
        public static final TagKey<Item> PLATES = tag("plates");
        public static final TagKey<Item> PLATES_SILVER = tag("plates/silver");

        // TOOLS
        // Plural on 1.20.1: forge:tools/knives is the tag Farmer's Delight's cutting recipes accept.
        public static final TagKey<Item> TOOLS_KNIFE = tag("tools/knives");
        public static final TagKey<Item> DUSTS = tag("dusts");
        public static final TagKey<Item> DUSTS_SILVER = tag("dusts/silver");
        // Mekanism's ore processing, under the tags it gives its own metals: its own namespace's on 1.20.1.
        public static final TagKey<Item> DIRTY_DUSTS_SILVER = mekanism("dirty_dusts/silver");
        public static final TagKey<Item> CLUMPS_SILVER = mekanism("clumps/silver");
        public static final TagKey<Item> SHARDS_SILVER = mekanism("shards/silver");
        public static final TagKey<Item> CRYSTALS_SILVER = mekanism("crystals/silver");
        // 1.21.11's nautilus armour, under the c: tag the newer versions give it.
        public static final TagKey<Item> ARMORS_NAUTILUS = TagKey.create(Registries.ITEM, new ResourceLocation("c", "armors/nautilus"));

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(COMMON_MOD_ID, path));
        }

        private static TagKey<Item> mekanism(String path) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("mekanism", path));
        }
    }
}
