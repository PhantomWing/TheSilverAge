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

/**
 * Drop-in replacement for NeoForge's default config section screen that renders
 * boolean options as a coloured <b>Yes</b>/<b>No</b> button instead of the
 * default ON/OFF. Wired via {@code ConfigurationScreen}'s section-screen factory
 * constructor in {@code TheSilverAgeNeoForge}, so it only affects this mod's
 * config screen (no global mixin / no effect on other mods).
 *
 * <p>Only {@link #createBooleanValue} is overridden; everything else (sections,
 * other value types, save/undo/reset plumbing) is inherited unchanged. The
 * boolean widget is rebuilt with a custom value stringifier (green "Yes" / red
 * "No") while preserving the parent's exact undo wiring
 * ({@code undoManager.add(apply, old, apply, new)} where {@code apply} re-applies
 * the value and marks the screen changed), so Done/Undo/Reset keep working.</p>
 */
public class StyledConfigSectionScreen extends ConfigurationScreen.ConfigurationSectionScreen {
    public StyledConfigSectionScreen(Screen parent, ModConfig.Type type, ModConfig config, Component title) {
        super(parent, type, config, title);
    }

    @Override
    protected Element createBooleanValue(String key, ModConfigSpec.ValueSpec spec,
                                         Supplier<Boolean> getter, Consumer<Boolean> setter) {
        // Re-apply the value AND flag the screen dirty — mirrors the parent's wiring.
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
                // UndoManager.add(run, newValue, undo, oldValue): runRedo() applies
                // run.accept(newValue), so the NEW value must be the 2nd arg and the
                // old value (getter.get(), still current here) the 4th — matching the
                // parent. Reversing them re-applies the OLD value → no persistence.
                newValue -> undoManager.add(apply, newValue, apply, getter.get()));
        return new Element(getTranslationComponent(key), getTooltipComponent(key, null), option);
    }
}
