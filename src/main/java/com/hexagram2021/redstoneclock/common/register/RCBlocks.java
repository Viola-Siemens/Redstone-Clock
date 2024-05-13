package com.hexagram2021.redstoneclock.common.register;

import com.hexagram2021.redstoneclock.common.block.RedstoneClockBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.redstoneclock.RedstoneClock.MODID;

public final class RCBlocks {
	private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	public static final RegistryObject<RedstoneClockBlock> REDSTONE_CLOCK = REGISTER.register("redstone_clock", () -> new RedstoneClockBlock(
			BlockBehaviour.Properties.of()
					.lightLevel(blockState -> blockState.getValue(RedstoneClockBlock.LIT) ? 7 : 0)
					.sound(SoundType.METAL).pushReaction(PushReaction.BLOCK)
					.isValidSpawn((BlockState blockState, BlockGetter level, BlockPos blockPos, EntityType<?> entityType) -> false)
	));

	private RCBlocks() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
