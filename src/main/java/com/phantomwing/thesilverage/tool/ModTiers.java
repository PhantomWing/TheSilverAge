package com.phantomwing.thesilverage.tool;

import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final Tier SILVER = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_SILVER_TOOL,
            208, // Durability (Wood is 59, Stone is 131, Iron is 250, Diamond is 1561, Gold is 32, Netherite is 2031)
            7.0f,
            2.5f,
            16, // Enchantability (Wood is 15, Stone is 5, Iron is 14, Diamond is 10, Gold is 22, Netherite is 15)
            () -> Ingredient.of(CommonTags.Items.TOOL_MATERIALS_SILVER));
}