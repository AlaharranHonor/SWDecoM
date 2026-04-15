package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.block.entity.MultiDoorBlockEntity;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class SwingingDoorBlock extends MultiDoorBlock {

    private static final MapCodec<SwingingDoorBlock> CODEC = simpleCodec(SwingingDoorBlock::new);

    public static final EnumProperty<DoorOpenState> OPEN = EnumProperty.create("open", DoorOpenState.class);
    public static final BooleanProperty MAIN = MultiDoorBlock.MAIN;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    public SwingingDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(OPEN, DoorOpenState.CLOSED)
            .setValue(MAIN, false)
            .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockPos mainPos = getMainPos(level, pos, state);
        MultiDoorBlockEntity be = level.getBlockEntity(mainPos, BlockSetup.MULTI_DOOR.get()).orElse(null);
        if (be == null) return InteractionResult.PASS;

        MultiDoorData data = be.getDoorData();
        DoorOpenState nextState = getNextState(state.getValue(OPEN), player.isSecondaryUseActive());

        // Calculate the physical offset for the door panels based on rotation
        BlockPos nextMainPos = getOriginForState(mainPos, state, nextState);

        // Check for collisions
        List<BlockPos> currentBlocks = getFootprint(mainPos, state, data);
        if (!canPlace(level, nextMainPos, state.setValue(OPEN, nextState), data, currentBlocks)) {
            return InteractionResult.FAIL;
        }

        // Remove old, Place new
        currentBlocks.forEach(p -> level.removeBlock(p, false));

        BlockState newState = state.setValue(OPEN, nextState);
        Direction panelOffset = getHingeOffset(newState);
        for (int w = 0; w < data.width(); w++) {
            for (int h = 0; h < data.height(); h++) {
                BlockPos target = nextMainPos.relative(panelOffset, w).above(h);
                level.setBlock(target, newState.setValue(MAIN, target.equals(nextMainPos)), Block.UPDATE_ALL_IMMEDIATE);
            }
        }

        level.getBlockEntity(nextMainPos, BlockSetup.MULTI_DOOR.get()).ifPresent(newBe -> newBe.setDoorData(data));
        level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), player.isSecondaryUseActive() ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public Direction getEffectiveFacingDirection(BlockState state) {
        Direction facing =  state.getValue(FACING);
        if (state.getValue(OPEN) != DoorOpenState.HALF_OPEN) {
            return facing;
        }

        return state.getValue(HINGE) == DoorHingeSide.RIGHT ? facing.getCounterClockWise() : facing.getClockWise();
    }

    @Override
    public Direction getHingeDirection(BlockState state) {
        return switch (state.getValue(OPEN)) {
            case CLOSED -> super.getHingeDirection(state);
            case HALF_OPEN -> state.getValue(FACING).getOpposite();
            case OPEN -> super.getHingeDirection(state).getOpposite();
        };
    }

    /**
     * Determines which way the door panels extend from the MAIN block.
     */
    private Direction getRotationOffset(BlockState state, DoorOpenState openState) {
        Direction facing = state.getValue(FACING);
        boolean isRight = state.getValue(HINGE) == DoorHingeSide.RIGHT;

        return switch (openState) {
            case CLOSED -> getHingeOffset(state); // Original hinge offset along the wall
            case HALF_OPEN -> facing;
            case OPEN -> isRight ? facing.getClockWise() : facing.getCounterClockWise(); // 180 deg: panels extend "forward" from the original wall line
        };
    }

    /**
     * Moves the MAIN block position at 180 degrees to keep it attached to the wall.
     */
    private BlockPos getOriginForState(BlockPos currentMain, BlockState currentState, DoorOpenState nextState) {
        DoorOpenState current = currentState.getValue(OPEN);

        // Moving FROM 180° back to 90° or 0°: Shift the pivot back to the original hinge side
        if (current == DoorOpenState.OPEN) {
            // We move in the OFFSET direction because we are currently on the 'far' side
            return currentMain.relative(this.getHingeDirection(currentState));
        }

        // Moving TO 180° from 90° or 0°: Shift the pivot across the opening
        if (nextState == DoorOpenState.OPEN) {
            if (current == DoorOpenState.CLOSED) {
                return currentMain.relative(this.getHingeDirection(currentState));
            }
            return currentMain.relative(this.getEffectiveFacingDirection(currentState).getOpposite());
        }

        // Otherwise, the pivot stays put
        return currentMain;
    }

    private List<BlockPos> getFootprint(BlockPos mainPos, BlockState state, MultiDoorData data) {
        List<BlockPos> list = new ArrayList<>();
        Direction offset = getRotationOffset(state, state.getValue(OPEN));
        for (int w = 0; w < data.width(); w++) {
            for (int h = 0; h < data.height(); h++) {
                list.add(mainPos.relative(offset, w).above(h));
            }
        }
        return list;
    }

    private DoorOpenState getNextState(DoorOpenState current, boolean shifting) {
        return shifting ? switch (current) {
            case CLOSED -> DoorOpenState.OPEN;
            case OPEN -> DoorOpenState.HALF_OPEN;
            case HALF_OPEN -> DoorOpenState.CLOSED;
        } : switch (current) {
            case CLOSED -> DoorOpenState.HALF_OPEN;
            case HALF_OPEN -> DoorOpenState.OPEN;
            case OPEN -> DoorOpenState.CLOSED;
        };
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
