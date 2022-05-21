package com.alaharranhonor.swdm.client.models;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.entity.ClockBE;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClockModel extends AnimatedGeoModel<ClockBE> {
	@Override
	public void setLivingAnimations(ClockBE entity, Integer uniqueID, AnimationEvent customPredicate) {

	}

	@Override
	public ResourceLocation getModelLocation(ClockBE object) {
		return new ResourceLocation(SWDM.MOD_ID, "geo/clock.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ClockBE object) {
		return new ResourceLocation(SWDM.MOD_ID, "textures/block/clock.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ClockBE animatable) {
		return new ResourceLocation(SWDM.MOD_ID, "animations/clock.animation.json");
	}
}
