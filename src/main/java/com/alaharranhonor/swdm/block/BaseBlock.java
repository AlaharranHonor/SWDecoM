package com.alaharranhonor.swdm.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.PlantType;

public class BaseBlock extends Block {

    public BaseBlock(Properties props) {
        super(props);
    }

    @Override
    public boolean canSustainPlant(net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.BlockGetter world, net.minecraft.core.BlockPos pos, net.minecraft.core.Direction facing, net.minecraftforge.common.IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(world, pos.relative(facing));
        return plantType != PlantType.CROP;
    }
}
