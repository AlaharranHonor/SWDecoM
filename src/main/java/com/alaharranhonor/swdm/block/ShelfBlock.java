package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.registry.TagSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShelfBlock extends Block {

    public static final EnumProperty<SWDMBlockstateProperties.ShelfType> SHELF_TYPE = SWDMBlockstateProperties.SHELF;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape[] SHAPES_BY_HEIGHT = new VoxelShape[] {
        Shapes.box(0, 0.5, 0, 1, 1, 1),
        Shapes.box(0, 0.25, 0, 1, 0.75, 1),
        Shapes.box(0, 0, 0, 1, 0.5, 1)
    };

    public ShelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(SHELF_TYPE, SWDMBlockstateProperties.ShelfType.MIDDLE_FRONT)
            .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        int stateOrdinal = pState.getValue(SHELF_TYPE).ordinal();
        int height = stateOrdinal / 3;
        int pos = stateOrdinal % 3;
        pos = (pos + 1) % 3;

        stateOrdinal = height * 3 + pos;

        stateOrdinal = stateOrdinal % SWDMBlockstateProperties.ShelfType.values().length;

        pLevel.setBlock(pPos, pState.setValue(SHELF_TYPE, SWDMBlockstateProperties.ShelfType.values()[stateOrdinal]), 3);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHELF_TYPE, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction facing = ctx.getClickedFace(); //ctx.getClickedFace().getAxis().isVertical() ? ctx.getHorizontalDirection().getAxis() : ctx.getClickedFace().getAxis();
        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES_BY_HEIGHT[pState.getValue(SHELF_TYPE).shapeIndex];
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }
}
