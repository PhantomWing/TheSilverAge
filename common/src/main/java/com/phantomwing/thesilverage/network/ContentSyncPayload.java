package com.phantomwing.thesilverage.network;

import net.minecraft.network.FriendlyByteBuf;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.resources.ResourceLocation;

/**
 * Server → client packet carrying the server's options that decide what a client shows of the mod's content -
 * {@code enable_silver_oxidation}, {@code enable_silver_arrows} and {@code enable_silver_gear} - sent on join and whenever its config changes, for
 * the creative tabs, the recipe viewers and honeycomb on the client
 * ({@link com.phantomwing.thesilverage.block.SilverOxidation#enabled(net.minecraft.world.level.Level)},
 * {@link com.phantomwing.thesilverage.client.ServerContentSettings}).
 */
public record ContentSyncPayload(boolean oxidation, boolean arrows, boolean gear) {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("content_sync");

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(oxidation);
        buf.writeBoolean(arrows);
        buf.writeBoolean(gear);
    }

    public static ContentSyncPayload read(FriendlyByteBuf buf) {
        return new ContentSyncPayload(buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }
}
