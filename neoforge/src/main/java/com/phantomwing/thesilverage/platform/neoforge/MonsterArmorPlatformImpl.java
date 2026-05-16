package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * NeoForge implementation of {@link MonsterArmorPlatform}.
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
        NeoForge.EVENT_BUS.addListener((EntityJoinLevelEvent event) ->
                MonsterArmorHandler.tryEquipSilverArmor(
                        event.getEntity(),
                        event.getLevel(),
                        event.loadedFromDisk()));
    }
}
