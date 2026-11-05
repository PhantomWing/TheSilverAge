package com.phantomwing.thesilverage.fabric.compat.create;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.platform.CommonPlatform;

/**
 * The home of any runtime Create integration on Fabric, which today has none.
 *
 * <p>This line builds against no Create for Fabric: its Create compat is Forge's datagen alone (the
 * {@code forge/.../compat/create/*RecipeGen} classes), whose recipes carry {@code fabric:all_mods_loaded ["create"]}
 * beside Forge's {@code conditions}, so Fabric loads them only where Create is. Whether Create Fabric's 1.20.1
 * build reads them is untested.</p>
 */
public final class CreateFabricCompat {
    private CreateFabricCompat() {
    }

    /** Called from the Fabric entrypoint. Only logs, where Create is loaded. */
    public static void init() {
        if (!CommonPlatform.isModLoaded(ModIds.CREATE)) {
            return;
        }
        TheSilverAge.LOGGER.info(
                "Create detected on Fabric — Silver Create recipes are active via shared "
                        + "condition-gated data; no runtime integration is currently implemented.");
    }
}
