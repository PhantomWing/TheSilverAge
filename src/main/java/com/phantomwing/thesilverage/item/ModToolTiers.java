package com.phantomwing.thesilverage.item;

import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier SILVER = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_SILVER_TOOL, 655, 7.0f, 2.5f, 18, () ->
            Ingredient.of(ModTags.Items.SILVER_TOOL_MATERIALS));
}
