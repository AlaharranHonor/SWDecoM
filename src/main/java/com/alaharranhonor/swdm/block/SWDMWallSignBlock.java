package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.block.entity.SWDMSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SWDMWallSignBlock extends WallSignBlock {

    public SWDMWallSignBlock(Properties pProperties, WoodType pType) {
        super(pProperties, pType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SWDMSignBlockEntity(pPos, pState);
    }
}
