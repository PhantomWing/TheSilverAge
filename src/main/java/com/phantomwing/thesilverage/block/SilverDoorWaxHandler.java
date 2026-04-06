package com.phantomwing.thesilverage.block;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.custom.WeatheringSilverDoorBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public class SilverDoorWaxHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack held = event.getItemStack();
        if (!held.is(Items.HONEYCOMB)) return;

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // Only handle our silver weathering doors.
        if (!(state.getBlock() instanceof WeatheringSilverDoorBlock)) return;

        Optional<BlockState> waxed = HoneycombItem.getWaxed(state);
        if (waxed.isEmpty()) return;

        // Prevent the default HoneycombItem.useOn() from running (it only waxes one half).
        event.setUseItem(Event.Result.DENY);

        if (level.isClientSide()) {
            event.getEntity().swing(event.getHand());
            return;
        }

        Block block = state.getBlock();

        // Wax the clicked half.
        level.setBlockAndUpdate(pos, waxed.get());

        // Wax the other half.
        DoubleBlockHalf half = state.getValue(DoorBlock.HALF);
        BlockPos otherPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        BlockState otherState = level.getBlockState(otherPos);
        if (otherState.getBlock() == block) {
            HoneycombItem.getWaxed(otherState).ifPresent(otherWaxedState ->
                    level.setBlockAndUpdate(otherPos, otherWaxedState));
        }

        // Play wax sound and particles.
        level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.levelEvent(null, 3003, pos, 0);

        // Consume honeycomb and trigger advancement.
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, held);
            if (!serverPlayer.isCreative()) {
                held.shrink(1);
            }
        }
    }
}
