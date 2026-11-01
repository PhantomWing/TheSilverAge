package com.phantomwing.thesilverage.tags;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
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
        /** Mobs whose naturally-spawned iron sword, axe or shovel may be swapped for the silver one. */
        public static final TagKey<EntityType<?>> CAN_HOLD_SILVER_TOOLS = tag("can_hold_silver_tools");
        /**
         * Mobs silver harms: silver tools' bonus damage, silver armour's protection and {@link Blocks#SILVER_FLOORS}, and
         * through {@link #CANNOT_PRESS_SILVER_PRESSURE_PLATES} the Silver Pressure Plate. Holds
         * {@code #minecraft:sensitive_to_smite} and, where Werewolves: Become a Beast is installed, its werewolves; a pack
         * can add another mob that Smite leaves alone.
         */
        public static final TagKey<EntityType<?>> SENSITIVE_TO_SILVER = tag("sensitive_to_silver");
        /**
         * Mobs that can't press a Silver Pressure Plate besides the hostile ones, which never do: holds
         * {@link #SENSITIVE_TO_SILVER}, and a pack can add another.
         */
        public static final TagKey<EntityType<?>> CANNOT_PRESS_SILVER_PRESSURE_PLATES = tag("cannot_press_silver_pressure_plates");

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
        /**
         * Silver that hurts a {@link EntityTypes#SENSITIVE_TO_SILVER} mob standing on it, or in it where it is a pressure
         * plate, keeps one from spawning on it and is walked round by one
         * ({@link com.phantomwing.thesilverage.combat.SilverFloor}).
         */
        public static final TagKey<Block> SILVER_FLOORS = tag("silver_floors");
        /**
         * Silver light, which a {@link EntityTypes#SENSITIVE_TO_SILVER} mob with nothing to fight keeps away from
         * ({@link com.phantomwing.thesilverage.combat.AvoidSilverLightGoal}): the Silver Torch and Lantern.
         */
        public static final TagKey<Block> UNDEAD_REPELLENTS = tag("undead_repellents");

        // Family aggregation tags for Sable compatibility entries.
        // WEATHERED_SILVER_BLOCKS excludes base SILVER_BLOCK (it lives in #c:storage_blocks).
        public static final TagKey<Block> WEATHERED_SILVER_BLOCKS = tag("weathered_silver_blocks");
        public static final TagKey<Block> CUT_SILVER_BLOCKS = tag("cut_silver_blocks");
        public static final TagKey<Block> CHISELED_SILVER_BLOCKS = tag("chiseled_silver_blocks");
        public static final TagKey<Block> SILVER_BRICK_BLOCKS = tag("silver_brick_blocks");
        public static final TagKey<Block> SILVER_GRATES = tag("silver_grates");
        public static final TagKey<Block> SILVER_PILLARS = tag("silver_pillars");
        // Lattice metal like the grates — same Sable classification.
        public static final TagKey<Block> SILVER_BARS = tag("silver_bars");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    /**
     * Tags over our own recipes so advancements can name a recipe without resolving it.
     *
     * <p>26.3 made recipes a datapack registry and recipe_unlocked takes a HolderSet. Recipes are
     * written in a parallel provider pass, so a direct reference is not resolvable and the
     * advancement is silently dropped. Entries are optional: a recipe whose condition fails is
     * simply absent from the tag rather than erroring.</p>
     */
    public static class Recipes {
        public static final TagKey<Recipe<?>> UNLOCKS_BREWING_STAND = tag("unlocks/brewing_stand");
        public static final TagKey<Recipe<?>> UNLOCKS_LODESTONE = tag("unlocks/lodestone");
        public static final TagKey<Recipe<?>> UNLOCKS_COMPARATOR = tag("unlocks/comparator");
        public static final TagKey<Recipe<?>> UNLOCKS_REPEATER = tag("unlocks/repeater");
        public static final TagKey<Recipe<?>> UNLOCKS_GLISTERING_MELON_SLICE = tag("unlocks/glistering_melon_slice");
        public static final TagKey<Recipe<?>> UNLOCKS_SILVER_FROM_CRUSHED = tag("unlocks/silver_ingot_from_crushed_raw_silver");

        private static TagKey<Recipe<?>> tag(String name) {
            return TagKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> GLISTERING_NUGGETS = tag("glistering_nuggets");

        /** Silver components for the Repeater/Comparator override recipes. */
        public static final TagKey<Item> REDSTONE_SILVER_COMPONENTS = tag("redstone_silver_components");

        /**
         * Silver tools that carry the innate anti-undead damage bonus (see
         * {@link com.phantomwing.thesilverage.combat.SilverSmiteHandler}). Datapacks
         * and addons can add their own items here to grant the same bonus.
         */
        public static final TagKey<Item> SILVER_SMITE_TOOLS = tag("silver_smite_tools");

        /**
         * Silver armour that burns the undead striking its wearer
         * ({@link com.phantomwing.thesilverage.combat.SilverThornsHandler}). Datapacks and addons can add their own
         * pieces; each adds a piece's chance, and animal armour a full set's.
         */
        public static final TagKey<Item> SILVER_THORNS_ARMOR = tag("silver_thorns_armor");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, name));
        }
    }
}