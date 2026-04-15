package com.alaharranhonor.swdm.item;

import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.block.entity.MultiDoorBlockEntity;
import com.alaharranhonor.swdm.multidoor.DoorHingeMenu;
import com.alaharranhonor.swdm.multidoor.HingeData;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.DataComponentSetup;
import com.alaharranhonor.swdm.util.DirectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class DoorHingeItem extends Item {

    public static final StreamCodec<RegistryFriendlyByteBuf, BlockState> BLOCK_STATE_STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(BlockState.CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, List<BlockState>> BLOCK_STATE_LIST_STREAM_CODEC = BLOCK_STATE_STREAM_CODEC.apply(ByteBufCodecs.list());

    public static final int MIN_DOOR_WIDTH = 1;
    public static final int MIN_DOOR_HEIGHT = 2;
    public static final int MAX_DOOR_SIZE = 9;

    public DoorHingeItem(Properties properties) {
        super(properties);
    }

    public static List<BlockPos> getDoorPositions(BlockPos pos1, BlockPos pos2, Direction facing) {
        List<BlockPos> positions = new ArrayList<>();

        // 1. Get the boundaries
        int minY = Math.min(pos1.getY(), pos2.getY());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int minX = Math.min(pos1.getX(), pos2.getX());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());

        // 2. Determine player's "Right" direction
        Direction rightDir = facing.getClockWise(); // North -> East, East -> South, etc.

        // 3. Iterate Top to Bottom
        for (int y = maxY; y >= minY; y--) {

            // 4. Iterate Left to Right relative to player
            // We find the start and end of the horizontal span
            if (rightDir.getAxis() == Direction.Axis.X) {
                // Player is facing North/South, door is on X axis
                int startX = (rightDir == Direction.EAST) ? minX : maxX;
                int endX = (rightDir == Direction.EAST) ? maxX : minX;
                int step = (startX <= endX) ? 1 : -1;

                for (int x = startX; (step > 0 ? x <= endX : x >= endX); x += step) {
                    positions.add(new BlockPos(x, y, minZ)); // thickness is on Z
                }
            } else {
                // Player is facing East/West, door is on Z axis
                int startZ = (rightDir == Direction.SOUTH) ? minZ : maxZ;
                int endZ = (rightDir == Direction.SOUTH) ? maxZ : minZ;
                int step = (startZ <= endZ) ? 1 : -1;

                for (int z = startZ; (step > 0 ? z <= endZ : z >= endZ); z += step) {
                    positions.add(new BlockPos(minX, y, z)); // thickness is on X
                }
            }
        }
        return positions;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack held = context.getItemInHand();
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(clickedPos);
        Player player = context.getPlayer();
        if (player.isShiftKeyDown() && state.getBlock() instanceof MultiDoorBlock block) {
            BlockPos mainPos = block.getMainPos(level, clickedPos, state);
            MultiDoorData data = level.getBlockEntity(mainPos, BlockSetup.MULTI_DOOR.get()).map(MultiDoorBlockEntity::getDoorData).orElse(null);
            if (data == null) {
                player.displayClientMessage(Component.translatable("item.swdm.door_hinge.use.invalid_door"), true);
                return InteractionResult.FAIL;
            }
            openExistingHingeMenu(level, player, context.getHand(), data, Direction.NORTH);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (held.has(DataComponentSetup.HINGE_SELECTION)) {
            HingeData hingeData = held.get(DataComponentSetup.HINGE_SELECTION);
            if (hingeData.end() == null) {
                HingeData selection = hingeData.end(clickedPos);
                if (!selection.isValid()) {
                    if (!level.isClientSide()) {
                        player.displayClientMessage(Component.translatable("item.swdm.door_hinge.use.invalid_selection"), false);
                    }
                    held.remove(DataComponentSetup.HINGE_SELECTION);
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }

                BlockPos size = selection.end().subtract(selection.start());
                Direction[] axisDirs;
                if (Math.abs(size.getX()) > 1) {
                    axisDirs = new Direction[] { Direction.SOUTH, Direction.NORTH };
                } else {
                    axisDirs = new Direction[] { Direction.EAST, Direction.WEST };
                }
                Direction doorFacing = DirectionUtil.getNearest(player.getLookAngle(), axisDirs).getOpposite();
                held.set(DataComponentSetup.HINGE_SELECTION, selection);
                openSelectionHingeMenu(level, player, context.getHand(), selection, doorFacing);

                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        held.set(DataComponentSetup.HINGE_SELECTION, HingeData.start(clickedPos));
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private static void openSelectionHingeMenu(Level level, Player player, InteractionHand useHand, HingeData selection, Direction facing) {
        player.openMenu(new SimpleMenuProvider((id, inv, plyr) -> new DoorHingeMenu(id, inv, plyr, useHand), Component.translatable("menu.swdm.door_hinge.title")), buf -> {
            buf.writeEnum(useHand);
            buf.writeBoolean(true);
            BLOCK_STATE_LIST_STREAM_CODEC.encode(buf, getDoorPositions(selection.start(), selection.end(), facing.getOpposite()).stream().map(level::getBlockState).toList());
            buf.writeVarInt(selection.getWidth());
            Direction.STREAM_CODEC.encode(buf, facing);
        });
    }

    private static void openExistingHingeMenu(Level level, Player player, InteractionHand useHand, MultiDoorData data, Direction facing) {
        player.openMenu(new SimpleMenuProvider((id, inv, plyr) -> new DoorHingeMenu(id, inv, plyr, useHand), Component.translatable("menu.swdm.door_hinge.title")), buf -> {
            buf.writeEnum(useHand);
            buf.writeBoolean(false);
            MultiDoorData.STREAM_CODEC.encode(buf, data);
            buf.writeVarInt(data.width());
            Direction.STREAM_CODEC.encode(buf, facing);
        });
    }
}
