package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;

import java.util.function.Supplier;

public class ButtonGen extends BasicBlockGen<ButtonBlock> {

    private final int ticks;
    public ButtonGen(GenSet set, Supplier<Block> baseBlock, int ticks) {
        super(set, baseBlock);
        this.ticks = ticks;
    }

    @Override
    protected ButtonBlock generate() {
        return new ButtonBlock(this.set.getBlockSetType(), ticks, this.props().noCollission());
    }

    @Override
    public void addRecipes(RecipeGen gen, RecipeOutput builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 16);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 7));
        gen.button(this.generated, textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory")); // Item model
    }

    @Override
    public void addItemTags(ItemTagGen gen) {
        gen.tag(net.minecraft.tags.ItemTags.BUTTONS).add(this.generated.asItem());
    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.BUTTONS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_button";
    }
}
