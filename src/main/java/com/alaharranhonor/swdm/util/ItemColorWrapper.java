package com.alaharranhonor.swdm.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface ItemColorWrapper {

    ItemColor get();

    static ItemColorWrapper item() {
        return () -> (stack, color) -> {
            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, color);
        };
    }
}
