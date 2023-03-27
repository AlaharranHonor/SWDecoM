package com.alaharranhonor.swdm.block.entity;

import com.alaharranhonor.swdm.util.ModEventBusSubscriber;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ClockBE extends BlockEntity implements IAnimatable {

	public ClockBE(BlockPos pWorldPosition, BlockState pBlockState) {
		this(ModEventBusSubscriber.CLOCK_TE, pWorldPosition, pBlockState);
	}
	public ClockBE(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	@Override
	public void registerControllers(AnimationData data) {

	}

	@Override
	public AnimationFactory getFactory() {
		return null;
	}
}
