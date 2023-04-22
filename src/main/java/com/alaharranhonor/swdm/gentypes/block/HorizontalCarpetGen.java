package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.HorizontalCarpetBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HorizontalCarpetGen extends BasicBlockGen<HorizontalCarpetBlock> {

    public HorizontalCarpetGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }


    @Override
    protected HorizontalCarpetBlock generate() {
        return new HorizontalCarpetBlock(this.props().noOcclusion());
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, net.minecraft.tags.ItemTags.CARPETS, 8);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path);
        gen.horizontalCarpet(this.generated, textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        gen.withExistingParent(this.generated.getRegistryName().getPath(), gen.modLoc("block/" + this.generated.getRegistryName().getPath()));
    }

    @Override
    public void addItemTags(ItemTags gen) {
        gen.tag(net.minecraft.tags.ItemTags.CARPETS).add(this.generated.asItem());
    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "";
    }
}
