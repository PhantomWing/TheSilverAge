package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for how silver's hits add up: armour's hurt stacks with Thorns, a silver tool's bonus is scaled by the
 * swing's charge as Smite's is, and a Silver Pressure Plate hurts only what stands in it. Listed in {@link GameTests}.
 */
public final class SilverCombatGameTest {
    private static final int STRIKES = 200;
    /** Thorns III's most, which vanilla rolls from 1 to 5. */
    private static final float THORNS = 3.0F;

    private SilverCombatGameTest() {
    }

    /**
     * A skeleton striking full silver armour is hurt by Thorns in full on every strike, silver's 1 or 2 on top where it
     * fires, as vanilla's post-attack Thorns lands after silver's hurt in the same strike.
     */
    public static void silverArmorStacksWithThorns(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        LivingEntity skeleton = helper.spawnWithNoFreeWill(EntityType.SKELETON, new BlockPos(1, 2, 1));
        Mob wearer = helper.spawnWithNoFreeWill(EntityType.VINDICATOR, new BlockPos(3, 2, 3));
        wearer.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.SILVER_HELMET.get()));
        wearer.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.SILVER_CHESTPLATE.get()));
        wearer.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.SILVER_LEGGINGS.get()));
        wearer.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.SILVER_BOOTS.get()));
        int stacked = 0;
        List<String> problems = new ArrayList<>();
        for (int i = 0; i < STRIKES; i++) {
            wearer.invulnerableTime = 0;
            wearer.setHealth(wearer.getMaxHealth());
            skeleton.invulnerableTime = 0;
            skeleton.setHealth(skeleton.getMaxHealth());
            wearer.hurt(level.damageSources().mobAttack(skeleton), 1.0F);
            skeleton.hurt(level.damageSources().thorns(wearer), THORNS);
            float lost = skeleton.getMaxHealth() - skeleton.getHealth();
            if (lost < THORNS - 0.01F) {
                problems.add("a strike's Thorns took " + lost + ", not " + THORNS);
                break;
            }
            if (lost > THORNS + 0.5F) {
                stacked++;
            }
        }
        if (stacked < 120 || stacked > 190) {
            problems.add("silver hurt on top of Thorns on " + stacked + " of " + STRIKES + " strikes, not 120 to 190");
        }
        skeleton.discard();
        wearer.discard();
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Silver armour and Thorns: " + String.join("; ", problems));
        }
    }

    /**
     * A fully charged swing of a Silver Sword at a zombie deals the bonus on top of the swing without it; a swing made
     * right after another deals almost none of it, as Smite's is scaled.
     */
    public static void silverSwingsScaleWithTheirCharge(GameTestHelper helper) {
        ServerPlayer player = TestCompat.serverPlayer(helper);
        LivingEntity zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(3, 2, 3));
        try {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.SILVER_SWORD.get()));
            float chargedOn = swing(player, zombie, true);
            float chargedOff = TestSettings.with("enable_silver_smite", false, () -> swing(player, zombie, true));
            float quickOn = swing(player, zombie, false);
            float quickOff = TestSettings.with("enable_silver_smite", false, () -> swing(player, zombie, false));
            float charged = chargedOn - chargedOff;
            float quick = quickOn - quickOff;
            List<String> problems = new ArrayList<>();
            if (charged < 0.8F || charged > 1.1F) {
                problems.add("a charged swing's bonus was " + charged + " (" + chargedOn + " against " + chargedOff + "), not about 1");
            }
            if (quick > 0.2F) {
                problems.add("a swing right after another took " + quick + " of the bonus (" + quickOn + " against " + quickOff + "), not almost none");
            }
            if (problems.isEmpty()) {
                helper.succeed();
            } else {
                fail(helper, "Silver Sword swings: " + String.join("; ", problems));
            }
        } finally {
            zombie.discard();
            TestCompat.removePlayer(player);
        }
    }

    /** A husk whose box reaches into a Silver Pressure Plate's block from beside it isn't hurt; one standing in it is. */
    public static void silverPlatesHurtOnlyWhatStandsInThem(GameTestHelper helper) {
        BlockPos plate = new BlockPos(2, 2, 3);
        BlockPos other = new BlockPos(5, 2, 3);
        helper.setBlock(plate, ModBlocks.SILVER_PRESSURE_PLATE.get());
        helper.setBlock(other, ModBlocks.SILVER_PRESSURE_PLATE.get());
        // Its box half a husk wide either side, so it reaches 0.1 into the plate's block.
        Mob beside = helper.spawnWithNoFreeWill(EntityType.HUSK, plate.getX() + 1.2F, plate.getY(), plate.getZ() + 0.5F);
        Mob on = helper.spawnWithNoFreeWill(EntityType.HUSK, other);
        helper.runAfterDelay(40, () -> {
            List<String> problems = new ArrayList<>();
            if (beside.getHealth() < beside.getMaxHealth()) {
                problems.add("a husk beside a plate was hurt");
            }
            if (on.getHealth() >= on.getMaxHealth()) {
                problems.add("a husk standing in a plate wasn't hurt");
            }
            beside.discard();
            on.discard();
            if (problems.isEmpty()) {
                helper.succeed();
            } else {
                fail(helper, "Silver Pressure Plates: " + String.join("; ", problems));
            }
        });
    }

    /** What {@code player}'s swing at {@code target} took off it, the swing fully charged or made at once. */
    private static float swing(ServerPlayer player, LivingEntity target, boolean charged) {
        target.invulnerableTime = 0;
        target.setHealth(target.getMaxHealth());
        player.resetAttackStrengthTicker();
        if (charged) {
            setAttackStrengthTicker(player, 100);
        }
        player.attack(target);
        return target.getMaxHealth() - target.getHealth();
    }

    // The ticker is protected, and a mock player's own ticks don't advance it.
    private static void setAttackStrengthTicker(LivingEntity entity, int ticks) {
        try {
            Field ticker = LivingEntity.class.getDeclaredField("attackStrengthTicker");
            ticker.setAccessible(true);
            ticker.setInt(entity, ticks);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("No attackStrengthTicker on LivingEntity", e);
        }
    }
}
