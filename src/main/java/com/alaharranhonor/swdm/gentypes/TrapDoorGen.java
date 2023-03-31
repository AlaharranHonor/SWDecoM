package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class TrapDoorGen extends BasicBlockGen<TrapDoorBlock> {

    public TrapDoorGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected TrapDoorBlock generate() {
        return new TrapDoorBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()));
    }

    @Override
    public void addBlockStates(BlockStates gen) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation location = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), "block/" + path.substring(0, path.length() - 9));
        gen.trapdoorBlock(this.generated, location, true);
    }

    @Override
    public void addItemModels(ItemModels gen) {
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
