package com.phantomwing.thesilverage.platform.fabric;

/** Fabric impl of MonsterArmorPlatform (@ExpectPlatform). */
public final class MonsterArmorPlatformImpl {
    private MonsterArmorPlatformImpl() {
    }

    public static void registerMobSpawnHandler() {
        // No-op: the equip hook is PersistentEntitySectionManagerMixin, which self-registers via the mixin config.
    }
}
