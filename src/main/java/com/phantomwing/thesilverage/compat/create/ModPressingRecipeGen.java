package com.phantomwing.thesilverage.compat.create;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class ModPressingRecipeGen extends PressingRecipeGen {

    @SuppressWarnings("unused")
    GeneratedRecipe SILVER_SHEET = create(new ResourceLocation(TheSilverAge.MOD_ID, "silver_sheet"), b -> b
            .require(CommonTags.Items.INGOTS_SILVER)
            .output(ModItems.SILVER_SHEET.get()));

    public ModPressingRecipeGen(PackOutput output) {
        super(output, TheSilverAge.MOD_ID);
    }
}
