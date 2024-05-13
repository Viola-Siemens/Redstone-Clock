package com.hexagram2021.redstoneclock.common.block.entity;

import com.hexagram2021.redstoneclock.common.block.RedstoneClockBlock;
import com.hexagram2021.redstoneclock.common.menu.RedstoneClockMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class RedstoneClockBlockEntity extends BlockEntity implements MenuProvider, Nameable {
	public static final int NUM_SLOT = 0;
	public static final int NUM_DATA = 3;
	public static final int DATA_SIGNAL_STRENGTH = 0;
	public static final int DATA_ACTIVE_INTERVAL = 1;
	public static final int DATA_IDLE_INTERVAL = 2;

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
			RedstoneClockBlockEntity.this.setChanged();
		}
		@Override
		public boolean stillValid(Player player) {
			return Container.stillValidBlockEntity(RedstoneClockBlockEntity.this, player);
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
				case DATA_SIGNAL_STRENGTH -> RedstoneClockBlockEntity.this.signalStrength;
				case DATA_ACTIVE_INTERVAL -> RedstoneClockBlockEntity.this.activeInterval;
				case DATA_IDLE_INTERVAL -> RedstoneClockBlockEntity.this.idleInterval;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case DATA_SIGNAL_STRENGTH -> RedstoneClockBlockEntity.this.signalStrength = value;
				case DATA_ACTIVE_INTERVAL -> RedstoneClockBlockEntity.this.activeInterval = value;
				case DATA_IDLE_INTERVAL -> RedstoneClockBlockEntity.this.idleInterval = value;
			}
		}

		@Override
		public int getCount() {
			return NUM_DATA;
		}
	};

	//Configurable
	private int signalStrength = 15;
	private int activeInterval = 2;
	private int idleInterval = 6;

	//Internal
	private int cyclicTick = 0;

	public RedstoneClockBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(RCBlockEntities.REDSTONE_CLOCK.get(), blockPos, blockState);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState blockState, RedstoneClockBlockEntity blockEntity) {
		if(RedstoneClockBlock.hasNeighborSignal(level, pos, blockState)) {
			int totalInterval = blockEntity.activeInterval + blockEntity.idleInterval;
			blockEntity.cyclicTick += 1;
			if (blockEntity.cyclicTick >= totalInterval) {
				blockEntity.cyclicTick = 0;
			}
			if (blockEntity.cyclicTick == 0) {
				level.setBlock(pos, blockState.setValue(RedstoneClockBlock.LIT, true), Block.UPDATE_ALL);
			} else if (blockEntity.cyclicTick == blockEntity.activeInterval) {
				level.setBlock(pos, blockState.setValue(RedstoneClockBlock.LIT, false), Block.UPDATE_ALL);
			}
		}
	}

	private static final String TAG_CUSTOM_NAME = "CustomName";
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		if(nbt.contains(TAG_CUSTOM_NAME, Tag.TAG_STRING)) {
			this.name = Component.Serializer.fromJson(nbt.getString(TAG_CUSTOM_NAME));
		}
	}
	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (this.name != null) {
			nbt.putString(TAG_CUSTOM_NAME, Component.Serializer.toJson(this.name));
		}
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
		return Component.translatable("block.redstoneclock.redstone_clock");
	}

	@Override
	public RedstoneClockMenu createMenu(int id, Inventory inventory, Player player) {
		return new RedstoneClockMenu(id, this.containerAccess, this.dataAccess);
	}

	public int getSignalStrength() {
		return this.signalStrength;
	}
}
