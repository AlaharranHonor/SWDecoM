package com.alaharranhonor.swdm.multidoor;

import com.alaharranhonor.swdm.item.DoorHingeItem;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;
import java.util.function.Function;

public record HingeData(BlockPos start, BlockPos end) {

    public static final Codec<HingeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BlockPos.CODEC.fieldOf("start").forGetter(HingeData::start),
        Codec.either(BlockPos.CODEC, Codec.EMPTY.codec()).fieldOf("end").forGetter(data -> data.end != null ? Either.left(data.end) : Either.right(Unit.INSTANCE))
    ).apply(instance, (start, end) -> new HingeData(start, end.map(Function.identity(), r -> null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, HingeData> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, HingeData::start,
        ByteBufCodecs.optional(BlockPos.STREAM_CODEC), data -> Optional.ofNullable(data.end),
        (start, end) -> new HingeData(start, end.orElse(null))
    );

    public static HingeData start(BlockPos pos) {
        return new HingeData(pos, null);
    }

    public HingeData end(BlockPos pos) {
        return new HingeData(this.start, pos);
    }

    public boolean isValid() {
        if (this.start == null || this.end == null) return false;

        int dx = Math.abs(this.start.getX() - this.end.getX()) + 1;
        int dy = Math.abs(this.start.getY() - this.end.getY()) + 1;
        int dz = Math.abs(this.start.getZ() - this.end.getZ()) + 1;

        // 1. Check height (must be between 2 and 9)
        if (dy < DoorHingeItem.MIN_DOOR_HEIGHT || dy > DoorHingeItem.MAX_DOOR_SIZE) return false;

        // 2. Ensure it is a vertical plane (one horizontal axis must be thickness 1)
        // One must be the 'width' (1-9), the other must be 'thickness' (1)
        boolean validX = (dx >= DoorHingeItem.MIN_DOOR_WIDTH && dx <= DoorHingeItem.MAX_DOOR_SIZE) && (dz == 1);
        boolean validZ = (dz >= DoorHingeItem.MIN_DOOR_WIDTH && dz <= DoorHingeItem.MAX_DOOR_SIZE) && (dx == 1);

        // XOR: It can be on the X plane OR Z plane, but not a 1x1 column (already covered by 2 <= dy <= 9)
        return validX || validZ;
    }

    /**
     * Helper to get dimensions if valid.
     */
    public int getWidth() {
        int dx = Math.abs(this.start.getX() - this.end.getX()) + 1;
        int dz = Math.abs(this.start.getZ() - this.end.getZ()) + 1;
        return Math.max(dx, dz);
    }

    public int getHeight() {
        return Math.abs(this.start.getY() - this.end.getY()) + 1;
    }
}
