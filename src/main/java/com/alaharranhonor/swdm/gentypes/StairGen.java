package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.datagen.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StairGen extends BasicBlockGen<StairBlock> {

    public StairGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected StairBlock generate() {
        return new StairBlock(() -> this.baseBlock.get().defaultBlockState(), BlockBehaviour.Properties.copy(this.baseBlock.get()));
    }

    @Override
    public void addBlockStates(BlockStates gen) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation location = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), "block/" + path.substring(0, path.length() - 7));
        gen.tintedStairs(this.generated, location);
    }

    @Override
    public void addItemModels(ItemModels gen) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path)); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "_stairs";
    }
}
