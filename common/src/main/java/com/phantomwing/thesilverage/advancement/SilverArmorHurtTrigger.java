package com.phantomwing.thesilverage.advancement;

import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * A player's silver armour hurting an undead mob that hit them ({@code SilverThornsHandler}). Its own trigger, since
 * the armour hurts with Thorns damage, which a Thorns enchantment deals as well.
 */
public class SilverArmorHurtTrigger extends SimpleCriterionTrigger<SilverArmorHurtTrigger.TriggerInstance> {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("silver_armor_hurt");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate player, @NotNull DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        trigger(player, instance -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }

        public static TriggerInstance hurtUndead() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }
    }
}
