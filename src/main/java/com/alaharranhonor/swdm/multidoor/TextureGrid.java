package com.alaharranhonor.swdm.multidoor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.List;
import java.util.function.Consumer;

public class TextureGrid extends ObjectSelectionList<TextureGrid.RowEntry> {
    private static final int ICON_SIZE = 8;
    private static final int PADDING = 2;
    private final int columns;
    private final Consumer<TextureAtlasSprite> selectCallback;

    public TextureGrid(Minecraft mc, int width, int height, int top, int left, Consumer<TextureAtlasSprite> selectCallback) {
        super(mc, width, height, top, ICON_SIZE + PADDING);
        this.setX(left);
        this.columns = (width - 6) / (ICON_SIZE + PADDING);

        this.selectCallback = selectCallback;
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getRight() - 6;
    }

    public void updateFilter(String query) {
        this.clearEntries();
        if (StringUtil.isBlank(query)) {
            return;
        }

        // Get all sprites from the block atlas
        var allSprites = Minecraft.getInstance().getModelManager()
            .getAtlas(InventoryMenu.BLOCK_ATLAS)
            .getTextures().keySet().stream()
            .filter(loc -> loc.getPath().startsWith("block"))
            .filter(loc -> loc.toString().contains(query))
            .toList();

        // Group sprites into rows based on column count
        for (int i = 0; i < allSprites.size(); i += columns) {
            int end = Math.min(i + columns, allSprites.size());
            this.addEntry(new RowEntry(allSprites.subList(i, end)));
        }
    }

    @Override
    protected void renderSelection(GuiGraphics guiGraphics, int top, int width, int height, int outerColor, int innerColor) {

    }

    public class RowEntry extends Entry<RowEntry> {
        private final List<ResourceLocation> locations;

        public RowEntry(List<ResourceLocation> locations) {
            this.locations = locations;
        }

        @Override
        public void render(GuiGraphics gui, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float partialTick) {
            for (int i = 0; i < locations.size(); i++) {
                int x = left + i * (ICON_SIZE + PADDING);
                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(locations.get(i));

                // Draw 8x8 icon
                gui.blit(x, top, 0, ICON_SIZE, ICON_SIZE, sprite);

                // Simple hover highlight
                if (mouseX >= x && mouseX < x + ICON_SIZE && mouseY >= top && mouseY < top + ICON_SIZE) {
                    gui.fill(x, top, x + ICON_SIZE, top + ICON_SIZE, 0x80FFFFFF);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int clickedCol = (int) ((mouseX - TextureGrid.this.getRowLeft()) / (ICON_SIZE + PADDING));
            if (clickedCol >= 0 && clickedCol < locations.size()) {
                ResourceLocation selected = locations.get(clickedCol);
                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(selected);
                TextureGrid.this.selectCallback.accept(sprite);
                return true;
            }
            return false;
        }

        @Override
        public Component getNarration() { return Component.empty(); }
    }
}
