package com.phantomwing.thesilverage.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A player's silver armour hurting an undead mob that hit them ({@code SilverThornsHandler}). Its own trigger, since
 * the armour hurts with Thorns damage, which a Thorns enchantment deals as well.
 */
public class SilverArmorHurtTrigger extends SimpleCriterionTrigger<SilverArmorHurtTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, instance -> true);
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> hurtUndead() {
            return ModCriteriaTriggers.SILVER_ARMOR_HURT.get().createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
