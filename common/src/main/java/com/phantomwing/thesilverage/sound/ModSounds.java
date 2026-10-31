package com.phantomwing.thesilverage.sound;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

/**
 * The mod's own sound events, so the subtitles name silver where vanilla's would name copper or gold. Each plays
 * vanilla's sound ({@code assets/thesilverage/sounds.json}) until it has its own, so it can be given real ones without
 * touching code.
 */
public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(TheSilverAge.MOD_ID, Registries.SOUND_EVENT);

    // A Silver Bulb toggled, "Silver Bulb turns on".
    public static final SoundEvent SILVER_BULB_TURN_ON = register("block.silver_bulb.turn_on");
    public static final SoundEvent SILVER_BULB_TURN_OFF = register("block.silver_bulb.turn_off");

    // Silver armor put on, "Silver armor clinks". A holder, which the armor material takes.
    public static final Holder<SoundEvent> ARMOR_EQUIP_SILVER = registerHolder("item.armor.equip_silver");

    private ModSounds() {
    }

    private static SoundEvent register(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(TheSilverAge.resourceLocation(name));
        SOUNDS.register(name, () -> event);
        return event;
    }

    private static Holder<SoundEvent> registerHolder(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(TheSilverAge.resourceLocation(name)));
    }

    public static void register() {
        SOUNDS.register();
    }
}
