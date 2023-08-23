package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class TrapdoorVariationGen extends TrapDoorGen {

    private final String variation;
    public TrapdoorVariationGen(GenSet set, Supplier<Block> baseBlock, String variation) {
        super(set, baseBlock);
        this.variation = variation;
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path.substring(0, path.length()));
        gen.tintedTrapdoor(this.generated, textures.get("", basePath));
    }

    @Override
    public String getSuffix() {
        return "_trapdoor_" + this.variation;
    }
}
