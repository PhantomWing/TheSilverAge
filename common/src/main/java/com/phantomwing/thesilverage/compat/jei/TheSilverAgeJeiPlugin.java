package com.phantomwing.thesilverage.compat.jei;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.SilverInfo;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * JEI's info pages ({@link SilverInfo}); silver's recipes are plain crafting, smelting and stonecutting, which JEI
 * shows by itself. EMI has no 1.21.10 build, so this line has no EMI plugin.
 *
 * <p>Lives in common - the JEI plugin API is loader-agnostic, and this class is only classloaded by JEI itself, so JEI
 * remains an optional dependency.</p>
 */
@JeiPlugin
public class TheSilverAgeJeiPlugin implements IModPlugin {
    private static final Identifier UID = TheSilverAge.resourceLocation("jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (SilverInfo page : SilverInfo.all()) {
            registration.addItemStackInfo(page.items().stream().map(ItemStack::new).toList(),
                    Component.translatable(page.key()));
        }
    }
}
