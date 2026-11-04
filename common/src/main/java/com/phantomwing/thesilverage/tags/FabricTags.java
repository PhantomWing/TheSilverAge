package com.phantomwing.thesilverage.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Fabric API's conventional tags on 1.20.1 ({@code c:silver_ingots}), which Fabric's mods share silver through. The
 * mod's silver goes in them so those mods take it, and each of {@link CommonTags}' Forge tags takes them in, so theirs
 * counts in the mod's recipes. 1.21 moved both loaders onto one set of {@code c:} tags.
 */
public class FabricTags {
    private static final String NAMESPACE = "c";

    public static class Blocks {
        public static final TagKey<Block> SILVER_BLOCKS = tag("silver_blocks");
        public static final TagKey<Block> RAW_SILVER_BLOCKS = tag("raw_silver_blocks");
        public static final TagKey<Block> SILVER_ORES = tag("silver_ores");

        private static TagKey<Block> tag(String path) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(NAMESPACE, path));
        }
    }

    public static class Items {
        public static final TagKey<Item> SILVER_BLOCKS = tag("silver_blocks");
        public static final TagKey<Item> RAW_SILVER_BLOCKS = tag("raw_silver_blocks");
        public static final TagKey<Item> SILVER_ORES = tag("silver_ores");
        public static final TagKey<Item> RAW_SILVER_ORES = tag("raw_silver_ores");
        public static final TagKey<Item> SILVER_NUGGETS = tag("silver_nuggets");
        public static final TagKey<Item> SILVER_INGOTS = tag("silver_ingots");
        public static final TagKey<Item> SILVER_PLATES = tag("silver_plates");
        public static final TagKey<Item> SILVER_DUSTS = tag("silver_dusts");
        public static final TagKey<Item> KNIVES = tag("tools/knives");

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(NAMESPACE, path));
        }
    }
}
