package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

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
            return new StoneButtonBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).noCollission());
        }

        return new WoodButtonBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).noCollission());
    }

    @Override
    public void addBlockStates(BlockStates gen) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation location = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), "block/" + path.substring(0, path.length() - 7));
        gen.button(this.generated, location);
    }

    @Override
    public void addItemModels(ItemModels gen) {
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
