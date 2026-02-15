package com.phantomwing.thesilverage.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class ModSoundTypes {
    public static final DeferredSoundType SILVER = ofCopy(SoundType.METAL, 1.0f, 0.9f);
    public static final DeferredSoundType SILVER_GRATE = ofCopy(SoundType.COPPER_GRATE, 1.0f, 0.8f);
    public static final DeferredSoundType MOON_PHASE_DETECTOR = ofCopy(SoundType.AMETHYST, 1.0f, 0.5f);

    private static DeferredSoundType ofCopy(SoundType soundType, float volume, float pitch) {
        float baseVolume = soundType.getVolume();
        float basePitch = soundType.getPitch();
        SoundEvent breakSound = soundType.getBreakSound();
        SoundEvent stepSound = soundType.getStepSound();
        SoundEvent placeSound = soundType.getPlaceSound();
        SoundEvent hitSound = soundType.getHitSound();
        SoundEvent fallSound = soundType.getFallSound();

        return new DeferredSoundType(baseVolume * volume, basePitch * pitch, () -> breakSound, () -> stepSound, () -> placeSound, () -> hitSound, () -> fallSound);
    }
}
