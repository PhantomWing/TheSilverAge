package vectorwing.farmersdelight.common.item;

import net.minecraft.world.item.Item;

/**
 * COMPILE-TIME STUB of Farmer's Delight's {@code KnifeItem} — NOT the real
 * class, and excluded from the shipped jar (see {@code shadowJar
 * { exclude 'vectorwing/**' }} in {@code neoforge/build.gradle}).
 *
 * <p><b>Why it exists.</b> Farmer's Delight (NeoForge) is not yet published
 * past MC 1.21.1, so there is no FD jar to compile against on this 1.21.3
 * branch. The NeoForge
 * {@code com.phantomwing.thesilverage.neoforge.compat.farmersdelight.SilverKnifeItem}
 * still extends FD's {@code KnifeItem}; this stub supplies only that class's
 * signature in named mappings so the subclass compiles, exactly mirroring the
 * Fabric-side stub used for FDR.</p>
 *
 * <p><b>Why it's safe.</b> The signature mirrors the real FD class's
 * constructor {@code (Item.Properties)}. It is stripped from the
 * published jar, so in a production instance the real
 * {@code vectorwing.farmersdelight.common.item.KnifeItem} is what
 * {@code SilverKnifeItem} binds to once FD ships for 1.21.5. In TheSilverAge's
 * own dev runtime the stub is present but never touched — FD is absent there,
 * so the {@code isModLoaded("farmersdelight")} guard means
 * {@code SilverKnifeItem} is never instantiated. The constructor body below
 * never runs.</p>
 */
public class KnifeItem extends Item {
    public KnifeItem(Item.Properties properties) {
        // The exact setup is irrelevant here — the stub is never instantiated (FD
        // absent on this branch) and is stripped from the jar; the ctor signature
        // just has to satisfy SilverKnifeItem's super call.
        super(properties);
    }
}
