package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.block.TwoWayBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class TwoWayGen extends BasicBlockGen<TwoWayBlock> {

    public TwoWayGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected TwoWayBlock generate() {
        return new TwoWayBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()));
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        gen.twoWayBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {

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
