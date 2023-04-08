package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class BookshelfGen extends BasicBlockGen<Block> {

    public BookshelfGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected Block generate() {
        return new Block(BlockBehaviour.Properties.copy(this.baseBlock.get()));
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 10));
        gen.simpleBlock(this.generated, gen.models().cubeColumn(path, textures.get("side", basePath), textures.get("end", basePath)));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        gen.existingBlock(this.generated);
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "_bookshelf";
    }
}
