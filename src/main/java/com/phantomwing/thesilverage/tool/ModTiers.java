package com.phantomwing.thesilverage.tool;

import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final Tier SILVER = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_SILVER_TOOL,
            224,
            7.0f,
            2.5f,
            18,
            () -> Ingredient.of(CommonTags.Items.TOOL_MATERIALS_SILVER));
}