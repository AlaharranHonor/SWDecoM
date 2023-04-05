package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class DoorGen extends BasicBlockGen<DoorBlock> {

    public DoorGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected DoorBlock generate() {
        return new DoorBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).color(this.baseBlock.get().defaultMaterialColor()));
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.doorBlock(this.generated, textures.get("top", basePath), textures.get("bottom", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.withExistingParent(path, gen.modLoc("block/door_inventory")).texture("top", textures.get("top", basePath)).texture("bottom", textures.get("bottom", basePath)); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {

    }

    @Override
    public void addLootTables(LootTables.ModLootTables gen) {
        gen.add(this.generated, BlockLoot::createDoorTable);
    }

    @Override
    public String getSuffix() {
        return "_door";
    }
}
