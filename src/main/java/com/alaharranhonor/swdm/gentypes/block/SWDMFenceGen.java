package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SWDMFenceGen extends HalfFenceGen {

    public SWDMFenceGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path);
        String fenceType = path.substring(path.lastIndexOf('_') + 1);
        gen.swdmFenceBlock(this.generated, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlockItem(this.generated, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
    }

    @Override
    public String getSuffix() {
        return "";
    }
}
