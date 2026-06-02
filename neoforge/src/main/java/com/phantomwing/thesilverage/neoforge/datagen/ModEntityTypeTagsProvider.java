package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR)
            .add(EntityType.ZOMBIE) // Husks are not included, because the Desert is thematically more suitable for golden armor.
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.SKELETON)
            .add(EntityType.STRAY)
            .add(EntityType.BOGGED);
    }
}
