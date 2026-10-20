package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public final class ModDamageTypes {
    /**
     * A silver floor under a silver-sensitive mob ({@link SilverFloor}). Its own type rather than magma's
     * {@code hot_floor}, which counts as fire, so wither skeletons and zombified piglins would take no notice of it.
     */
    public static final ResourceKey<DamageType> SILVER_FLOOR = ResourceKey.create(Registries.DAMAGE_TYPE, TheSilverAge.resourceLocation("silver_floor"));

    private ModDamageTypes() {
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        // hot_floor's exhaustion; the death messages are death.attack.thesilverage.silver_floor[.player].
        context.register(SILVER_FLOOR, new DamageType(TheSilverAge.MOD_ID + ".silver_floor", 0.1F));
    }

    public static DamageSource silverFloor(Level level) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(SILVER_FLOOR));
    }
}
