package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModTrimMaterials {
    public static final float SILVER_INDEX = 0.0039207F;
    public static final ResourceKey<TrimMaterial> SILVER = trimMaterialKey("silver");

    public static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> MOD_TRIM_MATERIALS = Util.make(new LinkedHashMap<>(), (map) -> {
        map.put(ModTrimMaterials.SILVER, ModTrimMaterials.SILVER_INDEX);
    });

    public static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> MINECRAFT_TRIM_MATERIALS = Util.make(new LinkedHashMap<>(), (map) -> {
        map.put(TrimMaterials.QUARTZ, 0.1F);
        map.put(TrimMaterials.IRON, 0.2F);
        map.put(TrimMaterials.NETHERITE, 0.3F);
        map.put(TrimMaterials.REDSTONE, 0.4F);
        map.put(TrimMaterials.COPPER, 0.5F);
        map.put(TrimMaterials.GOLD, 0.6F);
        map.put(TrimMaterials.EMERALD, 0.7F);
        map.put(TrimMaterials.DIAMOND, 0.8F);
        map.put(TrimMaterials.LAPIS, 0.9F);
        map.put(TrimMaterials.AMETHYST, 1.0F);
    });

    public static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> ALL_TRIM_MATERIALS = Util.make(new LinkedHashMap<>(), (map) -> {
        map.putAll(MOD_TRIM_MATERIALS);
        map.putAll(MINECRAFT_TRIM_MATERIALS);
    });

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        registerMaterial(context, SILVER,
                ModItems.SILVER_INGOT.get(),
                Style.EMPTY.withColor(TextColor.parseColor("#7c9eb7").getOrThrow()),
                SILVER_INDEX);
    }

    private static ResourceKey<TrimMaterial> trimMaterialKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, TheSilverAge.resourceLocation(name));
    }

    private static void registerMaterial(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item, Style style, float itemModelIndex) {
        TrimMaterial trimMaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimMaterial);
    }
}
