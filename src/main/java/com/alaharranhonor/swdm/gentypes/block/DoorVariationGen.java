package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.datagen.ItemModelGen;
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
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path.substring(0, path.length()));
        gen.doorBlock(this.generated, textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path.substring(0, path.length()));
        gen.withExistingParent(path, gen.modLoc("block/door_inventory")).texture("top", textures.get("top", basePath)).texture("bottom", textures.get("bottom", basePath)); // Item model
    }

    @Override
    public String getSuffix() {
        return "_door_" + this.variation;
    }
}
