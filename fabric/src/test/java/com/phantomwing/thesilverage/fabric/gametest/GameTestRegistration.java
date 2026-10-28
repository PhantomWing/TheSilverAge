package com.phantomwing.thesilverage.fabric.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.gametest.GameTests;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;

import java.util.List;

/**
 * Registers {@link GameTests} the way versions before 1.21.5 do: as one generator, which Fabric API finds through
 * the {@code fabric-gametest} entrypoint of the test source set's own mod. Fabric makes the entrypoint, so it keeps
 * a public constructor. Nothing here changes when a test is added.
 */
public final class GameTestRegistration {
    @GameTestGenerator
    public List<TestFunction> generate() {
        return GameTests.ALL.stream()
                .map(test -> new TestFunction("defaultBatch", test.name(),
                        TheSilverAge.MOD_ID + ":" + test.structure(), test.maxTicks(), 0, true, test.body()))
                .toList();
    }
}
