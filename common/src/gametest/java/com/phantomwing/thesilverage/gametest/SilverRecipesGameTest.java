package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.platform.Platform;
import net.minecraft.gametest.framework.GameTestHelper;

import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for the vanilla recipes silver takes part in. Listed in {@link GameTests}. */
public final class SilverRecipesGameTest {
    private SilverRecipesGameTest() {
    }

    /**
     * The mod loads after NeoForge, so its Lodestone and Glistering Melon Slice overrides outrank NeoForge's own
     * copies of those recipes. Mods' data is layered in load order, which without the mods.toml's ordering
     * followed a hash of the mod ids.
     */
    public static void silverDataOutranksNeoForge(GameTestHelper helper) {
        List<String> loadOrder = List.copyOf(Platform.getModIds());
        int neoForge = loadOrder.indexOf("neoforge");
        if (neoForge >= 0 && loadOrder.indexOf(TheSilverAge.MOD_ID) < neoForge) {
            fail(helper, "The Silver Age loads before NeoForge, so NeoForge's Lodestone and Glistering Melon Slice recipes replace its overrides");
        }
        helper.succeed();
    }
}
