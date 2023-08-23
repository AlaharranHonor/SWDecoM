package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.compat.SWLMCompat;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.datagen.BlockTagGen;
import com.alaharranhonor.swdm.datagen.ItemModelGen;
import com.alaharranhonor.swdm.datagen.ItemTagGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SandBlock;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SandGen extends BasicBlockGen<SandBlock> {

    public SandGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        if (super.register(name, blocks, items)) {
            SWLMCompat.addBlockName(ModRef.res(this.registeredName));
            return true;
        }

        return false;
    }

    @Override
    protected SandBlock generate() {
        return new SandBlock(14406560, this.props());
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        gen.simpleBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        gen.withExistingParent(blockKey(this.generated).getPath(), gen.modLoc("block/" + blockKey(this.generated).getPath()));
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {

    }

    @Override
    public String getSuffix() {
        return "";
    }
}
