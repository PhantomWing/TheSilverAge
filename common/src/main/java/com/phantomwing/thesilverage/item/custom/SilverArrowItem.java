package com.phantomwing.thesilverage.item.custom;

import com.phantomwing.thesilverage.entity.SilverArrow;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

/** Fires a {@link SilverArrow}, from a bow, a crossbow or a dispenser, as the Spectral Arrow's item does its own. */
public class SilverArrowItem extends ArrowItem {
    public SilverArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack ammo, @NotNull LivingEntity shooter) {
        return new SilverArrow(level, shooter);
    }

    /** A dispenser fires only the projectiles it has a behaviour for, which vanilla registers for its own arrows alone. */
    public static void registerDispenseBehavior(SilverArrowItem item) {
        DispenserBlock.registerBehavior(item, new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack) {
                SilverArrow arrow = new SilverArrow(level, pos.x(), pos.y(), pos.z());
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrow;
            }
        });
    }
}
