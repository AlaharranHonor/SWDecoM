package com.alaharranhonor.swdm.client.renderers;

import com.alaharranhonor.swdm.client.models.ClockModel;
import com.alaharranhonor.swdm.tileentity.ClockTE;
import com.alaharranhonor.swdm.util.TimeUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.shadowed.eliotlash.mclib.utils.Interpolations;

public class ClockTileRenderer extends GeoBlockRenderer<ClockTE> {
	public ClockTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new ClockModel());
	}

	@Override
	public void render(TileEntity tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		GeoModel clock = this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation((ClockTE) tile));
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
