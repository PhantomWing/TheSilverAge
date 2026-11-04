package com.phantomwing.thesilverage.platform.forge;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

/**
 * Forge implementation of {@link MonsterArmorPlatform}.
 *
 * <p>Subscribes the real {@code EntityJoinLevelEvent} and forwards its precise
 * {@code loadedFromDisk()} flag to the loader-agnostic equip logic, preserving
 * 100% of the original NeoForge behaviour (Architectury's {@code EntityEvent.ADD}
 * has no loaded-from-disk distinction).</p>
 */
public final class MonsterArmorPlatformImpl {
    private MonsterArmorPlatformImpl() {
    }

    public static void registerMobSpawnHandler() {
        MinecraftForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) ->
                MonsterArmorHandler.tryEquipSilverGear(
                        event.getEntity(),
                        event.getLevel(),
                        event.loadedFromDisk()));
    }
}
