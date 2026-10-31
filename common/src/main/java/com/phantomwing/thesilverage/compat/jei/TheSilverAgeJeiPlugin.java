package com.phantomwing.thesilverage.compat.jei;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.compat.SilverInfo;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * JEI's info pages ({@link SilverInfo}); silver's recipes are plain crafting, smelting and stonecutting, which JEI
 * shows by itself. The EMI plugin is its counterpart.
 *
 * <p>Lives in common - the JEI plugin API is loader-agnostic, and this class is only classloaded by JEI itself, so JEI
 * remains an optional dependency.</p>
 */
@JeiPlugin
public class TheSilverAgeJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = TheSilverAge.resourceLocation("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
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
