package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SWDMFenceGen extends HalfFenceGen {

    public SWDMFenceGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path);
        String fenceType = path.substring(path.lastIndexOf('_') + 1);
        gen.swdmFenceBlock(this.generated, SWDMBlockstateProperties.WallType.FULL, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlock(this.blockWaterlogged.get(), SWDMBlockstateProperties.WallType.FULL, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlock(this.lowerBlock.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlock(this.lowerBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlock(this.upperBlock.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlock(this.upperBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
        gen.swdmFenceBlockItem(this.generated, textures.get("", basePath), textures.get("lattice", basePath), fenceType);
    }

    @Override
    public String getSuffix() {
        return "";
    }
}
