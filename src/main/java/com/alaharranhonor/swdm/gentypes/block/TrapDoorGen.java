package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TrapDoorGen extends BasicBlockGen<TrapDoorBlock> {

    public TrapDoorGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected TrapDoorBlock generate() {
        return new TrapDoorBlock(this.props().noOcclusion());
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 8);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 9));
        gen.tintedTrapdoor(this.generated, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_bottom")); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.TRAPDOORS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_trapdoor";
    }
}
