package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityTypes;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR)
            .add(EntityTypes.ZOMBIE.builtInRegistryHolder().key()) // Husks excluded: desert suits golden armor thematically.
            .add(EntityTypes.ZOMBIE_VILLAGER.builtInRegistryHolder().key())
            .add(EntityTypes.SKELETON.builtInRegistryHolder().key())
            .add(EntityTypes.STRAY.builtInRegistryHolder().key())
            .add(EntityTypes.BOGGED.builtInRegistryHolder().key());
    }
}
