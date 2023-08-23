package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.ShutterBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ShutterGen extends BasicBlockGen<ShutterBlock> {

    public ShutterGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected ShutterBlock generate() {
        return new ShutterBlock(this.props().noOcclusion());
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 8);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 8));
        gen.shutter(this.generated, textures.get("side", basePath), textures.get("front", basePath), textures.get("back", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path)); // Item model
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {

    }

    @Override
    public String getSuffix() {
        return "_shutter";
    }
}
