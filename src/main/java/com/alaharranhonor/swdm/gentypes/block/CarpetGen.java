package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
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
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, ItemTags.WOOL_CARPETS, 8);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 7));
        gen.carpet(this.generated, textures.get("", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 7));
        gen.carpet(blockKey(this.generated).getPath(), textures.get("", basePath));
    }

    @Override
    public void addItemTags(ItemTagGen gen) {
        gen.tag(ItemTags.WOOL_CARPETS).add(this.generated.asItem());
    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(BlockTags.WOOL_CARPETS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_carpet";
    }
}
