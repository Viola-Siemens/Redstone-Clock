package com.hexagram2021.redstoneclock.common.menu;

import com.hexagram2021.redstoneclock.common.block.entity.PulseDividerBlockEntity;
import com.hexagram2021.redstoneclock.common.register.RCMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class PulseDividerMenu extends AbstractContainerMenu {
	private final Container pulseDividerContainer;
	public final ContainerData pulseDivider;
	public final ContainerLevelAccess access;

	public PulseDividerMenu(int id) {
		this(id, new SimpleContainer(PulseDividerBlockEntity.NUM_SLOT), new SimpleContainerData(PulseDividerBlockEntity.NUM_DATA), ContainerLevelAccess.NULL);
	}
	public PulseDividerMenu(int id, Container container, ContainerData containerData, ContainerLevelAccess access) {
		super(RCMenuTypes.PULSE_DIVIDER.get(), id);
		checkContainerSize(container, PulseDividerBlockEntity.NUM_SLOT);
		checkContainerDataCount(containerData, PulseDividerBlockEntity.NUM_DATA);
		this.pulseDividerContainer = container;
		this.pulseDivider = containerData;
		this.access = access;
		this.addDataSlots(containerData);
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		switch(id) {
			case 0 -> this.setData(PulseDividerBlockEntity.DATA_SIGNAL_STRENGTH, Math.min(
					15, this.pulseDivider.get(PulseDividerBlockEntity.DATA_SIGNAL_STRENGTH) + 1
			));
			case 1 -> this.setData(PulseDividerBlockEntity.DATA_SIGNAL_STRENGTH, Math.max(
					1, this.pulseDivider.get(PulseDividerBlockEntity.DATA_SIGNAL_STRENGTH) - 1
			));
			case 2 -> this.setData(PulseDividerBlockEntity.DATA_EVERY_PULSE, Math.min(
					100, this.pulseDivider.get(PulseDividerBlockEntity.DATA_EVERY_PULSE) + 1
			));
			case 3 -> this.setData(PulseDividerBlockEntity.DATA_EVERY_PULSE, Math.max(
					2, this.pulseDivider.get(PulseDividerBlockEntity.DATA_EVERY_PULSE) - 1
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
		return this.pulseDividerContainer.stillValid(player);
	}
}
