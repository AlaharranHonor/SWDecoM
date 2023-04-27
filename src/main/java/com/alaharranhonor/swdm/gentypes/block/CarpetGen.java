package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.HorizontalCarpetBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CarpetGen extends BasicBlockGen<CarpetBlock> {

    public CarpetGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected CarpetBlock generate() {
        return new CarpetBlock(this.props().noOcclusion());
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, net.minecraft.tags.ItemTags.CARPETS, 8);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 7));
        gen.carpet(this.generated, textures.get("", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 7));
        gen.carpet(this.generated.getRegistryName().getPath(), textures.get("", basePath));
    }

    @Override
    public void addItemTags(ItemTags gen) {
        gen.tag(net.minecraft.tags.ItemTags.CARPETS).add(this.generated.asItem());
    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.CARPETS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_carpet";
    }
}
