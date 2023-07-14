package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.WoodBoardBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Supplier;

public class WoodBoardGen extends BasicBlockGen<WoodBoardBlock> {

    private final WoodType wood;
    public WoodBoardGen(GenSet set, Supplier<Block> baseBlock, WoodType wood) {
        super(set, baseBlock);
        this.wood = wood;
    }

    @Override
    protected WoodBoardBlock generate() {
        return new WoodBoardBlock(this.props(), this.wood);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        ModelFile model = gen.models().withExistingParent(path, SWDM.res("block/sign")).texture("texture", textures.get("", basePath));
        //gen.simpleBlock(this.generated, model);
        gen.horizontalBlock(this.generated, model);
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
        return "";
    }
}
