package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.datagen.BlockTagGen;
import com.alaharranhonor.swdm.datagen.ItemModelGen;
import com.alaharranhonor.swdm.datagen.ItemTagGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BookshelfGen extends BasicBlockGen<Block> {

    public BookshelfGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected Block generate() {
        return new Block(this.props());
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 10));
        gen.simpleBlock(this.generated, gen.models().cubeColumn(path, textures.get("side", basePath), textures.get("end", basePath)));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        gen.existingBlock(this.generated);
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {

    }

    @Override
    public String getSuffix() {
        return "_bookshelf";
    }
}
