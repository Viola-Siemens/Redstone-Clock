package com.hexagram2021.redstoneclock.common.menu;

import com.hexagram2021.redstoneclock.common.block.entity.RedstoneClockBlockEntity;
import com.hexagram2021.redstoneclock.common.register.RCMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class RedstoneClockMenu extends AbstractContainerMenu {
	private final Container redstoneClockContainer;
	private final ContainerData redstoneClock;

	public RedstoneClockMenu(int id) {
		this(id, new SimpleContainer(RedstoneClockBlockEntity.NUM_SLOT), new SimpleContainerData(RedstoneClockBlockEntity.NUM_DATA));
	}
	public RedstoneClockMenu(int id, Container container, ContainerData containerData) {
		super(RCMenuTypes.REDSTONE_CLOCK.get(), id);
		checkContainerSize(container, RedstoneClockBlockEntity.NUM_SLOT);
		checkContainerDataCount(containerData, RedstoneClockBlockEntity.NUM_DATA);
		this.redstoneClockContainer = container;
		this.redstoneClock = containerData;
		this.addDataSlots(containerData);
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		switch(id) {
			case 0 -> this.setData(RedstoneClockBlockEntity.DATA_SIGNAL_STRENGTH, Math.max(
					0, this.redstoneClock.get(RedstoneClockBlockEntity.DATA_SIGNAL_STRENGTH) - 1
			));
			case 1 -> this.setData(RedstoneClockBlockEntity.DATA_SIGNAL_STRENGTH, Math.min(
					15, this.redstoneClock.get(RedstoneClockBlockEntity.DATA_SIGNAL_STRENGTH) + 1
			));
			case 2 -> this.setData(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL, Math.max(
					0, this.redstoneClock.get(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL) - 2
			));
			case 3 -> this.setData(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL, Math.min(
					100, this.redstoneClock.get(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL) + 2
			));
			case 4 -> this.setData(RedstoneClockBlockEntity.DATA_IDLE_INTERVAL, Math.max(
					0, this.redstoneClock.get(RedstoneClockBlockEntity.DATA_IDLE_INTERVAL) - 2
			));
			case 5 -> this.setData(RedstoneClockBlockEntity.DATA_IDLE_INTERVAL, Math.min(
					100, this.redstoneClock.get(RedstoneClockBlockEntity.DATA_IDLE_INTERVAL) + 2
			));
			default -> {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setData(int index, int value) {
		super.setData(index, value);
		this.broadcastChanges();
	}

	@Override
	public boolean stillValid(Player player) {
		return this.redstoneClockContainer.stillValid(player);
	}
}
