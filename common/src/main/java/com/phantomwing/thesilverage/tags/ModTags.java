package com.phantomwing.thesilverage.tags;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Block tags
    public static class Biomes {
        public static final TagKey<Biome> HAS_SILVER_ORE = tag("has_silver_ore");
        public static final TagKey<Biome> HAS_EXTRA_SILVER_ORE = tag("has_extra_silver_ore");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Block tags
    public static class Blocks {
        public static final TagKey<Block> NEEDS_SILVER_TOOL = tag("needs_silver_tool");
        public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = tag("incorrect_for_silver_tool");
        public static final TagKey<Block> SILVER_BLOCKS = tag("silver_blocks");

        // Family aggregation tags. Used to keep the Sable compatibility entries
        // (sable:heavy / super_light / quarter_volume in this mod's generated
        // data) tidy — Sable references the family tag, the family tag
        // enumerates the eight weathering/waxed variants once.
        //
        // weathered_silver_blocks: the seven non-base SILVER_BLOCK variants —
        // exposed/weathered/oxidized + their four waxed forms. The base
        // SILVER_BLOCK is intentionally excluded: it is the only one in
        // #c:storage_blocks (only it can be uncrafted back into ingots), and
        // Sable already classifies #c:storage_blocks as sable:heavy.
        public static final TagKey<Block> WEATHERED_SILVER_BLOCKS = tag("weathered_silver_blocks");
        public static final TagKey<Block> CUT_SILVER_BLOCKS = tag("cut_silver_blocks");
        public static final TagKey<Block> CHISELED_SILVER_BLOCKS = tag("chiseled_silver_blocks");
        public static final TagKey<Block> SILVER_BRICK_BLOCKS = tag("silver_brick_blocks");
        public static final TagKey<Block> SILVER_GRATES = tag("silver_grates");
        public static final TagKey<Block> SILVER_PILLARS = tag("silver_pillars");
        // Lattice metal like the grates — same Sable classification.
        public static final TagKey<Block> SILVER_BARS = tag("silver_bars");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> GLISTERING_NUGGETS = tag("glistering_nuggets");

        /**
         * Items accepted as the silver component in the Redstone Repeater and Redstone Comparator
         * override recipes. Populated with silver ingot (always) and silver sheet (Create-only).
         * Addons may contribute extra silver forms to this tag.
         */
        public static final TagKey<Item> REDSTONE_SILVER_COMPONENTS = tag("redstone_silver_components");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }
}