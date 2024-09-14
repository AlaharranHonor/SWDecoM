package com.alaharranhonor.swdm.block;

import net.minecraft.world.level.block.SandBlock;
import net.minecraftforge.common.PlantType;

public class BaseSandBlock extends SandBlock {

    public BaseSandBlock(int pDustColor, Properties pProperties) {
        super(pDustColor, pProperties);
    }

    @Override
    public boolean canSustainPlant(net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.BlockGetter world, net.minecraft.core.BlockPos pos, net.minecraft.core.Direction facing, net.minecraftforge.common.IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(world, pos.relative(facing));
        return plantType != PlantType.CROP;
    }
}
