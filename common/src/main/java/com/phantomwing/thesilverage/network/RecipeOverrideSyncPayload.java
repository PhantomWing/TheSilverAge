package com.phantomwing.thesilverage.network;

import net.minecraft.network.FriendlyByteBuf;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Server → client packet carrying the setting ids of the vanilla recipe overrides the server has
 * on (master switch and own option both on), sent once on player join.
 *
 * <p>The recipe overrides themselves are server-driven (the {@code config_boolean} condition gates
 * them at datapack-load time on the server). Their <em>texture</em> packs, however, are client
 * resource packs, so without this sync a player whose own config has an override on would keep
 * seeing its silver textures on a server that has it off. The value is connection-scoped and never
 * written to the client's own config file.</p>
 *
 * <p>Named apart from the single-boolean packet of 1.3.x, so neither side sends the other a
 * packet it can't read: a client that doesn't know this id is skipped and keeps its own config.</p>
 */
public record RecipeOverrideSyncPayload(List<String> enabledSettingIds) {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("recipe_overrides_sync");

    public void write(FriendlyByteBuf buf) {
        buf.writeCollection(enabledSettingIds, FriendlyByteBuf::writeUtf);
    }

    public static RecipeOverrideSyncPayload read(FriendlyByteBuf buf) {
        return new RecipeOverrideSyncPayload(buf.readList(FriendlyByteBuf::readUtf));
    }
}
