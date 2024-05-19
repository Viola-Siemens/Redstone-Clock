package com.hexagram2021.redstoneclock.common.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public final class RCItems {
	private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<BlockItem> REDSTONE_CLOCK = REGISTER.register("redstone_clock", () ->
			new BlockItem(RCBlocks.REDSTONE_CLOCK.get(), new Item.Properties()));

	private RCItems() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
