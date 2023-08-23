package com.alaharranhonor.swdm.entity;

import com.alaharranhonor.swdm.ModRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.joml.Matrix4f;

public class InvisibleItemFrameRenderer extends ItemFrameRenderer<InvisibleItemFrame> {

    private static final ResourceLocation FRAME_LOCATION = ModRef.res("textures/entity/invisible_item_frame.png");

    public InvisibleItemFrameRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(InvisibleItemFrame pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        Minecraft minecraft = Minecraft.getInstance();

        var renderNameTagEvent = new net.minecraftforge.client.event.RenderNameTagEvent(pEntity, pEntity.getDisplayName(), this, pPoseStack, pBuffer, pPackedLight, pPartialTick);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameTagEvent);
        if (renderNameTagEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameTagEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(pEntity))) {
            this.renderNameTag(pEntity, renderNameTagEvent.getContent(), pPoseStack, pBuffer, pPackedLight);
        }

        pPoseStack.pushPose();
        Direction direction = pEntity.getDirection();
        Vec3 vec3 = this.getRenderOffset(pEntity, pPartialTick);
        pPoseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        double d0 = 0.46875D;
        pPoseStack.translate((double)direction.getStepX() * d0, (double)direction.getStepY() * d0, (double)direction.getStepZ() * d0);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pEntity.getXRot()));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180 - pEntity.getYRot()));
        ItemStack placedItem = pEntity.getItem();

        boolean shouldRenderItem = !MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderItemInFrameEvent(pEntity, this, pPoseStack, pBuffer, pPackedLight));
        boolean isInvisible = pEntity.isInvisible() || !shouldRenderItem || !placedItem.isEmpty();
        if (!isInvisible) { // Render background frame
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(-90));
            pPoseStack.translate(-0.5D, -0.5D, -0.5D);
            this.renderFrame(pPoseStack, pBuffer.getBuffer(RenderType.text(FRAME_LOCATION)), pPackedLight);
            pPoseStack.popPose();
        }

        if (!placedItem.isEmpty()) { // Render placed item
            MapItemSavedData mapData = MapItem.getSavedData(placedItem, pEntity.level());
            pPoseStack.translate(0.0D, 0.0D, d0);

            int j = mapData != null ? pEntity.getRotation() % 4 * 2 : pEntity.getRotation();
            pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
            if (shouldRenderItem) {
                if (mapData != null) {
                    pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                    float f = 0.0078125F;
                    pPoseStack.scale(f, f, f);
                    pPoseStack.translate(-64.0D, -64.0D, 0.0D);
                    Integer integer = MapItem.getMapId(placedItem);
                    pPoseStack.translate(0.0D, 0.0D, -1.0D);
                    minecraft.gameRenderer.getMapRenderer().render(pPoseStack, pBuffer, integer, mapData, true, pPackedLight);
                } else {
                    pPoseStack.scale(0.5F, 0.5F, 0.5F);
                    minecraft.getItemRenderer().renderStatic(placedItem, ItemDisplayContext.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pEntity.level(), pEntity.getId());
                }
            }
        }

        pPoseStack.popPose();
    }

    private void renderFrame(PoseStack ms, VertexConsumer buf, int light) {
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, FRAME_LOCATION);
        Matrix4f matrix4f = ms.last().pose();
        buf.vertex(matrix4f, 0F, 1, 0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(light).endVertex();
        buf.vertex(matrix4f, 0F, 0F, 0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(light).endVertex();
        buf.vertex(matrix4f, 1, 0F, 0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(light).endVertex();
        buf.vertex(matrix4f, 1, 1, 0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(light).endVertex();

        buf.vertex(matrix4f, 0F, 1, 0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(light).endVertex();
        buf.vertex(matrix4f, 1, 1, 0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(light).endVertex();
        buf.vertex(matrix4f, 1, 0F, 0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(light).endVertex();
        buf.vertex(matrix4f, 0F, 0F, 0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(light).endVertex();
    }
}
