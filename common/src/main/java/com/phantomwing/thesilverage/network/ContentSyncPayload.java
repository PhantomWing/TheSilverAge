package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Server → client packet carrying the server's options that decide what a client shows of the mod's content -
 * {@code enable_silver_oxidation} and {@code enable_silver_arrows} - sent on join and whenever its config changes, for
 * the creative tabs, the recipe viewers and honeycomb on the client
 * ({@link com.phantomwing.thesilverage.block.SilverOxidation#enabled(net.minecraft.world.level.Level)},
 * {@link com.phantomwing.thesilverage.client.ServerContentSettings}).
 */
public record ContentSyncPayload(boolean oxidation, boolean arrows) implements CustomPacketPayload {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("content_sync");
    public static final Type<ContentSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ContentSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ContentSyncPayload::oxidation,
            ByteBufCodecs.BOOL, ContentSyncPayload::arrows,
            ContentSyncPayload::new);

    @Override
    public Type<ContentSyncPayload> type() {
        return TYPE;
    }
}
