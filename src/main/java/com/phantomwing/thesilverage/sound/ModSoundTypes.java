package com.phantomwing.thesilverage.sound;

import net.minecraft.world.level.block.SoundType;

public class ModSoundTypes {
    public static final SoundType SILVER = new SoundType(
            1.0f, 0.9f,
            SoundType.METAL.getBreakSound(),
            SoundType.METAL.getStepSound(),
            SoundType.METAL.getPlaceSound(),
            SoundType.METAL.getHitSound(),
            SoundType.METAL.getFallSound()
    );
}
