package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class BlockGen extends BasicBlockGen<Block> {

    public BlockGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected Block generate() {
        return new Block(BlockBehaviour.Properties.copy(this.baseBlock.get()));
    }


    @Override
    public void addBlockStates(BlockStates gen) {
        gen.simpleBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModels gen) {
        gen.withExistingParent(this.generated.getRegistryName().getPath(), gen.modLoc("block/" + this.generated.getRegistryName().getPath())); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "";
    }
}
