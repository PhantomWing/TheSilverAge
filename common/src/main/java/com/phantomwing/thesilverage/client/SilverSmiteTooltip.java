package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.platform.CommonConfig;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Tooltip line for the innate anti-undead damage on silver tools.
 *
 * <p>Blue with no leading space, as vanilla draws a bonus such as "+3 Armor", so
 * the built-in bonus reads like a native item property. Only a weapon's own base
 * lines (" 8 Attack Damage", dark green) are indented.</p>
 *
 * <p>Unlike the damage hook this needs no {@code @ExpectPlatform} bridge:
 * Architectury's {@code ClientTooltipEvent.ITEM} is implemented on both loaders.
 * Called from each loader's CLIENT entrypoint, so this class (and the client-only
 * types it names) never loads on a dedicated server.</p>
 */
public final class SilverSmiteTooltip {
    /** Matches vanilla's attribute-number formatting (e.g. "1.5", "2"). */
    private static final DecimalFormat FORMAT =
            new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private SilverSmiteTooltip() {
    }

    /** Hooks the cross-loader item-tooltip event. Called from both client entrypoints. */
    public static void register() {
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            // Gated on the local config: with the bonus switched off the line
            // would be a lie. (Server-side value is authoritative for damage.)
            if (!CommonConfig.silverSmite()) {
                return;
            }

            float bonus = SilverSmiteHandler.getBonusFor(stack);
            if (bonus <= 0.0f) {
                return;
            }

            lines.add(Component.translatable("tooltip.thesilverage.silver_smite", FORMAT.format(bonus))
                    .withStyle(ChatFormatting.BLUE));
        });
    }
}
