package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.client.ServerUndeadSettings;
import com.phantomwing.thesilverage.combat.SilverUndeadSettings;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.utils.Env;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Registration + send/receive plumbing for {@link RecipeOverrideSyncPayload} and {@link UndeadSettingsSyncPayload},
 * both sent on join and after each datapack reload.
 *
 * <p>{@link #register()} is called from {@code TheSilverAgeCommon.init()} (both
 * physical sides). Each loader's <em>client</em> entrypoint additionally calls
 * {@link #registerClientReceiver(Runnable)} so the client-facing receiver — and
 * its loader-specific "apply pack state" callback — is only ever constructed on
 * the physical client.</p>
 */
public final class ModNetworking {
    /** Set by a datapack reload, which is when the overridden recipes change; the next server tick tells every player. */
    private static volatile boolean overridesReloaded;

    private ModNetworking() {
    }

    /**
     * Server-side / common registration. The physical dedicated server registers
     * the S2C payload type so it can encode and send; the physical client must
     * NOT also register it here — it registers the type via
     * {@link #registerClientReceiver(Runnable)} instead. Architectury routes both
     * paths to {@code PayloadTypeRegistry.playS2C().register(...)} on Fabric,
     * which throws if the same id is registered twice on one JVM, so the
     * {@link Env} gate is mandatory rather than cosmetic.
     */
    public static void register() {
        if (Platform.getEnvironment() == Env.SERVER) {
            NetworkManager.registerS2CPayloadType(
                    RecipeOverrideSyncPayload.TYPE, RecipeOverrideSyncPayload.STREAM_CODEC);
            NetworkManager.registerS2CPayloadType(
                    UndeadSettingsSyncPayload.TYPE, UndeadSettingsSyncPayload.STREAM_CODEC);
        }

        // On join, tell the player whether this server overrides vanilla recipes
        // so their client can match its texture pack to the server. Fires for the
        // local player on the integrated server too, so single-player flows
        // through the same path (no special-casing).
        PlayerEvent.PLAYER_JOIN.register(ModNetworking::onPlayerJoin);

        // And again after /reload, or an option changed in a world: the recipes follow the config at each datapack
        // reload, and the textures sent on join stayed until the player rejoined.
        ReloadListenerRegistry.register(PackType.SERVER_DATA,
                (ResourceManagerReloadListener) manager -> overridesReloaded = true);
        TickEvent.SERVER_POST.register(server -> {
            if (overridesReloaded) {
                overridesReloaded = false;
                server.getPlayerList().getPlayers().forEach(ModNetworking::onPlayerJoin);
            }
        });
    }

    private static void onPlayerJoin(ServerPlayer player) {
        if (NetworkManager.canPlayerReceive(player, UndeadSettingsSyncPayload.TYPE)) {
            NetworkManager.sendToPlayer(player, new UndeadSettingsSyncPayload(SilverUndeadSettings.fromConfig()));
        }
        // Skip clients that can't decode the packet (no mod / older version):
        // sending anyway would be silently dropped, but guarding is explicit and
        // avoids log noise on mixed setups.
        if (!NetworkManager.canPlayerReceive(player, RecipeOverrideSyncPayload.TYPE)) return;
        List<String> enabled = Arrays.stream(RecipeOverride.values())
                .filter(CommonConfig::recipeOverride)
                .map(RecipeOverride::settingId)
                .toList();
        NetworkManager.sendToPlayer(player, new RecipeOverrideSyncPayload(enabled));
    }

    /**
     * Client-only: register the S2C receiver. The {@code refresh} runnable is the
     * loader-specific "re-evaluate and apply the texture pack" step (Fabric:
     * toggle the pack in the repository; NeoForge: reload resource packs so the
     * pack-finder re-reads {@link ServerOverrideState}). Must be called from a
     * client entrypoint — never on a dedicated server.
     */
    public static void registerClientReceiver(Runnable refresh) {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C,
                RecipeOverrideSyncPayload.TYPE, RecipeOverrideSyncPayload.STREAM_CODEC,
                (payload, context) -> context.queue(() -> {
                    ServerOverrideState.setFromServer(Set.copyOf(payload.enabledSettingIds()));
                    TheSilverAge.LOGGER.debug("Server recipe overrides on: {}; syncing their texture packs.", payload.enabledSettingIds());
                    refresh.run();
                }));
        NetworkManager.registerReceiver(NetworkManager.Side.S2C,
                UndeadSettingsSyncPayload.TYPE, UndeadSettingsSyncPayload.STREAM_CODEC,
                (payload, context) -> context.queue(() -> ServerUndeadSettings.setFromServer(payload.settings())));
    }
}
