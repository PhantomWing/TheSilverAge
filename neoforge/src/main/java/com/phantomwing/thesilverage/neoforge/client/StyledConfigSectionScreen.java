package com.phantomwing.thesilverage.neoforge.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Consumer;
import java.util.function.Supplier;

// Config section screen that renders boolean options as a coloured Yes/No button instead of ON/OFF.
public class StyledConfigSectionScreen extends ConfigurationScreen.ConfigurationSectionScreen {
    public StyledConfigSectionScreen(Screen parent, ModConfig.Type type, ModConfig config, Component title) {
        super(parent, type, config, title);
    }

    @Override
    protected Element createBooleanValue(String key, ModConfigSpec.ValueSpec spec,
                                         Supplier<Boolean> getter, Consumer<Boolean> setter) {
        Consumer<Boolean> apply = value -> {
            setter.accept(value);
            onChanged(key);
        };
        OptionInstance<Boolean> option = new OptionInstance<>(
                getTranslationKey(key),
                getTooltip(key, null),
                (caption, value) -> value
                        ? CommonComponents.GUI_YES.copy().withStyle(ChatFormatting.GREEN)
                        : CommonComponents.GUI_NO.copy().withStyle(ChatFormatting.RED),
                Custom.BOOLEAN_VALUES_NO_PREFIX,
                getter.get(),
                // UndoManager.add(run, newValue, undo, oldValue): NEW value must be 2nd arg, old value 4th, or persistence breaks.
                newValue -> undoManager.add(apply, newValue, apply, getter.get()));
        return new Element(getTranslationComponent(key), getTooltipComponent(key, null), option);
    }
}
