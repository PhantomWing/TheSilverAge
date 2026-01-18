package com.phantomwing.thesilverage.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CommonTags {
    private static final String COMMON_MOD_ID = "c";

    // Block tags
    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = tag("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = tag("storage_blocks/raw_silver");
        public static final TagKey<Block> ORES_SILVER = tag("ores_silver");

        private static TagKey<Block> tag(String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(COMMON_MOD_ID, path));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = tag("storage_blocks/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = tag("storage_blocks/raw_silver");
        public static final TagKey<Item> ORES_SILVER = tag("ores_silver");
        public static final TagKey<Item> RAW_MATERIALS_SILVER = tag("raw_materials/silver");
        public static final TagKey<Item> NUGGETS_SILVER = tag("nuggets/silver");
        public static final TagKey<Item> INGOTS_SILVER = tag("ingots/silver");

        // TOOLS
        public static final TagKey<Item> TOOLS_KNIFE = tag("tools/knife");

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(COMMON_MOD_ID, path));
        }
    }
}
