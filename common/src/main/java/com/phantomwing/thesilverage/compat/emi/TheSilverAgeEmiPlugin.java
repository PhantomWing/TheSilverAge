package com.phantomwing.thesilverage.compat.emi;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.SilverInfo;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * EMI's info pages ({@link SilverInfo}), the JEI plugin's counterpart. The waxing and scraping EMI shows on NeoForge are
 * {@code neoforge}'s {@code ModEmiPlugin}'s.
 *
 * <p>Found through {@link EmiEntrypoint} on NeoForge and the {@code emi} entrypoint on Fabric, and only ever
 * classloaded by EMI, so EMI stays optional.</p>
 */
@EmiEntrypoint
public class TheSilverAgeEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        for (SilverInfo page : SilverInfo.all()) {
            registry.addRecipe(new EmiInfoRecipe(page.items().stream().<EmiIngredient>map(EmiStack::of).toList(),
                    List.of(Component.translatable(page.key())), TheSilverAge.resourceLocation("/info/" + page.name())));
        }
    }
}
