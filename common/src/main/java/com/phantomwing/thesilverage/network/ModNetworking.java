package com.phantomwing.thesilverage.network;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.SilverOxidation;
import com.phantomwing.thesilverage.client.ServerContentSettings;
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
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.CreativeModeTabs;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Registration + send/receive plumbing for {@link RecipeOverrideSyncPayload}, {@link UndeadSettingsSyncPayload} and
 * {@link ContentSyncPayload}, sent on join and after each datapack reload, and the last two whenever the config
 * changes ({@link #configChanged()}).
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
    /** Set when the config changes, which the undead settings and oxidation follow at once; the next server tick tells every player. */
    private static volatile boolean settingsChanged;
    /**
     * The recipe overrides the loaded recipes were made with, at the last datapack reload. The config can change
     * before the next one, and the textures sent on join then matched recipes that weren't loaded.
     */
    private static volatile List<String> overridesInForce;

    private ModNetworking() {
    }

    /** Server-side / common registration: the server only sends, so these packets need no type registered here. */
    public static void register() {
        // On join, tell the player whether this server overrides vanilla recipes
        // so their client can match its texture pack to the server. Fires for the
        // local player on the integrated server too, so single-player flows
        // through the same path (no special-casing).
        PlayerEvent.PLAYER_JOIN.register(ModNetworking::onPlayerJoin);

        // And again after /reload, or an option changed in a world: the recipes follow the config at each datapack
        // reload, and the textures sent on join stayed until the player rejoined.
        ReloadListenerRegistry.register(PackType.SERVER_DATA, (ResourceManagerReloadListener) manager -> {
            overridesInForce = configuredOverrides();
            overridesReloaded = true;
        });
        TickEvent.SERVER_POST.register(server -> {
            if (overridesReloaded) {
                overridesReloaded = false;
                settingsChanged = false;
                server.getPlayerList().getPlayers().forEach(ModNetworking::onPlayerJoin);
            } else if (settingsChanged) {
                settingsChanged = false;
                server.getPlayerList().getPlayers().forEach(ModNetworking::sendSettings);
            }
        });
    }

    /**
     * Tells every player the undead settings and oxidation again on the next server tick. Called by each loader when
     * its config changes: the tooltips kept the numbers sent on join until a {@code /reload} or a rejoin.
     */
    public static void configChanged() {
        settingsChanged = true;
    }

    private static List<String> configuredOverrides() {
        return Arrays.stream(RecipeOverride.values())
                .filter(CommonConfig::recipeOverride)
                .map(RecipeOverride::settingId)
                .toList();
    }

    private static void onPlayerJoin(ServerPlayer player) {
        sendSettings(player);
        if (!canReceive(player, RecipeOverrideSyncPayload.ID)) return;
        List<String> enabled = overridesInForce;
        send(player, RecipeOverrideSyncPayload.ID, new RecipeOverrideSyncPayload(enabled != null ? enabled : configuredOverrides())::write);
    }

    private static void sendSettings(ServerPlayer player) {
        if (canReceive(player, UndeadSettingsSyncPayload.ID)) {
            send(player, UndeadSettingsSyncPayload.ID, new UndeadSettingsSyncPayload(SilverUndeadSettings.fromConfig())::write);
        }
        if (canReceive(player, ContentSyncPayload.ID)) {
            send(player, ContentSyncPayload.ID, new ContentSyncPayload(SilverOxidation.enabled(), CommonConfig.silverArrows(), CommonConfig.silverGear())::write);
        }
    }

    /**
     * Whether {@code player}'s client can decode a packet of {@code id}: on Fabric, whether it has the mod (a vanilla or
     * older client is skipped rather than sent what it would drop). On Forge always: Forge refuses a client without
     * the mod at login (its default {@code displayTest}, and the registries it syncs), and Architectury 9's Forge
     * {@code canPlayerReceive} never holds on join. It is empty until the client answers {@code architectury:sync_ids},
     * which is sent from the same login event as the join, and even then holds the client's C2S ids, not the S2C ones
     * these packets are, so the join sync and every resend after it reached no Forge client.
     */
    private static boolean canReceive(ServerPlayer player, ResourceLocation id) {
        return Platform.isForge() || NetworkManager.canPlayerReceive(player, id);
    }

    private static void send(ServerPlayer player, ResourceLocation id, Consumer<FriendlyByteBuf> writer) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        writer.accept(buf);
        NetworkManager.sendToPlayer(player, id, buf);
    }

    /**
     * Client-only: register the S2C receiver. The {@code refresh} runnable is the
     * loader-specific "re-evaluate and apply the texture pack" step (Fabric:
     * toggle the pack in the repository; NeoForge: reload resource packs so the
     * pack-finder re-reads {@link ServerOverrideState}). Must be called from a
     * client entrypoint — never on a dedicated server.
     */
    public static void registerClientReceiver(Runnable refresh) {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RecipeOverrideSyncPayload.ID, (buf, context) -> {
            RecipeOverrideSyncPayload payload = RecipeOverrideSyncPayload.read(buf);
            context.queue(() -> {
                    ServerOverrideState.setFromServer(Set.copyOf(payload.enabledSettingIds()));
                    TheSilverAge.LOGGER.debug("Server recipe overrides on: {}; syncing their texture packs.", payload.enabledSettingIds());
                    refresh.run();
            });
        });
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, UndeadSettingsSyncPayload.ID, (buf, context) -> {
            UndeadSettingsSyncPayload payload = UndeadSettingsSyncPayload.read(buf);
            context.queue(() -> ServerUndeadSettings.setFromServer(payload.settings()));
        });
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ContentSyncPayload.ID, (buf, context) -> {
            ContentSyncPayload payload = ContentSyncPayload.read(buf);
            context.queue(() -> setContentFromServer(payload.oxidation(), payload.arrows(), payload.gear()));
        });
    }

    /**
     * The server's {@code enable_silver_oxidation}, {@code enable_silver_arrows} and {@code enable_silver_gear} on the client, or {@code null}s on
     * disconnect for the client's own. Where that changes what the creative tabs hide, they are built again the next
     * time one opens.
     */
    public static void setContentFromServer(Boolean oxidation, Boolean arrows, Boolean gear) {
        boolean oxidationBefore = SilverOxidation.enabledOnClient();
        boolean arrowsBefore = ServerContentSettings.arrows();
        boolean gearBefore = ServerContentSettings.gear();
        if (oxidation == null) {
            SilverOxidation.clearFromServer();
        } else {
            SilverOxidation.setFromServer(oxidation);
        }
        ServerContentSettings.setFromServer(arrows, gear);
        if (SilverOxidation.enabledOnClient() != oxidationBefore || ServerContentSettings.arrows() != arrowsBefore
                || ServerContentSettings.gear() != gearBefore) {
            CreativeModeTabs.CACHED_PARAMETERS = null;
        }
    }
}
