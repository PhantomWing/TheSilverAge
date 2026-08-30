package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.combat.UndeadProtectionHandler;
import com.phantomwing.thesilverage.platform.CommonConfig;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Tooltip line for the innate anti-undead damage reduction on silver armour —
 * the defensive twin of {@link SilverSmiteTooltip}.
 *
 * <p>Same styling as the smite line (leading space, blue), which is how vanilla
 * renders an item's own attribute lines such as " +1 Armor", so the built-in
 * bonus reads like a native item property.</p>
 *
 * <p>Like the smite tooltip this needs no {@code @ExpectPlatform} bridge:
 * Architectury's {@code ClientTooltipEvent.ITEM} is implemented on both loaders.
 * Called from each loader's CLIENT entrypoint, so this class (and the client-only
 * types it names) never loads on a dedicated server.</p>
 */
public final class UndeadProtectionTooltip {
    /** Matches vanilla's attribute-number formatting (e.g. "1", "1.5"). */
    private static final DecimalFormat FORMAT =
            new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private UndeadProtectionTooltip() {
    }

    /** Hooks the cross-loader item-tooltip event. Called from both client entrypoints. */
    public static void register() {
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            // Gated on the local config: with the reduction switched off the line
            // would be a lie. (Server-side value is authoritative for the damage.)
            if (!CommonConfig.undeadProtection()) {
                return;
            }

            float protection = UndeadProtectionHandler.getProtectionFor(stack);
            if (protection <= 0.0f) {
                return;
            }

            lines.add(CommonComponents.space()
                    .append(Component.translatable("tooltip.thesilverage.undead_protection",
                            FORMAT.format(protection)))
                    .withStyle(ChatFormatting.BLUE));
        });
    }
}
