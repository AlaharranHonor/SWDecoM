package com.alaharranhonor.swdm.old.block;

import com.alaharranhonor.swdm.old.block.entity.ClockBE;
import com.alaharranhonor.swdm.old.util.TimeUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClockBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public ClockBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
		);
	}

	/**
	 * @param pState
	 * @param pLevel
	 * @param pPos
	 * @param pPlayer
	 * @param pHand
	 * @param pHit
	 * @deprecated
	 */
	@Deprecated
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (!pLevel.isClientSide()) return InteractionResult.CONSUME_PARTIAL;

		pPlayer.sendMessage(new TextComponent(TimeUtil.getRealLifeMessage( (int) (pLevel.getDayTime() % 24000f))), Util.NIL_UUID);
		return InteractionResult.CONSUME;
	}

	/**
	 * @param pState
	 * @param pLevel
	 * @param pPos
	 * @param pContext
	 * @deprecated
	 */
	@Deprecated
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		if (pState.getValue(HorizontalDirectionalBlock.FACING) == Direction.NORTH) {
			return Shapes.box(0, 0, 0.9475, 1, 1, 1);
		} else if (pState.getValue(HorizontalDirectionalBlock.FACING) == Direction.SOUTH) {
			return Shapes.box(0, 0, 0, 1, 1, 0.0625);
		} else if (pState.getValue(HorizontalDirectionalBlock.FACING) == Direction.WEST) {
			return Shapes.box(0.9475, 0, 0, 1, 1, 1);
		} else if (pState.getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST) {
			return Shapes.box(0, 0, 0, 0.0625, 1, 1);
		}
		return super.getShape(pState, pLevel, pPos, pContext);
	}


	/**
	 * @param pState
	 * @param pLevel
	 * @param pPos
	 * @param pContext
	 * @deprecated
	 */
	@Deprecated
	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return Shapes.empty();
	}



	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new ClockBE(pPos, pState);
	}



	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		BlockPos checkPos = pContext.getClickedPos().relative(pContext.getHorizontalDirection());
		BlockState checkState = pContext.getLevel().getBlockState(checkPos);
		if (checkState.isFaceSturdy(pContext.getLevel(), checkPos, pContext.getHorizontalDirection().getOpposite())) {
			return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, pContext.getHorizontalDirection().getOpposite());
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HorizontalDirectionalBlock.FACING);
	}


}
