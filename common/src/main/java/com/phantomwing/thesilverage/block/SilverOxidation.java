package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.platform.CommonConfig;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Central gate for the {@code enable_silver_oxidation} config option.
 *
 * <p>When the option is off, silver blocks never advance a weather stage and
 * refuse honeycomb. The oxidizable/waxable mappings stay registered either way,
 * because those same mappings are what let an axe scrape an existing oxidized or
 * waxed block back down — the reverse direction is deliberately kept working.</p>
 *
 * <p>Note the gate sits on {@code randomTick} rather than
 * {@code isRandomlyTicking}: the latter is cached per {@code BlockState} in
 * {@code BlockStateBase#initCache()} at load time, so gating it there would bake
 * in whatever the config said at startup. Gating the tick itself means toggling
 * the option takes effect immediately.</p>
 */
public final class SilverOxidation {
    private static Set<Block> nonBaseVariants;
    private static Set<Block> waxableSources;
    private static volatile Optional<Boolean> fromServer = Optional.empty();

    private SilverOxidation() {
    }

    /** Whether silver may oxidize over time and accept honeycomb waxing. */
    public static boolean enabled() {
        return CommonConfig.silverOxidation();
    }

    /**
     * Whether silver oxidizes where {@code level} is: on a client, the server's option, sent on join
     * ({@code ContentSyncPayload}), else its own config. A client reading its own config hid the weathered blocks
     * from its creative tab on a server where they can be had, and took a honeycomb the server then refused.
     */
    public static boolean enabled(Level level) {
        return level.isClientSide() ? fromServer.orElseGet(SilverOxidation::enabled) : enabled();
    }

    /** The server's option, on a client. */
    public static boolean enabledOnClient() {
        return fromServer.orElseGet(SilverOxidation::enabled);
    }

    public static void setFromServer(boolean enabled) {
        fromServer = Optional.of(enabled);
    }

    public static void clearFromServer() {
        fromServer = Optional.empty();
    }

    /**
     * Refuses honeycomb on silver while oxidation is off.
     *
     * <p>The waxable mapping itself stays registered (an axe must still be able to
     * scrape existing waxed blocks), so waxing is blocked at the interaction
     * instead: the right-click is cancelled before vanilla's {@code HoneycombItem}
     * ever runs, and a dispenser's honeycomb fails as it does on a block it can't wax. Called from
     * {@code TheSilverAgeCommon.init()}.</p>
     */
    public static void register() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            if (enabled(player.level()) || !player.getItemInHand(hand).is(Items.HONEYCOMB)) {
                return EventResult.pass();
            }

            Block block = player.level().getBlockState(pos).getBlock();
            return waxableSources().contains(block) ? EventResult.interruptFalse() : EventResult.pass();
        });
        LifecycleEvent.SETUP.register(SilverOxidation::refuseDispensedHoneycomb);
    }

    private static void refuseDispensedHoneycomb() {
        DispenseItemBehavior waxing = DispenserBlock.DISPENSER_REGISTRY.get(Items.HONEYCOMB);
        if (waxing == null) {
            return;
        }
        DispenseItemBehavior refused = new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                setSuccess(false);
                return stack;
            }
        };
        DispenserBlock.registerBehavior(Items.HONEYCOMB, (source, stack) -> {
            BlockPos target = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            boolean silver = waxableSources().contains(source.level().getBlockState(target).getBlock());
            return (!silver || enabled() ? waxing : refused).dispense(source, stack);
        });
    }

    /** The unwaxed silver blocks honeycomb would normally wax. */
    public static synchronized Set<Block> waxableSources() {
        if (waxableSources == null) {
            Set<Block> blocks = new HashSet<>();
            for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
                blocks.add(resolve(pair.from()));
            }
            waxableSources = blocks;
        }

        return waxableSources;
    }

    /**
     * Every silver block that is not a base (unaffected, unwaxed) variant: the
     * exposed/weathered/oxidized stages plus all waxed forms.
     *
     * <p>Derived from {@link SilverWeatheringSpec} so it can never drift out of
     * sync with the actual weathering chain — a new family added to the spec is
     * covered automatically. Used to hide those variants from the creative tab
     * while oxidation is off, since they are unobtainable in survival then.</p>
     */
    public static synchronized Set<Block> nonBaseVariants() {
        if (nonBaseVariants == null) {
            Set<Block> blocks = new HashSet<>();
            // Oxidation targets: exposed / weathered / oxidized of every family.
            for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
                blocks.add(resolve(pair.to()));
            }
            // Waxed forms of every stage, including the waxed base block.
            for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
                blocks.add(resolve(pair.to()));
            }
            nonBaseVariants = blocks;
        }

        return nonBaseVariants;
    }

    private static Block resolve(Supplier<Block> supplier) {
        return supplier.get();
    }
}
