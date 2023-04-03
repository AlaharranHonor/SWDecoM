package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.util.TextureSet;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class PressurePlateGen extends BasicBlockGen<PressurePlateBlock> {

    private final PressurePlateBlock.Sensitivity sensitivity;

    public PressurePlateGen(Supplier<Block> baseBlock, PressurePlateBlock.Sensitivity sensitivity) {
        super(baseBlock);
        this.sensitivity = sensitivity;
    }

    @Override
    protected PressurePlateBlock generate() {
        return new PressurePlateBlock(this.sensitivity, BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()).noCollission());
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 15));
        gen.pressurePlate(this.generated, textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path)); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public String getSuffix() {
        return "_pressure_plate";
    }
}
