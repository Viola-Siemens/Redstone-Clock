package com.hexagram2021.redstoneclock.common.block.entity;

import com.hexagram2021.redstoneclock.common.menu.PulseDividerMenu;
import com.hexagram2021.redstoneclock.common.register.RCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class PulseDividerBlockEntity extends BlockEntity implements MenuProvider, Nameable {
	public static final int NUM_SLOT = 0;
	public static final int NUM_DATA = 3;
	public static final int DATA_SIGNAL_STRENGTH = 0;
	public static final int DATA_EVERY_PULSE = 1;
	public static final int DATA_COUNT_PULSE = 2;

	@Nullable
	private Component name;

	private final Container containerAccess = new Container() {
		@Override
		public int getContainerSize() {
			return NUM_SLOT;
		}
		@Override
		public boolean isEmpty() {
			return true;
		}
		@Override
		public ItemStack getItem(int index) {
			return ItemStack.EMPTY;
		}
		@Override
		public ItemStack removeItem(int index, int count) {
			return ItemStack.EMPTY;
		}
		@Override
		public ItemStack removeItemNoUpdate(int index) {
			return ItemStack.EMPTY;
		}
		@Override
		public void setItem(int index, ItemStack itemStack) {
		}

		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public void setChanged() {
			PulseDividerBlockEntity.this.setChanged();
		}
		@Override
		public boolean stillValid(Player player) {
			return Container.stillValidBlockEntity(PulseDividerBlockEntity.this, player);
		}

		@Override
		public boolean canPlaceItem(int index, ItemStack itemStack) {
			return false;
		}

		@Override
		public void clearContent() {
		}
	};
	private final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case DATA_SIGNAL_STRENGTH -> PulseDividerBlockEntity.this.signalStrength;
				case DATA_EVERY_PULSE -> PulseDividerBlockEntity.this.everyPulse;
				case DATA_COUNT_PULSE -> PulseDividerBlockEntity.this.countPulse;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case DATA_SIGNAL_STRENGTH -> PulseDividerBlockEntity.this.signalStrength = value;
				case DATA_EVERY_PULSE -> PulseDividerBlockEntity.this.everyPulse = value;
			}
		}

		@Override
		public int getCount() {
			return NUM_DATA;
		}
	};

	//Configurable
	private int signalStrength = 15;
	private int everyPulse = 5;

	//Internal
	private int countPulse = 0;

	public PulseDividerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(RCBlockEntities.PULSE_DIVIDER.get(), blockPos, blockState);
	}

	public boolean applyPulse() {
		this.countPulse = (this.countPulse + 1) % this.everyPulse;
		return this.countPulse == 0;
	}

	private static final String TAG_CUSTOM_NAME = "CustomName";
	private static final String TAG_SIGNAL_STRENGTH = "SignalStrength";
	private static final String TAG_EVERY_PULSE = "EveryPulse";
	private static final String TAG_COUNT_PULSE = "CountPulse";

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		if(nbt.contains(TAG_CUSTOM_NAME, Tag.TAG_STRING)) {
			this.name = Component.Serializer.fromJson(nbt.getString(TAG_CUSTOM_NAME));
		}
		this.signalStrength = nbt.getInt(TAG_SIGNAL_STRENGTH);
		this.everyPulse = nbt.getInt(TAG_EVERY_PULSE);
		this.countPulse = nbt.getInt(TAG_COUNT_PULSE);
	}
	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (this.name != null) {
			nbt.putString(TAG_CUSTOM_NAME, Component.Serializer.toJson(this.name));
		}
		nbt.putInt(TAG_SIGNAL_STRENGTH, this.signalStrength);
		nbt.putInt(TAG_EVERY_PULSE, this.everyPulse);
		nbt.putInt(TAG_COUNT_PULSE, this.countPulse);
	}

	public void setCustomName(Component name) {
		this.name = name;
	}

	@Override
	public Component getName() {
		return this.name != null ? this.name : this.getDefaultName();
	}

	@Override
	public Component getDisplayName() {
		return this.getName();
	}

	@Override @Nullable
	public Component getCustomName() {
		return this.name;
	}

	protected Component getDefaultName() {
		return Component.translatable("block.redstoneclock.pulse_divider");
	}

	@Override @Nullable
	public PulseDividerMenu createMenu(int id, Inventory inventory, Player player) {
		if(this.level == null) {
			return null;
		}
		return new PulseDividerMenu(id, this.containerAccess, this.dataAccess, ContainerLevelAccess.create(this.level, this.worldPosition));
	}

	public int getSignalStrength() {
		return this.signalStrength;
	}
}
