package com.alaharranhonor.swdm.old.block;

import com.alaharranhonor.swdm.old.block.entity.SWDMSignBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModdedStandingSignBlock extends StandingSignBlock {
    public ModdedStandingSignBlock(Properties p_i225764_1_, WoodType p_i225764_2_) {
        super(p_i225764_1_, p_i225764_2_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SWDMSignBE(pPos, pState);
    }

}