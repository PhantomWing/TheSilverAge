package com.phantomwing.thesilverage.fabric.compat.vanillabackport;

import com.blackgear.vanillabackport.common.level.items.NautilusArmorItem;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

/**
 * Silver Nautilus Armor as Vanilla Backport's nautilus wears it: its renderer draws any {@link NautilusArmorItem} from
 * the texture it names, here {@code thesilverage:textures/entity/nautilus/armor/silver.png}. Only loaded when it is
 * installed.
 */
public final class SilverNautilusArmor extends NautilusArmorItem {
    private static final ResourceLocation TEXTURE = TheSilverAge.resourceLocation("textures/entity/nautilus/armor/silver.png");
    /** As silver's horse armour: between Vanilla Backport's iron (5) and gold (7) and its diamond (11). */
    private static final int PROTECTION = 8;

    private SilverNautilusArmor(Item.Properties properties) {
        super(PROTECTION, "silver", properties);
    }

    public static Item create(Item.Properties properties) {
        return new SilverNautilusArmor(properties);
    }

    @Override
    public @NotNull ResourceLocation getTexture() {
        return TEXTURE;
    }
}
