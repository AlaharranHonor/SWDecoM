package com.alaharranhonor.swdm.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class CoatedChain extends ChainBlock {

	public static final EnumProperty<SWDMBlockstateProperties.TwoWay> PART = SWDMBlockstateProperties.TWO_WAY;

	public CoatedChain(Properties p_i241175_1_) {
		super(p_i241175_1_);
		this.registerDefaultState(this.defaultBlockState().setValue(PART, SWDMBlockstateProperties.TwoWay.SINGLE));
	}


	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		// The state to be set for a coated chain.


		BlockPos pos1 = pContext.getClickedPos().relative(pContext.getClickedFace().getAxis(), 1);
		BlockPos pos2 = pContext.getClickedPos().relative(pContext.getClickedFace().getAxis(), -1);

		BlockState state1 = pContext.getLevel().getBlockState(pos1);
		BlockState state2 = pContext.getLevel().getBlockState(pos2);
		if (state1.getBlock() instanceof CoatedChain && state2.getBlock() instanceof CoatedChain) {
			return super.getStateForPlacement(pContext).setValue(PART, SWDMBlockstateProperties.TwoWay.MIDDLE);
		} else if (state1.getBlock() instanceof CoatedChain && !(state2.getBlock() instanceof CoatedChain)) {
			return super.getStateForPlacement(pContext).setValue(PART, SWDMBlockstateProperties.TwoWay.RIGHT);
		} else if (state2.getBlock() instanceof CoatedChain) {
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
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		// Update an already existing coated chain.

		BlockPos pos1 = pCurrentPos.relative(pState.getValue(AXIS), 1);
		BlockPos pos2 = pCurrentPos.relative(pState.getValue(AXIS), -1);

		BlockState state1 = pLevel.getBlockState(pos1);
		BlockState state2 = pLevel.getBlockState(pos2);
		if (state1.getBlock() instanceof CoatedChain && state2.getBlock() instanceof CoatedChain) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.MIDDLE);
		} else if (state1.getBlock() instanceof CoatedChain && !(state2.getBlock() instanceof CoatedChain)) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.RIGHT);
		} else if (state2.getBlock() instanceof CoatedChain) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.LEFT);
		}
		if (state1.getBlock() instanceof AirBlock && state2.getBlock() instanceof AirBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.SINGLE);
		} else if (state1.getBlock() instanceof AirBlock && state2.getBlock() instanceof CoatedChain) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.LEFT);
		} else if (state1.getBlock() instanceof CoatedChain && state2.getBlock() instanceof AirBlock) {
			return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(PART, SWDMBlockstateProperties.TwoWay.RIGHT);
		}


		return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		super.createBlockStateDefinition(pBuilder);
		pBuilder.add(PART);
	}
}
