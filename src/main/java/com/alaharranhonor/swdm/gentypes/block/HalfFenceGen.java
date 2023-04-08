package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.block.HalfFenceBlock;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HalfFenceGen extends BasicBlockGen<HalfFenceBlock> {

    public HalfFenceGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected HalfFenceBlock generate() {
        return new HalfFenceBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()));
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 4);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 11));
        gen.tintedHalfFence(this.generated, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
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
        gen.tag(net.minecraft.tags.BlockTags.FENCES).add(this.generated);
        gen.tag(net.minecraft.tags.BlockTags.WOODEN_FENCES).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_half_fence";
    }
}