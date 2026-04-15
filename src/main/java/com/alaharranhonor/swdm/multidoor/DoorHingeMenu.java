package com.alaharranhonor.swdm.multidoor;

import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.item.DoorHingeItem;
import com.alaharranhonor.swdm.registry.DataComponentSetup;
import com.alaharranhonor.swdm.registry.MenuSetup;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class DoorHingeMenu extends AbstractContainerMenu {

    private final Either<List<BlockState>, MultiDoorData> textureHolder;
    private final Direction doorFacing;
    private final Player player;
    private final ItemStack hingeItem;

    private int doorWidth;
    private int doorHeight;

    // Server ctor
    public DoorHingeMenu(int containerId, Inventory playerInventory, Player player, InteractionHand hand) {
        this(MenuSetup.DOOR_HINGE.get(), containerId, playerInventory, hand, Either.left(Collections.emptyList()), 1, Direction.NORTH);
    }

    // Client ctor
    public DoorHingeMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
        this(MenuSetup.DOOR_HINGE.get(), containerId, playerInventory,
            buf.readEnum(InteractionHand.class),
            buf.readBoolean() ? Either.left(DoorHingeItem.BLOCK_STATE_LIST_STREAM_CODEC.decode(buf)) : Either.right(MultiDoorData.STREAM_CODEC.decode(buf)),
            buf.readVarInt(),
            Direction.STREAM_CODEC.decode(buf)
        );
    }

    protected DoorHingeMenu(@Nullable MenuType<?> menuType, int containerId, Inventory playerInventory, InteractionHand hand, Either<List<BlockState>, MultiDoorData> textureHolder, int doorWidth, Direction facing) {
        super(menuType, containerId);
        this.textureHolder = textureHolder;
        this.doorWidth = doorWidth;
        this.doorHeight = slotCount(textureHolder) / doorWidth;
        this.doorFacing = facing;
        this.player = playerInventory.player;
        this.hingeItem = this.player.getItemInHand(hand);
    }

    public void createItem(MultiDoorBlock.DoorType doorType, int width, int height, List<MultiDoorData.MultiDoorTexture> textures) {
        if (this.player.level().isClientSide()) return;

        ItemStack stack = doorType.createItem();

        int damage = width * height;
        if (this.hingeItem.getDamageValue() + damage > this.hingeItem.getMaxDamage()) {
            return;
        }
        this.hingeItem.hurtAndBreak(damage, this.player, EquipmentSlot.MAINHAND);

        if (!stack.isEmpty()) {
            stack.set(DataComponentSetup.MULTI_DOOR, new MultiDoorData(width, height, textures));
        }

        if (this.player.addItem(stack)) {
            this.player.closeContainer();
        }

    }

    public Either<List<BlockState>, MultiDoorData> getTextureHolder() {
        return this.textureHolder;
    }

    public int slotCount() {
        return slotCount(this.textureHolder);
    }

    private static int slotCount(Either<List<BlockState>, MultiDoorData> textureHolder) {
        return textureHolder.map(List::size, data -> data.textures().size());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public int getDoorWidth() {
        return this.doorWidth;
    }

    public int getDoorHeight() {
        return this.doorHeight;
    }

    public void setDoorWidth(int doorWidth) {
        this.doorWidth = doorWidth;
    }

    public void setDoorHeight(int doorHeight) {
        this.doorHeight = doorHeight;
    }

    public @Nullable Direction getDoorFacing() {
        return this.doorFacing;
    }
}
