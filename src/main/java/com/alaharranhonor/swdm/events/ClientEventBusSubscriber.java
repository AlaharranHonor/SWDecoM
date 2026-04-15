package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.entity.InvisibleItemFrameRenderer;
import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.multidoor.*;
import com.alaharranhonor.swdm.registry.*;
import com.alaharranhonor.swdm.workshop.DecoWorkshopScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event) {
        ClientEventBusSubscriber.setRenderLayers();
        SetSetup.SETS.forEach(set -> set.genTypes.forEach(GenType::setupClient));

        Sheets.addWoodType(WoodSetup.WHITEWASH);
        Sheets.addWoodType(WoodSetup.THATCH);
        //Sheets.addWoodType(WoodSetup.BAMBOO);
    }

    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuSetup.DECO_WORKSHOP.get(), DecoWorkshopScreen::new);
        event.register(MenuSetup.DOOR_HINGE.get(), DoorHingeScreen::new);
    }

    @SubscribeEvent
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockSetup.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        event.registerEntityRenderer(EntitySetup.INVISIBLE_ITEM_FRAME.get(), InvisibleItemFrameRenderer::new);
        //event.registerEntityRenderer(EntitySetup.MIRROR_PAINTING.get(), PaintingRenderer::new);
        event.registerBlockEntityRenderer(BlockSetup.MULTI_DOOR.get(), MultiDoorRenderer::new);
    }

    @SubscribeEvent
    public static void registerClientTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MultiDoorData.class, ClientMultiDoorTooltip::new);
    }


    @SubscribeEvent
    private static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        SetSetup.SETS.forEach(set -> {
            if (set.getBlockColors() != null) {
                set.genTypes.forEach(type -> type.registerBlockColors(colors, set.getBlockColors()));
            }
        });
    }

    @SubscribeEvent
    private static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        ItemColors colors = event.getItemColors();
        SetSetup.SETS.forEach(set -> {
            if (set.getBlockColors() != null) {
                set.genTypes.forEach(type -> type.registerItemColors(colors, set.getItemColors()));
            }
        });
    }

    @SubscribeEvent
    private static void renderHeldItemHighlight(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.getItem() instanceof MultiDoorBlockItem item) {
            renderMultiDoorPlacement(mc, event.getPoseStack(), player, heldItem, item);
        } else if (heldItem.is(ItemSetup.DOOR_HINGE)) {
            renderDoorHingeSelection(mc, event.getPoseStack(), player, heldItem);
        }

    }

    private static void renderDoorHingeSelection(Minecraft mc, PoseStack poseStack, Player player, ItemStack held) {
        if (!held.has(DataComponentSetup.HINGE_SELECTION)) {
            return;
        }

        HingeData hingeData = held.get(DataComponentSetup.HINGE_SELECTION);
        RenderBuffers renderBuffers = mc.renderBuffers();
        BlockPos pos = player.blockPosition();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        Vec3 renderPos = new Vec3(-camPos.x, -camPos.y, -camPos.z);

        BlockPos startPos = hingeData.start();
        BlockPos endPos = startPos;

        if (hingeData.isValid()) {
            endPos = hingeData.end();
        } else {
            HitResult hitResult = mc.hitResult;
            if (hitResult instanceof BlockHitResult blockHitResult && blockHitResult.getType() == HitResult.Type.BLOCK) {
                endPos = blockHitResult.getBlockPos();
            }
        }

        poseStack.pushPose();
        poseStack.translate(renderPos.x, renderPos.y, renderPos.z);
        LevelRenderer.renderLineBox(poseStack, renderBuffers.bufferSource().getBuffer(RenderType.lines()), AABB.encapsulatingFullBlocks(startPos, endPos), 1, 1, 1, 1);
        poseStack.popPose();

    }

    private static void renderMultiDoorPlacement(Minecraft mc, PoseStack poseStack, Player player, ItemStack held, MultiDoorBlockItem item) {
        if (!held.has(DataComponentSetup.MULTI_DOOR)) {
            return;
        }

        RenderBuffers renderBuffers = mc.renderBuffers();
        BlockPos pos = player.blockPosition();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        Vec3 renderPos = new Vec3(-camPos.x, -camPos.y, -camPos.z);
        HitResult hitResult = mc.hitResult;
        if (hitResult instanceof BlockHitResult blockHitResult && blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPlaceContext context = new BlockPlaceContext(player, InteractionHand.MAIN_HAND, held, blockHitResult);
            BlockState state = item.getPlacementState(context);
            if (state == null) return;
            MultiDoorData doorData = held.get(DataComponentSetup.MULTI_DOOR);
            BlockPos hitPos = context.getClickedPos();
            renderPos = renderPos.add(hitPos.getX(), hitPos.getY(), hitPos.getZ());

            poseStack.pushPose();
            poseStack.translate(renderPos.x, renderPos.y, renderPos.z);
            MultiDoorRenderer.renderHighlightOverlay(state, doorData, poseStack, renderBuffers.bufferSource(), LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

    public static void setRenderLayers() {
        SetSetup.SETS.forEach(set -> set.genTypes.forEach(type -> type.setRenderType(set.getRenderType().get())));
    }
}
