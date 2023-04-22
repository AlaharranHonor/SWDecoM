package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class DoorVariationGen extends DoorGen {

    private final String variation;
    public DoorVariationGen(GenSet set, Supplier<Block> baseBlock, String variation) {
        super(set, baseBlock);
        this.variation = variation;
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length()));
        gen.doorBlock(this.generated, textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length()));
        gen.withExistingParent(path, gen.modLoc("block/door_inventory")).texture("top", textures.get("top", basePath)).texture("bottom", textures.get("bottom", basePath)); // Item model
    }

    @Override
    public String getSuffix() {
        return "_door_" + this.variation;
    }
}
