package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.sound.ModSounds;
import com.phantomwing.thesilverage.tags.CommonTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Silver armour's material. On 1.20.1 an armour material is an interface rather than a registry entry, so this is an
 * enum, as vanilla's {@code ArmorMaterials} is. Its name, {@code thesilverage:silver}, puts the worn layers at
 * {@code thesilverage:textures/models/armor/silver_layer_1.png} and {@code _layer_2}.
 */
public enum ModArmorMaterials implements ArmorMaterial {
    SILVER("silver", 10, new int[]{2, 6, 7, 3}, 12, 0.0F, 0.0F, () -> Ingredient.of(CommonTags.Items.INGOTS_SILVER));

    /** Kept under its 1.21 name, which other code reads. */
    public static final ArmorMaterial SILVER_ARMOR_MATERIAL = SILVER;

    /** Vanilla's durability per piece before the material's multiplier: boots, leggings, chestplate, helmet. */
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.@NotNull Type type) {
        return HEALTH_PER_SLOT[type.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.@NotNull Type type) {
        return this.slotProtections[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return ModSounds.ARMOR_EQUIP_SILVER;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return TheSilverAge.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    /** Nothing to register on 1.20.1, where armour materials have no registry. */
    public static void register() {
    }
}
