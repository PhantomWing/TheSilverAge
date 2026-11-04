package com.phantomwing.thesilverage.gametest;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 1.21's {@code CraftingInput} for this line's tests, which 1.20.1 doesn't have: a crafting grid of its width and height,
 * filled row by row, as a container its recipes match against. Only on this line; the bodies name it as they name
 * vanilla's on the others.
 */
public final class CraftingInput {
    private final TransientCraftingContainer container;

    private CraftingInput(int width, int height, List<ItemStack> items) {
        this.container = new TransientCraftingContainer(new NoMenu(), width, height);
        for (int i = 0; i < items.size(); i++) {
            container.setItem(i, items.get(i));
        }
    }

    public static CraftingInput of(int width, int height, List<ItemStack> items) {
        return new CraftingInput(width, height, items);
    }

    public TransientCraftingContainer container() {
        return container;
    }

    /** A grid no menu shows: its slots tell no one when they change. */
    private static final class NoMenu extends AbstractContainerMenu {
        private NoMenu() {
            super((MenuType<?>) null, -1);
        }

        @Override
        public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean stillValid(@NotNull Player player) {
            return false;
        }
    }
}
