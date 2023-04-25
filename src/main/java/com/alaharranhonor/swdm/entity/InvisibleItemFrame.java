package com.alaharranhonor.swdm.entity;

import com.alaharranhonor.swdm.registry.EntitySetup;
import com.alaharranhonor.swdm.registry.ItemSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InvisibleItemFrame extends ItemFrame {

    public InvisibleItemFrame(EntityType<? extends InvisibleItemFrame> type, Level level) {
        super(type, level);
    }

    public InvisibleItemFrame(Level level, BlockPos pos, Direction direction) {
        super(EntitySetup.INVISIBLE_ITEM_FRAME.get(), level, pos, direction);
    }

    @Override
    protected ItemStack getFrameItemStack() {
        return new ItemStack(ItemSetup.INVISIBLE_ITEM_FRAME.get());
    }
}
