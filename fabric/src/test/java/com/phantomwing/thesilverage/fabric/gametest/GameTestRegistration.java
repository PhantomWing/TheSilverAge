package com.phantomwing.thesilverage.fabric.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.gametest.GameTests;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;

/**
 * Registers {@link GameTests} the way 1.21.5 on does: each body into the static {@code test_function} registry, and
 * each test into {@code test_instance} with the structure and time limit its entry gives. Before 1.21.5 this class is a
 * {@code @GameTestGenerator} instead. Nothing here changes when a test is added.
 *
 * <p>Fabric API's own registration from 1.21.5 takes one annotated method per test, so this registers as vanilla does,
 * from the test source set's own mod's {@code main} entrypoint: the functions at once, the instances as the server sets
 * up its datapack registries, before they load and freeze. Fabric makes the entrypoint, so it keeps a public
 * constructor.</p>
 */
public final class GameTestRegistration implements ModInitializer {
    @Override
    public void onInitialize() {
        GameTests.ALL.forEach(test -> Registry.register(BuiltInRegistries.TEST_FUNCTION, id(test.name()), test.body()));

        DynamicRegistrySetupCallback.EVENT.register(registries -> {
            Optional<Registry<GameTestInstance>> instances = registries.getOptional(Registries.TEST_INSTANCE);
            Optional<Registry<TestEnvironmentDefinition>> environments = registries.getOptional(Registries.TEST_ENVIRONMENT);
            if (instances.isEmpty() || environments.isEmpty()) {
                return;
            }
            Holder<TestEnvironmentDefinition> environment = Registry.registerForHolder(environments.get(), id("default"),
                    new TestEnvironmentDefinition.AllOf(List.of()));
            for (GameTests.Test test : GameTests.ALL) {
                Registry.register(instances.get(), id(test.name()), new FunctionGameTestInstance(
                        ResourceKey.create(Registries.TEST_FUNCTION, id(test.name())),
                        new TestData<>(environment, id(test.structure()), test.maxTicks(), 0, true)));
            }
        });
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, path);
    }
}
