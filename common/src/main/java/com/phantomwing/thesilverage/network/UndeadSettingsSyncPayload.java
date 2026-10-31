package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.combat.SilverUndeadSettings;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Server → client packet carrying the server's {@link SilverUndeadSettings}, sent on join and after each
 * {@code /reload}, for the tooltips. Like {@link RecipeOverrideSyncPayload} it is skipped for a client that doesn't
 * know its id, which keeps its own config.
 */
public record UndeadSettingsSyncPayload(SilverUndeadSettings settings) implements CustomPacketPayload {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("undead_settings_sync");
    public static final Type<UndeadSettingsSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, UndeadSettingsSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    SilverUndeadSettings.STREAM_CODEC, UndeadSettingsSyncPayload::settings,
                    UndeadSettingsSyncPayload::new);

    @Override
    public Type<UndeadSettingsSyncPayload> type() {
        return TYPE;
    }
}
