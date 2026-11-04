package com.phantomwing.thesilverage.network;

import net.minecraft.network.FriendlyByteBuf;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.combat.SilverUndeadSettings;
import net.minecraft.resources.ResourceLocation;

/**
 * Server → client packet carrying the server's {@link SilverUndeadSettings}, sent on join and after each
 * {@code /reload}, for the tooltips. Like {@link RecipeOverrideSyncPayload} it is skipped for a client that doesn't
 * know its id, which keeps its own config.
 */
public record UndeadSettingsSyncPayload(SilverUndeadSettings settings) {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("undead_settings_sync");

    public void write(FriendlyByteBuf buf) {
        settings.write(buf);
    }

    public static UndeadSettingsSyncPayload read(FriendlyByteBuf buf) {
        return new UndeadSettingsSyncPayload(SilverUndeadSettings.read(buf));
    }
}
