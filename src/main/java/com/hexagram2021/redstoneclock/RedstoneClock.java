package com.hexagram2021.redstoneclock;

import com.hexagram2021.redstoneclock.client.RedstoneClockClient;
import com.hexagram2021.redstoneclock.common.RCContent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RedstoneClock.MODID)
public class RedstoneClock {
	public static final String MODID = "redstoneclock";

	public RedstoneClock() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		RCContent.modConstruction(bus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> RedstoneClockClient::modConstruction);
		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void setup(FMLCommonSetupEvent event) {
	}
}
