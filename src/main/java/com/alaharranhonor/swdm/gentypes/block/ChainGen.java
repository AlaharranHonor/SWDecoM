package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.TwoWayBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ChainGen extends BasicBlockGen<TwoWayBlock> {

    public ChainGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected TwoWayBlock generate() {
        return new TwoWayBlock(this.props());
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 16);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        gen.twoWayBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {

    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "";
    }
}
