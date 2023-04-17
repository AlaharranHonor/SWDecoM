package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class HalfWallGen extends BasicBlockGen<HalfWallBlock> {

    public HalfWallGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected HalfWallBlock generate() {
        return new HalfWallBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()));
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.tintedHalfWall(this.generated, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory")); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.WALLS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_wall";
    }
}
