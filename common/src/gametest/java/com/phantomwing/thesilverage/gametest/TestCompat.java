package com.phantomwing.thesilverage.gametest;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

/**
 * The calls a test makes whose shape differs between Minecraft versions, each behind a signature every
 * line shares. This is the one test file that differs from line to line, so the bodies don't have to:
 * when a port turns up another such call, it goes here rather than into a body.
 */
public final class TestCompat {
    private TestCompat() {
    }

    /** Fails the test. {@code GameTestHelper.fail} takes only a Component from 1.21.5 to 1.21.8. */
    public static void fail(GameTestHelper helper, String message) {
        helper.fail(message);
    }

    /** Kills an entity as {@code /kill} does, so it drops its loot. It takes the level from 1.21.2. */
    public static void kill(Entity entity) {
        entity.kill();
    }

    /** What a crafting grid makes, or an empty stack. Recipe lookups and assembly changed shape in 1.21.2 and 26.1. */
    public static ItemStack craft(ServerLevel level, CraftingInput input) {
        return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level)
                .map(recipe -> recipe.value().assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    /** A vanilla loot table by its path, e.g. {@code chests/bastion_other}. */
    public static LootTable lootTable(ServerLevel level, String path) {
        return level.getServer().reloadableRegistries()
                .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(path)));
    }

    /** A player in the test's level who can click blocks as a real one does, through its game mode. */
    public static ServerPlayer serverPlayer(GameTestHelper helper) {
        return helper.makeMockServerPlayerInLevel();
    }

    /** Takes a player made by {@link #serverPlayer} out of the game again, so no test's mob goes after it. */
    public static void removePlayer(ServerPlayer player) {
        player.server.getPlayerList().remove(player);
    }

    /** A wind charge bursting at {@code at}, as a thrown one does: it triggers blocks and breaks none. */
    public static void windChargeBurst(ServerLevel level, Vec3 at) {
        level.explode(null, null, AbstractWindCharge.EXPLOSION_DAMAGE_CALCULATOR, at.x, at.y, at.z, 1.2f, false,
                Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.WIND_CHARGE_BURST);
    }
}
