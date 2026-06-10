package com.phantomwing.thesilverage.item.custom;

import com.phantomwing.thesilverage.utils.LevelUtils;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

/** Moon Dial item: hover tooltip names the current moon phase. */
public class MoonDialItem extends Item {
    // Indexed by Level#getMoonPhase(): 0 = Full ... 7 = Waxing Gibbous.
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
                                TooltipDisplay display, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        // Wrapped via EnvExecutor so Minecraft is never class-loaded on a dedicated server.
        Level level = EnvExecutor.getEnvSpecific(
                () -> () -> net.minecraft.client.Minecraft.getInstance().level,
                () -> () -> null);

        int phase = LevelUtils.getMoonPhase(level);
        tooltipAdder.accept(Component.translatable(PHASE_KEYS[phase])
                .withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, display, tooltipAdder, flag);
    }
}
