package com.hexagram2021.redstoneclock.common.register;

import com.google.common.collect.ImmutableSet;
import com.hexagram2021.redstoneclock.common.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

@SuppressWarnings("DataFlowIssue")
public final class RCBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

	public static final RegistryObject<BlockEntityType<RedstoneClockBlockEntity>> REDSTONE_CLOCK = REGISTER.register(
			"redstone_clock", () -> new BlockEntityType<>(
					RedstoneClockBlockEntity::new, ImmutableSet.of(RCBlocks.REDSTONE_CLOCK.get()), null
			)
	);
	public static final RegistryObject<BlockEntityType<PulseDividerBlockEntity>> PULSE_DIVIDER = REGISTER.register(
			"pulse_divider", () -> new BlockEntityType<>(
					PulseDividerBlockEntity::new, ImmutableSet.of(RCBlocks.PULSE_DIVIDER.get()), null
			)
	);

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
