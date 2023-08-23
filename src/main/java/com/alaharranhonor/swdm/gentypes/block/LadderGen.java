package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LadderGen extends BasicBlockGen<LadderBlock> {

    public LadderGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected LadderBlock generate() {
        return new LadderBlock(this.props().noOcclusion());
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 8);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path.substring(0, path.length() - 7));
        gen.ladder(this.generated, textures.get("ladder", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.generated).getNamespace(), path.substring(0, path.length() - 7));
        gen.singleTexture(path, gen.mcLoc("item/generated"), "layer0", textures.get("ladder", basePath));
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.CLIMBABLE).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_ladder";
    }
}
