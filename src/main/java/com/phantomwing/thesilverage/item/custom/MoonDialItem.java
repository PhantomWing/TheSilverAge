package com.phantomwing.thesilverage.item.custom;

import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Moon Dial with a tooltip naming the current moon phase. The tooltip uses the canonical phase,
 * while the icon can show a between-phases frame outside the night.
 */
public class MoonDialItem extends Item {
    /** Indexed by {@link Level#getMoonPhase()}: 0 is the full moon, 4 the new moon. */
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable(PHASE_KEYS[LevelUtils.getMoonPhase(level)])
                .withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
