package com.phantomwing.thesilverage.gametest;

import com.mojang.authlib.GameProfile;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

import com.phantomwing.thesilverage.forge.Configuration;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;

/**
 * The calls a test makes that each modloader answers its own way, each behind a signature both share. NeoForge's
 * and Fabric's test source sets each have this class, so a test body compiles on both unchanged.
 */
public final class TestLoader {
    private TestLoader() {
    }

    /**
     * Sets a boolean option by its id, in memory only, and gives back what it was. Each option's value is the
     * {@link Configuration} field named as its id in capitals. Forge 47's {@code set} writes the file, which its
     * watcher then holds, and a test setting an option in the meantime fails to write it ("being used by another
     * process"), so this sets the value the option keeps in memory instead.
     */
    public static boolean setBooleanSetting(String id, boolean value) {
        try {
            ForgeConfigSpec.BooleanValue option = (ForgeConfigSpec.BooleanValue) Configuration.class
                    .getField(id.toUpperCase(Locale.ROOT)).get(null);
            boolean was = option.get();
            java.lang.reflect.Field cached = ForgeConfigSpec.ConfigValue.class.getDeclaredField("cachedValue");
            cached.setAccessible(true);
            cached.set(option, value);
            return was;
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("No option " + id, e);
        }
    }

    /**
     * A player in the test's level, who clicks blocks as a real one does. Forge 47's {@code PlayerList.placeNewPlayer}
     * reaches for the mock player's connection's channel, which it hasn't got, so it is a {@link FakePlayer}, with a
     * profile of its own each time so tests running side by side don't share an inventory.
     */
    public static ServerPlayer mockServerPlayer(GameTestHelper helper) {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "test-mock-player"));
        player.setGameMode(GameType.SURVIVAL);
        return player;
    }
}
