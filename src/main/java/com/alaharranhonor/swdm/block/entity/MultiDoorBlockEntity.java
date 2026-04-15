package com.alaharranhonor.swdm.block.entity;

import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import com.alaharranhonor.swdm.registry.AttachmentSetup;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.DataComponentSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MultiDoorBlockEntity extends BlockEntity {

    public MultiDoorBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockSetup.MULTI_DOOR.get(), pos, blockState);
    }

    public MultiDoorData getDoorData() {
        return this.getData(AttachmentSetup.MULTI_DOOR);
    }

    public void setDoorData(MultiDoorData data) {
        this.setData(AttachmentSetup.MULTI_DOOR, data);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput components) {
        this.setDoorData(components.get(DataComponentSetup.MULTI_DOOR));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        components.set(DataComponentSetup.MULTI_DOOR, this.getDoorData());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }
}
