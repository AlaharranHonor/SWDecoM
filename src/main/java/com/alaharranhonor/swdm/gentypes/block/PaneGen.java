package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class PaneGen extends BasicBlockGen<IronBarsBlock> {

    public PaneGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected IronBarsBlock generate() {
        return new IronBarsBlock(this.props());
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.paneBlock(this.generated, textures.get("pane", basePath), textures.get("pane", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.singleTexture(path, gen.mcLoc("item/generated"), "layer0", textures.get("pane", basePath));
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "_pane";
    }
}
