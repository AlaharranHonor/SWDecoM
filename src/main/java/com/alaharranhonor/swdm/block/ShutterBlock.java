package com.alaharranhonor.swdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ShutterBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final float AABB_DOOR_THICKNESS = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    public ShutterBlock(BlockBehaviour.Properties p_52737_) {
        super(p_52737_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE).setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, Boolean.FALSE));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean isClosed = !pState.getValue(OPEN);
        boolean flag1 = pState.getValue(HINGE) == DoorHingeSide.RIGHT;
        return switch (direction) {
            default -> isClosed ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
            case SOUTH -> isClosed ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
            case WEST -> isClosed ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
            case NORTH -> isClosed ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
        };
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        /*if (pFacing.getAxis() == Direction.Axis.Y) {
            return pFacingState.is(this) ? pState.setValue(FACING, pFacingState.getValue(FACING)).setValue(OPEN, pFacingState.getValue(OPEN)).setValue(HINGE, pFacingState.getValue(HINGE)).setValue(POWERED, pFacingState.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
        } else {
            return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }*/
        return pState;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> pState.getValue(OPEN);
            default -> false;
        };
    }

    private int getCloseSound() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getOpenSound() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        boolean isPowered = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
        Direction placementFace = this.getFacingForPlacement(pContext);
        return this.defaultBlockState().setValue(FACING, placementFace).setValue(HINGE, this.getHinge(pContext, placementFace)).setValue(POWERED, isPowered).setValue(OPEN, isPowered);
    }

    private Direction getFacingForPlacement(BlockPlaceContext ctx) {
        Vec3 placedPos = ctx.getClickLocation().subtract(Vec3.atCenterOf(ctx.getClickedPos()));
        Direction playerFacing = ctx.getHorizontalDirection();
        Direction placedFace = ctx.getClickedFace();
        Direction facing = placedFace;
        Direction.Axis placingAxis = placedFace.getAxis() == Direction.Axis.Y ? ctx.getHorizontalDirection().getClockWise().getAxis() : placedFace.getAxis();
        if (placingAxis != playerFacing.getAxis()) {
            if (placingAxis == Direction.Axis.X) {
                facing = placedPos.z < 0 ? Direction.SOUTH : Direction.NORTH;
            } else if (placingAxis == Direction.Axis.Z) {
                facing = placedPos.x < 0 ? Direction.EAST : Direction.WEST;
            }
        }
        return facing;
    }

    private DoorHingeSide getHinge(BlockPlaceContext pContext, Direction placementFace) {
        BlockGetter blockgetter = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        Direction playerFacing = pContext.getHorizontalDirection();
        Direction playerFacingLeft = playerFacing.getCounterClockWise();
        BlockPos leftPos = clickedPos.relative(playerFacingLeft);
        BlockState leftBlock = blockgetter.getBlockState(leftPos);
        Direction playerFacingRight = playerFacing.getClockWise();
        BlockPos rightPos = clickedPos.relative(playerFacingRight);
        BlockState rightBlock = blockgetter.getBlockState(rightPos);
        Direction clickedFace = pContext.getClickedFace();

        Direction.Axis placingAxis = placementFace.getClockWise().getAxis();
        boolean isAxisX = placingAxis == Direction.Axis.X;
        boolean isAxisZ = placingAxis == Direction.Axis.Z;
        boolean placingOnLeft = clickedFace == playerFacingLeft.getOpposite();
        boolean placingOnRight = clickedFace == playerFacingRight.getOpposite();

        // I don't know what im doing. It just works.
        if (isAxisX) {
            if (placingOnLeft) {
                if (playerFacing.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.POSITIVE ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
                } else {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.POSITIVE ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
                }
            } else if (placingOnRight) {
                if (playerFacing.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.POSITIVE ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
                } else {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.POSITIVE ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
                }
            }
        } else if (isAxisZ) {
            if (placingOnLeft) {
                if (playerFacing.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
                } else {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
                }
            } else if (placingOnRight) {
                if (playerFacing.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
                } else {
                    return placementFace.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
                }
            }
        }

        int i = (leftBlock.isCollisionShapeFullBlock(blockgetter, leftPos) ? -1 : 0) + (rightBlock.isCollisionShapeFullBlock(blockgetter, rightPos) ? 1 : 0);
        boolean isSameAsLeft = leftBlock.is(this);
        boolean isSameAsRight = rightBlock.is(this);
        if ((!isSameAsLeft || isSameAsRight) && i <= 0) {
            if ((!isSameAsRight || isSameAsLeft) && i >= 0) {
                int stepX = playerFacing.getStepX();
                int stepZ = playerFacing.getStepZ();
                Vec3 vec3 = pContext.getClickLocation();
                double dX = vec3.x - (double) clickedPos.getX();
                double dZ = vec3.z - (double) clickedPos.getZ();
                if ((stepX >= 0 || !(dZ < 0.5D)) && (stepX <= 0 || !(dZ > 0.5D)) && (stepZ >= 0 || !(dX > 0.5D)) && (stepZ <= 0 || !(dX < 0.5D))) {
                    return playerFacing == placementFace ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
                } else {
                    return playerFacing == placementFace ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
                }
            } else {
                return playerFacing == placementFace ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            }
        } else {
            return playerFacing == placementFace ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pState = pState.cycle(OPEN);
        pLevel.setBlock(pPos, pState, 10);
        pLevel.levelEvent(pPlayer, pState.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pPos, 0);
        pLevel.gameEvent(pPlayer, this.isOpen(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    public boolean isOpen(BlockState pState) {
        return pState.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity p_153166_, Level p_153167_, BlockState p_153168_, BlockPos p_153169_, boolean p_153170_) {
        if (p_153168_.is(this) && p_153168_.getValue(OPEN) != p_153170_) {
            p_153167_.setBlock(p_153169_, p_153168_.setValue(OPEN, Boolean.valueOf(p_153170_)), 10);
            this.playSound(p_153167_, p_153169_, p_153170_);
            p_153167_.gameEvent(p_153166_, p_153170_ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, p_153169_);
        }
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean isPowered = pLevel.hasNeighborSignal(pPos);
        if (!this.defaultBlockState().is(pBlock) && isPowered != pState.getValue(POWERED)) {
            if (isPowered != pState.getValue(OPEN)) {
                this.playSound(pLevel, pPos, isPowered);
                pLevel.gameEvent(isPowered ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
            }

            pLevel.setBlock(pPos, pState.setValue(POWERED, isPowered).setValue(OPEN, isPowered), 2);
        }
    }

    private void playSound(Level pLevel, BlockPos pPos, boolean pIsOpening) {
        pLevel.levelEvent(null, pIsOpening ? this.getOpenSound() : this.getCloseSound(), pPos, 0);
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING))).cycle(HINGE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN, HINGE, POWERED);
    }
}
