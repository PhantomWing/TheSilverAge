package com.phantomwing.thesilverage.item.custom;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import org.jetbrains.annotations.NotNull;

/**
 * Silver Horse Armor. 1.20.1's {@link HorseArmorItem} finds its texture in vanilla's namespace from the name it is
 * given, so the mod's own is handed back here: {@code thesilverage:textures/entity/horse/armor/horse_armor_silver.png}.
 */
public class SilverHorseArmorItem extends HorseArmorItem {
    private static final ResourceLocation TEXTURE = TheSilverAge.resourceLocation("textures/entity/horse/armor/horse_armor_silver.png");

    public SilverHorseArmorItem(int protection, Properties properties) {
        super(protection, "silver", properties);
    }

    @Override
    public @NotNull ResourceLocation getTexture() {
        return TEXTURE;
    }
}
