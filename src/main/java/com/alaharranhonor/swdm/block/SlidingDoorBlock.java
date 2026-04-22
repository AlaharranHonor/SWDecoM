package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.block.entity.MultiDoorBlockEntity;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import com.alaharranhonor.swdm.network.CBWhyMinecraftWhyDoorPacket;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class SlidingDoorBlock extends MultiDoorBlock {

    private static final MapCodec<SlidingDoorBlock> CODEC = simpleCodec(SlidingDoorBlock::new);

    public static final EnumProperty<DoorOpenState> OPEN = EnumProperty.create("open", DoorOpenState.class);
    public static final BooleanProperty MAIN = MultiDoorBlock.MAIN;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    public SlidingDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(OPEN, DoorOpenState.CLOSED)
            .setValue(MAIN, false)
            .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockPos mainPos = getMainPos(level, pos, state);
        MultiDoorData doorData = level.getBlockEntity(mainPos, BlockSetup.MULTI_DOOR.get()).map(MultiDoorBlockEntity::getDoorData).orElse(null);
        if (doorData == null) return InteractionResult.PASS;

        int width = doorData.width();
        Direction moveDirection = getHingeDirection(state);
        Direction hingeOffset = getHingeOffset(state);
        DoorOpenState currentDoorState = state.getValue(OPEN);
        boolean isShifting = player.isSecondaryUseActive();

        // Determine the next state based on width
        DoorOpenState nextState;
        if (width == 1) {
            nextState = (currentDoorState == DoorOpenState.CLOSED) ? DoorOpenState.OPEN : DoorOpenState.CLOSED;
        } else {
            if (isShifting) {
                // Reverse Cycle: Closed -> Open -> Half -> Closed
                nextState = switch (currentDoorState) {
                    case CLOSED -> DoorOpenState.OPEN;
                    case OPEN -> DoorOpenState.HALF_OPEN;
                    case HALF_OPEN -> DoorOpenState.CLOSED;
                };
            } else {
                // Normal Cycle: Closed -> Half -> Open -> Closed
                nextState = switch (currentDoorState) {
                    case CLOSED -> DoorOpenState.HALF_OPEN;
                    case HALF_OPEN -> DoorOpenState.OPEN;
                    case OPEN -> DoorOpenState.CLOSED;
                };
            }
        }

        int moveAmount;
        if (width == 1) {
            moveAmount = 1;
        } else {
            moveAmount = switch (currentDoorState) {
                case HALF_OPEN -> isShifting ? (int) Math.ceil(width / 2f) : width / 2;
                case OPEN -> isShifting ? width / 2 : width;
                case CLOSED -> isShifting ? width : (int) Math.ceil(width / 2f);
            };
        }

        boolean closing = isShifting && currentDoorState != DoorOpenState.CLOSED || !isShifting && currentDoorState == DoorOpenState.OPEN;
        if (closing) {
            moveDirection = moveDirection.getOpposite();
        }

        BlockPos newMainPos = mainPos.relative(moveDirection, moveAmount);
        BlockState mainState = level.getBlockState(mainPos);
        List<BlockPos> ignoreBlocks = BlockPos.betweenClosedStream(
            mainPos,
            mainPos.relative(hingeOffset, width - 1).above(doorData.height() - 1)
        ).map(BlockPos::immutable).toList();

        if (!canPlace(level, newMainPos, mainState, doorData, ignoreBlocks)) {
            return InteractionResult.PASS;
        }

        // Remove the old door blocks.
        if (!level.isClientSide()) {
            // Why do block entities not get synced to client properly
            PacketDistributor.sendToPlayersTrackingChunk(((ServerLevel) level), new ChunkPos(mainPos), new CBWhyMinecraftWhyDoorPacket(mainPos));
        }

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < doorData.height(); h++) {
                BlockPos currentPos = mainPos.relative(hingeOffset, w).above(h);
                level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        }

        // Place new door blocks.
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < doorData.height(); h++) {
                BlockPos targetPos = newMainPos.relative(hingeOffset, w).above(h);
                level.setBlock(targetPos, mainState.setValue(MAIN, targetPos.equals(newMainPos)).setValue(OPEN, nextState), Block.UPDATE_ALL);
            }
        }

        level.getBlockEntity(newMainPos, BlockSetup.MULTI_DOOR.get()).ifPresent(newMain -> {
            newMain.setDoorData(doorData);
            newMain.setChanged();
        });

        level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), player.isSecondaryUseActive() ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MAIN, FACING, HINGE, OPEN);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
