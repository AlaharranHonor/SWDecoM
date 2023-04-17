package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ButtonGen extends BasicBlockGen<ButtonBlock> {

    private final boolean isStone;
    public ButtonGen(Supplier<Block> baseBlock, boolean isStone) {
        super(baseBlock);
        this.isStone = isStone;
    }

    @Override
    protected ButtonBlock generate() {
        if (this.isStone) {
            return new StoneButtonBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()).noCollission());
        }

        return new WoodButtonBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()).noCollission());
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 16);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 7));
        gen.button(this.generated, textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory")); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {
        gen.tag(net.minecraft.tags.ItemTags.BUTTONS).add(this.generated.asItem());
        if (!this.isStone) {
            gen.tag(net.minecraft.tags.ItemTags.WOODEN_BUTTONS).add(this.generated.asItem());
        }
    }

    @Override
    public void addBlockTags(BlockTags gen) {
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
