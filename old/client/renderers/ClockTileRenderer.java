package com.alaharranhonor.swdm.old.client.renderers;

import com.alaharranhonor.swdm.old.block.entity.ClockBE;
import com.alaharranhonor.swdm.old.client.models.ClockModel;
import com.alaharranhonor.swdm.old.util.TimeUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.shadowed.eliotlash.mclib.utils.Interpolations;

public class ClockTileRenderer extends GeoBlockRenderer<ClockBE> {
	public ClockTileRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		super(rendererDispatcherIn, new ClockModel());
	}



	@Override
	public void render(BlockEntity tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		GeoModel clock = this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation((ClockBE) tile));
		GeoBone minuteHand = clock.getBone("MinuteHand").get();
		GeoBone hourHand = clock.getBone("HourHand").get();

		float magicNumber = 6.315f;

		minuteHand.setRotationZ(
			(float) Interpolations.lerp(
				Math.toRadians(0),
				Math.toRadians(360),
				Math.toRadians(TimeUtil.getMinuteHandTurnDegrees((int) (tile.getLevel().getDayTime() % 24000))) / magicNumber
				));
		hourHand.setRotationZ(
			(float) Interpolations.lerp(
				Math.toRadians(0),
				Math.toRadians(360),
				Math.toRadians(TimeUtil.getHourHandTurnDegrees((int) (tile.getLevel().getDayTime() % 24000))) / magicNumber
			));
		super.render(tile, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}
