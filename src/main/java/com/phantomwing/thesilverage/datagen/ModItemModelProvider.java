package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleBlock(ModBlocks.SILVER_BLOCK);
    }

    // A simple item with a model generated from its sprite.
    private void simpleItem(DeferredItem<Item> item) {
        withExistingParent(getItemName(item), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", getItemResourceLocation(item));
    }

    private void simpleBlock(DeferredBlock<Block> item) {
        this.withExistingParent(TheSilverAge.MOD_ID + ":" + getItemName(item), getBlockResourceLocation(item));
    }

    private String getItemName(DeferredItem<Item> item) {
        return item.getId().getPath();
    }

    private String getItemName(DeferredBlock<Block> item) {
        return item.getId().getPath();
    }

    private ResourceLocation getItemResourceLocation(DeferredItem<Item> item) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "item/" + getItemName(item));
    }

    private ResourceLocation getBlockResourceLocation(DeferredBlock<Block> item) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "block/" + getItemName(item));
    }
}
