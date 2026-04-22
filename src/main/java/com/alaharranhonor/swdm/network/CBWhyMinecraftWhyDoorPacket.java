package com.alaharranhonor.swdm.network;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.block.entity.MultiDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CBWhyMinecraftWhyDoorPacket(BlockPos pos) implements CustomPacketPayload {

    public static final Type<CBWhyMinecraftWhyDoorPacket> TYPE = new Type<>(ModRef.res("cb_remove_old_door_block_entity"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CBWhyMinecraftWhyDoorPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, CBWhyMinecraftWhyDoorPacket::pos,
        CBWhyMinecraftWhyDoorPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CBWhyMinecraftWhyDoorPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Level level = ctx.player().level();
            if (level.getBlockEntity(msg.pos) instanceof MultiDoorBlockEntity) {
                level.removeBlockEntity(msg.pos());
            }
        });
    }
}
