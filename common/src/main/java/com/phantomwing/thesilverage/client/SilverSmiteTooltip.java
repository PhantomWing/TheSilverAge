package com.phantomwing.thesilverage.client;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.combat.SilverUndeadSettings;
import com.phantomwing.thesilverage.item.ModItems;
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
            // Its speed isn't the smite toggle's, so it shows either way.
            if (stack.is(ModItems.SILVER_ARROW.get())) {
                lines.add(Component.translatable("tooltip.thesilverage.flies_faster").withStyle(ChatFormatting.BLUE));
            }

            // The server's settings where it sent them: with the bonus off or set otherwise there, the line would lie.
            SilverUndeadSettings settings = ServerUndeadSettings.current();
            if (!settings.smite()) {
                return;
            }

            float bonus = SilverSmiteHandler.getTooltipBonusFor(stack, settings);
            if (bonus <= 0.0f) {
                return;
            }

            lines.add(Component.translatable("tooltip.thesilverage.silver_smite", FORMAT.format(bonus))
                    .withStyle(ChatFormatting.BLUE));
        });
    }
}
