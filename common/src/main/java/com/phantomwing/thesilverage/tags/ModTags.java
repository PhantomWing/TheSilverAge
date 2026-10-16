package com.phantomwing.thesilverage.tags;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Entity type tags
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> CAN_WEAR_SILVER_ARMOR = tag("can_wear_silver_armor");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Block tags
    public static class Biomes {
        public static final TagKey<Biome> HAS_SILVER_ORE = tag("has_silver_ore");
        public static final TagKey<Biome> HAS_EXTRA_SILVER_ORE = tag("has_extra_silver_ore");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Block tags
    public static class Blocks {
        public static final TagKey<Block> NEEDS_SILVER_TOOL = tag("needs_silver_tool");
        public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = tag("incorrect_for_silver_tool");
        public static final TagKey<Block> SILVER_BLOCKS = tag("silver_blocks");

        // Family aggregation tags for Sable compatibility entries.
        // WEATHERED_SILVER_BLOCKS excludes base SILVER_BLOCK (it lives in #c:storage_blocks).
        public static final TagKey<Block> WEATHERED_SILVER_BLOCKS = tag("weathered_silver_blocks");
        public static final TagKey<Block> CUT_SILVER_BLOCKS = tag("cut_silver_blocks");
        public static final TagKey<Block> CHISELED_SILVER_BLOCKS = tag("chiseled_silver_blocks");
        public static final TagKey<Block> SILVER_BRICK_BLOCKS = tag("silver_brick_blocks");
        public static final TagKey<Block> SILVER_GRATES = tag("silver_grates");
        public static final TagKey<Block> SILVER_PILLARS = tag("silver_pillars");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> GLISTERING_NUGGETS = tag("glistering_nuggets");

        /** Silver components for the Repeater/Comparator override recipes. */
        public static final TagKey<Item> REDSTONE_SILVER_COMPONENTS = tag("redstone_silver_components");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }
}