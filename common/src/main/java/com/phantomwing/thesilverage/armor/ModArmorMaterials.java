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

/**
 * Silver armor material.
 *
 * <p>1.21.2 removed the {@code Registries.ARMOR_MATERIAL} registry: {@link ArmorMaterial}
 * is now a plain record passed directly to {@link net.minecraft.world.item.ArmorItem}
 * (which internally applies {@link ArmorMaterial#humanoidProperties}), so there is no
 * {@code DeferredRegister}/{@code Holder} indirection anymore. The {@code List<Layer>}
 * was also replaced by a single asset id that points at the equipment definition. In
 * 1.21.4 that lookup is {@code assets/<ns>/equipment/<path>.json} (1.21.2's
 * {@code models/equipment/} segment was dropped).</p>
 */
public class ModArmorMaterials {
    public static final ArmorMaterial SILVER_ARMOR_MATERIAL = create(
            "silver",
            10, // Durability factor (Iron is 15, Gold is 7) — multiplied per-slot by ArmorType#getDurability
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
        // 1.21.4: the equipment model id is now a typed ResourceKey<EquipmentAsset>
        // (the EquipmentAssets registry), not a plain Identifier. Resolves to
        // the equipment asset at assets/<ns>/equipment/<path>.json.
        ResourceKey<EquipmentAsset> assetId =
                ResourceKey.create(EquipmentAssets.ROOT_ID, TheSilverAge.resourceLocation(name));

        // ArmorMaterial(int durability, Map<ArmorType,Integer> defense, int enchantmentValue,
        //   Holder<SoundEvent> equipSound, float toughness, float knockbackResistance,
        //   TagKey<Item> repairIngredient, ResourceKey<EquipmentAsset> assetId)
        return new ArmorMaterial(durability, defense, enchantmentValue, equipSound,
                toughness, knockbackResistance, CommonTags.Items.INGOTS_SILVER, assetId);
    }

    /**
     * No-op since 1.21.2: armor materials are no longer registry objects, so there is
     * nothing to register. Retained so {@code TheSilverAgeCommon#init()} keeps its
     * existing call site unchanged.
     */
    public static void register() {
    }
}
