package com.phantomwing.thesilverage.combat;

import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

/**
 * Smite's burst of sparks on a mob silver has hurt, where no player's swing brings it: vanilla's magic-crit animation,
 * sent to every player who sees the mob, as {@code Player#magicCrit} sends it for an enchanted hit.
 */
public final class SilverSparks {
    private SilverSparks() {
    }

    public static void burst(ServerLevel level, Entity hurt) {
        level.getChunkSource().broadcast(hurt, new ClientboundAnimatePacket(hurt, ClientboundAnimatePacket.MAGIC_CRITICAL_HIT));
    }
}
