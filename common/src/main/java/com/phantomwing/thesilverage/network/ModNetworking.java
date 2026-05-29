package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerOverrideState;
import com.phantomwing.thesilverage.platform.CommonConfig;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.server.level.ServerPlayer;

/**
 * Registration + send/receive plumbing for {@link RecipeOverrideSyncPayload}.
 *
 * <p>{@link #register()} is called from {@code TheSilverAgeCommon.init()} (both
 * physical sides). Each loader's <em>client</em> entrypoint additionally calls
 * {@link #registerClientReceiver(Runnable)} so the client-facing receiver — and
 * its loader-specific "apply pack state" callback — is only ever constructed on
 * the physical client.</p>
 */
public final class ModNetworking {
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
        }

        // On join, tell the player whether this server overrides vanilla recipes
        // so their client can match its texture pack to the server. Fires for the
        // local player on the integrated server too, so single-player flows
        // through the same path (no special-casing).
        PlayerEvent.PLAYER_JOIN.register(ModNetworking::onPlayerJoin);
    }

    private static void onPlayerJoin(ServerPlayer player) {
        // Skip clients that can't decode the packet (no mod / older version):
        // sending anyway would be silently dropped, but guarding is explicit and
        // avoids log noise on mixed setups.
        if (!NetworkManager.canPlayerReceive(player, RecipeOverrideSyncPayload.TYPE)) {
            TheSilverAge.LOGGER.info("[recipe-sync] {} can't receive the override packet (no mod / older version); skipping.", player.getGameProfile().getName());
            return;
        }
        boolean value = CommonConfig.overrideVanillaRecipes();
        TheSilverAge.LOGGER.info("[recipe-sync] server sending override_vanilla_recipes={} to {}", value, player.getGameProfile().getName());
        NetworkManager.sendToPlayer(player, new RecipeOverrideSyncPayload(value));
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
                    ServerOverrideState.setFromServer(payload.enabled());
                    TheSilverAge.LOGGER.info("[recipe-sync] client received override_vanilla_recipes={}; syncing texture pack.", payload.enabled());
                    refresh.run();
                }));
    }
}
