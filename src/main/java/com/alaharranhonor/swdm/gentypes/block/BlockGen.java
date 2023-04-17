package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class BlockGen extends BasicBlockGen<Block> {

    public BlockGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected Block generate() {
        return new Block(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()));
    }


    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        gen.simpleBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
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
