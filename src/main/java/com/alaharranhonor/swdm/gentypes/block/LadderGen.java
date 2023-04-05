package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class LadderGen extends BasicBlockGen<LadderBlock> {

    public LadderGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected LadderBlock generate() {
        return new LadderBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()).noOcclusion());
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length() - 7));
        gen.ladder(this.generated, textures.get("ladder", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.generated.getRegistryName().getNamespace(), path.substring(0, path.length() - 7));
        gen.singleTexture(path, gen.mcLoc("item/generated"), "layer0", textures.get("ladder", basePath));
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.CLIMBABLE).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_ladder";
    }
}
