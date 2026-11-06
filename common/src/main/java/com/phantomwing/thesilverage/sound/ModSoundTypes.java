package com.phantomwing.thesilverage.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

/**
 * Silver block sound types.
 *
 * <p>These are plain vanilla {@link SoundType}s copied from existing vanilla
 * sound types with tweaked volume/pitch. (The original NeoForge build used
 * {@code DeferredSoundType}, but every referenced sound is a built-in vanilla
 * {@link SoundEvent} that is always present, so no deferral is needed and this
 * stays loader-agnostic.)</p>
 */
public class ModSoundTypes {
    public static final SoundType SILVER = ofCopy(SoundType.METAL, 1.0f, 0.9f);
    public static final SoundType SILVER_GRATE = ofCopy(SoundType.COPPER_GRATE, 1.0f, 0.8f);
    public static final SoundType SILVER_BULB = ofCopy(SoundType.COPPER_BULB, 1.0f, 0.8f);
    // A thin part sounds as its vanilla counterpart does: chain, lantern and lightning rod (copper) sounds, not a block's
    public static final SoundType SILVER_CHAIN = ofCopy(SoundType.CHAIN, 1.0f, 0.9f);
    public static final SoundType SILVER_LANTERN = ofCopy(SoundType.LANTERN, 1.0f, 0.9f);
    public static final SoundType SILVER_LIGHTNING_ROD = ofCopy(SoundType.COPPER, 1.0f, 0.8f);

    private static SoundType ofCopy(SoundType soundType, float volume, float pitch) {
        float baseVolume = soundType.getVolume();
        float basePitch = soundType.getPitch();
        SoundEvent breakSound = soundType.getBreakSound();
        SoundEvent stepSound = soundType.getStepSound();
        SoundEvent placeSound = soundType.getPlaceSound();
        SoundEvent hitSound = soundType.getHitSound();
        SoundEvent fallSound = soundType.getFallSound();

        return new SoundType(baseVolume * volume, basePitch * pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }
}
