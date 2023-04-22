package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DoorGen extends BasicBlockGen<DoorBlock> {

    public DoorGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected DoorBlock generate() {
        return new DoorBlock(this.props());
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 4);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.doorBlock(this.generated, textures.get("bottom", basePath), textures.get("top", basePath));
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
