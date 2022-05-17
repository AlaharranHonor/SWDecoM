package com.alaharranhonor.swdm.tileentity;

import com.alaharranhonor.swdm.util.ModEventBusSubscriber;
import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ClockTE extends TileEntity implements IAnimatable {
	public ClockTE() {
		super(ModEventBusSubscriber.CLOCK_TE);
	}

	@Override
	public TileEntityType<?> getType() {
		return ModEventBusSubscriber.CLOCK_TE;
	}

	@Override
	public void registerControllers(AnimationData data) {

	}

	@Override
	public AnimationFactory getFactory() {
		return null;
	}
}
