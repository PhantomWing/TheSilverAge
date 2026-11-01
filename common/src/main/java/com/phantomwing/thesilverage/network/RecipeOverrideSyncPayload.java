package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Server → client packet carrying the server's {@code override_vanilla_recipes}
 * config value, sent once on player join.
 *
 * <p>The recipe overrides themselves are already server-driven (the
 * {@code config_boolean} resource condition gates them at datapack-load time on
 * the server). The matching <em>texture</em> overrides, however, live in a
 * client resource pack toggled by each client's own config — so without this
 * sync a player whose local config says {@code true} would keep seeing the
 * silver brewing-stand / comparator / repeater textures even on a server that
 * has the recipe overrides switched off. This packet lets the client match the
 * texture pack to the server it is actually connected to; the value is
 * connection-scoped and never written to the client's own config file.</p>
 */
public record RecipeOverrideSyncPayload(boolean enabled) implements CustomPacketPayload {
    public static final Identifier ID = TheSilverAge.resourceLocation("recipe_override_sync");
    public static final Type<RecipeOverrideSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeOverrideSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, RecipeOverrideSyncPayload::enabled,
                    RecipeOverrideSyncPayload::new);

    @Override
    public Type<RecipeOverrideSyncPayload> type() {
        return TYPE;
    }
}
