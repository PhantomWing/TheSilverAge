package com.phantomwing.thesilverage.gametest;

import net.minecraft.gametest.framework.GameTestHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Every game test on this line. A test is its body and its entry here, and both read the same on every
 * line: how the list is registered changed at 1.21.5, which belongs to {@code GameTestRegistration}
 * alone, so porting a test never touches a registration class or a JSON.
 */
public final class GameTests {
    public static final List<Test> ALL = List.of(
            test("silver_data_outranks_neoforge", SilverRecipesGameTest::silverDataOutranksNeoForge));

    private GameTests() {
    }

    /** A required test in the mod's arena, with vanilla's default of 100 ticks. */
    private static Test test(String name, Consumer<GameTestHelper> body) {
        return new Test(name, body, "silver_test_arena", 100);
    }

    /** One test: its id in the mod's namespace, its body, the structure it runs in and its time limit. */
    public record Test(String name, Consumer<GameTestHelper> body, String structure, int maxTicks) {
        public Test maxTicks(int ticks) {
            return new Test(name, body, structure, ticks);
        }
    }
}
