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

import java.util.Map;

public class ModTrimMaterials {
    public static final ResourceKey<TrimMaterial> SILVER = trimMaterialKey("silver");

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        registerMaterial(context, SILVER, ModItems.SILVER_INGOT.get(), Style.EMPTY.withColor(TextColor.parseColor("#031cfc").getOrThrow()), 1.1F);
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
