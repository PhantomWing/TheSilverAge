package com.phantomwing.thesilverage.entity;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An arrow that flies faster than vanilla's, and so hits harder, since an arrow's damage is its base damage times its
 * speed. Its extra damage to the undead is {@link com.phantomwing.thesilverage.combat.SilverSmiteHandler}'s.
 */
public class SilverArrow extends AbstractArrow {
    /**
     * A crossbow fires at 3.15, which this takes to 3.62. The game sends an entity's speed to clients capped at 3.9, so
     * much more would show other players the arrow slower than it flies.
     */
    public static final float SPEED_MULTIPLIER = 1.15f;

    public SilverArrow(EntityType<? extends SilverArrow> type, Level level) {
        super(type, level);
    }

    public SilverArrow(Level level, LivingEntity owner, ItemStack pickupItem, @Nullable ItemStack firedFromWeapon) {
        super(ModEntityTypes.SILVER_ARROW.get(), owner, level, pickupItem, firedFromWeapon);
    }

    public SilverArrow(Level level, double x, double y, double z, ItemStack pickupItem, @Nullable ItemStack firedFromWeapon) {
        super(ModEntityTypes.SILVER_ARROW.get(), x, y, z, level, pickupItem, firedFromWeapon);
    }

    // Bows, crossbows and dispensers all launch an arrow through here.
    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity * SPEED_MULTIPLIER, inaccuracy);
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.SILVER_ARROW.get());
    }
}
