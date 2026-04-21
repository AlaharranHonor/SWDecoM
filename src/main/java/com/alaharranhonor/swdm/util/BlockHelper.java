package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.registry.BlockSetup;
import net.minecraft.world.level.block.state.BlockState;

public class BlockHelper {

    public static boolean isExceptionToConnect(BlockState state) {
        return state.is(BlockSetup.SLIDING_DOOR) || state.is(BlockSetup.SWINGING_DOOR) || state.is(BlockSetup.STATIC_DOOR);
    }
}
