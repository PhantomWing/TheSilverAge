package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    /** 26.2: TagAppender#add takes a ResourceKey, so the chains below go through a wrapper that still takes entity types. */
    @Override
    protected ValueAppender<EntityType<?>> tag(TagKey<EntityType<?>> tag) {
        return new ValueAppender<>(super.tag(tag), value -> value.builtInRegistryHolder().key());
    }

    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR)
            .add(EntityTypes.ZOMBIE)
            .add(EntityTypes.ZOMBIE_VILLAGER)
            .add(EntityTypes.HUSK)
            .add(EntityTypes.SKELETON)
            .add(EntityTypes.STRAY)
            .add(EntityTypes.BOGGED)
            .add(EntityTypes.PARCHED);
        // Mobs that can naturally spawn holding an iron sword, axe or shovel.
        this.tag(ModTags.EntityTypes.CAN_HOLD_SILVER_TOOLS)
            .add(EntityTypes.ZOMBIE)
            .add(EntityTypes.ZOMBIE_VILLAGER)
            .add(EntityTypes.HUSK)
            .add(EntityTypes.VINDICATOR);
        // Every mob Smite targets, and the werewolves of Werewolves: Become a Beast where it is installed, which silver
        // harms in folklore and Smite doesn't. A pack adds others.
        this.tag(ModTags.EntityTypes.SENSITIVE_TO_SILVER)
            .addTag(EntityTypeTags.SENSITIVE_TO_SMITE)
            .addOptionalTag(WEREWOLVES);
        // Besides every hostile mob, which the plate leaves out in code.
        this.tag(ModTags.EntityTypes.CANNOT_PRESS_SILVER_PRESSURE_PLATES)
            .addTag(ModTags.EntityTypes.SENSITIVE_TO_SILVER);
    }

    private static final TagKey<EntityType<?>> WEREWOLVES =
            TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("werewolves", "werewolf"));
}
