package com.phantomwing.thesilverage.item.custom;

import com.phantomwing.thesilverage.utils.LevelUtils;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * The Moon Dial item. Adds a hover tooltip naming the moon phase the dial is
 * currently displaying (the same phase that drives its {@code moon_phase}
 * item-property texture, so text and icon always agree).
 *
 * <p>Vanilla has <b>no</b> translation strings for lunar phases (the moon is
 * never shown as text anywhere in the game), so the eight names are mod-owned
 * keys under {@code thesilverage.moon_phase.*} (en/de/nl supplied).</p>
 *
 * <p>{@code Item.TooltipContext} in 1.21.1 exposes no {@link Level} (only
 * {@code registries()}/{@code tickRate()}/{@code mapData()}), so the phase is
 * read from the client world — exactly like the moon_phase item-property
 * predicate, which is also fed the client level. The client lookup is wrapped
 * in {@link EnvExecutor#getEnvSpecific} (supplier-of-supplier) so the
 * {@code net.minecraft.client.Minecraft} reference is never class-loaded on a
 * dedicated server even though {@code appendHoverText} is declared on the
 * common {@link Item}.</p>
 */
public class MoonDialItem extends Item {
    /**
     * Index = vanilla {@link Level#getMoonPhase()}: 0 = Full, 1 = Waning
     * Gibbous, 2 = Third Quarter, 3 = Waning Crescent, 4 = New, 5 = Waxing
     * Crescent, 6 = First Quarter, 7 = Waxing Gibbous (the brightness ordering
     * documented at https://minecraft.wiki/w/Moon).
     */
    private static final String[] PHASE_KEYS = {
            "thesilverage.moon_phase.full",
            "thesilverage.moon_phase.waning_gibbous",
            "thesilverage.moon_phase.third_quarter",
            "thesilverage.moon_phase.waning_crescent",
            "thesilverage.moon_phase.new",
            "thesilverage.moon_phase.waxing_crescent",
            "thesilverage.moon_phase.first_quarter",
            "thesilverage.moon_phase.waxing_gibbous",
    };

    public MoonDialItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        Level level = EnvExecutor.getEnvSpecific(
                () -> () -> net.minecraft.client.Minecraft.getInstance().level,
                () -> () -> null);

        int phase = LevelUtils.getMoonPhase(level);
        tooltip.add(Component.translatable("thesilverage.tooltip.moon_dial",
                        Component.translatable(PHASE_KEYS[phase]))
                .withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, tooltip, flag);
    }
}
