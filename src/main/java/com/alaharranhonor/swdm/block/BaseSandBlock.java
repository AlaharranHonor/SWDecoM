package com.alaharranhonor.swdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;

public class BaseSandBlock extends ColoredFallingBlock {

    public BaseSandBlock(int pDustColor, Properties pProperties) {
        super(new ColorRGBA(pDustColor), pProperties);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plantable) {
        return !plantable.is(BlockTags.CROPS) ? TriState.TRUE : TriState.DEFAULT;
    }
}
