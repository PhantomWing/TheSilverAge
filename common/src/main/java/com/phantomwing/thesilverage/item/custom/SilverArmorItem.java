package com.phantomwing.thesilverage.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

/** Silver armour, an {@link ArmorItem} whose material is the registry's own holder of it. */
public class SilverArmorItem extends ArmorItem {
    @Nullable
    private Holder<ArmorMaterial> registered;

    public SilverArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    /**
     * The registry's own holder of the material rather than Architectury's: a trim material's palette for an armour
     * material - silver's darker one on silver armour - is found in a map keyed by the registry's holders, and on Fabric
     * those don't count Architectury's as equal, so worn silver armour drew its silver trim in the palette that vanishes.
     */
    @Override
    public Holder<ArmorMaterial> getMaterial() {
        if (registered != null) {
            return registered;
        }
        Holder<ArmorMaterial> holder = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(super.getMaterial().value());
        if (holder instanceof Holder.Reference<ArmorMaterial>) {
            registered = holder; // kept once the material is registered, which it is before any trim is drawn
        }
        return holder;
    }
}
