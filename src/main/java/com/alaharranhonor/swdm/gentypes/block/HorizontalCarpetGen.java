package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.HorizontalCarpetBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

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
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, ItemTags.WOOL_CARPETS, 8);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path);
        gen.horizontalCarpet(this.generated, textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        gen.withExistingParent(blockKey(this.generated).getPath(), gen.modLoc("block/" + blockKey(this.generated).getPath()));
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
        return "";
    }
}
