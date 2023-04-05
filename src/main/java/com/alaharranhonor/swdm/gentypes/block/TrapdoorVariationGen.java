package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class TrapdoorVariationGen extends TrapDoorGen {

    private final String variation;
    public TrapdoorVariationGen(Supplier<Block> baseBlock, String variation) {
        super(baseBlock);
        this.variation = variation;
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length()));
        gen.tintedTrapdoor(this.generated, textures.get("", basePath));
    }

    @Override
    public String getSuffix() {
        return "_trapdoor_" + this.variation;
    }
}
