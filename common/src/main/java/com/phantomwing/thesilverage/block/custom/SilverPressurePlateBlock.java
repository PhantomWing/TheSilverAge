package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.block.ModBlockSetTypes;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * A pressure plate only undead mobs press: whatever silver tools deal their bonus damage to
 * ({@code thesilverage:sensitive_to_silver}). Fresh silver gives a full signal for one undead; the more
 * it has tarnished, the more undead it takes, so a tarnished plate counts them as a weighted plate
 * does. The waxed variants keep their stage's count and never tarnish.
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

    /** How much signal each undead adds at a stage: full power at 1, 3, 5 and 15 undead. */
    public static int signalPerUndead(WeatheringCopper.WeatherState weatherState) {
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
                && entity.getType().is(ModTags.EntityTypes.SENSITIVE_TO_SILVER);
    }

    @Override
    protected int getSignalStrength(@NotNull Level level, @NotNull BlockPos pos) {
        int undead = level.getEntitiesOfClass(LivingEntity.class, TOUCH_AABB.move(pos), SilverPressurePlateBlock::pressesPlate).size();
        return Math.min(undead * signalPerUndead(weatherState), MAX_SIGNAL);
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
