package com.phantomwing.thesilverage.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Vanilla tags newer than 1.21.10, which Backported Spears fills on this version under vanilla's own ids. Unused
 * without it, as a tag nothing reads is.
 */
public class BackportTags {
    public static class Items {
        /** 1.21.11's spears, which take Lunge and the melee enchantments. */
        public static final TagKey<Item> SPEARS = tag("spears");

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(path));
        }
    }
}
