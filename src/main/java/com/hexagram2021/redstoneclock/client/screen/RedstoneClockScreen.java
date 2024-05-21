package com.hexagram2021.redstoneclock.client.screen;

import com.hexagram2021.redstoneclock.common.block.entity.RedstoneClockBlockEntity;
import com.hexagram2021.redstoneclock.common.menu.RedstoneClockMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public class RedstoneClockScreen extends AbstractContainerScreen<RedstoneClockMenu> {
	private static final ResourceLocation BG_LOCATION = new ResourceLocation(MODID, "textures/gui/redstone_clock.png");
	private static final Component TEXT_SIGNAL_STRENGTH = Component.translatable("screen.redstoneclock.redstone_clock.signal_strength");
	private static final Component TEXT_ACTIVE_INTERVAL = Component.translatable("screen.redstoneclock.redstone_clock.active_interval");
	private static final Component TEXT_IDLE_INTERVAL = Component.translatable("screen.redstoneclock.redstone_clock.idle_interval");

	private static final RCButton[] BUTTONS = new RCButton[] {
			new RCButton(7, 24, 16, 16, 0, 136, 0),
			new RCButton(27, 24, 16, 16, 16, 136, 1),
			new RCButton(7, 56, 16, 16, 0, 136, 2),
			new RCButton(27, 56, 16, 16, 16, 136, 3),
			new RCButton(7, 88, 16, 16, 0, 136, 4),
			new RCButton(27, 88, 16, 16, 16, 136, 5),
			new RCButton(116, 112, 40, 16, 32, 136, 6) {
				@Override
				public boolean release(RedstoneClockMenu menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y) {
					boolean ret = super.release(menu, gameMode, leftPos, topPos, x, y);
					if(ret) {
						menu.setNextMultiplier();
					}
					return ret;
				}
			}
	};

	public RedstoneClockScreen(RedstoneClockMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 216;
		this.imageHeight = 136;
	}

	@Override
	public void render(GuiGraphics transform, int x, int y, float partialTicks) {
		super.render(transform, x, y, partialTicks);
		this.renderTooltip(transform, x, y);
	}

	@Override
	protected void renderLabels(GuiGraphics transform, int x, int y) {
		transform.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
		transform.drawString(this.font, TEXT_SIGNAL_STRENGTH, 50, 28, 0x404040, false);
		transform.drawString(this.font, TEXT_ACTIVE_INTERVAL, 50, 60, 0x404040, false);
		transform.drawString(this.font, TEXT_IDLE_INTERVAL, 50, 92, 0x404040, false);

		transform.drawString(this.font, Component.literal(
				String.valueOf(this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_SIGNAL_STRENGTH))
		), 168, 28, 0xffffff, false);
		int activeInterval = this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL);
		transform.drawString(this.font, Component.literal(
				activeInterval >= 10000 ? (activeInterval / 1000) + "." + ((activeInterval / 100) % 10) + "k" : String.valueOf(activeInterval)
		), 168, 60, 0xffffff, false);
		int idleInterval = this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_IDLE_INTERVAL);
		transform.drawString(this.font, Component.literal(
				idleInterval >= 10000 ? (idleInterval / 1000) + "." + ((idleInterval / 100) % 10) + "k" : String.valueOf(idleInterval)
		), 168, 92, 0xffffff, false);
		String multi = "*" + RedstoneClockMenu.toMultiplier(this.menu.getMultiplier());
		transform.drawString(this.font, Component.literal(multi), 152 - this.font.width(multi), 117, 0x404040, false);
		Component multiText = Component.translatable("screen.redstoneclock.redstone_clock.multiplier");
		transform.drawString(this.font, multiText, 114 - this.font.width(multiText), 116, 0xa0a0a0, false);
	}

	@Override
	protected void renderBg(GuiGraphics transform, float ticks, int x, int y) {
		transform.blit(BG_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.renderButtons(transform, x, y);
		this.renderSignals(transform);
	}

	private void renderButtons(GuiGraphics transform, int x, int y) {
		for(RCButton b: BUTTONS) {
			b.render(transform, this.leftPos, this.topPos, x, y);
		}
	}
	private void renderSignals(GuiGraphics transform) {
		int cyclicTick = this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_CYCLIC_TICK);
		if(cyclicTick < 0) {
			return;
		}

		transform.blit(BG_LOCATION, this.leftPos + 160, this.topPos + 6, 216, 0, 10, 10);
		int activeInterval = this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL);
		if(cyclicTick < activeInterval) {
			transform.blit(BG_LOCATION, this.leftPos + 176, this.topPos + 6, 216, 0, 10, 10);
		}
	}

	@Override
	protected void renderTooltip(GuiGraphics transform, int x, int y) {
		super.renderTooltip(transform, x, y);

		int logicX = x - this.leftPos;
		int logicY = y - this.topPos;
		if(logicX >= 166 && logicX < 206) {
			int interval;
			if(logicY >= 57 && logicY < 71) {
				interval = this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_ACTIVE_INTERVAL);
			} else if(logicY >= 89 && logicY < 103) {
				interval = this.menu.redstoneClock.get(RedstoneClockBlockEntity.DATA_IDLE_INTERVAL);
			} else {
				return;
			}
			int ms = interval % 20 * 50;
			int s = interval / 20;
			int m = s / 60;
			int h = m / 60;
			m %= 60;
			s %= 60;
			transform.renderTooltip(this.font, List.of(
					Component.translatable("screen.redstoneclock.redstone_clock.tooltip.total_ticks", interval),
					Component.translatable("screen.redstoneclock.redstone_clock.tooltip.time", h, m, s, ms)
			), Optional.empty(), x, y);
		}
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		MultiPlayerGameMode gameMode = Objects.requireNonNull(Objects.requireNonNull(this.minecraft).gameMode);
		for(RCButton b: BUTTONS) {
			if(b.click(this.menu, gameMode, this.leftPos, this.topPos, x, y)) {
				return true;
			}
		}
		return super.mouseClicked(x, y, button);
	}
	@Override
	public boolean mouseReleased(double x, double y, int button) {
		MultiPlayerGameMode gameMode = Objects.requireNonNull(Objects.requireNonNull(this.minecraft).gameMode);
		for(RCButton b: BUTTONS) {
			if(b.release(this.menu, gameMode, this.leftPos, this.topPos, x, y)) {
				return true;
			}
		}
		return super.mouseReleased(x, y, button);
	}

	static class RCButton implements IButton<RedstoneClockMenu> {
		private final int posX;
		private final int posY;
		private final int width;
		private final int height;
		private final int texX;
		private final int texY;
		private final int index;
		private boolean pressed;

		public RCButton(int posX, int posY, int width, int height, int texX, int texY, int index) {
			this.posX = posX;
			this.posY = posY;
			this.width = width;
			this.height = height;
			this.texX = texX;
			this.texY = texY;
			this.index = index;
			this.pressed = false;
		}

		@Override
		public void render(GuiGraphics transform, int leftPos, int topPos, int x, int y) {
			int buttonTexYAddition = 0;
			int screenX = leftPos + this.posX;
			int screenY = topPos + this.posY;
			if(x >= screenX && x < screenX + this.width && y >= screenY && y < screenY + this.height) {
				buttonTexYAddition = this.pressed ? this.height : this.height * 2;
			}
			transform.blit(BG_LOCATION, screenX, screenY, this.texX, this.texY + buttonTexYAddition, this.width, this.height);
		}
		@Override
		public boolean click(RedstoneClockMenu menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y) {
			int screenX = leftPos + this.posX;
			int screenY = topPos + this.posY;
			if(x >= screenX && x < screenX + this.width && y >= screenY && y < screenY + this.height) {
				Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
				this.pressed = true;
				return true;
			}
			return false;
		}
		@Override
		public boolean release(RedstoneClockMenu menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y) {
			int screenX = leftPos + this.posX;
			int screenY = topPos + this.posY;
			if(x >= screenX && x < screenX + this.width && y >= screenY && y < screenY + this.height) {
				if(this.pressed) {
					this.pressed = false;
					gameMode.handleInventoryButtonClick(menu.containerId, this.index);
					return true;
				}
			}
			return false;
		}
	}
}
