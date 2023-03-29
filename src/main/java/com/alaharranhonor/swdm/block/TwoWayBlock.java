package com.alaharranhonor.swdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public class TwoWayBlock extends ChainBlock {

	public static final EnumProperty<SWDMBlockstateProperties.TwoWay> PART = SWDMBlockstateProperties.TWO_WAY;

	public TwoWayBlock(BlockBehaviour.Properties p_i241175_1_) {
		super(p_i241175_1_);
		this.registerDefaultState(this.defaultBlockState().setValue(PART, SWDMBlockstateProperties.TwoWay.SINGLE));
	}


	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		// The state to be set for a coated chain.


		BlockPos pos1 = pContext.getClickedPos().relative(pContext.getClickedFace().getAxis(), 1);
		BlockPos pos2 = pContext.getClickedPos().relative(pContext.getClickedFace().getAxis(), -1);

		BlockState state1 = pContext.getLevel().getBlockState(pos1);
		BlockState state2 = pContext.getLevel().getBlockState(pos2);
		if (state1.getBlock() instanceof TwoWayBlock && state2.getBlock() instanceof TwoWayBlock) {
			return super.getStateForPlacement(pContext).setValue(PART, SWDMBlockstateProperties.TwoWay.MIDDLE);
		} else if (state1.getBlock() instanceof TwoWayBlock && !(state2.getBlock() instanceof TwoWayBlock)) {
			return super.getStateForPlacement(pContext).setValue(PART, SWDMBlockstateProperties.TwoWay.RIGHT);
		} else if (state2.getBlock() instanceof TwoWayBlock) {
			return super.getStateForPlacement(pContext).setValue(PART, SWDMBlockstateProperties.TwoWay.LEFT);
		}


		return super.getStateForPlacement(pContext).setValue(PART, SWDMBlockstateProperties.TwoWay.SINGLE);
	}



	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 *
	 * @param pState
	 * @param pFacing
	 * @param pFacingState
	 * @param pLevel
	 * @param pCurrentPos
	 * @param pFacingPos
	 */
	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		// Update an already existing coated chain.

		BlockPos pos1 = pCurrentPos.relative(pState.getValue(AXIS), 1);
		BlockPos pos2 = pCurrentPos.relative(pState.getValue(AXIS), -1);

		BlockState state1 = pLevel.getBlockState(pos1);
		BlockState state2 = pLevel.getBlockState(pos2);
		if (state1.getBlock() instanceof TwoWayBlock && state2.getBlock() instanceof TwoWayBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.MIDDLE);
		} else if (state1.getBlock() instanceof TwoWayBlock && !(state2.getBlock() instanceof TwoWayBlock)) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.RIGHT);
		} else if (state2.getBlock() instanceof TwoWayBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.LEFT);
		}
		if (state1.getBlock() instanceof AirBlock && state2.getBlock() instanceof AirBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.SINGLE);
		} else if (state1.getBlock() instanceof AirBlock && state2.getBlock() instanceof TwoWayBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.LEFT);
		} else if (state1.getBlock() instanceof TwoWayBlock && state2.getBlock() instanceof AirBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.RIGHT);
		}


		return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		super.createBlockStateDefinition(pBuilder);
		pBuilder.add(PART);
	}
}
