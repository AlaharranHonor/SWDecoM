package com.alaharranhonor.swdm.multidoor;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.item.DoorHingeItem;
import com.alaharranhonor.swdm.network.SBCreateMultiDoorItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DoorHingeScreen extends AbstractContainerScreen<DoorHingeMenu> {

    private static final ResourceLocation BACKGROUND_TEX = ModRef.res("textures/gui/container/door_hinge.png");
    private static final int MAX_DISPLAY_SIZE = 72;
    //private static final int SIZE_SELECTOR_GAP = 52;

    public TextureSlot[] textureSlots;
    private int cellSize = MAX_DISPLAY_SIZE / DoorHingeItem.MAX_DOOR_SIZE;
    private int displayWidth = MAX_DISPLAY_SIZE;
    private int displayHeight = MAX_DISPLAY_SIZE;

    public Button[] transformButtons;
    private EditBox searchBox;
    private TextureGrid textureGrid;
    private MultiDoorBlock.DoorType doorType = MultiDoorBlock.DoorType.SLIDING;
    private int selectedSlotIndex = -1; // -1 means nothing selected
    private boolean validationFailed = false;

    public DoorHingeScreen(DoorHingeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 230;
        this.imageHeight = 136;
    }

    @Override
    protected void init() {
        super.init();

        int paneX = this.leftPos + 128;

        // Texture Selector (Right Side)
        this.searchBox = new EditBox(this.font, paneX, this.topPos + 6, 88, 12, Component.literal("Search"));
        this.searchBox.setResponder(s -> textureGrid.updateFilter(s));
        this.addRenderableWidget(searchBox);

        // Door Texture Display (Left Side)
        int doorWidth = this.getMenu().getDoorWidth();
        int doorHeight = this.getMenu().getDoorHeight();

        int maxDim = Math.max(doorWidth, doorHeight);
        this.cellSize = (int) Math.floor(MAX_DISPLAY_SIZE / (float) maxDim);

        this.displayWidth = doorWidth * cellSize;
        this.displayHeight = doorHeight * cellSize;

        // Transform buttons dimensions
        int transformBtnSize = 16;
        int startX = this.leftPos + 16;
        int startY = this.topPos + 21;
        this.transformButtons = new Button[4];

        // Rotate Clockwise
        this.transformButtons[0] = this.addRenderableWidget(Button.builder(Component.literal("⟳"), b -> transformSelected(TextureSlot::rotateClockwise))
            .bounds(startX, startY, transformBtnSize, transformBtnSize).build());

        // Rotate Anticlockwise
        this.transformButtons[1] = this.addRenderableWidget(Button.builder(Component.literal("⟲"), b -> transformSelected(TextureSlot::rotateAnticlockwise))
            .bounds(startX, startY + (transformBtnSize + 2), transformBtnSize, transformBtnSize).build());

        // Mirror Horizontal
        this.transformButtons[2] = this.addRenderableWidget(Button.builder(Component.literal("⟺"), b -> transformSelected(TextureSlot::mirrorHorizontal))
            .bounds(startX, startY + 2 * (transformBtnSize + 2), transformBtnSize, transformBtnSize).build());

        // Mirror Vertical
        this.transformButtons[3] = this.addRenderableWidget(Button.builder(Component.literal("⇕"), b -> transformSelected(TextureSlot::mirrorVertical))
            .bounds(startX, startY + 3 * (transformBtnSize + 2), transformBtnSize, transformBtnSize).build());

        for (Button b : this.transformButtons) {
            b.active = false;
        }

        if (this.textureSlots == null) {
            this.textureSlots = new TextureSlot[this.getMenu().slotCount()];
            this.getMenu().getTextureHolder()
                .ifLeft(this::computeTextureSlotsFromStates)
                .ifRight(this::computeTextureSlotsFromData);
        }

        this.textureGrid = new TextureGrid(this.minecraft, 88, MAX_DISPLAY_SIZE, this.topPos + 22, paneX, sprite -> {
            if (this.selectedSlotIndex != -1 && this.selectedSlotIndex < this.textureSlots.length) {
                this.textureSlots[this.selectedSlotIndex].setSprite(sprite);
            }
        });
        this.addRenderableWidget(textureGrid);

        // Size Selectors
        int sizeSelectorSize = 10;
        int selectorStartX = this.leftPos + 21;
        int gap = 44;
        int widthDecBtnX = selectorStartX - sizeSelectorSize / 2;
        int widthIncBtnX = selectorStartX + this.font.width("Width") - sizeSelectorSize / 2;

        int heightDecBtnX = selectorStartX + gap - sizeSelectorSize / 2;
        int heightIncBtnX = selectorStartX + gap + this.font.width("Height") - sizeSelectorSize / 2;
        int sizeSelectorY = this.topPos + 22 + MAX_DISPLAY_SIZE + 6 + this.font.lineHeight + 1;

        // --- WIDTH CONTROLS ---
        // Left Button "<"
        this.addRenderableWidget(Button.builder(Component.literal("<"), b -> updateDimensions(-1, 0))
            .bounds(widthDecBtnX, sizeSelectorY, sizeSelectorSize, sizeSelectorSize).build());

        // Right Button ">"
        this.addRenderableWidget(Button.builder(Component.literal(">"), b -> updateDimensions(1, 0))
            .bounds(widthIncBtnX, sizeSelectorY, sizeSelectorSize, sizeSelectorSize).build());

        // --- HEIGHT CONTROLS ---
        // Left Button "<"
        this.addRenderableWidget(Button.builder(Component.literal("<"), b -> updateDimensions(0, -1))
            .bounds(heightDecBtnX, sizeSelectorY, sizeSelectorSize, sizeSelectorSize).build());

        // Right Button ">"
        this.addRenderableWidget(Button.builder(Component.literal(">"), b -> updateDimensions(0, 1))
            .bounds(heightIncBtnX, sizeSelectorY, sizeSelectorSize, sizeSelectorSize).build());

        // Door Type Toggle
        this.addRenderableWidget(CycleButton.builder(MultiDoorBlock.DoorType::getKey)
            .withValues(MultiDoorBlock.DoorType.values())
            .withInitialValue(MultiDoorBlock.DoorType.SLIDING)
            .displayOnlyValue()
            .create(this.leftPos + 110, this.topPos + 102, 42, 16, Component.empty(), (btn, val) -> this.doorType = val));

        this.addRenderableWidget(Button.builder(Component.literal("Build"), b -> {
            // If any of the sprites are empty, fail validation and exit.
            for (TextureSlot slot : this.textureSlots) {
                if (slot.getSprite() == null) {
                    this.validationFailed = true;
                    return;
                }
            }

            // Otherwise build the door.
            PacketDistributor.sendToServer(new SBCreateMultiDoorItem(
                this.doorType,
                this.getMenu().getDoorWidth(),
                this.getMenu().getDoorHeight(),
                Arrays.stream(this.textureSlots).map(TextureSlot::toMultiDoorTexture).toList()
            ));
        }).bounds(this.leftPos + 174, this.topPos + 102, 42, 16).build());
    }

    private void computeTextureSlotsFromData(MultiDoorData data) {
        for (int i = 0; i < data.textures().size(); i++) {
            MultiDoorData.MultiDoorTexture texture = data.textures().get(i);
            TextureSlot slot = new TextureSlot(this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture.texture()), texture.uvs());
            this.textureSlots[i] = slot;
        }
    }

    private void computeTextureSlotsFromStates(List<BlockState> states) {
        for (int i = 0; i < states.size(); i++) {
            TextureSlot slot = new TextureSlot();
            BlockState state = states.get(i);
            if (!state.isEmpty()) {
                var quads = this.minecraft.getBlockRenderer().getBlockModel(state).getQuads(state, this.getMenu().getDoorFacing(), this.minecraft.level.getRandom(), ModelData.EMPTY, RenderType.CUTOUT);
                if (!quads.isEmpty()) {
                    var quad = quads.getFirst();
                    slot.setSprite(quad.getSprite());
                }
            }
            this.textureSlots[i] = slot;
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND_TEX, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (this.textureSlots != null) {

            // Calculate offsets to center the door within the 72x72 area
            // Assuming your preview area starts at (37, 25) relative to leftPos/topPos
            int startX = this.leftPos + 37 + (72 - this.displayWidth) / 2;
            int startY = this.topPos + 22 + (72 - this.displayHeight) / 2;

            int doorWidth = this.menu.getDoorWidth();
            for (int i = 0; i < this.textureSlots.length; i++) {
                TextureSlot textureSlot = this.textureSlots[i];
                int col = i % doorWidth;
                int row = i / doorWidth;
                int x = startX + col * this.cellSize;
                int y = startY + row * this.cellSize;

                // Draw the actual sprite if it exists
                if (textureSlot.getSprite() != null) {
                    textureSlot.render(guiGraphics, x, y, this.cellSize, this.cellSize);
                }
                // If validation failed and this slot is empty, draw a red box
                else if (validationFailed) {
                    guiGraphics.fill(x, y, x + this.cellSize, y + this.cellSize, 0x88FF0000); // Semi-transparent red
                }

                // Draw highlight over the hovered slot
                boolean isHovered = mouseX >= x && mouseX < x + this.cellSize && mouseY >= y && mouseY < y + this.cellSize;
                if (isHovered) {
                    // 0x80 is ~50% opacity white
                    guiGraphics.fill(x, y, x + this.cellSize, y + this.cellSize, 0x80FFFFFF);
                }

                // Selection highlight (from previous step)
                if (i == this.selectedSlotIndex) {
                    guiGraphics.fill(x, y, x + this.cellSize, y + this.cellSize, 0x55FFFFFF);
                }
            }

            guiGraphics.hLine(startX - 1, startX + this.displayWidth, startY - 1, 0xFF666666);
            guiGraphics.vLine(startX - 1, startY - 1, startY + this.displayHeight, 0xFF666666);
            guiGraphics.hLine(startX - 1, startX + this.displayWidth, startY + this.displayHeight, 0xFF666666);
            guiGraphics.vLine(startX + this.displayWidth, startY - 1, startY + this.displayHeight, 0xFF666666);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);

        int startX = 21; // Relative to leftPos
        int startY = 22 + MAX_DISPLAY_SIZE + 6;
        int gap = 44;

        guiGraphics.drawCenteredString(this.font, "Width", startX + this.font.width("Width") / 2, startY, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, String.valueOf(this.menu.getDoorWidth()), startX + this.font.width("Width") / 2, startY + this.font.lineHeight + 2, 0xFFFFFF);

        guiGraphics.drawCenteredString(this.font, "Height", startX + gap + this.font.width("Height") / 2, startY, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, String.valueOf(this.menu.getDoorHeight()), startX + gap + this.font.width("Height") / 2, startY + this.font.lineHeight + 2, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.textureSlots != null) {
            int doorWidth = this.menu.getDoorWidth();
            int doorHeight = this.menu.getDoorHeight();
            int startX = this.leftPos + 37 + (72 - this.displayWidth) / 2;
            int startY = this.topPos + 22 + (72 - this.displayHeight) / 2;

            // Check if click is within the total door bounds
            if (mouseX >= startX && mouseX < startX + this.displayWidth && mouseY >= startY && mouseY < startY + this.displayHeight) {
                int col = (int) ((mouseX - startX) / cellSize);
                int row = (int) ((mouseY - startY) / cellSize);
                this.selectedSlotIndex = row * doorWidth + col;
                this.validationFailed = false;
                for (Button b : this.transformButtons) {
                    b.active = true;
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return !this.searchBox.keyPressed(keyCode, scanCode, modifiers) && !this.searchBox.canConsumeInput() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
    }

    private void updateDimensions(int widthDelta, int heightDelta) {
        int oldW = this.menu.getDoorWidth();
        int oldH = this.menu.getDoorHeight();

        int newW = Math.max(DoorHingeItem.MIN_DOOR_WIDTH, Math.min(DoorHingeItem.MAX_DOOR_SIZE, oldW + widthDelta));
        int newH = Math.max(DoorHingeItem.MIN_DOOR_HEIGHT, Math.min(DoorHingeItem.MAX_DOOR_SIZE, oldH + heightDelta));

        if (newW == oldW && newH == oldH) return;

        // 1. Update the menu/data
        this.menu.setDoorWidth(newW);
        this.menu.setDoorHeight(newH);

        // Door Texture Display (Left Side)
        int doorWidth = this.getMenu().getDoorWidth();
        int doorHeight = this.getMenu().getDoorHeight();

        int maxDim = Math.max(doorWidth, doorHeight);
        this.cellSize = (int) Math.floor(MAX_DISPLAY_SIZE / (float) maxDim);

        this.displayWidth = doorWidth * cellSize;
        this.displayHeight = doorHeight * cellSize;

        // 2. Create new array and migrate existing slots
        TextureSlot[] newSlots = new TextureSlot[newW * newH];
        for (int row = 0; row < newH; row++) {
            for (int col = 0; col < newW; col++) {
                int newIdx = row * newW + col;
                if (row < oldH && col < oldW) {
                    // Copy existing slot if it fits in the new bounds
                    newSlots[newIdx] = this.textureSlots[row * oldW + col];
                } else {
                    newSlots[newIdx] = new TextureSlot();
                }
            }
        }
        this.textureSlots = newSlots;
        this.selectedSlotIndex = -1; // Reset selection as indices shifted
    }

    private void transformSelected(Consumer<TextureSlot> action) {
        if (selectedSlotIndex != -1 && selectedSlotIndex < textureSlots.length) {
            action.accept(textureSlots[selectedSlotIndex]);
            this.validationFailed = false; // Reset error highlight on change
        }
    }

    public static class TextureSlot {
        private TextureAtlasSprite sprite;

        private List<Float> uvs = new ArrayList<>(MultiDoorData.MultiDoorTexture.DEFAULT_UVS);

        public TextureSlot() {
        }

        public TextureSlot(TextureAtlasSprite sprite, List<Float> uvs) {
            this.sprite = sprite;
            this.uvs = uvs;
        }

        public void rotateClockwise() {
            // Shift values: BL -> TL -> TR -> BR -> BL
            float lastU = uvs.get(6), lastV = uvs.get(7);
            for (int i = 7; i >= 2; i--) uvs.set(i, uvs.get(i - 2));
            uvs.set(0, lastU);
            uvs.set(1, lastV);
        }

        public void rotateAnticlockwise() {
            // Shift values: TL -> BL -> BR -> TR -> TL
            // Store the first pair (TL)
            float firstU = uvs.get(0);
            float firstV = uvs.get(1);

            // Move 1->0, 2->1, 3->2
            for (int i = 0; i < 6; i++) {
                uvs.set(i, uvs.get(i + 2));
            }

            // Put the stored TL into the last position (BL)
            uvs.set(6, firstU);
            uvs.set(7, firstV);
        }

        public void mirrorHorizontal() {
            // Swap Left points with Right points
            swap(0, 2);
            swap(1, 3); // Top pairs
            swap(6, 4);
            swap(7, 5); // Bottom pairs
        }

        public void mirrorVertical() {
            // Swap Top-Left (0,1) with Bottom-Left (6,7)
            swap(0, 6);
            swap(1, 7);
            // Swap Top-Right (2,3) with Bottom-Right (4,5)
            swap(2, 4);
            swap(3, 5);
        }

        private void swap(int a, int b) {
            float temp = this.uvs.get(a);
            this.uvs.set(a, this.uvs.get(b));
            this.uvs.set(b, temp);
        }

        public List<Float> getUvs() {
            return uvs;
        }

        private void render(GuiGraphics guiGraphics, int x, int y, int width, int height) {
            TextureAtlasSprite sprite = this.getSprite();
            if (sprite == null) return;

            List<Float> uvs = this.getUvs(); // [u0,v0, u1,v1, u2,v2, u3,v3] (TL, TR, BR, BL)

            // Map 0-1 UVs to actual Sprite Atlas UVs
            float u0 = sprite.getU(uvs.get(0));
            float v0 = sprite.getV(uvs.get(1));
            float u1 = sprite.getU(uvs.get(2));
            float v1 = sprite.getV(uvs.get(3));
            float u2 = sprite.getU(uvs.get(4));
            float v2 = sprite.getV(uvs.get(5));
            float u3 = sprite.getU(uvs.get(6));
            float v3 = sprite.getV(uvs.get(7));

            RenderSystem.setShaderTexture(0, sprite.atlasLocation());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            // Add vertices in order: TL, BL, BR, TR
            Matrix4f matrix = guiGraphics.pose().last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.addVertex(matrix, (float) x, (float) y, 0).setUv(u0, v0);
            bufferbuilder.addVertex(matrix, (float) x, (float) (y + height), 0).setUv(u3, v3);
            bufferbuilder.addVertex(matrix, (float) (x + width), (float) (y + height), 0).setUv(u2, v2);
            bufferbuilder.addVertex(matrix, (float) (x + width), (float) y, 0).setUv(u1, v1);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        }

        public void setSprite(TextureAtlasSprite sprite) {
            this.sprite = sprite;
        }

        public TextureAtlasSprite getSprite() {
            return this.sprite;
        }

        public MultiDoorData.MultiDoorTexture toMultiDoorTexture() {
            return new MultiDoorData.MultiDoorTexture(this.sprite.contents().name(), this.uvs);
        }
    }
}
