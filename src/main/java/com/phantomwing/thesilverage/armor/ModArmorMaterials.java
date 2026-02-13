package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.CommonTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, TheSilverAge.MOD_ID);

    public static final Holder<ArmorMaterial> SILVER_ARMOR_MATERIAL = register("silver",
            2,
            6,
            7,
            3,
            8,
            0,
            0,
            20,
            Ingredient.of(CommonTags.Items.INGOTS_SILVER)
    );


    private static Holder<ArmorMaterial> register(String name, int bootsProtection, int legsProtection, int chestProtection, int headProtection, int bodyProtection, float toughness, float knockbackResistance, int enchantability, Ingredient repairIngredient) {
        EnumMap<ArmorItem.Type, Integer> typeProtection = Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
            attribute.put(ArmorItem.Type.BOOTS, bootsProtection);
            attribute.put(ArmorItem.Type.LEGGINGS, legsProtection);
            attribute.put(ArmorItem.Type.CHESTPLATE, chestProtection);
            attribute.put(ArmorItem.Type.HELMET, headProtection);
            attribute.put(ArmorItem.Type.BODY, bodyProtection); // Body is for Horse/Wolf armor
        });

        ResourceLocation location = TheSilverAge.resourceLocation(name);
        Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_GOLD;
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(typeProtection, enchantability, equipSound, () -> repairIngredient, layers, toughness, knockbackResistance));
    }

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }

}
