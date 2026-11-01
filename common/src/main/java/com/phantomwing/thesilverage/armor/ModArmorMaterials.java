package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.CommonTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import com.phantomwing.thesilverage.sound.ModSounds;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import com.google.common.base.Suppliers;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class ModArmorMaterials {
    /**
     * Made the first time an item asks for it, as the item registers: its equip sound's holder only exists once the
     * sound is registered, and from Architectury 21 a RegistrySupplier is no longer a holder of its own.
     */
    public static final Supplier<ArmorMaterial> SILVER_ARMOR_MATERIAL = Suppliers.memoize(() -> create(
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
    ));

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
        Holder<SoundEvent> equipSound = Objects.requireNonNull(ModSounds.ARMOR_EQUIP_SILVER.asHolder(), "the silver equip sound isn't registered yet");
        // 1.21.4: the equipment model id is now a typed ResourceKey<EquipmentAsset>
        // (the EquipmentAssets registry), not a plain Identifier. Resolves to
        // the equipment asset at assets/<ns>/equipment/<path>.json.
        ResourceKey<EquipmentAsset> assetId =
                ResourceKey.create(EquipmentAssets.ROOT_ID, TheSilverAge.resourceLocation(name));

        return new ArmorMaterial(durability, defense, enchantmentValue, equipSound,
                toughness, knockbackResistance, CommonTags.Items.INGOTS_SILVER, assetId);
    }

    // No-op: armor materials are not registry objects. Retained for call-site compatibility.
    public static void register() {
    }
}
