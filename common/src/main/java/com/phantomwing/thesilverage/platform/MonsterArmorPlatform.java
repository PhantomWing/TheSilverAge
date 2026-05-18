package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge for the entity-join hook backing
 * {@link com.phantomwing.thesilverage.armor.MonsterArmorHandler}.
 *
 * <p>NeoForge has no Architectury-equivalent for {@code EntityJoinLevelEvent}'s
 * {@code loadedFromDisk()} flag (Architectury's {@code EntityEvent.ADD} only
 * exposes the entity + level). To preserve the exact original behaviour on
 * NeoForge, the subscription itself is loader-specific.</p>
 */
public final class MonsterArmorPlatform {
    private MonsterArmorPlatform() {
    }

    /**
     * Subscribes the loader's entity-join event and dispatches to
     * {@code MonsterArmorHandler.tryEquipSilverArmor(...)}.
     *
     * <p>NeoForge: subscribes {@code EntityJoinLevelEvent} and forwards its real
     * {@code loadedFromDisk()} value (full behavioural parity).<br>
     * Fabric: TODO(phase 4) — Architectury {@code EntityEvent.ADD} shell, passes
     * {@code loadedFromDisk = false} (Fabric feature parity is a later phase).</p>
     */
    @ExpectPlatform
    public static void registerMobSpawnHandler() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
