package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.util.TextureSet;
import com.alaharranhonor.swdm.datagen.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class SlabGen extends BasicBlockGen<SlabBlock> {

    public SlabGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected SlabBlock generate() {
        return new SlabBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()));
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.tintedSlab(this.generated, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath), basePath);
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
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
        return "_slab";
    }
}
