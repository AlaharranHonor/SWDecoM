package com.alaharranhonor.swdm.network;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.multidoor.DoorHingeMenu;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record SBCreateMultiDoorItem(MultiDoorBlock.DoorType doorType, int width, int height, List<MultiDoorData.MultiDoorTexture> textures) implements CustomPacketPayload {

    public static final Type<SBCreateMultiDoorItem> TYPE = new Type<>(ModRef.res("sb_create_multi_door_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SBCreateMultiDoorItem> STREAM_CODEC = StreamCodec.composite(
        MultiDoorBlock.DoorType.STREAM_CODEC, SBCreateMultiDoorItem::doorType,
        ByteBufCodecs.VAR_INT, SBCreateMultiDoorItem::width,
        ByteBufCodecs.VAR_INT, SBCreateMultiDoorItem::height,
        MultiDoorData.MultiDoorTexture.STREAM_CODEC.apply(ByteBufCodecs.list()), SBCreateMultiDoorItem::textures,
        SBCreateMultiDoorItem::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SBCreateMultiDoorItem msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            AbstractContainerMenu menu = ctx.player().containerMenu;
            if (menu instanceof DoorHingeMenu doorHingeMenu) {
                doorHingeMenu.createItem(msg.doorType, msg.width, msg.height, msg.textures);
            }
        });
    }
}
