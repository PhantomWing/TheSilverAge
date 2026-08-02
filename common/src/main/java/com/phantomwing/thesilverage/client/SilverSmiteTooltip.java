package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.platform.CommonConfig;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Tooltip line for the innate anti-undead damage on silver tools.
 *
 * <p>Rendered as a single grey line, the same styling vanilla uses for
 * enchantment names, so the built-in bonus reads like a native item property.</p>
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

            // Leading space + blue, matching how vanilla renders an item's own
            // attribute lines (e.g. armour's " +1 Armor") — see
            // ItemStack#addModifierTooltip, which also prepends CommonComponents.space().
            lines.add(CommonComponents.space()
                    .append(Component.translatable("tooltip.thesilverage.silver_smite", FORMAT.format(bonus)))
                    .withStyle(ChatFormatting.BLUE));
        });
    }
}
