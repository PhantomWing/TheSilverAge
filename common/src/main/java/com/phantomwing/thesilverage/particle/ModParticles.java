package com.phantomwing.thesilverage.particle;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

/**
 * Custom particles. {@code silver_flame} is the Silver Torch's violet flame — vanilla has no
 * purple flame particle, so this is vanilla's {@code FlameParticle} behaviour (the provider is
 * registered per loader) over a recoloured flame sprite.
 */
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> SILVER_FLAME =
            PARTICLES.register("silver_flame", () -> new SimpleParticleType(false) {
            }); // anonymous subclass — the vanilla constructor is protected

    public static void register() {
        PARTICLES.register();
    }
}
