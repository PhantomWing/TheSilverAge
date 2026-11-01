package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.sound.ModSoundTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ModBlockSetTypes {
    // Opens like copper: by hand and by a wind charge, not by an arrow on its button.
    public static final BlockSetType SILVER = BlockSetType.register(new BlockSetType("silver",
            true,   // canOpenByHand
            true,   // canOpenByWindCharge
            false,  // canButtonBeActivatedByArrows
            BlockSetType.PressurePlateSensitivity.EVERYTHING,
            ModSoundTypes.SILVER,
            SoundEvents.IRON_DOOR_CLOSE,
            SoundEvents.IRON_DOOR_OPEN,
            SoundEvents.IRON_TRAPDOOR_CLOSE,
            SoundEvents.IRON_TRAPDOOR_OPEN,
            SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
            SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.STONE_BUTTON_CLICK_OFF,
            SoundEvents.STONE_BUTTON_CLICK_ON
        )
    );
}
