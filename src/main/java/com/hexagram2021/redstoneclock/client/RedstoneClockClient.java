package com.hexagram2021.redstoneclock.client;

import com.hexagram2021.redstoneclock.client.screen.RedstoneClockScreen;
import com.hexagram2021.redstoneclock.common.register.RCMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RedstoneClockClient {
	public static void modConstruction() {
	}

	@SubscribeEvent
	public static void setup(FMLClientSetupEvent event) {
		event.enqueueWork(RedstoneClockClient::registerContainersAndScreens);
	}

	private static void registerContainersAndScreens() {
		MenuScreens.register(RCMenuTypes.REDSTONE_CLOCK.get(), RedstoneClockScreen::new);
	}
}
