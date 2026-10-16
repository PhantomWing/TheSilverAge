package com.phantomwing.thesilverage.platform.neoforge;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

// NeoForge implementation of MonsterArmorPlatform. Uses the real EntityJoinLevelEvent
// for its loadedFromDisk() flag, which Architectury's EntityEvent.ADD lacks.
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
