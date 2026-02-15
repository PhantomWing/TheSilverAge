package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public class MonsterArmorHandler {
    private static final float REPLACE_CHANCE = 0.5f; // 50% chance to replace full set

    @SubscribeEvent
    public static void onMobSpawn(EntityJoinLevelEvent event) {
        // Only apply when the mob is spawning naturally in the world, not when loaded from disk or on the client side.
        if (event.getLevel().isClientSide() || event.loadedFromDisk()) {
            return;
        }

        // Check if we should equip Silver armor.
        Entity entity = event.getEntity();
        if (entity.getType().is(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR) && entity instanceof Mob mob) {
            RandomSource random = mob.getRandom();

            ItemStack helmet = mob.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chestplate = mob.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggings = mob.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack boots = mob.getItemBySlot(EquipmentSlot.FEET);

            boolean hasGoldenHelmet = helmet.is(Items.GOLDEN_HELMET);
            boolean hasGoldenChestplate = chestplate.is(Items.GOLDEN_CHESTPLATE);
            boolean hasGoldenLeggings = leggings.is(Items.GOLDEN_LEGGINGS);
            boolean hasGoldenBoots = boots.is(Items.GOLDEN_BOOTS);
            boolean hasFullSet = hasGoldenHelmet && hasGoldenChestplate && hasGoldenLeggings && hasGoldenBoots;

            // If wearing a full set of Golden armor, try to replace the entire set with Silver armor.
            if (hasFullSet) {
                if (random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.HEAD, ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET));
                    mob.setItemSlot(EquipmentSlot.CHEST, ItemUtils.tryTransmuteStack(chestplate, ModItems.SILVER_CHESTPLATE));
                    mob.setItemSlot(EquipmentSlot.LEGS, ItemUtils.tryTransmuteStack(leggings, ModItems.SILVER_LEGGINGS));
                    mob.setItemSlot(EquipmentSlot.FEET, ItemUtils.tryTransmuteStack(boots, ModItems.SILVER_BOOTS));
                }
            } else {
                // If mob is wearing any separate pieces of Golden armor, try to individually replace with a piece of Silver armor.
                if (hasGoldenHelmet && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.HEAD, ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET));
                }

                if (hasGoldenChestplate && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.CHEST, ItemUtils.tryTransmuteStack(chestplate, ModItems.SILVER_CHESTPLATE));
                }

                if (hasGoldenLeggings && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.LEGS, ItemUtils.tryTransmuteStack(leggings, ModItems.SILVER_LEGGINGS));
                }

                if (hasGoldenBoots && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.FEET, ItemUtils.tryTransmuteStack(boots, ModItems.SILVER_BOOTS));
                }
            }
        }
    }
}