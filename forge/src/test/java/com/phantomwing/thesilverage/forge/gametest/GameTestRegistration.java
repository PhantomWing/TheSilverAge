package com.phantomwing.thesilverage.forge.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.gametest.GameTests;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraftforge.gametest.GameTestHolder;

import java.util.List;

/**
 * Registers {@link GameTests} the way versions before 1.21.5 do: as one generator, which NeoForge finds
 * through the holder annotation. From 1.21.5 this class registers into the test registries instead.
 * Nothing here changes when a test is added.
 */
@GameTestHolder(TheSilverAge.MOD_ID)
public final class GameTestRegistration {
    private GameTestRegistration() {
    }

    @GameTestGenerator
    public static List<TestFunction> generate() {
        return GameTests.ALL.stream()
                .map(test -> new TestFunction("defaultBatch", test.name(),
                        TheSilverAge.MOD_ID + ":" + test.structure(), test.maxTicks(), 0, true, test.body()))
                .toList();
    }
}
