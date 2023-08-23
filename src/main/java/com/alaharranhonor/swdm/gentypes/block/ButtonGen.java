package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ButtonGen extends BasicBlockGen<ButtonBlock> {

    private final boolean isStone;
    public ButtonGen(GenSet set, Supplier<Block> baseBlock, boolean isStone) {
        super(set, baseBlock);
        this.isStone = isStone;
    }

    @Override
    protected ButtonBlock generate() {
        if (this.isStone) {
            return new ButtonBlock(this.props().noCollission(), BlockSetType.STONE, 20, false);
        }

        return new ButtonBlock(this.props().noCollission(), BlockSetType.OAK, 30, true);
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 16);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 7));
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
        if (!this.isStone) {
            gen.tag(net.minecraft.tags.ItemTags.WOODEN_BUTTONS).add(this.generated.asItem());
        }
    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.BUTTONS).add(this.generated);
        if (!this.isStone) {
            gen.tag(net.minecraft.tags.BlockTags.WOODEN_BUTTONS).add(this.generated);
        }

    }

    @Override
    public String getSuffix() {
        return "_button";
    }
}
