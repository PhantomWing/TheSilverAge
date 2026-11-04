package com.phantomwing.thesilverage.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A player's swing takes the silver tools' bonus where Smite's goes, into {@code Player#attack}'s enchantment bonus, so
 * the swing's charge scales it as it does Smite and the magic crit shows it. 1.21 does the same through
 * {@code ServerPlayer#getEnchantedDamage}, which 1.20.1 doesn't have.
 */
@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyExpressionValue(method = "attack(Lnet/minecraft/world/entity/Entity;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageBonus(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/MobType;)F"))
    private float thesilverage$addSilverSmiteDamage(float bonus, @Local(argsOnly = true) Entity target) {
        Player player = (Player) (Object) this;
        return bonus + SilverSmiteHandler.getSwingBonus(target, player.damageSources().playerAttack(player));
    }
}
