package com.phantomwing.thesilverage.tool;

import com.phantomwing.thesilverage.tags.CommonTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ModTiers {
    public static final Tier SILVER = new SilverTier(
            2, // Mining level: iron's, so silver mines what an iron pickaxe does (Forge falls back to it for a tier it hasn't sorted)
            208, // Durability (Wood is 59, Stone is 131, Iron is 250, Diamond is 1561, Gold is 32, Netherite is 2031)
            7.0f,
            2.5f,
            16, // Enchantability (Wood is 15, Stone is 5, Iron is 14, Diamond is 10, Gold is 22, Netherite is 15)
            () -> Ingredient.of(CommonTags.Items.TOOL_MATERIALS_SILVER));

    /** Loader-agnostic {@link Tier}, with a lazily evaluated repair ingredient, since tags aren't bound at class load. */
    private record SilverTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue,
                              Supplier<Ingredient> repairIngredient) implements Tier {
        @Override
        public int getUses() {
            return uses;
        }

        @Override
        public float getSpeed() {
            return speed;
        }

        @Override
        public float getAttackDamageBonus() {
            return attackDamageBonus;
        }

        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }
    }
}
