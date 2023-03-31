package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class HalfWallGen extends BasicBlockGen<HalfWallBlock> {

    public HalfWallGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected HalfWallBlock generate() {
        return new HalfWallBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()));
    }

    @Override
    public void addBlockStates(BlockStates gen) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation location = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), "block/" + path.substring(0, path.length() - 5));
        gen.halfWallBlock(this.generated, location);
        gen.models().wallInventory(path + "_inventory", location);
    }

    @Override
    public void addItemModels(ItemModels gen) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory")); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.WALLS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_wall";
    }
}
