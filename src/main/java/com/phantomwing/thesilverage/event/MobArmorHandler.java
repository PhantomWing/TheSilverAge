package com.phantomwing.thesilverage.event;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public class MobArmorHandler {
    private static final float REPLACE_CHANCE = 0.5f; // 50% chance to replace full set

    @SubscribeEvent
    public static void onMobSpawn(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.loadedFromDisk()) {
            return;
        }

        TheSilverAge.LOGGER.info("EntityJoinLevelEvent triggered for entity: {}", event.getEntity().getType());

        // Check if this is a Zombie or Skeleton spawn.
        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.ZOMBIE && entity.getType() != EntityType.SKELETON) {
            return;
        }

        // Check if we should equip Silver armor.
        if (entity instanceof Mob mob) {
            RandomSource random = mob.getRandom();

            boolean hasGoldenHelmet = mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.GOLDEN_HELMET);
            boolean hasGoldenChestplate = mob.getItemBySlot(EquipmentSlot.CHEST).is(Items.GOLDEN_CHESTPLATE);
            boolean hasGoldenLeggings = mob.getItemBySlot(EquipmentSlot.LEGS).is(Items.GOLDEN_LEGGINGS);
            boolean hasGoldenBoots = mob.getItemBySlot(EquipmentSlot.FEET).is(Items.GOLDEN_BOOTS);

            // If wearing a full set of Golden armor, give a 50% chance to replace the entire set with Silver armor.
            if (hasGoldenHelmet && hasGoldenChestplate && hasGoldenLeggings && hasGoldenBoots) {
                // Mob is wearing a full set of Golden armor.
                float randomChance = random.nextFloat(); // 50% chance to replace with Silver armor

                if (randomChance < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.SILVER_HELMET.get()));
                    mob.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.SILVER_CHESTPLATE.get()));
                    mob.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.SILVER_LEGGINGS.get()));
                    mob.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.SILVER_BOOTS.get()));
                }
            } else {
                // If mob is wearing any separate pieces of Golden armor, give each a 50% chance to be replaced with a piece of Silver armor.

                if (hasGoldenHelmet) {
                    float randomChance = random.nextFloat(); // 50% chance to replace with Silver Helmet

                    if (randomChance < REPLACE_CHANCE) {
                        mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.SILVER_HELMET.get()));
                    }
                }

                if (hasGoldenChestplate) {
                    float randomChance = random.nextFloat(); // 50% chance to replace with Silver Chestplate

                    if (randomChance < REPLACE_CHANCE) {
                        mob.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.SILVER_CHESTPLATE.get()));
                    }
                }

                if (hasGoldenLeggings) {
                    float randomChance = random.nextFloat(); // 50% chance to replace with Silver Leggings

                    if (randomChance < REPLACE_CHANCE) {
                        mob.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.SILVER_LEGGINGS.get()));
                    }
                }

                if (hasGoldenBoots) {
                    float randomChance = random.nextFloat(); // 50% chance to replace with Silver Boots

                    if (randomChance < REPLACE_CHANCE) {
                        mob.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.SILVER_BOOTS.get()));
                    }
                }
            }
        }
    }
}