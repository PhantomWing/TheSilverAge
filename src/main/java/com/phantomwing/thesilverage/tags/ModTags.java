package com.phantomwing.thesilverage.tags;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
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

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> GLISTERING_NUGGETS = tag("glistering_nuggets");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }
}