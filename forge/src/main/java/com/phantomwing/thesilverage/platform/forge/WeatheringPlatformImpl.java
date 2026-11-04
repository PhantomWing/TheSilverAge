package com.phantomwing.thesilverage.platform.forge;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Forge implementation of {@link com.phantomwing.thesilverage.platform.WeatheringPlatform}.
 *
 * <p>Forge 1.20.1 has neither data maps nor a registry for oxidizable and waxable blocks: vanilla reads both from the
 * memoized maps in {@link WeatheringCopper} and {@link HoneycombItem}, which tarnishing, an axe's scraping and
 * honeycomb all go through. The pairs are gathered as the common code names them, and {@link #apply()} puts them in
 * those maps once the blocks exist, at common setup. The maps are {@code static final} - an interface's constant for
 * the copper ones, which no access transformer can open - so they are replaced through {@code Unsafe}, as The Silver
 * Age's single-loader Forge build did.</p>
 */
public final class WeatheringPlatformImpl {
    private static final List<Supplier<Block>[]> OXIDATION = new ArrayList<>();
    private static final List<Supplier<Block>[]> WAXING = new ArrayList<>();

    private WeatheringPlatformImpl() {
    }

    @SuppressWarnings("unchecked")
    public static void registerOxidation(Supplier<Block> less, Supplier<Block> more) {
        OXIDATION.add(new Supplier[]{less, more});
    }

    @SuppressWarnings("unchecked")
    public static void registerWaxable(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        WAXING.add(new Supplier[]{unwaxed, waxed});
    }

    /** Adds the gathered pairs to vanilla's maps. Called once, on the main thread, at common setup. */
    public static void apply() {
        BiMap<Block, Block> next = merged(WeatheringCopper.NEXT_BY_BLOCK.get(), OXIDATION);
        BiMap<Block, Block> waxables = merged(HoneycombItem.WAXABLES.get(), WAXING);
        try {
            // Dev runs use Mojang's names, a shipped jar Forge's (SRG) ones.
            setStaticFinal(WeatheringCopper.class, "NEXT_BY_BLOCK", "f_154886_", Suppliers.memoize(() -> next));
            setStaticFinal(WeatheringCopper.class, "PREVIOUS_BY_BLOCK", "f_154887_", Suppliers.memoize(next::inverse));
            setStaticFinal(HoneycombItem.class, "WAXABLES", "f_150863_", Suppliers.memoize(() -> waxables));
            setStaticFinal(HoneycombItem.class, "WAX_OFF_BY_BLOCK", "f_150864_", Suppliers.memoize(waxables::inverse));
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Couldn't add silver's oxidation and waxing to vanilla's", e);
        }
    }

    private static BiMap<Block, Block> merged(BiMap<Block, Block> vanilla, List<Supplier<Block>[]> pairs) {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.putAll(vanilla);
        for (Supplier<Block>[] pair : pairs) {
            builder.put(pair[0].get(), pair[1].get());
        }
        return builder.build();
    }

    @SuppressWarnings({"removal", "deprecation"})
    private static void setStaticFinal(Class<?> owner, String name, String srgName, Object value) throws ReflectiveOperationException {
        Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);

        Field field;
        try {
            field = owner.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            field = owner.getDeclaredField(srgName);
        }
        unsafe.putObject(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), value);
    }
}
