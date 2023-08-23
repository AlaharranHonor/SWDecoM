package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ShutterVariationGen extends ShutterGen {

    private final String variation;
    public ShutterVariationGen(GenSet set, Supplier<Block> baseBlock, String variation) {
        super(set, baseBlock);
        this.variation = variation;
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path.substring(0, path.length()));
        gen.shutter(this.generated, textures.get("shutter", basePath), textures.get("shutter", basePath), textures.get("shutter", basePath));
    }

    @Override
    public String getSuffix() {
        return "_shutter_" + this.variation;
    }
}
