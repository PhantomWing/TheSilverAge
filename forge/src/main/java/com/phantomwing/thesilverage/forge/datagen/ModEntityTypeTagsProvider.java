package com.phantomwing.thesilverage.forge.datagen;

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
import net.minecraftforge.common.data.ExistingFileHelper;
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
            // 1.21.11's desert skeleton, as the 1.21.11 lines have it, where Vanilla Backport adds it under its id.
            .addOptional(new ResourceLocation("parched"));
        // Mobs that can naturally spawn holding an iron sword, axe or shovel.
        this.tag(ModTags.EntityTypes.CAN_HOLD_SILVER_TOOLS)
            .add(EntityType.ZOMBIE)
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.HUSK)
            .add(EntityType.VINDICATOR);
        // Every mob Smite targets, and the werewolves of Werewolves: Become a Beast where it is installed, which silver
        // harms in folklore and Smite doesn't. A pack adds others. 1.20.1 has no minecraft:sensitive_to_smite (Smite
        // asks a mob's MobType), so vanilla's undead are listed, for the advancement's predicate and the packs that
        // read the tag; SilverSensitivity counts every MobType.UNDEAD mob, another mod's too, whether listed or not.
        this.tag(ModTags.EntityTypes.SENSITIVE_TO_SILVER)
            .add(EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED, EntityType.ZOMBIE_VILLAGER,
                    EntityType.ZOMBIFIED_PIGLIN, EntityType.ZOGLIN, EntityType.ZOMBIE_HORSE,
                    EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.SKELETON_HORSE,
                    EntityType.PHANTOM, EntityType.WITHER)
            .addOptionalTag(WEREWOLVES.location());
        // Besides every hostile mob, which the plate leaves out in code.
        this.tag(ModTags.EntityTypes.CANNOT_PRESS_SILVER_PRESSURE_PLATES)
            .addTag(ModTags.EntityTypes.SENSITIVE_TO_SILVER);
    }

    private static final TagKey<EntityType<?>> WEREWOLVES =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("werewolves", "werewolf"));
}
