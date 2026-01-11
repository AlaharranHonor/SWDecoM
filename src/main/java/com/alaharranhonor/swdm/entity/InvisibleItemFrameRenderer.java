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
import org.joml.Matrix4f;

public class InvisibleItemFrameRenderer extends ItemFrameRenderer<InvisibleItemFrame> {

    private static final ResourceLocation FRAME_LOCATION = ModRef.res("textures/entity/invisible_item_frame.png");

    public InvisibleItemFrameRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(InvisibleItemFrame frame, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        Minecraft minecraft = Minecraft.getInstance();

        var event = new net.neoforged.neoforge.client.event.RenderNameTagEvent(frame, frame.getDisplayName(), this, pPoseStack, pBuffer, pPackedLight, pPartialTick);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);
        if (event.canRender().isTrue() || event.canRender().isDefault() && this.shouldShowName(frame)) {
            this.renderNameTag(frame, event.getContent(), pPoseStack, pBuffer, pPackedLight, pPartialTick);
        }

        pPoseStack.pushPose();
        Direction direction = frame.getDirection();
        Vec3 vec3 = this.getRenderOffset(frame, pPartialTick);
        pPoseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        double d0 = 0.46875D;
        pPoseStack.translate((double)direction.getStepX() * d0, (double)direction.getStepY() * d0, (double)direction.getStepZ() * d0);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(frame.getXRot()));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180 - frame.getYRot()));
        ItemStack placedItem = frame.getItem();

        boolean shouldRenderItem = !net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.client.event.RenderItemInFrameEvent(frame, this, pPoseStack, pBuffer, pPackedLight)).isCanceled();
        boolean isInvisible = frame.isInvisible() || !shouldRenderItem || !placedItem.isEmpty();
        if (!isInvisible) { // Render background frame
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(-90));
            pPoseStack.translate(-0.5D, -0.5D, -0.5D);
            this.renderFrame(pPoseStack, pBuffer.getBuffer(RenderType.text(FRAME_LOCATION)), pPackedLight);
            pPoseStack.popPose();
        }

        if (!placedItem.isEmpty()) { // Render placed item
            MapItemSavedData mapData = MapItem.getSavedData(placedItem, frame.level());
            pPoseStack.translate(0.0D, 0.0D, d0);

            int j = mapData != null ? frame.getRotation() % 4 * 2 : frame.getRotation();
            pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
            if (shouldRenderItem) {
                if (mapData != null) {
                    pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                    float f = 0.0078125F;
                    pPoseStack.scale(f, f, f);
                    pPoseStack.translate(-64.0D, -64.0D, 0.0D);
                    pPoseStack.translate(0.0D, 0.0D, -1.0D);
                    minecraft.gameRenderer.getMapRenderer().render(pPoseStack, pBuffer, frame.getFramedMapId(placedItem), mapData, true, pPackedLight);
                } else {
                    pPoseStack.scale(0.5F, 0.5F, 0.5F);
                    minecraft.getItemRenderer().renderStatic(placedItem, ItemDisplayContext.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, frame.level(), frame.getId());
                }
            }
        }

        pPoseStack.popPose();
    }

    private void renderFrame(PoseStack ms, VertexConsumer buf, int light) {
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, FRAME_LOCATION);
        Matrix4f matrix4f = ms.last().pose();
        buf.addVertex(matrix4f, 0F, 1, 0.01F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F).setLight(light);
        buf.addVertex(matrix4f, 0F, 0F, 0.01F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F).setLight(light);
        buf.addVertex(matrix4f, 1, 0F, 0.01F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F).setLight(light);
        buf.addVertex(matrix4f, 1, 1, 0.01F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F).setLight(light);

        buf.addVertex(matrix4f, 0F, 1, 0.01F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F).setLight(light);
        buf.addVertex(matrix4f, 1, 1, 0.01F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F).setLight(light);
        buf.addVertex(matrix4f, 1, 0F, 0.01F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F).setLight(light);
        buf.addVertex(matrix4f, 0F, 0F, 0.01F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F).setLight(light);
    }
}
