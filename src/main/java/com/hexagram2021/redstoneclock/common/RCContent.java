package com.hexagram2021.redstoneclock.common;

import com.hexagram2021.redstoneclock.common.register.*;
import net.minecraftforge.eventbus.api.IEventBus;

public final class RCContent {
	public static void modConstruction(IEventBus bus) {
		RCBlocks.init(bus);
		RCItems.init(bus);
		RCBlockEntities.init(bus);
		RCMenuTypes.init(bus);
		RCCreativeModeTabs.init(bus);
	}

	private RCContent() {
	}
}
