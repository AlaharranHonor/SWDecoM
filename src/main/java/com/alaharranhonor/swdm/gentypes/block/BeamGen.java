package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.BeamBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class BeamGen extends BasicBlockGen<BeamBlock> {

    public BeamGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected BeamBlock generate() {
        return new BeamBlock(this.props());
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        gen.beamBlock(this.generated);
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
