package com.hexagram2021.redstoneclock.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public final class RCCreativeModeTabs {
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	@SuppressWarnings("unused")
	public static RegistryObject<CreativeModeTab> ITEM_GROUP = REGISTER.register(
			"redstoneclock",
			() -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
					.title(Component.translatable("itemGroup.redstoneclock"))
					.icon(() -> new ItemStack(RCItems.REDSTONE_CLOCK.get()))
					.displayItems((flags, output) -> {
						output.accept(RCItems.REDSTONE_CLOCK.get());
						output.accept(RCItems.PULSE_DIVIDER.get());
					})
					.build()
	);

	private RCCreativeModeTabs() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
