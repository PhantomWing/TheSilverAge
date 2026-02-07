package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import java.util.concurrent.CompletableFuture;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void gather() {

    }
}