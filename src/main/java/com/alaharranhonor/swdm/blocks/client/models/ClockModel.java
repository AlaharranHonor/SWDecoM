package com.alaharranhonor.swdm.blocks.client.models;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.tileentity.ClockTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClockModel extends AnimatedGeoModel<ClockTE> {
	@Override
	public void setLivingAnimations(ClockTE entity, Integer uniqueID, AnimationEvent customPredicate) {

	}

	@Override
	public ResourceLocation getModelLocation(ClockTE object) {
		return new ResourceLocation(SWDM.MOD_ID, "geo/clock.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ClockTE object) {
		return new ResourceLocation(SWDM.MOD_ID, "textures/block/clock.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ClockTE animatable) {
		return new ResourceLocation(SWDM.MOD_ID, "animations/clock.animation.json");
	}
}
