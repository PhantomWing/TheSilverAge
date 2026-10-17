package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
public record RecipeOverrideSyncPayload(List<String> enabledSettingIds) implements CustomPacketPayload {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("recipe_overrides_sync");
    public static final Type<RecipeOverrideSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeOverrideSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), RecipeOverrideSyncPayload::enabledSettingIds,
                    RecipeOverrideSyncPayload::new);

    @Override
    public Type<RecipeOverrideSyncPayload> type() {
        return TYPE;
    }
}
