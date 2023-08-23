package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MeterPointGen extends BasicBlockGen<Block> {

    public static final Map<Supplier<Block>, Supplier<Block>> METER_POINT_FLATTENABLES = new HashMap<>();

    public MeterPointGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        this.registeredName = name;
        blocks.register(name + this.getSuffix(), this);
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties()));
        METER_POINT_FLATTENABLES.put(this.baseBlock, this);
        return true;
    }

    @Override
    protected Block generate() {
        return new Block(this.props());
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        gen.simpleBlock(this.generated);
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        gen.withExistingParent(blockKey(this.generated).getPath(), gen.modLoc("block/" + blockKey(this.generated).getPath())); // Item model
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {

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
