package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.block.SilverOxidation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopperBulbBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * {@link WeatheringCopperBulbBlock} that honours the {@code enable_silver_oxidation} config.
 *
 * <p>Vanilla's class is used unchanged for everything except the weather-stage
 * advance, which is skipped while the option is off. The oxidizable mapping is
 * still registered, so scraping an already-oxidized block back down with an axe
 * keeps working — see {@link SilverOxidation}.</p>
 */
public class WeatheringSilverBulbBlock extends WeatheringCopperBulbBlock {
    public WeatheringSilverBulbBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(weatherState, properties);
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!SilverOxidation.enabled()) {
            return;
        }

        super.randomTick(state, level, pos, random);
    }
}
