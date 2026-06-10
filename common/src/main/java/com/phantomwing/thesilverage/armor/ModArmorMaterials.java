package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.CommonTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModArmorMaterials {
    public static final ArmorMaterial SILVER_ARMOR_MATERIAL = create(
            "silver",
            10, // Durability factor (Iron is 15, Gold is 7)
            2,  // Boots defense
            6,  // Leggings defense
            7,  // Chestplate defense
            3,  // Helmet defense
            8,  // Body defense (horse/wolf armor)
            0f, // Toughness
            0f, // Knockback resistance
            12  // Enchantability
    );

    private static ArmorMaterial create(String name, int durability,
                                        int bootsDefense, int legsDefense, int chestDefense,
                                        int headDefense, int bodyDefense,
                                        float toughness, float knockbackResistance, int enchantmentValue) {
        Map<ArmorType, Integer> defense = Map.of(
                ArmorType.BOOTS, bootsDefense,
                ArmorType.LEGGINGS, legsDefense,
                ArmorType.CHESTPLATE, chestDefense,
                ArmorType.HELMET, headDefense,
                ArmorType.BODY, bodyDefense
        );
        Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_GOLD;
        ResourceKey<EquipmentAsset> assetId =
                ResourceKey.create(EquipmentAssets.ROOT_ID, TheSilverAge.resourceLocation(name));

        return new ArmorMaterial(durability, defense, enchantmentValue, equipSound,
                toughness, knockbackResistance, CommonTags.Items.INGOTS_SILVER, assetId);
    }

    // No-op: armor materials are not registry objects. Retained for call-site compatibility.
    public static void register() {
    }
}
