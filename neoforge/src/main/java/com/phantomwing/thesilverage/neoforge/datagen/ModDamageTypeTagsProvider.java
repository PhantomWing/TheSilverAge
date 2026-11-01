package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.combat.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Tags of the mod's damage types, which its datapack provider defines: the lookup given is that provider's. From 1.21.6
 * the registries tagged by key go through KeyTagProvider, so this extends vanilla's damage type tags provider and leaves
 * out its own tags.
 */
public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // As magma's hot_floor is: a silver floor hopping its mob at every hit kept it bouncing. SilverFloor hops it
        // itself, now and then.
        this.tag(DamageTypeTags.NO_KNOCKBACK)
            .add(ModDamageTypes.SILVER_FLOOR);
    }
}
