package com.hexagram2021.redstoneclock.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.inventory.AbstractContainerMenu;

public interface IButton<T extends AbstractContainerMenu> {
	void render(GuiGraphics transform, int leftPos, int topPos, int x, int y);
	boolean click(T menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y);
	boolean release(T menu, MultiPlayerGameMode gameMode, int leftPos, int topPos, double x, double y);
}
