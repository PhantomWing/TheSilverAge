package com.phantomwing.thesilverage.block.custom;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;

import com.phantomwing.thesilverage.block.ModBlockEntityTypes;
import com.phantomwing.thesilverage.block.entity.MoonPhaseDetectorBlockEntity;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class MoonPhaseDetectorBlock extends BaseEntityBlock {
    private static final int MAX_POWER = 15;

    public static final MapCodec<net.minecraft.world.level.block.DaylightDetectorBlock> CODEC = simpleCodec(net.minecraft.world.level.block.DaylightDetectorBlock::new);
    public static final IntegerProperty POWER;
    public static final BooleanProperty INVERTED;
    protected static final VoxelShape SHAPE;

    public @NotNull MapCodec<net.minecraft.world.level.block.DaylightDetectorBlock> codec() {
        return CODEC;
    }

    public MoonPhaseDetectorBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0).setValue(INVERTED, false));
    }

    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    protected boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    protected int getSignal(BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
        return blockState.getValue(POWER);
    }

    private static void updateSignalStrength(BlockState state, Level level, BlockPos pos) {
        int signalStrength = LevelUtils.getMoonPhaseSignal(level);

        // We want a Full Moon to give a maximum signal strength.
        if (!state.getValue(INVERTED)) {
            signalStrength = MAX_POWER - signalStrength;
        }

        signalStrength = Mth.clamp(signalStrength, 0, MAX_POWER);

        if (state.getValue(POWER) != signalStrength) {
            level.setBlock(pos, state.setValue(POWER, signalStrength), 3);
        }
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hitResult) {
        if (player.mayBuild()) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                // Invert the block.
                BlockState blockstate = state.cycle(INVERTED);
                level.setBlock(pos, blockstate, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, blockstate));
                updateSignalStrength(blockstate, level, pos);

                // Play a sound when the block is toggled.
                level.playSound(null, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.3f, 0.7f);

                return InteractionResult.CONSUME;
            }
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    protected boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new MoonPhaseDetectorBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return !level.isClientSide && level.dimensionType().hasSkyLight() ? createTickerHelper(blockEntityType, ModBlockEntityTypes.MOON_PHASE_DETECTOR.get(), MoonPhaseDetectorBlock::tickEntity) : null;
    }

    private static void tickEntity(Level level, BlockPos pos, BlockState state, MoonPhaseDetectorBlockEntity blockEntity) {
        if (level.getGameTime() % 20L == 0L) {
            updateSignalStrength(state, level, pos);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER, INVERTED);
    }

    static {
        POWER = BlockStateProperties.POWER;
        INVERTED = BlockStateProperties.INVERTED;
        SHAPE = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 6.0F, 16.0F);
    }
}
