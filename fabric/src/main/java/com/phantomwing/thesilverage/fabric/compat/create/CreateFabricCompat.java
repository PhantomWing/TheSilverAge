package com.phantomwing.thesilverage.fabric.compat.create;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.platform.CommonPlatform;

/**
 * Future-proof extension point for Create integration on Fabric.
 *
 * <p><b>Why this is a stub:</b> Create has no Fabric build for Minecraft 1.21.1.
 * Neither the official {@code create} mod nor the community {@code create-fabric}
 * port publishes a 1.21.1 Fabric artifact (the Fabric port tops out at 1.20.1 as
 * of this writing). There is therefore nothing to compile or run Fabric Create
 * compat against — this is an external blocker, not deferred work.</p>
 *
 * <p><b>Recipe parity is already future-proofed without code here.</b> The
 * NeoForge Create compat is <em>datagen-only</em> (the
 * {@code neoforge/.../compat/create/*RecipeGen} classes generate filling /
 * pressing / deploying recipe JSON). Those recipes live in the shared generated
 * tree and are gated by {@code fabric:all_mods_loaded ["create"]} (emitted
 * alongside {@code neoforge:conditions} by the Phase&nbsp;4a datagen
 * post-processor). The moment a Create build for Fabric 1.21.1 exists and
 * provides the referenced {@code create:*} items and recipe serializers, those
 * shared recipes activate on Fabric automatically — <em>no change to this file
 * required.</em></p>
 *
 * <p><b>What this hook is for:</b> if Create-for-Fabric-1.21.1 ever ships AND
 * future <em>runtime</em> (not datagen) Create integration is wanted, this
 * mod-loaded-guarded entry point is the designated home for it. Today it
 * intentionally no-ops; the guard is wired so the pattern is live and a future
 * contributor has an obvious, correct place to extend (mirroring how the
 * NeoForge side keys off {@link ModIds#CREATE}).</p>
 */
public final class CreateFabricCompat {
    private CreateFabricCompat() {
    }

    /**
     * Invoked (guarded) from the Fabric entrypoint. No-ops unless Create is
     * present — which it never is on Fabric 1.21.1 today. Kept as the live
     * extension point for any future runtime Create-Fabric integration.
     */
    public static void init() {
        if (!CommonPlatform.isModLoaded(ModIds.CREATE)) {
            return;
        }
        // Create is present (only reachable if a future Create-for-Fabric-1.21.1
        // ships). Datagen-driven recipe parity needs nothing here; add any future
        // runtime Create-Fabric integration below.
        TheSilverAge.LOGGER.info(
                "Create detected on Fabric — Silver Create recipes are active via shared "
                        + "condition-gated data; no runtime integration is currently implemented.");
    }
}
