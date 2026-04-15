package com.alaharranhonor.swdm.multidoor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;

import java.util.List;

public class ClientMultiDoorTooltip implements ClientTooltipComponent {

    private static final int CELL_SIZE = 16; // Size of each door segment in pixels
    private final MultiDoorData data;

    public ClientMultiDoorTooltip(MultiDoorData data) {
        this.data = data;
    }

    @Override
    public int getHeight() {
        return data.height() * CELL_SIZE;
    }

    @Override
    public int getWidth(Font font) {
        return data.width() * CELL_SIZE;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        for (int row = 0; row < data.height(); row++) {
            for (int col = 0; col < data.width(); col++) {
                MultiDoorData.MultiDoorTexture doorTex = data.getTexture(col, row);
                renderCell(guiGraphics, x + (col * CELL_SIZE), y + (row * CELL_SIZE), doorTex);
            }
        }
    }

    private void renderCell(GuiGraphics guiGraphics, int x, int y, MultiDoorData.MultiDoorTexture doorTex) {
        List<Float> uvs = doorTex.uvs();
        if (uvs.size() < 8) return;

        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();

        Matrix4f matrix = guiGraphics.pose().last().pose();
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(doorTex.texture());

        // Mapping: Top-Left (0,1), Bottom-Left (6,7), Bottom-Right (4,5), Top-Right (2,3)
        buffer.addVertex(matrix, (float)x, (float)y, 0)
            .setUv(sprite.getU(uvs.get(0)), sprite.getV(uvs.get(1)))
            .setColor(1f, 1f, 1f, 1f);

        buffer.addVertex(matrix, (float)x, (float)y + CELL_SIZE, 0)
            .setUv(sprite.getU(uvs.get(6)), sprite.getV(uvs.get(7)))
            .setColor(1f, 1f, 1f, 1f);

        buffer.addVertex(matrix, (float)x + CELL_SIZE, (float)y + CELL_SIZE, 0)
            .setUv(sprite.getU(uvs.get(4)), sprite.getV(uvs.get(5)))
            .setColor(1f, 1f, 1f, 1f);

        buffer.addVertex(matrix, (float)x + CELL_SIZE, (float)y, 0)
            .setUv(sprite.getU(uvs.get(2)), sprite.getV(uvs.get(3)))
            .setColor(1f, 1f, 1f, 1f);

        BufferUploader.drawWithShader(buffer.buildOrThrow());
        RenderSystem.disableBlend();
    }
}
