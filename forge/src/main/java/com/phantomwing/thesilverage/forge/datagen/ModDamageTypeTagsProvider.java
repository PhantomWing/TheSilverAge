package com.phantomwing.thesilverage.forge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.combat.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/** Tags of the mod's damage types, which its datapack provider defines: the lookup given is that provider's. */
public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // 1.20.1 has no minecraft:no_knockback, where 1.21 puts the floor's damage as magma's: a hurt with no entity
        // behind it knocks nothing back on 1.20.1, so the floor's doesn't either. SilverFloor hops a mob itself.
    }
}
