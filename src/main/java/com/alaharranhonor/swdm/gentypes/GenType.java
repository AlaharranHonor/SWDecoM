package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class GenType<T> implements Supplier<T> {

    protected final GenSet set;
    protected final Supplier<Block> baseBlock;
    protected T generated;
    protected String registeredName;

    public GenType(GenSet set, Supplier<Block> baseBlock) {
        this.set = set;
        this.baseBlock = baseBlock;
    }

    public T get() {
        if (this.generated == null) {
            this.generated = this.generate();
        }

        return this.generated;
    }

    protected BlockBehaviour.Properties props() {
        return this.set.applyCustomProperties(BlockBehaviour.Properties.copy(this.baseBlock.get()).mapColor(this.baseBlock.get().defaultMapColor()));
    }

    protected abstract T generate();
    public abstract boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items);
    public abstract void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder);
    public abstract void addBlockStates(BlockStateGen gen, TextureSet textures);
    public abstract void addItemModels(ItemModelGen gen, TextureSet textures);
    public abstract void addItemTags(ItemTagGen gen);
    public abstract void addBlockTags(BlockTagGen gen);
    public abstract void addLang(EnUsLanguageGen gen);
    public abstract void addLootTables(LootTableGen.BlockLoot gen);
    public abstract String getSuffix();
    public void setRenderType(RenderType renderType) {};
    public void setupClient() {}

    public void registerBlockColors(BlockColors reg, BlockColor color) {}
    public void registerItemColors(ItemColors reg, ItemColor color) {}

    public Stream<T> subTypes() {
        return Stream.of(this.generated);
    }

    public static ResourceLocation blockKey(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public static ResourceLocation itemKey(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
