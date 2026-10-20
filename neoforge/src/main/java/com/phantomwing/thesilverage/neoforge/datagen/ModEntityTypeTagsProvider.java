package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR)
            .add(EntityType.ZOMBIE)
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.HUSK)
            .add(EntityType.SKELETON)
            .add(EntityType.STRAY)
            .add(EntityType.BOGGED);
        // Mobs that can naturally spawn holding an iron sword, axe or shovel.
        this.tag(ModTags.EntityTypes.CAN_HOLD_SILVER_TOOLS)
            .add(EntityType.ZOMBIE)
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.HUSK)
            .add(EntityType.VINDICATOR);
        // Every mob Smite targets, and the werewolves of Werewolves: Become a Beast where it is installed, which silver
        // harms in folklore and Smite doesn't. A pack adds others.
        this.tag(ModTags.EntityTypes.SENSITIVE_TO_SILVER)
            .addTag(EntityTypeTags.SENSITIVE_TO_SMITE)
            .addOptionalTag(WEREWOLVES);
    }

    private static final TagKey<EntityType<?>> WEREWOLVES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("werewolves", "werewolf"));
}
