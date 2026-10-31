package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.block.ModBlockSetTypes;
import com.phantomwing.thesilverage.combat.SilverFloor;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * A pressure plate no hostile mob presses: players, villagers, animals and golems do, as on a stone plate, but not a
 * mob that is an {@link Enemy} or in {@code thesilverage:cannot_press_silver_pressure_plates}, which holds the mobs
 * silver harms. Those are hurt standing in it and walk round it, as on any silver floor ({@link SilverFloor}). Fresh
 * silver gives a full signal for one mob; the more it has tarnished, the more mobs it takes, so a tarnished plate
 * counts them as a weighted plate does. The waxed variants keep their stage's count and never tarnish.
 */
public class SilverPressurePlateBlock extends WeightedPressurePlateBlock {
    /** Typed as the parent's codec type: {@code WeightedPressurePlateBlock#codec} is declared concretely, so an override must match. */
    public static final MapCodec<WeightedPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(block -> ((SilverPressurePlateBlock) block).weatherState),
            propertiesCodec()
    ).apply(instance, SilverPressurePlateBlock::new));

    private static final int MAX_SIGNAL = 15;

    protected final WeatheringCopper.WeatherState weatherState;

    public SilverPressurePlateBlock(WeatheringCopper.WeatherState weatherState, Properties properties) {
        super(MAX_SIGNAL, ModBlockSetTypes.SILVER, properties);
        this.weatherState = weatherState;
    }

    @Override
    public @NotNull MapCodec<WeightedPressurePlateBlock> codec() {
        return CODEC;
    }

    /** How much signal each mob adds at a stage: full power at 1, 3, 5 and 15 mobs. */
    public static int signalPerMob(WeatheringCopper.WeatherState weatherState) {
        return switch (weatherState) {
            case UNAFFECTED -> 15;
            case EXPOSED -> 5;
            case WEATHERED -> 3;
            case OXIDIZED -> 1;
        };
    }

    public static boolean pressesPlate(Entity entity) {
        return EntitySelector.NO_SPECTATORS.test(entity)
                && !entity.isIgnoringBlockTriggers()
                && !(entity instanceof Enemy)
                && !entity.getType().is(ModTags.EntityTypes.CANNOT_PRESS_SILVER_PRESSURE_PLATES);
    }

    @Override
    protected int getSignalStrength(@NotNull Level level, @NotNull BlockPos pos) {
        int mobs = level.getEntitiesOfClass(LivingEntity.class, TOUCH_AABB.move(pos), SilverPressurePlateBlock::pressesPlate).size();
        return Math.min(mobs * signalPerMob(weatherState), MAX_SIGNAL);
    }

    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        super.entityInside(state, level, pos, entity);
        // Asked of every block a mob's box reaches, so a zombie beside the plate was hurt, and never walked off, since
        // its goal looks only at the block it stands in.
        if (entity.blockPosition().equals(pos)) {
            SilverFloor.stepOn(level, state, entity);
        }
    }

    // A plate that tarnished, or was waxed or scraped, while pressed stayed powered for good without
    // this: the check its old block had scheduled doesn't run on the new one.
    @Override
    protected void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide() && getSignalForState(state) > 0) {
            level.scheduleTick(pos, this, getPressedTime());
        }
    }
}
