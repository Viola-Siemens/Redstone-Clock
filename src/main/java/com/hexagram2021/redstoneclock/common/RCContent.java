package com.hexagram2021.redstoneclock.common;

import com.hexagram2021.redstoneclock.common.register.RCBlockEntities;
import com.hexagram2021.redstoneclock.common.register.RCBlocks;
import com.hexagram2021.redstoneclock.common.register.RCMenuTypes;
import net.minecraftforge.eventbus.api.IEventBus;

public final class RCContent {
	public static void modConstruction(IEventBus bus) {
		RCBlocks.init(bus);
		RCBlockEntities.init(bus);
		RCMenuTypes.init(bus);
	}

	private RCContent() {
	}
}
