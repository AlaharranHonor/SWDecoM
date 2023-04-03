package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.block.HorizontalCarpetBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class HorizontalCarpetGen extends BasicBlockGen<HorizontalCarpetBlock> {

    public HorizontalCarpetGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected HorizontalCarpetBlock generate() {
        return new HorizontalCarpetBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()).noOcclusion());
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path);
        gen.horizontalCarpet(this.generated, textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        gen.withExistingParent(this.generated.getRegistryName().getPath(), gen.modLoc("block/" + this.generated.getRegistryName().getPath()));
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
