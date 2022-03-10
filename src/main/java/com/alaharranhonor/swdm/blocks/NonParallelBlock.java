package com.alaharranhonor.swdm.blocks;

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.blocks.WaterTroughBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class NonParallelBlock extends HorizontalBlock {

	public static final EnumProperty<SWEMBlockStateProperties.TwoWay> PART = SWEMBlockStateProperties.TWO_WAY;

	private DyeColor colour;

	public NonParallelBlock(Properties properties, DyeColor colour) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, SWEMBlockStateProperties.TwoWay.SINGLE));
		this.colour = colour;
	}

	public DyeColor getColour() {
		return this.colour;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		// TODO: IF SINGLE CAN CONNECT ON ALL 4 SIDES
		// TODO: IF NOT SINGLE; ONLY CONNECT TO THE SIDE IT'S ALREADY CONNECTED.

		IBlockReader iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = iblockreader.getBlockState(blockpos);

		BlockPos blockpos1 = context.getClickedPos().relative(context.getHorizontalDirection().getClockWise());
		BlockPos blockpos2 = context.getClickedPos().relative(context.getHorizontalDirection().getCounterClockWise());
		BlockPos blockpos3 = context.getClickedPos().relative(context.getHorizontalDirection());
		BlockPos blockpos4 = context.getClickedPos().relative(context.getHorizontalDirection().getOpposite());


		BlockState blockstate1 = iblockreader.getBlockState(blockpos1);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate4 = iblockreader.getBlockState(blockpos4);

		BlockState standard = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());

		// Check if water has been split
		/*BlockState checkState = context.getLevel().getBlockState(context.getClickedPos());
		if (checkState.getBlock() instanceof WaterTroughBlock) {
			standard.setValue(WaterTroughBlock.LEVEL, checkState.getValue(WaterTroughBlock.LEVEL));
		}*/


		if (blockstate1.getBlock() == this && blockstate2.getBlock() == this) {

			return blockstate1.getValue(FACING).getAxis() == context.getHorizontalDirection().getAxis() && blockstate2.getValue(FACING).getAxis() == context.getHorizontalDirection().getAxis() ? standard.setValue(PART, SWEMBlockStateProperties.TwoWay.MIDDLE) : standard;
		} else if (blockstate1.getBlock() == this) {
			return blockstate1.getValue(FACING).getAxis() == context.getHorizontalDirection().getAxis() ? standard.setValue(PART, SWEMBlockStateProperties.TwoWay.LEFT) : standard;
		} else if (blockstate2.getBlock() == this) {
			return blockstate2.getValue(FACING).getAxis() == context.getHorizontalDirection().getAxis() ? standard.setValue(PART, SWEMBlockStateProperties.TwoWay.RIGHT) : standard;
		} else {
			return standard;
		}
	}



	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {

		switch (stateIn.getValue(PART)) {
			case RIGHT: {
				if (facing == stateIn.getValue(FACING).getCounterClockWise()) {
					if (facingState.isAir()) {
						BlockState state = stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.SINGLE);
						if (state.getBlock() instanceof WaterTroughBlock) {
							state.setValue(WaterTroughBlock.LEVEL, state.getValue(WaterTroughBlock.LEVEL) > 12 ? 12 : state.getValue(WaterTroughBlock.LEVEL) > 8 ? 8 : state.getValue(WaterTroughBlock.LEVEL) > 4 ? 4 : 0);
						}
						return state;
					}
				} else if (facing == stateIn.getValue(FACING).getClockWise()) {
					if (facingState.getBlock() == this && stateIn.getValue(FACING).getAxis() == facingState.getValue(FACING).getAxis()) {
						return stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.MIDDLE);
					}
				}
				break;
			}
			case LEFT: {
				if (facing == stateIn.getValue(FACING).getClockWise()) {
					if (facingState.isAir()) {
						BlockState state = stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.SINGLE);
						if (state.getBlock() instanceof WaterTroughBlock) {
							state.setValue(WaterTroughBlock.LEVEL, state.getValue(WaterTroughBlock.LEVEL) > 12 ? 12 : state.getValue(WaterTroughBlock.LEVEL) > 8 ? 8 : state.getValue(WaterTroughBlock.LEVEL) > 4 ? 4 : 0);
						}
						return state;
					}
				} else if (facing == stateIn.getValue(FACING).getCounterClockWise()) {
					if (facingState.getBlock() == this && stateIn.getValue(FACING).getAxis() == facingState.getValue(FACING).getAxis()) {
						return stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.MIDDLE);
					}
				}
				break;
			}
			case SINGLE: {
				if (facing == stateIn.getValue(FACING)) {
					if (!(facingState.getBlock() instanceof com.alaharranhonor.swem.blocks.NonParallelBlock)) {
						return stateIn;
					}
					if (worldIn.getBlockState(currentPos.relative(facingState.getValue(FACING).getCounterClockWise())).getBlock() instanceof com.alaharranhonor.swem.blocks.NonParallelBlock) {
						return stateIn.setValue(FACING, facingState.getValue(FACING)).setValue(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
					} else if (worldIn.getBlockState(currentPos.relative(facingState.getValue(FACING).getClockWise())).getBlock() instanceof com.alaharranhonor.swem.blocks.NonParallelBlock) {
						return stateIn.setValue(FACING, facingState.getValue(FACING)).setValue(PART, SWEMBlockStateProperties.TwoWay.LEFT);
					}
				} else if (facing == stateIn.getValue(FACING).getOpposite()) {
					if (!(facingState.getBlock() instanceof com.alaharranhonor.swem.blocks.NonParallelBlock)) {
						return stateIn;
					}
					if (worldIn.getBlockState(currentPos.relative(facingState.getValue(FACING).getCounterClockWise())).getBlock() instanceof com.alaharranhonor.swem.blocks.NonParallelBlock) {
						return stateIn.setValue(FACING, facingState.getValue(FACING)).setValue(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
					} else if (worldIn.getBlockState(currentPos.relative(facingState.getValue(FACING).getClockWise())).getBlock() instanceof com.alaharranhonor.swem.blocks.NonParallelBlock) {
						return stateIn.setValue(FACING, facingState.getValue(FACING)).setValue(PART, SWEMBlockStateProperties.TwoWay.LEFT);
					}
				}

				if (facing == stateIn.getValue(FACING).getClockWise()) {
					if (facingState.getBlock() == this && stateIn.getValue(FACING).getAxis() == facingState.getValue(FACING).getAxis()) {
						return stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.LEFT);
					}
				} else if (facing == stateIn.getValue(FACING).getCounterClockWise()) {
					if (facingState.getBlock() == this && stateIn.getValue(FACING).getAxis() == facingState.getValue(FACING).getAxis()) {
						return stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
					}
				}
				break;
			}
			case MIDDLE: {
				if (facing == stateIn.getValue(FACING).getClockWise()) {
					if (facingState.isAir()) {
						return stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
					}
				} else if (facing == stateIn.getValue(FACING).getCounterClockWise()) {
					if (facingState.isAir()) {
						return stateIn.setValue(PART, SWEMBlockStateProperties.TwoWay.LEFT);
					}
				}
				break;
			}
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);

	}

	@Override
	public PushReaction getPistonPushReaction(BlockState pState) {
		return PushReaction.BLOCK;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, PART);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
			return VoxelShapes.box(0.01d, 0.01d, 0.4375d, 0.99d, 0.99d, 0.5625d);
		} else {
			return VoxelShapes.box(0.4375d, 0.01d, 0.01d, 0.5625d, 0.99d, 0.99d);
		}
	}
}

