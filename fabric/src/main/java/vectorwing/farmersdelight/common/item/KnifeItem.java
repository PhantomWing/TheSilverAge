package vectorwing.farmersdelight.common.item;

import net.minecraft.world.item.Item;

/**
 * COMPILE-TIME STUB of Farmer's Delight Refabricated's {@code KnifeItem} — NOT
 * the real class, and excluded from the shipped jar (see {@code shadowJar
 * { exclude 'vectorwing/**' }} in {@code fabric/build.gradle}).
 *
 * <p><b>Why it exists.</b> The Fabric
 * {@code com.phantomwing.thesilverage.fabric.compat.farmersdelight.SilverKnifeItem}
 * extends FDR's {@code KnifeItem}, but the FDR jar can't be consumed as a
 * dependency under architectury-loom 1.7.435 (FDR is built with fabric-loom
 * 1.16.1 — newer-Loom stamp + a ClassTweaker access widener loom 1.7 can't
 * parse). This stub supplies only {@code KnifeItem}'s signature in named
 * mappings so the subclass compiles.</p>
 *
 * <p><b>Why it's safe.</b> The signature mirrors the real FDR class's
 * constructor {@code (Item.Properties)}. It is stripped from the
 * published jar, so in a production instance the real
 * {@code vectorwing.farmersdelight.common.item.KnifeItem} is what
 * {@code SilverKnifeItem} binds to. In TheSilverAge's own dev runtime the
 * stub is present but never touched — FDR is absent there, so the
 * {@code isModLoaded("farmersdelight")} guard means {@code SilverKnifeItem} is
 * never instantiated. The constructor body below never runs.</p>
 */
public class KnifeItem extends Item {
    public KnifeItem(Item.Properties properties) {
        // The exact setup is irrelevant here — the stub is never instantiated (FDR
        // absent on this branch) and is stripped from the jar; the ctor signature
        // just has to satisfy SilverKnifeItem's super call.
        super(properties);
    }
}
