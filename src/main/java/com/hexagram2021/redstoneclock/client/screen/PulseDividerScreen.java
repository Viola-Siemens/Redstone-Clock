package com.hexagram2021.redstoneclock.client.screen;

import com.hexagram2021.redstoneclock.common.block.entity.PulseDividerBlockEntity;
import com.hexagram2021.redstoneclock.common.menu.PulseDividerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public class PulseDividerScreen extends AbstractContainerScreen<PulseDividerMenu> {
	private static final ResourceLocation BG_LOCATION = new ResourceLocation(MODID, "textures/gui/pulse_divider.png");
	private static final Component TEXT_SIGNAL_STRENGTH = Component.translatable("screen.redstoneclock.pulse_divider.signal_strength");
	private static final Component TEXT_EVERY_PULSE = Component.translatable("screen.redstoneclock.pulse_divider.every_pulse");

	private static final PDButton[] BUTTONS = new PDButton[] {
			new PDButton(7, 24, 16, 16, 0, 88, 0),
			new PDButton(27, 24, 16, 16, 16, 88, 1),
			new PDButton(7, 56, 16, 16, 0, 88, 2),
			new PDButton(27, 56, 16, 16, 16, 88, 3)
	};

	public PulseDividerScreen(PulseDividerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 216;
		this.imageHeight = 88;
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
		transform.drawString(this.font, TEXT_EVERY_PULSE, 50, 60, 0x404040, false);

		transform.drawString(this.font, Component.literal(
				String.valueOf(this.menu.pulseDivider.get(PulseDividerBlockEntity.DATA_SIGNAL_STRENGTH))
		), 168, 28, 0xffffff, false);
		transform.drawString(this.font, Component.literal(
				String.valueOf(this.menu.pulseDivider.get(PulseDividerBlockEntity.DATA_EVERY_PULSE))
		), 168, 60, 0xffffff, false);
	}

	@Override
	protected void renderBg(GuiGraphics transform, float ticks, int x, int y) {
		this.renderBackground(transform);
		transform.blit(BG_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.renderButtons(transform, x, y);
	}

	private void renderButtons(GuiGraphics transform, int x, int y) {
		for(PDButton b: BUTTONS) {
			b.render(transform, this.leftPos, this.topPos, x, y);
		}
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		MultiPlayerGameMode gameMode = Objects.requireNonNull(Objects.requireNonNull(this.minecraft).gameMode);
		for(PDButton b: BUTTONS) {
			if(b.click(this.menu, gameMode, this.leftPos, this.topPos, x, y)) {
				return true;
			}
		}
		return super.mouseClicked(x, y, button);
	}
	@Override
	public boolean mouseReleased(double x, double y, int button) {
		MultiPlayerGameMode gameMode = Objects.requireNonNull(Objects.requireNonNull(this.minecraft).gameMode);
		for(PDButton b: BUTTONS) {
			if(b.release(this.menu, gameMode, this.leftPos, this.topPos, x, y)) {
				return true;
			}
		}
		return super.mouseReleased(x, y, button);
	}

	static class PDButton implements IButton<PulseDividerMenu> {
		private final int posX;
		private final int posY;
		private final int width;
		private final int height;
		private final int texX;
		private final int texY;
		private final int index;
		private boolean pressed;

		public PDButton(int posX, int posY, int width, int height, int texX, int texY, int index) {
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
		public boolean click(PulseDividerMenu menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y) {
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
		public boolean release(PulseDividerMenu menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y) {
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
