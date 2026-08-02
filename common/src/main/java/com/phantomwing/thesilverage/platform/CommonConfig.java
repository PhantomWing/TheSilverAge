package com.phantomwing.thesilverage.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * {@code @ExpectPlatform} bridge exposing the config booleans the common loot
 * code needs.
 *
 * <p>The config system itself stays loader-specific in Phase 2 (NeoForge
 * {@code ModConfigSpec}; full Fabric config is Phase 5). Common loot
 * ({@link com.phantomwing.thesilverage.loot.SilverLootSpec} consumers) reaches
 * the gate values through this bridge — mirroring Phase 1's
 * {@link CommonPlatform#isModLoaded(String)} pattern.</p>
 *
 * <p>Implemented per loader at
 * {@code com.phantomwing.thesilverage.platform.<loader>.CommonConfigImpl}
 * (Architectury rewrites the call sites at build time).</p>
 */
public final class CommonConfig {
    private CommonConfig() {
    }

    /**
     * Gate for the structure-loot injections (ADD / REPLACE entries). NeoForge:
     * {@code Configuration.GENERATE_STRUCTURE_LOOT.get()}. Fabric: {@code true}
     * until the Phase 5 Fabric config exists.
     */
    @ExpectPlatform
    public static boolean generateStructureLoot() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for the silverfish silver drop. NeoForge:
     * {@code Configuration.SILVERFISH_DROP_SILVER.get()}. Fabric: {@code true}
     * until the Phase 5 Fabric config exists.
     */
    @ExpectPlatform
    public static boolean silverfishDropSilver() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for the vanilla-recipe overrides (lodestone / brewing stand /
     * comparator / repeater silver crafts, with the original recipe kept as a
     * {@code _fallback} when this is off). NeoForge:
     * {@code Configuration.OVERRIDE_VANILLA_RECIPES.get()}. Fabric: {@code true}
     * until the Phase 5 Fabric config exists — matches the NeoForge default
     * ({@code OVERRIDE_VANILLA_RECIPES = true}).
     *
     * <p>Read at datapack-load time by the Fabric {@code thesilverage:config_boolean}
     * resource condition (the parity twin of the NeoForge
     * {@code ConfigBooleanCondition}), so the same conditional/fallback recipe
     * pair resolves identically on both loaders.</p>
     */
    @ExpectPlatform
    public static boolean overrideVanillaRecipes() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }

    /**
     * Gate for the innate anti-undead damage on silver tools
     * ({@link com.phantomwing.thesilverage.combat.SilverSmiteHandler}). NeoForge:
     * {@code Configuration.SILVER_SMITE.get()}. Fabric: {@code true} until the
     * Phase 5 Fabric config exists (matches the NeoForge default).
     */
    @ExpectPlatform
    public static boolean silverSmite() {
        throw new AssertionError("@ExpectPlatform stub – replaced per loader at build time");
    }
}
