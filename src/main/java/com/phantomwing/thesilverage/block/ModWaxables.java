package com.phantomwing.thesilverage.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;

import java.lang.reflect.Field;

public class ModWaxables {
    public static void register() {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();

        // Add existing vanilla mappings
        builder.putAll(HoneycombItem.WAXABLES.get());

        // Block of Silver
        builder.put(ModBlocks.SILVER_BLOCK.get(), ModBlocks.WAXED_SILVER_BLOCK.get());
        builder.put(ModBlocks.EXPOSED_SILVER.get(), ModBlocks.WAXED_EXPOSED_SILVER.get());
        builder.put(ModBlocks.WEATHERED_SILVER.get(), ModBlocks.WAXED_WEATHERED_SILVER.get());
        builder.put(ModBlocks.OXIDIZED_SILVER.get(), ModBlocks.WAXED_OXIDIZED_SILVER.get());

        // Cut Silver
        builder.put(ModBlocks.CUT_SILVER.get(), ModBlocks.WAXED_CUT_SILVER.get());
        builder.put(ModBlocks.EXPOSED_CUT_SILVER.get(), ModBlocks.WAXED_EXPOSED_CUT_SILVER.get());
        builder.put(ModBlocks.WEATHERED_CUT_SILVER.get(), ModBlocks.WAXED_WEATHERED_CUT_SILVER.get());
        builder.put(ModBlocks.OXIDIZED_CUT_SILVER.get(), ModBlocks.WAXED_OXIDIZED_CUT_SILVER.get());

        // Cut Silver Slab
        builder.put(ModBlocks.CUT_SILVER_SLAB.get(), ModBlocks.WAXED_CUT_SILVER_SLAB.get());
        builder.put(ModBlocks.EXPOSED_CUT_SILVER_SLAB.get(), ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB.get());
        builder.put(ModBlocks.WEATHERED_CUT_SILVER_SLAB.get(), ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB.get());
        builder.put(ModBlocks.OXIDIZED_CUT_SILVER_SLAB.get(), ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB.get());

        // Cut Silver Stairs
        builder.put(ModBlocks.CUT_SILVER_STAIRS.get(), ModBlocks.WAXED_CUT_SILVER_STAIRS.get());
        builder.put(ModBlocks.EXPOSED_CUT_SILVER_STAIRS.get(), ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS.get());
        builder.put(ModBlocks.WEATHERED_CUT_SILVER_STAIRS.get(), ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS.get());
        builder.put(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS.get(), ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS.get());

        // Chiseled Silver
        builder.put(ModBlocks.CHISELED_SILVER.get(), ModBlocks.WAXED_CHISELED_SILVER.get());
        builder.put(ModBlocks.EXPOSED_CHISELED_SILVER.get(), ModBlocks.WAXED_EXPOSED_CHISELED_SILVER.get());
        builder.put(ModBlocks.WEATHERED_CHISELED_SILVER.get(), ModBlocks.WAXED_WEATHERED_CHISELED_SILVER.get());
        builder.put(ModBlocks.OXIDIZED_CHISELED_SILVER.get(), ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER.get());

        // Silver Trapdoor
        builder.put(ModBlocks.SILVER_TRAPDOOR.get(), ModBlocks.WAXED_SILVER_TRAPDOOR.get());
        builder.put(ModBlocks.EXPOSED_SILVER_TRAPDOOR.get(), ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR.get());
        builder.put(ModBlocks.WEATHERED_SILVER_TRAPDOOR.get(), ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR.get());
        builder.put(ModBlocks.OXIDIZED_SILVER_TRAPDOOR.get(), ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR.get());

        // Silver Door
        builder.put(ModBlocks.SILVER_DOOR.get(), ModBlocks.WAXED_SILVER_DOOR.get());
        builder.put(ModBlocks.EXPOSED_SILVER_DOOR.get(), ModBlocks.WAXED_EXPOSED_SILVER_DOOR.get());
        builder.put(ModBlocks.WEATHERED_SILVER_DOOR.get(), ModBlocks.WAXED_WEATHERED_SILVER_DOOR.get());
        builder.put(ModBlocks.OXIDIZED_SILVER_DOOR.get(), ModBlocks.WAXED_OXIDIZED_SILVER_DOOR.get());

        // Silver Grate
        builder.put(ModBlocks.SILVER_GRATE.get(), ModBlocks.WAXED_SILVER_GRATE.get());
        builder.put(ModBlocks.EXPOSED_SILVER_GRATE.get(), ModBlocks.WAXED_EXPOSED_SILVER_GRATE.get());
        builder.put(ModBlocks.WEATHERED_SILVER_GRATE.get(), ModBlocks.WAXED_WEATHERED_SILVER_GRATE.get());
        builder.put(ModBlocks.OXIDIZED_SILVER_GRATE.get(), ModBlocks.WAXED_OXIDIZED_SILVER_GRATE.get());

        // Silver Bulb
        builder.put(ModBlocks.SILVER_BULB.get(), ModBlocks.WAXED_SILVER_BULB.get());
        builder.put(ModBlocks.EXPOSED_SILVER_BULB.get(), ModBlocks.WAXED_EXPOSED_SILVER_BULB.get());
        builder.put(ModBlocks.WEATHERED_SILVER_BULB.get(), ModBlocks.WAXED_WEATHERED_SILVER_BULB.get());
        builder.put(ModBlocks.OXIDIZED_SILVER_BULB.get(), ModBlocks.WAXED_OXIDIZED_SILVER_BULB.get());

        BiMap<Block, Block> map = builder.build();

        // HoneycombItem fields are in a class but still final. Use reflection with Unsafe
        // to replace them since access transformers may not work for interface-sourced patterns.
        try {
            setStaticFinalField(HoneycombItem.class, "WAXABLES",
                    com.google.common.base.Suppliers.memoize(() -> map));
            setStaticFinalField(HoneycombItem.class, "WAX_OFF_BY_BLOCK",
                    com.google.common.base.Suppliers.memoize(() -> map.inverse()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to register silver waxable mappings", e);
        }
    }

    @SuppressWarnings("removal")
    private static void setStaticFinalField(Class<?> clazz, String fieldName, Object value) throws Exception {
        Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);

        Field field = clazz.getDeclaredField(fieldName);
        Object base = unsafe.staticFieldBase(field);
        long offset = unsafe.staticFieldOffset(field);
        unsafe.putObject(base, offset, value);
    }
}
