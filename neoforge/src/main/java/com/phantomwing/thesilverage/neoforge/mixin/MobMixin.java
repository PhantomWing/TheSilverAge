package com.phantomwing.thesilverage.neoforge.mixin;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Carries a converting mob's silver-gear roll onto the mob that replaces it, before that one joins the level. The
 * other loader's {@code MobMixin} is the same. The logic is {@link MonsterArmorHandler#carryRoll}.
 */
@Mixin(Mob.class)
public abstract class MobMixin {
    @ModifyArg(method = "convertTo", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private Entity thesilverage$carrySilverGearRoll(Entity converted) {
        return MonsterArmorHandler.carryRoll((Mob) (Object) this, converted);
    }
}
