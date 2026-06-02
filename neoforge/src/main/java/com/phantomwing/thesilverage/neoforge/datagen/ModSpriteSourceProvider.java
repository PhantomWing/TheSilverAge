package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import java.util.concurrent.CompletableFuture;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    // 1.21.4: NeoForge dropped ExistingFileHelper from the datagen providers.
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TheSilverAge.MOD_ID);
    }

    @Override
    protected void gather() {

    }
}