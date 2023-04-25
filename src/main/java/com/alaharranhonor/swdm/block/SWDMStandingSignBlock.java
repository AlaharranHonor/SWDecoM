package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.block.entity.SWDMSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SWDMStandingSignBlock extends StandingSignBlock {

    public SWDMStandingSignBlock(Properties pProperties, WoodType pType) {
        super(pProperties, pType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SWDMSignBlockEntity(pPos, pState);
    }
}
