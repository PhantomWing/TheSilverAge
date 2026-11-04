package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Which mobs silver harms: the undead, as Smite asks it on 1.20.1 ({@link MobType#UNDEAD}), and whatever
 * {@code thesilverage:sensitive_to_silver} holds, which a pack adds to. 1.20.1 has no {@code minecraft:sensitive_to_smite},
 * so the tag alone left another mod's undead untouched by every silver effect. Every check of the mod asks here, never
 * the tag. Whether an effect is on at all is its own option's, which {@code silver_affects_undead} is folded into.
 */
public final class SilverSensitivity {
    /**
     * The types an entity has shown to be undead, for the checks that have a type and no entity: a spawn's. Every mob
     * passes {@link SilverUndeadGoals#addTo} as it joins a level, so a type is known once one of it has loaded.
     */
    private static final Set<EntityType<?>> UNDEAD_TYPES = ConcurrentHashMap.newKeySet();

    private SilverSensitivity() {
    }

    public static boolean isSensitive(Entity entity) {
        if (entity instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD) {
            UNDEAD_TYPES.add(entity.getType());
            return true;
        }
        return entity.getType().is(ModTags.EntityTypes.SENSITIVE_TO_SILVER);
    }

    /** For a type alone: in the tag, or of a type an entity has shown to be undead. */
    public static boolean isSensitive(EntityType<?> type) {
        return type.is(ModTags.EntityTypes.SENSITIVE_TO_SILVER) || UNDEAD_TYPES.contains(type);
    }
}
