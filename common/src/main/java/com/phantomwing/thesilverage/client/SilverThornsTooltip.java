package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.combat.SilverThornsHandler;
import com.phantomwing.thesilverage.combat.SilverUndeadSettings;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * Tooltip line for silver armour's burn on undead attackers, "Thorns against Undead" - the defensive twin of
 * {@link SilverSmiteTooltip}. No number, as vanilla shows none for Thorns: each piece's share of a chance would read
 * as a bonus that adds up.
 *
 * <p>Same styling as the smite line: blue with no leading space, as vanilla draws a bonus such as "+3 Armor", so the
 * built-in bonus reads like a native item property rather than an enchantment, which is grey.</p>
 *
 * <p>Like the smite tooltip this needs no {@code @ExpectPlatform} bridge: Architectury's
 * {@code ClientTooltipEvent.ITEM} is implemented on both loaders. Called from each loader's CLIENT entrypoint, so this
 * class (and the client-only types it names) never loads on a dedicated server.</p>
 */
public final class SilverThornsTooltip {
    private SilverThornsTooltip() {
    }

    /** Hooks the cross-loader item-tooltip event. Called from both client entrypoints. */
    public static void register() {
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            // The server's settings where it sent them: with the burn off there, the line would lie.
            SilverUndeadSettings settings = ServerUndeadSettings.current();
            if (!settings.thorns() || SilverThornsHandler.chanceFor(stack, settings) <= 0.0F) {
                return;
            }

            lines.add(Component.translatable("tooltip.thesilverage.silver_thorns").withStyle(ChatFormatting.BLUE));
        });
    }
}
