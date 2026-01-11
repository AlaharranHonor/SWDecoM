package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

import java.util.function.Supplier;

public class SlabGen extends BasicBlockGen<SlabBlock> {

    public SlabGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected SlabBlock generate() {
        return new SlabBlock(this.props());
    }

    @Override
    public void addRecipes(RecipeGen gen, RecipeOutput builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 2);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 5));
        gen.tintedSlab(this.generated, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath), basePath);
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path)); // Item model
    }

    @Override
    public void addLootTables(BlockLoot gen) {
        gen.add(this.generated, gen::createSlabItemTable);
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {

    }

    @Override
    public String getSuffix() {
        return "_slab";
    }
}
