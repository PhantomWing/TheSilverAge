package com.phantomwing.thesilverage.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

import java.lang.reflect.Field;

public class ModOxidizables {
    public static void register() {
        // Build the oxidizable mappings
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();

        // Add existing vanilla mappings
        builder.putAll(WeatheringCopper.NEXT_BY_BLOCK.get());

        // Block of Silver
        builder.put(ModBlocks.SILVER_BLOCK.get(), ModBlocks.EXPOSED_SILVER.get());
        builder.put(ModBlocks.EXPOSED_SILVER.get(), ModBlocks.WEATHERED_SILVER.get());
        builder.put(ModBlocks.WEATHERED_SILVER.get(), ModBlocks.OXIDIZED_SILVER.get());

        // Cut Silver
        builder.put(ModBlocks.CUT_SILVER.get(), ModBlocks.EXPOSED_CUT_SILVER.get());
        builder.put(ModBlocks.EXPOSED_CUT_SILVER.get(), ModBlocks.WEATHERED_CUT_SILVER.get());
        builder.put(ModBlocks.WEATHERED_CUT_SILVER.get(), ModBlocks.OXIDIZED_CUT_SILVER.get());

        // Cut Silver Slab
        builder.put(ModBlocks.CUT_SILVER_SLAB.get(), ModBlocks.EXPOSED_CUT_SILVER_SLAB.get());
        builder.put(ModBlocks.EXPOSED_CUT_SILVER_SLAB.get(), ModBlocks.WEATHERED_CUT_SILVER_SLAB.get());
        builder.put(ModBlocks.WEATHERED_CUT_SILVER_SLAB.get(), ModBlocks.OXIDIZED_CUT_SILVER_SLAB.get());

        // Cut Silver Stairs
        builder.put(ModBlocks.CUT_SILVER_STAIRS.get(), ModBlocks.EXPOSED_CUT_SILVER_STAIRS.get());
        builder.put(ModBlocks.EXPOSED_CUT_SILVER_STAIRS.get(), ModBlocks.WEATHERED_CUT_SILVER_STAIRS.get());
        builder.put(ModBlocks.WEATHERED_CUT_SILVER_STAIRS.get(), ModBlocks.OXIDIZED_CUT_SILVER_STAIRS.get());

        // Chiseled Silver
        builder.put(ModBlocks.CHISELED_SILVER.get(), ModBlocks.EXPOSED_CHISELED_SILVER.get());
        builder.put(ModBlocks.EXPOSED_CHISELED_SILVER.get(), ModBlocks.WEATHERED_CHISELED_SILVER.get());
        builder.put(ModBlocks.WEATHERED_CHISELED_SILVER.get(), ModBlocks.OXIDIZED_CHISELED_SILVER.get());

        // Silver Trapdoor
        builder.put(ModBlocks.SILVER_TRAPDOOR.get(), ModBlocks.EXPOSED_SILVER_TRAPDOOR.get());
        builder.put(ModBlocks.EXPOSED_SILVER_TRAPDOOR.get(), ModBlocks.WEATHERED_SILVER_TRAPDOOR.get());
        builder.put(ModBlocks.WEATHERED_SILVER_TRAPDOOR.get(), ModBlocks.OXIDIZED_SILVER_TRAPDOOR.get());

        // Silver Door
        builder.put(ModBlocks.SILVER_DOOR.get(), ModBlocks.EXPOSED_SILVER_DOOR.get());
        builder.put(ModBlocks.EXPOSED_SILVER_DOOR.get(), ModBlocks.WEATHERED_SILVER_DOOR.get());
        builder.put(ModBlocks.WEATHERED_SILVER_DOOR.get(), ModBlocks.OXIDIZED_SILVER_DOOR.get());

        // Silver Grate
        builder.put(ModBlocks.SILVER_GRATE.get(), ModBlocks.EXPOSED_SILVER_GRATE.get());
        builder.put(ModBlocks.EXPOSED_SILVER_GRATE.get(), ModBlocks.WEATHERED_SILVER_GRATE.get());
        builder.put(ModBlocks.WEATHERED_SILVER_GRATE.get(), ModBlocks.OXIDIZED_SILVER_GRATE.get());

        // Silver Bulb
        builder.put(ModBlocks.SILVER_BULB.get(), ModBlocks.EXPOSED_SILVER_BULB.get());
        builder.put(ModBlocks.EXPOSED_SILVER_BULB.get(), ModBlocks.WEATHERED_SILVER_BULB.get());
        builder.put(ModBlocks.WEATHERED_SILVER_BULB.get(), ModBlocks.OXIDIZED_SILVER_BULB.get());

        BiMap<Block, Block> map = builder.build();

        // WeatheringCopper fields are interface constants (implicitly final) and cannot be
        // modified via access transformers. Use Unsafe to replace them.
        // Dev uses Parchment names, production uses SRG — try both.
        try {
            setStaticFinalField(WeatheringCopper.class, "NEXT_BY_BLOCK", "f_154886_",
                    com.google.common.base.Suppliers.memoize(() -> map));
            setStaticFinalField(WeatheringCopper.class, "PREVIOUS_BY_BLOCK", "f_154887_",
                    com.google.common.base.Suppliers.memoize(() -> map.inverse()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to register silver oxidizable mappings", e);
        }
    }

    @SuppressWarnings("removal")
    private static void setStaticFinalField(Class<?> clazz, String mappedName, String srgName, Object value) throws Exception {
        Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);

        Field field;
        try {
            field = clazz.getDeclaredField(mappedName);
        } catch (NoSuchFieldException e) {
            field = clazz.getDeclaredField(srgName);
        }
        Object base = unsafe.staticFieldBase(field);
        long offset = unsafe.staticFieldOffset(field);
        unsafe.putObject(base, offset, value);
    }
}
