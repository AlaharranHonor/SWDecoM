package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.registry.TagSetup;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Supplier;

public class HalfWallBlock extends Block implements SimpleWaterloggedBlock {

    public final boolean isWaterlogged;
    private final Supplier<HalfWallBlock> clone;
    private final Supplier<HalfWallBlock> next;
    private final Supplier<HalfWallBlock> waterlogState;

    public HalfWallBlock(Properties properties, boolean waterlogged, Supplier<HalfWallBlock> clone, Supplier<HalfWallBlock> next, Supplier<HalfWallBlock> waterlogState) {
        super(properties);
        this.isWaterlogged = waterlogged;
        this.clone = clone;
        this.next = next;
        this.waterlogState = waterlogState;
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.TRUE).setValue(NORTH_WALL, WallSide.NONE).setValue(EAST_WALL, WallSide.NONE).setValue(SOUTH_WALL, WallSide.NONE).setValue(WEST_WALL, WallSide.NONE));
        this.shapeByIndex = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.collisionShapeByIndex = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        pLevel.setBlock(pPos, this.nextState(pState), 3);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public BlockState nextState(BlockState state) {
        BlockState nextState = this.next.get().defaultBlockState();
        nextState = nextState.setValue(UP, state.getValue(UP));
        nextState = nextState.setValue(NORTH_WALL, state.getValue(NORTH_WALL));
        nextState = nextState.setValue(EAST_WALL, state.getValue(EAST_WALL));
        nextState = nextState.setValue(WEST_WALL, state.getValue(WEST_WALL));
        nextState = nextState.setValue(SOUTH_WALL, state.getValue(SOUTH_WALL));
        return nextState;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(this.clone.get());
    }

    protected Map<BlockState, VoxelShape> makeShapes(float x0, float z0, float postHeight, float bottom, float lowWallHeight, float tallWallHeight) {
        float f = 8.0F - x0;
        float f1 = 8.0F + x0;
        float f2 = 8.0F - z0;
        float f3 = 8.0F + z0;
        VoxelShape postShape = Block.box(f, 0.0D, f, f1, postHeight, f1);
        VoxelShape northLowWall = Block.box(f2, bottom, 0.0D, f3, lowWallHeight, f3);
        VoxelShape southLowWall = Block.box(f2, bottom, f2, f3, lowWallHeight, 16.0D);
        VoxelShape westLowWall = Block.box(0.0D, bottom, f2, f3, lowWallHeight, f3);
        VoxelShape eastLowWall = Block.box(f2, bottom, f2, 16.0D, lowWallHeight, f3);
        VoxelShape northTallWall = Block.box(f2, bottom, 0.0D, f3, tallWallHeight, f3);
        VoxelShape southTallWall = Block.box(f2, bottom, f2, f3, tallWallHeight, 16.0D);
        VoxelShape westTallWall = Block.box(0.0D, bottom, f2, f3, tallWallHeight, f3);
        VoxelShape eastTallWall = Block.box(f2, bottom, f2, 16.0D, tallWallHeight, f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean isPost : WallBlock.UP.getPossibleValues()) {
            for (WallSide east : WallBlock.EAST_WALL.getPossibleValues()) {
                for (WallSide north : WallBlock.NORTH_WALL.getPossibleValues()) {
                    for (WallSide west : WallBlock.WEST_WALL.getPossibleValues()) {
                        for (WallSide south : WallBlock.SOUTH_WALL.getPossibleValues()) {
                            VoxelShape wallShape = Shapes.empty();
                            wallShape = applyWallShape(wallShape, east, eastLowWall, eastTallWall);
                            wallShape = applyWallShape(wallShape, west, westLowWall, westTallWall);
                            wallShape = applyWallShape(wallShape, north, northLowWall, northTallWall);
                            wallShape = applyWallShape(wallShape, south, southLowWall, southTallWall);
                            if (isPost) {
                                wallShape = Shapes.or(wallShape, postShape);
                            }

                            BlockState blockstate = this.defaultBlockState().setValue(WallBlock.UP, isPost).setValue(WallBlock.EAST_WALL, east).setValue(WallBlock.WEST_WALL, west).setValue(WallBlock.NORTH_WALL, north).setValue(WallBlock.SOUTH_WALL, south);
                            builder.put(blockstate, wallShape);
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    // ----------------- Simple Waterlogged Block -----------------

    private BlockState getWaterlogState(BlockState state) {
        BlockState nextState = this.waterlogState.get().defaultBlockState();
        nextState = nextState.setValue(UP, state.getValue(UP));
        nextState = nextState.setValue(NORTH_WALL, state.getValue(NORTH_WALL));
        nextState = nextState.setValue(EAST_WALL, state.getValue(EAST_WALL));
        nextState = nextState.setValue(WEST_WALL, state.getValue(WEST_WALL));
        nextState = nextState.setValue(SOUTH_WALL, state.getValue(SOUTH_WALL));
        return nextState;
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return !this.isWaterlogged && pFluid == Fluids.WATER;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        if (!this.isWaterlogged && pFluidState.getType() == Fluids.WATER) {
            if (!pLevel.isClientSide()) {
                pLevel.setBlock(pPos, this.getWaterlogState(pState), 3);
                pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        if (this.isWaterlogged) {
            pLevel.setBlock(pPos, this.getWaterlogState(pState), 3);
            if (!pState.canSurvive(pLevel, pPos)) {
                pLevel.destroyBlock(pPos, true);
            }

            return new ItemStack(Items.WATER_BUCKET);
        } else {
            return ItemStack.EMPTY;
        }
    }

    // ----------------- Wall Block -----------------
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<WallSide> EAST_WALL = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallSide> NORTH_WALL = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallSide> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallSide> WEST_WALL = BlockStateProperties.WEST_WALL;
    private final Map<BlockState, VoxelShape> shapeByIndex;
    private final Map<BlockState, VoxelShape> collisionShapeByIndex;
    private static final int WALL_WIDTH = 3;
    private static final int WALL_HEIGHT = 14;
    private static final int POST_WIDTH = 4;
    private static final int POST_COVER_WIDTH = 1;
    private static final int WALL_COVER_START = 7;
    private static final int WALL_COVER_END = 9;
    private static final VoxelShape POST_TEST = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape NORTH_TEST = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape SOUTH_TEST = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_TEST = Block.box(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape EAST_TEST = Block.box(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

    protected static VoxelShape applyWallShape(VoxelShape pBaseShape, WallSide pHeight, VoxelShape pLowShape, VoxelShape pTallShape) {
        if (pHeight == WallSide.TALL) {
            return Shapes.or(pBaseShape, pTallShape);
        } else {
            return pHeight == WallSide.LOW ? Shapes.or(pBaseShape, pLowShape) : pBaseShape;
        }
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapeByIndex.get(pState);
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.collisionShapeByIndex.get(pState);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    private boolean connectsTo(BlockState pState, boolean pSideSolid, Direction pDirection) {
        Block block = pState.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(pState, pDirection);
        return pState.is(BlockTags.WALLS) || !isExceptionForConnection(pState) && pSideSolid || block instanceof IronBarsBlock || flag;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.above();
        BlockState blockstate = levelreader.getBlockState(blockpos1);
        BlockState blockstate1 = levelreader.getBlockState(blockpos2);
        BlockState blockstate2 = levelreader.getBlockState(blockpos3);
        BlockState blockstate3 = levelreader.getBlockState(blockpos4);
        BlockState blockstate4 = levelreader.getBlockState(blockpos5);
        boolean flag = this.connectsTo(blockstate, blockstate.isFaceSturdy(levelreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
        boolean flag1 = this.connectsTo(blockstate1, blockstate1.isFaceSturdy(levelreader, blockpos2, Direction.WEST), Direction.WEST);
        boolean flag2 = this.connectsTo(blockstate2, blockstate2.isFaceSturdy(levelreader, blockpos3, Direction.NORTH), Direction.NORTH);
        boolean flag3 = this.connectsTo(blockstate3, blockstate3.isFaceSturdy(levelreader, blockpos4, Direction.EAST), Direction.EAST);
        BlockState blockstate5 = this.defaultBlockState();
        if (fluidstate.is(Fluids.WATER)) {
            blockstate5 = this.waterlogState.get().defaultBlockState();
        }
        return this.updateShape(levelreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (this.isWaterlogged) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        if (pFacing == Direction.DOWN) {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return pFacing == Direction.UP ? this.topUpdate(pLevel, pState, pFacingPos, pFacingState) : this.sideUpdate(pLevel, pCurrentPos, pState, pFacingPos, pFacingState, pFacing);
        }
    }

    private static boolean isConnected(BlockState pState, Property<WallSide> pHeightProperty) {
        return pState.getValue(pHeightProperty) != WallSide.NONE;
    }

    private static boolean isCovered(VoxelShape pShape1, VoxelShape pShape2) {
        return !Shapes.joinIsNotEmpty(pShape2, pShape1, BooleanOp.ONLY_FIRST);
    }

    private BlockState topUpdate(LevelReader pLevel, BlockState p_57976_, BlockPos p_57977_, BlockState p_57978_) {
        boolean flag = isConnected(p_57976_, NORTH_WALL);
        boolean flag1 = isConnected(p_57976_, EAST_WALL);
        boolean flag2 = isConnected(p_57976_, SOUTH_WALL);
        boolean flag3 = isConnected(p_57976_, WEST_WALL);
        return this.updateShape(pLevel, p_57976_, p_57977_, p_57978_, flag, flag1, flag2, flag3);
    }

    private BlockState sideUpdate(LevelReader pLevel, BlockPos p_57990_, BlockState p_57991_, BlockPos p_57992_, BlockState p_57993_, Direction p_57994_) {
        Direction direction = p_57994_.getOpposite();
        boolean flag = p_57994_ == Direction.NORTH ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, NORTH_WALL);
        boolean flag1 = p_57994_ == Direction.EAST ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, EAST_WALL);
        boolean flag2 = p_57994_ == Direction.SOUTH ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, SOUTH_WALL);
        boolean flag3 = p_57994_ == Direction.WEST ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, WEST_WALL);
        BlockPos blockpos = p_57990_.above();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return this.updateShape(pLevel, p_57991_, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    private BlockState updateShape(LevelReader pLevel, BlockState p_57981_, BlockPos p_57982_, BlockState p_57983_, boolean p_57984_, boolean p_57985_, boolean p_57986_, boolean p_57987_) {
        VoxelShape voxelshape = p_57983_.getCollisionShape(pLevel, p_57982_).getFaceShape(Direction.DOWN);
        BlockState blockstate = this.updateSides(p_57981_, p_57984_, p_57985_, p_57986_, p_57987_, voxelshape);
        return blockstate.setValue(UP, Boolean.valueOf(this.shouldRaisePost(blockstate, p_57983_, voxelshape)));
    }

    private boolean shouldRaisePost(BlockState p_58007_, BlockState p_58008_, VoxelShape p_58009_) {
        boolean flag = p_58008_.getBlock() instanceof WallBlock && p_58008_.getValue(UP);
        if (flag) {
            return true;
        } else {
            WallSide wallside = p_58007_.getValue(NORTH_WALL);
            WallSide wallside1 = p_58007_.getValue(SOUTH_WALL);
            WallSide wallside2 = p_58007_.getValue(EAST_WALL);
            WallSide wallside3 = p_58007_.getValue(WEST_WALL);
            boolean flag1 = wallside1 == WallSide.NONE;
            boolean flag2 = wallside3 == WallSide.NONE;
            boolean flag3 = wallside2 == WallSide.NONE;
            boolean flag4 = wallside == WallSide.NONE;
            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
            if (flag5) {
                return true;
            } else {
                boolean flag6 = wallside == WallSide.TALL && wallside1 == WallSide.TALL || wallside2 == WallSide.TALL && wallside3 == WallSide.TALL;
                if (flag6) {
                    return false;
                } else {
                    return p_58008_.is(BlockTags.WALL_POST_OVERRIDE) || isCovered(p_58009_, POST_TEST);
                }
            }
        }
    }

    private BlockState updateSides(BlockState p_58025_, boolean p_58026_, boolean p_58027_, boolean p_58028_, boolean p_58029_, VoxelShape p_58030_) {
        return p_58025_.setValue(NORTH_WALL, this.makeWallState(p_58026_, p_58030_, NORTH_TEST)).setValue(EAST_WALL, this.makeWallState(p_58027_, p_58030_, EAST_TEST)).setValue(SOUTH_WALL, this.makeWallState(p_58028_, p_58030_, SOUTH_TEST)).setValue(WEST_WALL, this.makeWallState(p_58029_, p_58030_, WEST_TEST));
    }

    private WallSide makeWallState(boolean p_58042_, VoxelShape p_58043_, VoxelShape p_58044_) {
        if (p_58042_) {
            return isCovered(p_58043_, p_58044_) ? WallSide.TALL : WallSide.LOW;
        } else {
            return WallSide.NONE;
        }
    }

    public FluidState getFluidState(BlockState pState) {
        return this.isWaterlogged ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return !this.isWaterlogged;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, NORTH_WALL, EAST_WALL, WEST_WALL, SOUTH_WALL);
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        switch(pRotation) {
            case CLOCKWISE_180:
                return pState.setValue(NORTH_WALL, pState.getValue(SOUTH_WALL)).setValue(EAST_WALL, pState.getValue(WEST_WALL)).setValue(SOUTH_WALL, pState.getValue(NORTH_WALL)).setValue(WEST_WALL, pState.getValue(EAST_WALL));
            case COUNTERCLOCKWISE_90:
                return pState.setValue(NORTH_WALL, pState.getValue(EAST_WALL)).setValue(EAST_WALL, pState.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, pState.getValue(WEST_WALL)).setValue(WEST_WALL, pState.getValue(NORTH_WALL));
            case CLOCKWISE_90:
                return pState.setValue(NORTH_WALL, pState.getValue(WEST_WALL)).setValue(EAST_WALL, pState.getValue(NORTH_WALL)).setValue(SOUTH_WALL, pState.getValue(EAST_WALL)).setValue(WEST_WALL, pState.getValue(SOUTH_WALL));
            default:
                return pState;
        }
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        switch(pMirror) {
            case LEFT_RIGHT:
                return pState.setValue(NORTH_WALL, pState.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, pState.getValue(NORTH_WALL));
            case FRONT_BACK:
                return pState.setValue(EAST_WALL, pState.getValue(WEST_WALL)).setValue(WEST_WALL, pState.getValue(EAST_WALL));
            default:
                return super.mirror(pState, pMirror);
        }
    }
}
