package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class PressurePlateGen extends BasicBlockGen<PressurePlateBlock> {

    private final PressurePlateBlock.Sensitivity sensitivity;

    public PressurePlateGen(Supplier<Block> baseBlock, PressurePlateBlock.Sensitivity sensitivity) {
        super(baseBlock);
        this.sensitivity = sensitivity;
    }

    @Override
    protected PressurePlateBlock generate() {
        return new PressurePlateBlock(this.sensitivity, BlockBehaviour.Properties.copy(this.baseBlock.get()).noCollission());
    }

    @Override
    public void addBlockStates(BlockStates gen) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation location = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), "block/" + path.substring(0, path.length() - 15));
        gen.pressurePlate(this.generated, location);
    }

    @Override
    public void addItemModels(ItemModels gen) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path)); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "_pressure_plate";
    }
}
