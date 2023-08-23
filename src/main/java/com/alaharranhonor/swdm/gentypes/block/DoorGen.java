package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DoorGen extends BasicBlockGen<DoorBlock> {

    public DoorGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected DoorBlock generate() {
        return new DoorBlock(this.props().noOcclusion(), BlockSetType.OAK);
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 4);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 5));
        gen.doorBlock(this.generated, textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 5));
        gen.withExistingParent(path, gen.modLoc("block/door_inventory")).texture("top", textures.get("top", basePath)).texture("bottom", textures.get("bottom", basePath)); // Item model
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {

    }

    @Override
    public void addLootTables(LootTableGen.BlockLoot gen) {
        gen.add(this.generated, gen::createDoorTable);
    }

    @Override
    public String getSuffix() {
        return "_door";
    }
}
