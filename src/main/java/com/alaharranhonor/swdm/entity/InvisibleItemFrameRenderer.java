package com.alaharranhonor.swdm.entity;

import com.alaharranhonor.swdm.SWDM;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

public class InvisibleItemFrameRenderer extends ItemFrameRenderer<InvisibleItemFrame> {

    private static final ResourceLocation FRAME_LOCATION = SWDM.res("textures/entity/invisible_item_frame.png");

    public InvisibleItemFrameRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(InvisibleItemFrame frame, float yaw, float partialTicks, PoseStack ms, MultiBufferSource buf, int light) {
        Minecraft minecraft = Minecraft.getInstance();

        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(frame, frame.getDisplayName(), this, ms, buf, light, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(frame))) {
            this.renderNameTag(frame, renderNameplateEvent.getContent(), ms, buf, light);
        }

        ms.pushPose();
        Direction direction = frame.getDirection();
        Vec3 vec3 = this.getRenderOffset(frame, partialTicks);
        ms.translate(-vec3.x(), -vec3.y(), -vec3.z());
        double d0 = 0.46875D;
        ms.translate((double)direction.getStepX() * d0, (double)direction.getStepY() * d0, (double)direction.getStepZ() * d0);
        ms.mulPose(Vector3f.XP.rotationDegrees(frame.getXRot()));
        ms.mulPose(Vector3f.YP.rotationDegrees(180 - frame.getYRot()));
        ItemStack placedItem = frame.getItem();

        boolean shouldRenderItem = !MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderItemInFrameEvent(frame, this, ms, buf, light));
        boolean isInvisible = frame.isInvisible() || !shouldRenderItem || !placedItem.isEmpty();
        if (!isInvisible) { // Render background frame
            ms.pushPose();
            ms.mulPose(Vector3f.YP.rotationDegrees(180));
            ms.mulPose(Vector3f.ZP.rotationDegrees(-90));
            ms.translate(-0.5D, -0.5D, -0.5D);
            this.renderFrame(ms, buf.getBuffer(RenderType.text(FRAME_LOCATION)), light);
            ms.popPose();
        }

        if (!placedItem.isEmpty()) { // Render placed item
            MapItemSavedData mapData = MapItem.getSavedData(placedItem, frame.level);
            ms.translate(0.0D, 0.0D, d0);

            int j = mapData != null ? frame.getRotation() % 4 * 2 : frame.getRotation();
            ms.mulPose(Vector3f.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
            if (shouldRenderItem) {
                if (mapData != null) {
                    ms.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                    float f = 0.0078125F;
                    ms.scale(f, f, f);
                    ms.translate(-64.0D, -64.0D, 0.0D);
                    Integer integer = MapItem.getMapId(placedItem);
                    ms.translate(0.0D, 0.0D, -1.0D);
                    minecraft.gameRenderer.getMapRenderer().render(ms, buf, integer, mapData, true, light);
                } else {
                    ms.scale(0.5F, 0.5F, 0.5F);
                    minecraft.getItemRenderer().renderStatic(placedItem, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, ms, buf, frame.getId());
                }
            }
        }

        ms.popPose();
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
