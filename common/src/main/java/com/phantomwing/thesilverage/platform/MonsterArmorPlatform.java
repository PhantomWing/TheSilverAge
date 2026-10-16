package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge for the entity-join hook backing
 * {@link com.phantomwing.thesilverage.armor.MonsterArmorHandler}. Loader-specific because
 * Architectury's {@code EntityEvent.ADD} doesn't expose {@code EntityJoinLevelEvent.loadedFromDisk()}.
 */
public final class MonsterArmorPlatform {
    private MonsterArmorPlatform() {
    }

    /** Subscribes the loader's entity-join event and dispatches to {@code MonsterArmorHandler.tryEquipSilverArmor(...)}. */
    @ExpectPlatform
    public static void registerMobSpawnHandler() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
