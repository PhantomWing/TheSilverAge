package com.phantomwing.thesilverage.neoforge.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.platform.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * Automated regression tests for The Silver Age, on Minecraft 1.21.5+'s data-driven GameTest framework, in a
 * dedicated {@code gametest} source set that only the {@code :neoforge:runGameTest} run loads (see
 * neoforge/build.gradle). Each test is a {@code TEST_FUNCTION} registered below, bound to a runnable test by a
 * {@code test_instance} JSON under {@code src/gametest/resources/data/thesilverage/test_instance/}, which also
 * names the arena structure it runs in.
 */
@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public final class TheSilverAgeGameTests {
    private TheSilverAgeGameTests() {
    }

    @SubscribeEvent
    static void registerTestFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> {
            helper.register(id("silver_data_outranks_neoforge"),
                    (Consumer<GameTestHelper>) TheSilverAgeGameTests::silverDataOutranksNeoForge);
        });
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, path);
    }

    /**
     * The mod loads after NeoForge, so its Lodestone and Glistering Melon Slice overrides outrank NeoForge's
     * own copies of those recipes. Mods' data is layered in load order, which without the mods.toml's
     * ordering followed a hash of the mod ids.
     */
    private static void silverDataOutranksNeoForge(GameTestHelper helper) {
        List<String> loadOrder = List.copyOf(Platform.getModIds());
        int neoForge = loadOrder.indexOf("neoforge");
        if (neoForge >= 0 && loadOrder.indexOf(TheSilverAge.MOD_ID) < neoForge) {
            helper.fail(Component.literal("The Silver Age loads before NeoForge, so NeoForge's Lodestone and Glistering Melon Slice recipes replace its overrides"));
        }
        helper.succeed();
    }
}
