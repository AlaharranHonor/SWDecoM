package com.alaharranhonor.swdm.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class BeamBlock extends Block {
    public static final EnumProperty<SWDMBlockstateProperties.Tileable> TILE = SWDMBlockstateProperties.TILEABLE;

    public BeamBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TILE);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == Direction.UP) {
            if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.SINGLE) {
                    return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.BOTTOM);
            }
            if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.UPPER) {
                return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.MIDDLE);
            }
            if (pFacingState.isAir()) {
                if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.BOTTOM) {
                    return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.SINGLE);
                }
                if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.MIDDLE) {
                    return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.UPPER);
                }
            }
        }
        //come back to
       else if (pFacing == Direction.DOWN) {
            if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.BOTTOM) {
                return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.MIDDLE);
            }
            if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.SINGLE) {
                return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.UPPER);
            }
            if (pFacingState.isAir()) {
                if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.MIDDLE) {
                    return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.BOTTOM);
                }
                if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.UPPER) {
                    return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.SINGLE);
                }
            }
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {

        BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
        BlockState state1 = pContext.getLevel().getBlockState(pContext.getClickedPos().above());
        if (state.getBlock() instanceof BeamBlock && state1.getBlock() instanceof BeamBlock) {
            return state.getBlock().defaultBlockState().setValue(TILE, SWDMBlockstateProperties.Tileable.MIDDLE);
        }
        else if (state.getBlock() instanceof BeamBlock) {
            return state.getBlock().defaultBlockState().setValue(TILE, SWDMBlockstateProperties.Tileable.UPPER);
        }
        else if (state1.getBlock() instanceof BeamBlock) {
            return state1.getBlock().defaultBlockState().setValue(TILE, SWDMBlockstateProperties.Tileable.BOTTOM);
        }
        return super.getStateForPlacement(pContext);
    }
}
