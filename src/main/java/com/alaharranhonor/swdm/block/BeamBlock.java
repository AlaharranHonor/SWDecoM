package com.alaharranhonor.swdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public class BeamBlock extends Block {
    public static final EnumProperty<SWDMBlockstateProperties.Tileable> TILE = SWDMBlockstateProperties.TILEABLE;

    public BeamBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TILE);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
            if (pFacing == Direction.UP) {
                if (pFacingState.getBlock() instanceof BeamBlock) {
                    if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.SINGLE && pFacingState.getValue(TILE) == SWDMBlockstateProperties.Tileable.UPPER) {
                        return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.BOTTOM);
                    }
                    if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.UPPER && pFacingState.getValue(TILE) == SWDMBlockstateProperties.Tileable.UPPER) {
                        return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.MIDDLE);
                    }
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
            else if (pFacing == Direction.DOWN) {
                if (pFacingState.getBlock() instanceof BeamBlock) {
                    if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.BOTTOM && pFacingState.getValue(TILE) == SWDMBlockstateProperties.Tileable.BOTTOM) {
                        return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.MIDDLE);
                    }
                    if (pState.getValue(TILE) == SWDMBlockstateProperties.Tileable.SINGLE && pFacingState.getValue(TILE) == SWDMBlockstateProperties.Tileable.BOTTOM) {
                        return pState.setValue(TILE, SWDMBlockstateProperties.Tileable.UPPER);
                    }
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
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getPlayer().isCrouching()) {
            return super.getStateForPlacement(pContext);
        }
        BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
        BlockState state1 = pContext.getLevel().getBlockState(pContext.getClickedPos().above());

        if (state.getBlock() instanceof BeamBlock && state1.getBlock() instanceof BeamBlock) {
            return this.defaultBlockState().setValue(TILE, SWDMBlockstateProperties.Tileable.MIDDLE);
        }
        else if (state.getBlock() instanceof BeamBlock) {
            return this.defaultBlockState().setValue(TILE, SWDMBlockstateProperties.Tileable.UPPER);
        }
        else if (state1.getBlock() instanceof BeamBlock) {
            return this.defaultBlockState().setValue(TILE, SWDMBlockstateProperties.Tileable.BOTTOM);
        }
        return super.getStateForPlacement(pContext);
    }
}
