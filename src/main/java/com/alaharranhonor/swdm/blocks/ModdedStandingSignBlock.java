package com.alaharranhonor.swdm.blocks;

import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ModdedStandingSignBlock extends StandingSignBlock {
    public ModdedStandingSignBlock(Properties p_i225764_1_, WoodType p_i225764_2_) {
        super(p_i225764_1_, p_i225764_2_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return SWDMTileEntities.SWDM_SIGN.get().create();
    }
}