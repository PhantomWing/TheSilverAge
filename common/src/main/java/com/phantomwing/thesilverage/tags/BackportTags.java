package com.phantomwing.thesilverage.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Vanilla tags newer than 1.21.1, which Vanilla Backport and Copper Age Backport fill on this version under vanilla's
 * own ids. Unused without them, as a tag nothing reads is.
 */
public class BackportTags {
    public static class Blocks {
        public static final TagKey<Block> BARS = tag("bars");
        public static final TagKey<Block> CHAINS = tag("chains");
        public static final TagKey<Block> LANTERNS = tag("lanterns");
        public static final TagKey<Block> LIGHTNING_RODS = tag("lightning_rods");

        private static TagKey<Block> tag(String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(path));
        }
    }

    public static class Items {
        public static final TagKey<Item> BARS = tag("bars");
        public static final TagKey<Item> CHAINS = tag("chains");
        public static final TagKey<Item> LANTERNS = tag("lanterns");
        public static final TagKey<Item> LIGHTNING_RODS = tag("lightning_rods");
        /** 1.21.11's spears, which take Lunge and the melee enchantments. */
        public static final TagKey<Item> SPEARS = tag("spears");

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(path));
        }
    }
}
