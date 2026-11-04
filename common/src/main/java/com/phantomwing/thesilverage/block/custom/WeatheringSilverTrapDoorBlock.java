package com.phantomwing.thesilverage.block.custom;

import com.phantomwing.thesilverage.block.SilverOxidation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

/** The unwaxed Silver Trapdoors, 1.21's copper trapdoor, honouring the {@code enable_silver_oxidation} config. */
public class WeatheringSilverTrapDoorBlock extends TrapDoorBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState weatherState;

    public WeatheringSilverTrapDoorBlock(BlockSetType blockSetType, WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(properties, blockSetType);
        this.weatherState = weatherState;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (SilverOxidation.enabled()) {
            this.onRandomTick(state, level, pos, random);
        }
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public @NotNull WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }
}
