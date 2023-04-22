package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ArrayGen extends ShutterGen {
    public ArrayGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length()));
        gen.shutter(this.generated, textures.get("side", basePath), textures.get("front", basePath), textures.get("back", basePath));
    }

    @Override
    public String getSuffix() {
        return "";
    }
}
