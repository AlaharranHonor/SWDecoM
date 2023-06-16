package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.registry.TagSetup;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Supplier;

public class HalfFenceBlock extends Block implements SimpleWaterloggedBlock {

    public final boolean isWaterlogged;
    private final Supplier<HalfFenceBlock> clone;
    private final Supplier<HalfFenceBlock> next;
    private final Supplier<HalfFenceBlock> waterlogState;

    public HalfFenceBlock(Properties properties, boolean waterlogged, Supplier<HalfFenceBlock> clone, Supplier<HalfFenceBlock> next, Supplier<HalfFenceBlock> waterlogState) {
        super(properties);
        this.isWaterlogged = waterlogged;
        this.clone = clone;
        this.next = next;
        this.waterlogState = waterlogState;
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE));
        this.occlusionByIndex = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
        this.collisionShapeByIndex = this.makeShapes(2.0F, 2.0F, 24.0F, 0.0F, 24.0F);
        this.shapeByIndex = this.makeShapes(2.0F, 2.0F, 16.0F, 0.0F, 16.0F);

        for(BlockState blockstate : this.stateDefinition.getPossibleStates()) {
            this.getAABBIndex(blockstate);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(this.clone.get());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack held = pPlayer.getItemInHand(pHand);
        if (held.is(TagSetup.STATE_CYCLER)) {
            pLevel.setBlock(pPos, this.nextState(pState), 3);
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        // Fence Block Logic
        if (pLevel.isClientSide) {
            return held.is(Items.LEAD) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        } else {
            return LeadItem.bindPlayerMobs(pPlayer, pLevel, pPos);
        }
    }

    private BlockState nextState(BlockState state) {
        BlockState nextState = this.next.get().defaultBlockState();
        nextState = nextState.setValue(NORTH, state.getValue(NORTH));
        nextState = nextState.setValue(EAST, state.getValue(EAST));
        nextState = nextState.setValue(WEST, state.getValue(WEST));
        nextState = nextState.setValue(SOUTH, state.getValue(SOUTH));
        return nextState;
    }

    //
    // ----------------- Simple Waterlogged Block -----------------
    //
    private BlockState getWaterlogState(BlockState state) {
        BlockState nextState = this.waterlogState.get().defaultBlockState();
        nextState = nextState.setValue(NORTH, state.getValue(NORTH));
        nextState = nextState.setValue(EAST, state.getValue(EAST));
        nextState = nextState.setValue(WEST, state.getValue(WEST));
        nextState = nextState.setValue(SOUTH, state.getValue(SOUTH));
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

    //
    // ----------------- Cross Collision Block -----------------
    //

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((dir) -> {
        return dir.getKey().getAxis().isHorizontal();
    }).collect(Util.toMap());
    protected final VoxelShape[] collisionShapeByIndex;
    protected final VoxelShape[] shapeByIndex;
    private final Object2IntMap<BlockState> stateToIndex = new Object2IntOpenHashMap<>();

    protected VoxelShape[] makeShapes(float pNodeWidth, float pExtensionWidth, float pNodeHeight, float pExtensionBottom, float pExtensionHeight) {
        float f = 8.0F - pNodeWidth;
        float f1 = 8.0F + pNodeWidth;
        float f2 = 8.0F - pExtensionWidth;
        float f3 = 8.0F + pExtensionWidth;
        VoxelShape voxelshape = Block.box((double)f, 0.0D, (double)f, (double)f1, (double)pNodeHeight, (double)f1);
        VoxelShape voxelshape1 = Block.box((double)f2, (double)pExtensionBottom, 0.0D, (double)f3, (double)pExtensionHeight, (double)f3);
        VoxelShape voxelshape2 = Block.box((double)f2, (double)pExtensionBottom, (double)f2, (double)f3, (double)pExtensionHeight, 16.0D);
        VoxelShape voxelshape3 = Block.box(0.0D, (double)pExtensionBottom, (double)f2, (double)f3, (double)pExtensionHeight, (double)f3);
        VoxelShape voxelshape4 = Block.box((double)f2, (double)pExtensionBottom, (double)f2, 16.0D, (double)pExtensionHeight, (double)f3);
        VoxelShape voxelshape5 = Shapes.or(voxelshape1, voxelshape4);
        VoxelShape voxelshape6 = Shapes.or(voxelshape2, voxelshape3);
        VoxelShape[] avoxelshape = new VoxelShape[]{Shapes.empty(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, Shapes.or(voxelshape2, voxelshape1), Shapes.or(voxelshape3, voxelshape1), Shapes.or(voxelshape6, voxelshape1), voxelshape4, Shapes.or(voxelshape2, voxelshape4), Shapes.or(voxelshape3, voxelshape4), Shapes.or(voxelshape6, voxelshape4), voxelshape5, Shapes.or(voxelshape2, voxelshape5), Shapes.or(voxelshape3, voxelshape5), Shapes.or(voxelshape6, voxelshape5)};

        for(int i = 0; i < 16; ++i) {
            avoxelshape[i] = Shapes.or(voxelshape, avoxelshape[i]);
        }

        return avoxelshape;
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return !this.isWaterlogged;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapeByIndex[this.getAABBIndex(pState)];
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.collisionShapeByIndex[this.getAABBIndex(pState)];
    }

    private static int indexFor(Direction pFacing) {
        return 1 << pFacing.get2DDataValue();
    }

    protected int getAABBIndex(BlockState pState) {
        return this.stateToIndex.computeIntIfAbsent(pState, (p_52366_) -> {
            int i = 0;
            if (p_52366_.getValue(NORTH)) {
                i |= indexFor(Direction.NORTH);
            }

            if (p_52366_.getValue(EAST)) {
                i |= indexFor(Direction.EAST);
            }

            if (p_52366_.getValue(SOUTH)) {
                i |= indexFor(Direction.SOUTH);
            }

            if (p_52366_.getValue(WEST)) {
                i |= indexFor(Direction.WEST);
            }

            return i;
        });
    }

    public FluidState getFluidState(BlockState pState) {
        return this.isWaterlogged ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        switch(pRot) {
            case CLOCKWISE_180:
                return pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(EAST, pState.getValue(WEST)).setValue(SOUTH, pState.getValue(NORTH)).setValue(WEST, pState.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return pState.setValue(NORTH, pState.getValue(EAST)).setValue(EAST, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(WEST)).setValue(WEST, pState.getValue(NORTH));
            case CLOCKWISE_90:
                return pState.setValue(NORTH, pState.getValue(WEST)).setValue(EAST, pState.getValue(NORTH)).setValue(SOUTH, pState.getValue(EAST)).setValue(WEST, pState.getValue(SOUTH));
            default:
                return pState;
        }
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        switch(pMirror) {
            case LEFT_RIGHT:
                return pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(NORTH));
            case FRONT_BACK:
                return pState.setValue(EAST, pState.getValue(WEST)).setValue(WEST, pState.getValue(EAST));
            default:
                return super.mirror(pState, pMirror);
        }
    }

    //
    // ----------------- Fence Block -----------------
    //
    private final VoxelShape[] occlusionByIndex;

    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return this.occlusionByIndex[this.getAABBIndex(pState)];
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return this.getShape(pState, pReader, pPos, pContext);
    }

    public boolean connectsTo(BlockState pState, boolean pIsSideSolid, Direction pDirection) {
        Block block = pState.getBlock();
        boolean flag = this.isSameFence(pState);
        boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(pState, pDirection);
        return !isExceptionForConnection(pState) && pIsSideSolid || flag || flag1;
    }

    private boolean isSameFence(BlockState pState) {
        return pState.is(BlockTags.FENCES) && pState.is(BlockTags.WOODEN_FENCES) == this.defaultBlockState().is(BlockTags.WOODEN_FENCES);
    }


    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockGetter blockgetter = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = blockgetter.getBlockState(blockpos1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
        BlockState state = super.getStateForPlacement(pContext).setValue(NORTH, Boolean.valueOf(this.connectsTo(blockstate, blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH), Direction.SOUTH))).setValue(EAST, Boolean.valueOf(this.connectsTo(blockstate1, blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.WEST), Direction.WEST))).setValue(SOUTH, Boolean.valueOf(this.connectsTo(blockstate2, blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.NORTH), Direction.NORTH))).setValue(WEST, Boolean.valueOf(this.connectsTo(blockstate3, blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.EAST), Direction.EAST)));
        if (fluidstate.is(Fluids.WATER)) {
            return this.getWaterlogState(state);
        }
        return state;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (this.isWaterlogged) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pFacing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), Boolean.valueOf(this.connectsTo(pFacingState, pFacingState.isFaceSturdy(pLevel, pFacingPos, pFacing.getOpposite()), pFacing.getOpposite()))) : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, WEST, SOUTH);
    }
}
