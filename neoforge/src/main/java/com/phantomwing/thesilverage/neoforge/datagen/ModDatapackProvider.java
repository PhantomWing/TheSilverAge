package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.armor.ModTrimMaterials;
import com.phantomwing.thesilverage.neoforge.world.ModBiomeModifiers;
import com.phantomwing.thesilverage.world.ModFeatures;
import com.phantomwing.thesilverage.world.ModPlacedFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


/** World-layer datapack registry entries; wired in DataGenerators. */
public final class ModDatapackProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap)
            .add(Registries.FEATURE, ModFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);


    private ModDatapackProvider() {
    }
}