package com.alaharranhonor.swdm.old.block.entity;

import com.alaharranhonor.swdm.old.util.init.SWDMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SWDMSignBE extends SignBlockEntity {


    public SWDMSignBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return SWDMBlockEntities.SWDM_SIGN.get();
    }
}