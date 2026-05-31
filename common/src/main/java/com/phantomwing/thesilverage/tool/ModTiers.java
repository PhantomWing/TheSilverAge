package com.phantomwing.thesilverage.tool;

import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.world.item.ToolMaterial;

public class ModTiers {
    // 1.21.2 replaced the Tier interface (+ Tiers enum) with the ToolMaterial
    // record. Constructor order:
    //   (TagKey<Block> incorrectBlocksForDrops, int durability, float speed,
    //    float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems)
    // The repair input is now a TagKey<Item> directly (was a Supplier<Ingredient>).
    public static final ToolMaterial SILVER = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_SILVER_TOOL,
            208, // Durability (Wood is 59, Stone is 131, Iron is 250, Diamond is 1561, Gold is 32, Netherite is 2031)
            7.0f, // Mining speed
            2.5f, // Attack damage bonus
            16, // Enchantability (Wood is 15, Stone is 5, Iron is 14, Diamond is 10, Gold is 22, Netherite is 15)
            CommonTags.Items.TOOL_MATERIALS_SILVER);
}
