package com.alaharranhonor.swdm.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ShapeRenderer {

    public static void renderBox(
        PoseStack poseStack, VertexConsumer buffer, AABB box, List<Float> uvs,
        float red, float green, float blue, float alpha,
        int light, int overlay,
        boolean mirror
    ) {
        renderBox(
            poseStack,
            buffer,
            (float) box.minX,
            (float) box.minY,
            (float) box.minZ,
            (float) box.maxX,
            (float) box.maxY,
            (float) box.maxZ,
            uvs,
            red,
            green,
            blue,
            alpha,
            light,
            overlay,
            mirror
        );
    }

    public static void renderBox(
        PoseStack poseStack, VertexConsumer buffer, AABB box,
        float red, float green, float blue, float alpha,
        int light, int overlay
    ) {
        renderBox(
            poseStack,
            buffer,
            (float) box.minX,
            (float) box.minY,
            (float) box.minZ,
            (float) box.maxX,
            (float) box.maxY,
            (float) box.maxZ,
            red,
            green,
            blue,
            alpha,
            red,
            green,
            blue,
            light,
            overlay
        );
    }

    public static void renderBox(
        PoseStack poseStack,
        VertexConsumer buffer,
        float minX,
        float minY,
        float minZ,
        float maxX,
        float maxY,
        float maxZ,
        float red,
        float green,
        float blue,
        float alpha,
        float red2,
        float green2,
        float blue2,
        int light,
        int overlay
    ) {
        PoseStack.Pose pose = poseStack.last();
        float sizeX = maxX - minX;
        float sizeY = maxY - minY;
        float sizeZ = maxZ - minZ;

        // Down (XZ plane, Y=minY)
        buffer.addVertex(pose, maxX, minY, minZ).setUv(sizeX, 0).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, minY, maxZ).setUv(sizeX, sizeZ).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, minY, maxZ).setUv(0, sizeZ).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, minY, minZ).setUv(0, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);

        // Up (XZ plane, Y=maxY)
        buffer.addVertex(pose, maxX, maxY, minZ).setUv(sizeX, 0).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, maxY, minZ).setUv(0, 0).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, maxY, maxZ).setUv(0, sizeZ).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, maxY, maxZ).setUv(sizeX, sizeZ).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);

        // North (XY plane, Z=minZ)
        buffer.addVertex(pose, minX, minY, minZ).setUv(0, sizeY).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, maxY, minZ).setUv(0, 0).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, maxY, minZ).setUv(sizeX, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, minY, minZ).setUv(sizeX, sizeY).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        // South (XY plane, Z=maxZ)
        buffer.addVertex(pose, minX, minY, maxZ).setUv(0, sizeY).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, minY, maxZ).setUv(sizeX, sizeY).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, maxY, maxZ).setUv(sizeX, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, maxY, maxZ).setUv(0, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        // West (ZY plane, X=minX)
        buffer.addVertex(pose, minX, minY, minZ).setUv(sizeZ, sizeY).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, minY, maxZ).setUv(0, sizeY).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, maxY, maxZ).setUv(0, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, minX, maxY, minZ).setUv(sizeZ, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);

        // East (ZY plane, X=maxX)
        buffer.addVertex(pose, maxX, minY, minZ).setUv(0, sizeY).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, maxY, minZ).setUv(0, 0).setColor(red, green2, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, maxY, maxZ).setUv(sizeZ, 0).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
        buffer.addVertex(pose, maxX, minY, maxZ).setUv(sizeZ, sizeY).setColor(red2, green, blue2, alpha).setLight(light).setOverlay(overlay).setNormal(pose, 0, 1, 0);
    }

    public static void renderBox(
        PoseStack poseStack,
        VertexConsumer buffer,
        float minX, float minY, float minZ,
        float maxX, float maxY, float maxZ,
        List<Float> uvs, // [u0,v0 (TL), u1,v1 (TR), u2,v2 (BR), u3,v3 (BL)]
        float red, float green, float blue, float alpha,
        int light, int overlay,
        boolean mirror // Global horizontal flip
    ) {
        PoseStack.Pose pose = poseStack.last();
        float sizeX = maxX - minX;
        float sizeY = maxY - minY;
        float sizeZ = maxZ - minZ;

        // 1. Define base UV coordinates from the list
        float uTL = mirror ? uvs.get(2) : uvs.get(0), vTL = mirror ? uvs.get(3) : uvs.get(1);
        float uTR = mirror ? uvs.get(0) : uvs.get(2), vTR = mirror ? uvs.get(1) : uvs.get(3);
        float uBR = mirror ? uvs.get(6) : uvs.get(4), vBR = mirror ? uvs.get(7) : uvs.get(5);
        float uBL = mirror ? uvs.get(4) : uvs.get(6), vBL = mirror ? uvs.get(5) : uvs.get(7);

        // --- South ---
        addVertex(buffer, pose, minX, maxY, maxZ, uTL * sizeX, vTL, red, green, blue, alpha, light, overlay, 0, 0, 1);
        addVertex(buffer, pose, minX, minY, maxZ, uBL * sizeX, vBL, red, green, blue, alpha, light, overlay, 0, 0, 1);
        addVertex(buffer, pose, maxX, minY, maxZ, uBR * sizeX, vBR, red, green, blue, alpha, light, overlay, 0, 0, 1);
        addVertex(buffer, pose, maxX, maxY, maxZ, uTR * sizeX, vTR, red, green, blue, alpha, light, overlay, 0, 0, 1);

        // --- North ---
        addVertex(buffer, pose, maxX, maxY, minZ, uTR * sizeX, vTR, red, green, blue, alpha, light, overlay, 0, 0, -1);
        addVertex(buffer, pose, maxX, minY, minZ, uBR * sizeX, vBR, red, green, blue, alpha, light, overlay, 0, 0, -1);
        addVertex(buffer, pose, minX, minY, minZ, uBL * sizeX, vBL, red, green, blue, alpha, light, overlay, 0, 0, -1);
        addVertex(buffer, pose, minX, maxY, minZ, uTL * sizeX, vTL, red, green, blue, alpha, light, overlay, 0, 0, -1);

        // --- West ---
        addVertex(buffer, pose, minX, maxY, maxZ, uTL * sizeZ, vTL, red, green, blue, alpha, light, overlay, -1, 0, 0);
        addVertex(buffer, pose, minX, maxY, minZ, uTR * sizeZ, vTR, red, green, blue, alpha, light, overlay, -1, 0, 0);
        addVertex(buffer, pose, minX, minY, minZ, uBR * sizeZ, vBR, red, green, blue, alpha, light, overlay, -1, 0, 0);
        addVertex(buffer, pose, minX, minY, maxZ, uBL * sizeZ, vBL, red, green, blue, alpha, light, overlay, -1, 0, 0);

        // --- East ---
        addVertex(buffer, pose, maxX, maxY, maxZ, uTL * sizeZ, vTL, red, green, blue, alpha, light, overlay, 1, 0, 0);
        addVertex(buffer, pose, maxX, minY, maxZ, uBL * sizeZ, vBL, red, green, blue, alpha, light, overlay, 1, 0, 0);
        addVertex(buffer, pose, maxX, minY, minZ, uBR * sizeZ, vBR, red, green, blue, alpha, light, overlay, 1, 0, 0);
        addVertex(buffer, pose, maxX, maxY, minZ, uTR * sizeZ, vTR, red, green, blue, alpha, light, overlay, 1, 0, 0);

        // --- Top ---
        addVertex(buffer, pose, minX, maxY, maxZ, uTL * sizeX, vTL * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, maxX, maxY, maxZ, uTR * sizeX, vTR * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, maxX, maxY, minZ, uBR * sizeX, vBR * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, minX, maxY, minZ, uBL * sizeX, vBL * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);

        // --- Bottom ---
        addVertex(buffer, pose, minX, minY, maxZ, uTL * sizeX, vTL * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, minX, minY, minZ, uBL * sizeX, vBL * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, maxX, minY, minZ, uBR * sizeX, vBR * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, maxX, minY, maxZ, uTR * sizeX, vTR * sizeZ, red, green, blue, alpha, light, overlay, 0, 1, 0);
    }

    /*public static void renderBox(
        PoseStack poseStack,
        VertexConsumer buffer,
        float minX, float minY, float minZ,
        float maxX, float maxY, float maxZ,
        List<Float> uvs, // [u0,v0, u1,v1, u2,v2, u3,v3] (TL, TR, BR, BL)
        float red, float green, float blue, float alpha,
        int light, int overlay
    ) {
        PoseStack.Pose pose = poseStack.last();
        float sizeX = maxX - minX;
        float sizeY = maxY - minY;
        float sizeZ = maxZ - minZ;

        // --- FRONT (South: Z = maxZ) ---
        // Mapping: BL(3), BR(2), TR(1), TL(0)
        addVertex(buffer, pose, minX, minY, maxZ, uvs.get(6), uvs.get(7), red, green, blue, alpha, light, overlay, 0, 0, 1);
        addVertex(buffer, pose, maxX, minY, maxZ, uvs.get(4), uvs.get(5), red, green, blue, alpha, light, overlay, 0, 0, 1);
        addVertex(buffer, pose, maxX, maxY, maxZ, uvs.get(2), uvs.get(3), red, green, blue, alpha, light, overlay, 0, 0, 1);
        addVertex(buffer, pose, minX, maxY, maxZ, uvs.get(0), uvs.get(1), red, green, blue, alpha, light, overlay, 0, 0, 1);

        // --- BACK (North: Z = minZ) ---
        // Mirrored mapping to keep the texture facing the right way on the back
        addVertex(buffer, pose, maxX, minY, minZ, uvs.get(6), uvs.get(7), red, green, blue, alpha, light, overlay, 0, 0, -1);
        addVertex(buffer, pose, minX, minY, minZ, uvs.get(4), uvs.get(5), red, green, blue, alpha, light, overlay, 0, 0, -1);
        addVertex(buffer, pose, minX, maxY, minZ, uvs.get(2), uvs.get(3), red, green, blue, alpha, light, overlay, 0, 0, -1);
        addVertex(buffer, pose, maxX, maxY, minZ, uvs.get(0), uvs.get(1), red, green, blue, alpha, light, overlay, 0, 0, -1);

        // --- TOP (Up: Y = maxY) ---
        addVertex(buffer, pose, minX, maxY, maxZ, uvs.get(0), uvs.get(1), red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, maxX, maxY, maxZ, uvs.get(2), uvs.get(3), red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, maxX, maxY, minZ, uvs.get(4), uvs.get(5), red, green, blue, alpha, light, overlay, 0, 1, 0);
        addVertex(buffer, pose, minX, maxY, minZ, uvs.get(6), uvs.get(7), red, green, blue, alpha, light, overlay, 0, 1, 0);

        // --- BOTTOM (Down: Y = minY) ---
        addVertex(buffer, pose, minX, minY, minZ, uvs.get(0), uvs.get(1), red, green, blue, alpha, light, overlay, 0, -1, 0);
        addVertex(buffer, pose, maxX, minY, minZ, uvs.get(2), uvs.get(3), red, green, blue, alpha, light, overlay, 0, -1, 0);
        addVertex(buffer, pose, maxX, minY, maxZ, uvs.get(4), uvs.get(5), red, green, blue, alpha, light, overlay, 0, -1, 0);
        addVertex(buffer, pose, minX, minY, maxZ, uvs.get(6), uvs.get(7), red, green, blue, alpha, light, overlay, 0, -1, 0);

        // --- LEFT (West: X = minX) ---
        addVertex(buffer, pose, minX, minY, minZ, uvs.get(6), uvs.get(7), red, green, blue, alpha, light, overlay, -1, 0, 0);
        addVertex(buffer, pose, minX, minY, maxZ, uvs.get(4), uvs.get(5), red, green, blue, alpha, light, overlay, -1, 0, 0);
        addVertex(buffer, pose, minX, maxY, maxZ, uvs.get(2), uvs.get(3), red, green, blue, alpha, light, overlay, -1, 0, 0);
        addVertex(buffer, pose, minX, maxY, minZ, uvs.get(0), uvs.get(1), red, green, blue, alpha, light, overlay, -1, 0, 0);

        // --- RIGHT (East: X = maxX) ---
        addVertex(buffer, pose, maxX, minY, maxZ, uvs.get(6), uvs.get(7), red, green, blue, alpha, light, overlay, 1, 0, 0);
        addVertex(buffer, pose, maxX, minY, minZ, uvs.get(4), uvs.get(5), red, green, blue, alpha, light, overlay, 1, 0, 0);
        addVertex(buffer, pose, maxX, maxY, minZ, uvs.get(2), uvs.get(3), red, green, blue, alpha, light, overlay, 1, 0, 0);
        addVertex(buffer, pose, maxX, maxY, maxZ, uvs.get(0), uvs.get(1), red, green, blue, alpha, light, overlay, 1, 0, 0);
    }*/

    // Helper to keep code clean
    private static void addVertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, float z, float u, float v, float r, float g, float b, float a, int light, int overlay, int nx, int ny, int nz) {
        buffer.addVertex(pose, x, y, z)
            .setUv(u, v)
            .setColor(r, g, b, a)
            .setLight(light)
            .setOverlay(overlay)
            .setNormal(pose, nx, ny, nz);
    }
}