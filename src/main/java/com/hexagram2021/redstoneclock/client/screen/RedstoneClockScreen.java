package com.hexagram2021.redstoneclock.client.screen;

import com.hexagram2021.redstoneclock.common.menu.RedstoneClockMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public class RedstoneClockScreen extends AbstractContainerScreen<RedstoneClockMenu> {
	private static final ResourceLocation BG_LOCATION = new ResourceLocation(MODID, "textures/gui/redstone_clock.png");
	private static final Component TEXT_SIGNAL_STRENGTH = Component.translatable("screen.redstoneclock.redstone_clock.signal_strength");
	private static final Component TEXT_ACTIVE_INTERVAL = Component.translatable("screen.redstoneclock.redstone_clock.active_interval");
	private static final Component TEXT_IDLE_INTERVAL = Component.translatable("screen.redstoneclock.redstone_clock.idle_interval");

	public RedstoneClockScreen(RedstoneClockMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		--this.titleLabelY;
	}

	@Override
	protected void renderLabels(GuiGraphics transform, int x, int y) {
		transform.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
		transform.drawString(this.font, TEXT_SIGNAL_STRENGTH, 48, 26, 0x404040, false);
		transform.drawString(this.font, TEXT_ACTIVE_INTERVAL, 48, 58, 0x404040, false);
		transform.drawString(this.font, TEXT_IDLE_INTERVAL, 48, 90, 0x404040, false);
	}

	@Override
	protected void renderBg(GuiGraphics transform, float ticks, int x, int y) {
		//TODO
	}

	private void renderButtons(GuiGraphics transform, int x, int y) {
		//TODO
	}
}
