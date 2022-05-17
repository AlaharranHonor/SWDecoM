package com.alaharranhonor.swdm.blocks;

import com.alaharranhonor.swdm.util.ModEventBusSubscriber;
import com.alaharranhonor.swdm.util.TimeUtil;
import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ClockBlock extends HorizontalBlock {
	public ClockBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
		);
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		if (!pLevel.isClientSide()) return ActionResultType.CONSUME;

		pPlayer.sendMessage(new StringTextComponent(TimeUtil.getRealLifeMessage( (int) (pLevel.getDayTime() % 24000f))), Util.NIL_UUID);
		return ActionResultType.CONSUME;
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		if (pState.getValue(FACING) == Direction.NORTH) {
			return VoxelShapes.box(0, 0, 0.9475, 1, 1, 1);
		} else if (pState.getValue(FACING) == Direction.SOUTH) {
			return VoxelShapes.box(0, 0, 0, 1, 1, 0.0625);
		} else if (pState.getValue(FACING) == Direction.WEST) {
			return VoxelShapes.box(0.9475, 0, 0, 1, 1, 1);
		} else if (pState.getValue(FACING) == Direction.EAST) {
			return VoxelShapes.box(0, 0, 0, 0.0625, 1, 1);
		}
		return super.getShape(pState, pLevel, pPos, pContext);
	}



	@Override
	public VoxelShape getCollisionShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModEventBusSubscriber.CLOCK_TE.create();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos checkPos = context.getClickedPos().relative(context.getHorizontalDirection());
		BlockState checkState = context.getLevel().getBlockState(checkPos);
		if (checkState.isFaceSturdy(context.getLevel(), checkPos, context.getHorizontalDirection().getOpposite())) {
			return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}


}
