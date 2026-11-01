package com.phantomwing.thesilverage.item.custom;

import com.phantomwing.thesilverage.entity.SilverArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Fires a {@link SilverArrow}, from a bow, a crossbow or a dispenser, as the Spectral Arrow's item does its own. */
public class SilverArrowItem extends ArrowItem {
    public SilverArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack ammo, @NotNull LivingEntity shooter, @Nullable ItemStack weapon) {
        return new SilverArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, Position pos, @NotNull ItemStack stack, @NotNull Direction direction) {
        SilverArrow arrow = new SilverArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}
