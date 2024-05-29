package com.hexagram2021.redstoneclock.common.register;

import com.hexagram2021.redstoneclock.common.menu.PulseDividerMenu;
import com.hexagram2021.redstoneclock.common.menu.RedstoneClockMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public final class RCMenuTypes {
	private static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

	public static final RegistryObject<MenuType<RedstoneClockMenu>> REDSTONE_CLOCK = REGISTER.register(
			"redstone_clock", () -> new MenuType<>((id, inventory) -> new RedstoneClockMenu(id), FeatureFlags.VANILLA_SET)
	);
	public static final RegistryObject<MenuType<PulseDividerMenu>> PULSE_DIVIDER = REGISTER.register(
			"pulse_divider", () -> new MenuType<>((id, inventory) -> new PulseDividerMenu(id), FeatureFlags.VANILLA_SET)
	);

	private RCMenuTypes() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
