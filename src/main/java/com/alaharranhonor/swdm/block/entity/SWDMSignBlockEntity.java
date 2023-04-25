package com.alaharranhonor.swdm.block.entity;

import com.alaharranhonor.swdm.registry.BlockSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SWDMSignBlockEntity extends SignBlockEntity {

    public SWDMSignBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockSetup.SIGN_BLOCK_ENTITY.get();
    }
}
