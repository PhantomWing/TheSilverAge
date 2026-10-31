package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Server → client packet carrying the server's {@code enable_silver_oxidation}, sent on join and whenever its config
 * changes, for the creative tab, EMI and honeycomb on the client
 * ({@link com.phantomwing.thesilverage.block.SilverOxidation#enabled(net.minecraft.world.level.Level)}).
 */
public record OxidationSyncPayload(boolean enabled) implements CustomPacketPayload {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("oxidation_sync");
    public static final Type<OxidationSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, OxidationSyncPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, OxidationSyncPayload::enabled, OxidationSyncPayload::new);

    @Override
    public Type<OxidationSyncPayload> type() {
        return TYPE;
    }
}
