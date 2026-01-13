package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;

import java.util.function.Supplier;

public class TrapDoorGen extends BasicBlockGen<TrapDoorBlock> {

    public TrapDoorGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected TrapDoorBlock generate() {
        return new TrapDoorBlock(this.set.getBlockSetType(), this.props().noOcclusion());
    }

    @Override
    public void addRecipes(RecipeGen gen, RecipeOutput builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 8);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 9));
        gen.tintedTrapdoor(this.generated, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_bottom")); // Item model
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.TRAPDOORS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_trapdoor";
    }
}
