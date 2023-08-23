package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.BeamBlock;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.datagen.BlockTagGen;
import com.alaharranhonor.swdm.datagen.ItemModelGen;
import com.alaharranhonor.swdm.datagen.ItemTagGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.world.level.block.Block;

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
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        gen.beamBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {

    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {

    }

    @Override
    public String getSuffix() {
        return "";
    }
}
