package com.alaharranhonor.swdm.multidoor;

import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.block.SwingingDoorBlock;
import com.alaharranhonor.swdm.block.entity.MultiDoorBlockEntity;
import com.alaharranhonor.swdm.util.ShapeHelper;
import com.alaharranhonor.swdm.util.ShapeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class MultiDoorRenderer implements BlockEntityRenderer<MultiDoorBlockEntity> {

    private static final Map<Direction, VoxelShape> DIRECTIONAL_SHAPES = ShapeHelper.createDirectionalMap(Block.box(0, 0, 14, 16, 16, 16));

    public MultiDoorRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(MultiDoorBlockEntity door, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        MultiDoorData doorData = door.getDoorData();
        BlockState state = door.getBlockState();
        MultiDoorBlock block = (MultiDoorBlock) state.getBlock();
        DoorHingeSide hinge = state.getValue(MultiDoorBlock.HINGE);
        Direction facing = block.getEffectiveFacingDirection(state);//state.getValue(MultiDoorBlock.FACING);
        Direction hingeOffset = block.getHingeOffset(state);
        VoxelShape baseShape = state.getShape(mc.level, door.getBlockPos());
        boolean mirror = (hinge == DoorHingeSide.LEFT) != (facing.getAxisDirection() == Direction.AxisDirection.NEGATIVE);

        // v This is a hack, it works, and I cannot be bothered figuring out the math.
        // If anyone finds this and can manage to put it nicely into a neat function please DM me so I can present my godhood to you.
        if (block instanceof SwingingDoorBlock && state.getValue(SwingingDoorBlock.OPEN) != MultiDoorBlock.DoorOpenState.CLOSED) {
            mirror = !mirror;
        }

        for (int w = 0; w < doorData.width(); w++) {
            for (int h = 0; h < doorData.height(); h++) {
                MultiDoorData.MultiDoorTexture texture = doorData.getTexture(w, h);
                VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityTranslucentCull(InventoryMenu.BLOCK_ATLAS));
                buffer = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture.texture()).wrap(buffer);

                // Offset by w blocks along the hinge direction and h blocks upward
                double offsetX = hingeOffset.getStepX() * w;
                double offsetY = (doorData.height() - 1) - h;
                double offsetZ = hingeOffset.getStepZ() * w;

                VoxelShape cellShape = baseShape.move(offsetX, offsetY, offsetZ);
                ShapeRenderer.renderBox(poseStack, buffer, cellShape.bounds(), texture.uvs(), 1, 1, 1, 1, packedLight, packedOverlay, mirror);
            }
        }
    }

    public static void renderHighlightOverlay(BlockState state, MultiDoorData doorData, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        MultiDoorBlock block = (MultiDoorBlock) state.getBlock();
        Direction facing = state.getValue(MultiDoorBlock.FACING);
        DoorHingeSide hinge = state.getValue(MultiDoorBlock.HINGE);
        Direction hingeOffset = block.getHingeOffset(state);
        float delta = Mth.sin((float) (mc.level.getGameTime() / 5.0 % (2 * Math.PI))) * 0.5f + 0.5f;
        float alpha = Mth.lerp(delta, 0.2f, 0.7f);
        VoxelShape baseShape = DIRECTIONAL_SHAPES.get(facing);
        boolean mirror = (hinge == DoorHingeSide.LEFT) != (facing.getAxisDirection() == Direction.AxisDirection.NEGATIVE);

        for (int w = 0; w < doorData.width(); w++) {
            for (int h = 0; h < doorData.height(); h++) {
                MultiDoorData.MultiDoorTexture texture = doorData.getTexture(w, h);
                VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityTranslucentCull(InventoryMenu.BLOCK_ATLAS));
                buffer = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture.texture()).wrap(buffer);

                // Offset by w blocks along the hinge direction and h blocks upward
                double offsetX = hingeOffset.getStepX() * w;
                double offsetY = (doorData.height() - 1) - h;
                double offsetZ = hingeOffset.getStepZ() * w;

                VoxelShape cellShape = baseShape.move(offsetX, offsetY, offsetZ);
                ShapeRenderer.renderBox(poseStack, buffer, cellShape.bounds(), texture.uvs(), 1, 1, 1, alpha, packedLight, packedOverlay, mirror);
            }
        }
    }

    @Override
    public int getViewDistance() {
        return (Minecraft.getInstance().options.renderDistance().get() + 1) * 16;
    }

    @Override
    public AABB getRenderBoundingBox(MultiDoorBlockEntity blockEntity) {
        BlockState state = blockEntity.getBlockState();
        MultiDoorBlock block = (MultiDoorBlock) state.getBlock();
        MultiDoorData doorData = blockEntity.getDoorData();
        VoxelShape shape = state.getShape(blockEntity.getLevel(), blockEntity.getBlockPos());
        return shape.bounds().move(blockEntity.getBlockPos())
            .expandTowards(new Vec3(block.getHingeOffset(state).step().mul(doorData.width(), 1, doorData.width())))
            .expandTowards(0, doorData.height(), 0);
    }
}
