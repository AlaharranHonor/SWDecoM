package com.alaharranhonor.swdm.tileentity;

import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class SWDMSignTE extends SignTileEntity {


    @Override
    public TileEntityType<?> getType() {
        return SWDMTileEntities.SWDM_SIGN.get();
    }
}