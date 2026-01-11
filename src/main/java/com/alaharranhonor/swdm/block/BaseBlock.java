package com.alaharranhonor.swdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;

public class BaseBlock extends Block {

    public BaseBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plantable) {
        return !plantable.is(BlockTags.CROPS) ? TriState.TRUE : TriState.DEFAULT;
    }
}
