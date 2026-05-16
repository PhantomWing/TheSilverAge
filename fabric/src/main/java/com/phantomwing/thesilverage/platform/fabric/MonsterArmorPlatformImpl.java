package com.phantomwing.thesilverage.platform.fabric;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;

/**
 * Fabric implementation of {@link MonsterArmorPlatform}.
 *
 * <p>Phase 1 shell using the Architectury {@code EntityEvent.ADD} event. Note
 * Architectury exposes no loaded-from-disk distinction, so {@code loadedFromDisk}
 * is passed as {@code false}. Refining this to full NeoForge behavioural parity
 * on Fabric is TODO(phase 4).</p>
 */
public final class MonsterArmorPlatformImpl {
    private MonsterArmorPlatformImpl() {
    }

    public static void registerMobSpawnHandler() {
        EntityEvent.ADD.register((entity, level) -> {
            // TODO(phase 4): Fabric has no direct loadedFromDisk signal here; the
            // server-side / natural-spawn gating inside tryEquipSilverArmor still
            // applies (level.isClientSide() check), but disk-loaded entities are
            // not yet excluded. Acceptable for the Phase 1 Fabric shell.
            MonsterArmorHandler.tryEquipSilverArmor(entity, level, false);
            return EventResult.pass();
        });
    }
}
