package com.phantomwing.thesilverage.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.gui.entries.TooltipListEntry;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

/**
 * The config screen Mod Menu opens from its mod list, as NeoForge's mod list opens its own: Cloth Config's, made from
 * {@link TheSilverAgeFabricConfig}. Loaded by Mod Menu alone, through the {@code modmenu} entrypoint.
 *
 * <p>It names its title, options and tooltips with NeoForge's keys ({@code thesilverage.configuration.<id>} and
 * {@code .tooltip}), which every language has, rather than AutoConfig's own {@code text.autoconfig.thesilverage.*}.</p>
 */
public final class TheSilverAgeModMenu implements ModMenuApi {
    private static final String KEYS = "thesilverage.configuration";

    static {
        GuiRegistry options = AutoConfig.getGuiRegistry(TheSilverAgeFabricConfig.class);
        // AutoConfig lists every declared field, the option ids' constants among them as text boxes.
        options.registerPredicateProvider((optionKey, field, config, defaults, registry) -> List.of(),
                field -> Modifier.isStatic(field.getModifiers()));
        // AutoConfig only reads a tooltip from <option>.@Tooltip, for a field marked @Tooltip.
        options.registerPredicateTransformer(
                (guis, optionKey, field, config, defaults, registry) -> {
                    for (AbstractConfigListEntry<?> gui : guis) {
                        if (gui instanceof TooltipListEntry<?> entry) {
                            entry.setTooltipSupplier(() -> Optional.of(new Component[]{Component.translatable(optionKey + ".tooltip")}));
                        }
                    }
                    return guis;
                },
                field -> true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigScreenProvider<TheSilverAgeFabricConfig> screen =
                    (ConfigScreenProvider<TheSilverAgeFabricConfig>) AutoConfig.getConfigScreen(TheSilverAgeFabricConfig.class, parent);
            screen.setI13nFunction(manager -> KEYS);
            screen.setOptionFunction((keys, field) -> keys + "." + field.getName());
            return screen.get();
        };
    }
}
